package com.example.data

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.MediaPlayer
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale
import java.util.UUID

private const val VOICE_TAG = "MakinaVoicePlayer"

/**
 * Dual-engine voice player for Makina:
 * 1) Primary: Gemini 3.1 Flash TTS via Firebase AI Logic (natural pretty female voice "Aoede")
 * 2) Automatic Fallback: Tuned on-device Android TextToSpeech (warm female voice selection)
 */
class MakinaVoicePlayer(
    private val appContext: Context,
    private val companionService: MakinaCompanionService
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var playJob: Job? = null
    private var activeAudioTrack: AudioTrack? = null
    private var activeMediaPlayer: MediaPlayer? = null

    @Volatile
    private var ttsReady = false
    private var textToSpeech: TextToSpeech? = null
    private var pendingFallbackText: String? = null

    private val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
    private var audioFocusRequest: AudioFocusRequest? = null

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    private val _autoSpeakEnabled = MutableStateFlow(true)
    val autoSpeakEnabled: StateFlow<Boolean> = _autoSpeakEnabled.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _isLoadingVoice = MutableStateFlow(false)
    val isLoadingVoice: StateFlow<Boolean> = _isLoadingVoice.asStateFlow()

    private val _voiceEngineLabel = MutableStateFlow("Gemini Voice")
    val voiceEngineLabel: StateFlow<String> = _voiceEngineLabel.asStateFlow()

    init {
        initOnDeviceTts()
    }

    private fun initOnDeviceTts() {
        try {
            textToSpeech = TextToSpeech(appContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    ttsReady = true
                    configurePrettyFemaleTtsVoice()
                    pendingFallbackText?.let { queued ->
                        pendingFallbackText = null
                        if (!_isMuted.value) {
                            speakWithDeviceTts(queued)
                        }
                    }
                } else {
                    Log.w(VOICE_TAG, "Android TTS init returned status $status")
                }
            }
        } catch (e: Exception) {
            Log.w(VOICE_TAG, "Unable to initialize Android TTS fallback", e)
        }
    }

    private fun configurePrettyFemaleTtsVoice() {
        val tts = textToSpeech ?: return
        try {
            tts.language = Locale.US
            val voices = tts.voices?.filter { v ->
                !v.isNetworkConnectionRequired &&
                    v.locale.language.equals("en", ignoreCase = true)
            }.orEmpty()

            // Prefer expressive female English voice profiles when installed on device
            val preferredFemale = voices.firstOrNull { v ->
                val n = v.name.lowercase(Locale.US)
                n.contains("sfg") || n.contains("tpf") || n.contains("iol") ||
                    n.contains("female") || n.contains("en-us-x-sfg") ||
                    n.contains("en-us-x-tpc") || n.contains("en-gb-x-gba") ||
                    n.contains("en-in-x-enc") || n.contains("en-in-x-ene")
            } ?: voices.firstOrNull()

            if (preferredFemale != null) {
                tts.voice = preferredFemale
            }
            // Gently brighter pitch & smooth cadence for a pretty young woman tone
            tts.setPitch(1.14f)
            tts.setSpeechRate(0.98f)
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                }

                @Deprecated("Deprecated in Android API")
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                }
            })
        } catch (e: Exception) {
            Log.w(VOICE_TAG, "TTS voice tuning warning", e)
        }
    }

    fun toggleMute() {
        val nextMuted = !_isMuted.value
        _isMuted.value = nextMuted
        if (nextMuted) {
            stop()
            _voiceEngineLabel.value = "Muted"
        } else {
            _voiceEngineLabel.value = if (_autoSpeakEnabled.value) "Gemini Voice" else "Tap Replay"
        }
    }

    fun toggleAutoSpeak() {
        val next = !_autoSpeakEnabled.value
        _autoSpeakEnabled.value = next
        if (!next) {
            stop()
            if (!_isMuted.value) _voiceEngineLabel.value = "Silent Mode"
        } else if (!_isMuted.value) {
            _voiceEngineLabel.value = "Gemini Voice"
        }
    }

    fun speak(rawText: String, preferOffline: Boolean = false, forceSpeak: Boolean = false) {
        if (_isMuted.value) return
        if (!forceSpeak && !_autoSpeakEnabled.value) return
        val cleanText = MakinaCompanionService.sanitizeForSpeech(rawText)
        if (cleanText.isBlank()) return

        stop()

        playJob = scope.launch {
            if (!preferOffline) {
                _isLoadingVoice.value = true
                _voiceEngineLabel.value = "Gemini Voice"
                val audioPayload = companionService.synthesizeSpeech(cleanText)
                _isLoadingVoice.value = false

                if (!isActive || _isMuted.value) return@launch

                if (audioPayload != null) {
                    val played = playGeminiAudio(audioPayload)
                    if (played) return@launch
                }
            }

            if (!isActive || _isMuted.value) return@launch
            _voiceEngineLabel.value = "Device Voice"
            speakWithDeviceTts(cleanText)
        }
    }

    private suspend fun playGeminiAudio(payload: MakinaAudioPayload): Boolean = withContext(Dispatchers.IO) {
        val mime = payload.mimeType.lowercase(Locale.US)
        try {
            if (mime.contains("pcm") || mime.contains("l16")) {
                val sampleRate = Regex("rate=(\\d+)").find(mime)?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 24000
                val minBuf = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                ).coerceAtLeast(4096)

                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANT)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(minBuf)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                activeAudioTrack = track
                requestTransientAudioFocus()
                _isSpeaking.value = true
                track.play()

                var offset = 0
                val chunkSize = 4096
                while (offset < payload.audioBytes.size && isActive && !_isMuted.value) {
                    val toWrite = minOf(chunkSize, payload.audioBytes.size - offset)
                    val written = track.write(payload.audioBytes, offset, toWrite)
                    if (written <= 0) break
                    offset += written
                }

                // Wait for buffered PCM frames to drain through the DAC so the final syllable isn't clipped
                val totalFrames = offset / 2 // 16-bit mono = 2 bytes per frame
                if (isActive && !_isMuted.value && totalFrames > 0) {
                    val maxDrainMs = ((totalFrames * 1000L) / sampleRate).coerceIn(250L, 6000L) + 250L
                    val drainStart = System.currentTimeMillis()
                    while (isActive && !_isMuted.value) {
                        val head = try { track.playbackHeadPosition } catch (_: Exception) { totalFrames }
                        if (head >= totalFrames || System.currentTimeMillis() - drainStart >= maxDrainMs) break
                        delay(45L)
                    }
                }

                try {
                    track.stop()
                } catch (_: Exception) {
                }
                track.release()
                if (activeAudioTrack === track) activeAudioTrack = null
                abandonAudioFocus()
                _isSpeaking.value = false
                true
            } else {
                val ext = when {
                    mime.contains("wav") -> "wav"
                    mime.contains("mpeg") || mime.contains("mp3") -> "mp3"
                    mime.contains("ogg") -> "ogg"
                    else -> "audio"
                }
                val tempFile = File(appContext.cacheDir, "makina_voice_${System.currentTimeMillis()}.$ext")
                tempFile.writeBytes(payload.audioBytes)

                val player = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANT)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .build()
                    )
                    setDataSource(tempFile.absolutePath)
                    prepare()
                    setOnCompletionListener { mp ->
                        _isSpeaking.value = false
                        mp.release()
                        if (activeMediaPlayer === mp) activeMediaPlayer = null
                        abandonAudioFocus()
                        tempFile.delete()
                    }
                    setOnErrorListener { mp, _, _ ->
                        _isSpeaking.value = false
                        mp.release()
                        if (activeMediaPlayer === mp) activeMediaPlayer = null
                        abandonAudioFocus()
                        tempFile.delete()
                        true
                    }
                }
                activeMediaPlayer = player
                requestTransientAudioFocus()
                _isSpeaking.value = true
                player.start()
                true
            }
        } catch (e: CancellationException) {
            stopAudioHardware()
            throw e
        } catch (e: Exception) {
            Log.w(VOICE_TAG, "Gemini audio playback error: ${e.localizedMessage}")
            stopAudioHardware()
            false
        }
    }

    private fun speakWithDeviceTts(cleanText: String) {
        val tts = textToSpeech
        if (tts == null || !ttsReady) {
            pendingFallbackText = cleanText
            return
        }
        try {
            tts.speak(cleanText, TextToSpeech.QUEUE_FLUSH, null, "makina_${UUID.randomUUID()}")
        } catch (e: Exception) {
            Log.w(VOICE_TAG, "Device TTS speak failed", e)
            _isSpeaking.value = false
        }
    }

    fun stop() {
        playJob?.cancel()
        playJob = null
        _isLoadingVoice.value = false
        stopAudioHardware()
        try {
            textToSpeech?.stop()
        } catch (_: Exception) {
        }
        _isSpeaking.value = false
    }

    private fun stopAudioHardware() {
        try {
            activeAudioTrack?.let { track ->
                if (track.playState == AudioTrack.PLAYSTATE_PLAYING) {
                    track.stop()
                }
                track.release()
            }
        } catch (_: Exception) {
        }
        activeAudioTrack = null

        try {
            activeMediaPlayer?.let { mp ->
                if (mp.isPlaying) mp.stop()
                mp.release()
            }
        } catch (_: Exception) {
        }
        activeMediaPlayer = null
        abandonAudioFocus()
    }

    private fun requestTransientAudioFocus() {
        val am = audioManager ?: return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val req = audioFocusRequest ?: AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANT)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .build()
                    )
                    .build()
                    .also { audioFocusRequest = it }
                am.requestAudioFocus(req)
            } else {
                @Suppress("DEPRECATION")
                am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            }
        } catch (_: Exception) {
        }
    }

    private fun abandonAudioFocus() {
        val am = audioManager ?: return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                audioFocusRequest?.let { am.abandonAudioFocusRequest(it) }
            } else {
                @Suppress("DEPRECATION")
                am.abandonAudioFocus(null)
            }
        } catch (_: Exception) {
        }
    }

    fun release() {
        stop()
        try {
            textToSpeech?.shutdown()
        } catch (_: Exception) {
        }
        textToSpeech = null
        scope.cancel()
    }
}
