package com.example.data

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Lightweight 16 kHz mono PCM-to-WAV microphone recorder used as fallback
 * when device SpeechRecognizer service is unavailable, sending spoken audio directly to Gemini.
 */
class VoiceQuestionRecorder {
    private var audioRecord: AudioRecord? = null
    private var captureJob: Job? = null
    private val pcmBuffer = ByteArrayOutputStream()

    @Volatile
    var isRecording: Boolean = false
        private set

    @SuppressLint("MissingPermission")
    fun startRecording(
        scope: CoroutineScope,
        onError: (String) -> Unit
    ): Boolean {
        if (isRecording) return true
        val sampleRate = 16000
        val minBuf = AudioRecord.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ).coerceAtLeast(4096)

        val recorder = try {
            AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minBuf * 2
            )
        } catch (e: Exception) {
            onError("Could not access microphone: ${e.localizedMessage ?: "Permission denied"}")
            return false
        }

        if (recorder.state != AudioRecord.STATE_INITIALIZED) {
            recorder.release()
            onError("Microphone hardware unavailable on this device.")
            return false
        }

        synchronized(pcmBuffer) { pcmBuffer.reset() }
        audioRecord = recorder
        isRecording = true

        try {
            recorder.startRecording()
        } catch (e: Exception) {
            isRecording = false
            recorder.release()
            audioRecord = null
            onError("Failed to start microphone capture: ${e.localizedMessage}")
            return false
        }

        val maxBytes = sampleRate * 2 * 20 // Max 20 seconds mono 16-bit PCM
        captureJob = scope.launch(Dispatchers.IO) {
            val temp = ByteArray(minBuf)
            while (isActive && isRecording) {
                val read = try {
                    recorder.read(temp, 0, temp.size)
                } catch (_: Exception) {
                    -1
                }
                if (read > 0) {
                    synchronized(pcmBuffer) {
                        if (pcmBuffer.size() < maxBytes) {
                            pcmBuffer.write(temp, 0, read)
                        }
                    }
                } else if (read < 0) {
                    break
                }
            }
        }
        return true
    }

    suspend fun stopAndEncodeWav(): ByteArray? = withContext(Dispatchers.IO) {
        isRecording = false
        try {
            captureJob?.cancel()
            audioRecord?.stop()
        } catch (_: Exception) {
        }
        try {
            audioRecord?.release()
        } catch (_: Exception) {
        }
        audioRecord = null

        val rawPcm = synchronized(pcmBuffer) {
            val bytes = pcmBuffer.toByteArray()
            pcmBuffer.reset()
            bytes
        }
        if (rawPcm.size < 3200) return@withContext null // Ignore ultra-short (<0.1s) taps
        encodePcmToWavBytes(rawPcm, sampleRate = 16000)
    }

    fun release() {
        isRecording = false
        captureJob?.cancel()
        try {
            audioRecord?.stop()
        } catch (_: Exception) {
        }
        try {
            audioRecord?.release()
        } catch (_: Exception) {
        }
        audioRecord = null
        synchronized(pcmBuffer) { pcmBuffer.reset() }
    }

    companion object {
        fun encodePcmToWavBytes(pcmData: ByteArray, sampleRate: Int = 16000): ByteArray {
            val totalAudioLen = pcmData.size
            val totalDataLen = totalAudioLen + 36
            val channels = 1
            val bitsPerSample = 16
            val byteRate = sampleRate * channels * bitsPerSample / 8
            val blockAlign = channels * bitsPerSample / 8

            val header = ByteBuffer.allocate(44).order(ByteOrder.LITTLE_ENDIAN).apply {
                put("RIFF".toByteArray(Charsets.US_ASCII))
                putInt(totalDataLen)
                put("WAVE".toByteArray(Charsets.US_ASCII))
                put("fmt ".toByteArray(Charsets.US_ASCII))
                putInt(16) // PCM chunk size
                putShort(1) // PCM format
                putShort(channels.toShort())
                putInt(sampleRate)
                putInt(byteRate)
                putShort(blockAlign.toShort())
                putShort(bitsPerSample.toShort())
                put("data".toByteArray(Charsets.US_ASCII))
                putInt(totalAudioLen)
            }.array()

            return ByteArray(header.size + pcmData.size).also { out ->
                System.arraycopy(header, 0, out, 0, header.size)
                System.arraycopy(pcmData, 0, out, header.size, pcmData.size)
            }
        }
    }
}
