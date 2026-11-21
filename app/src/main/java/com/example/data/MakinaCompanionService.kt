package com.example.data

import android.util.Log
import com.example.model.QuestionResultItem
import com.example.model.TestReport
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.InlineDataPart
import com.google.firebase.ai.type.PublicPreviewAPI
import com.google.firebase.ai.type.ResponseModality
import com.google.firebase.ai.type.SpeechConfig
import com.google.firebase.ai.type.Voice
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

private const val MAKINA_TAG = "MakinaCompanion"

data class MakinaAudioPayload(
    val audioBytes: ByteArray,
    val mimeType: String
)

/**
 * Firebase AI Logic backend for Makina — StudyApp's playful, charmingly confident
 * anime study companion powered by Gemini 3.8 Flash (text) and Gemini 3.1 Flash TTS (voice).
 */
@OptIn(PublicPreviewAPI::class)
class MakinaCompanionService(
    private val personaManager: PersonaManager = PersonaManager()
) {

    private fun buildTextModel(personaContext: PersonaContext) = Firebase.ai.generativeModel(
        modelName = "gemini-3.8-flash",
        systemInstruction = content { text(personaManager.systemPromptFor(personaContext)) },
        generationConfig = generationConfig {
            temperature = 0.85f
            maxOutputTokens = 700
        }
    )

    private val greetingModel by lazy { buildTextModel(PersonaContext.ARRIVAL_GREETING) }
    private val testExplanationModel by lazy { buildTextModel(PersonaContext.TEST_RESULT_EXPLANATION) }
    private val questionExplanationModel by lazy { buildTextModel(PersonaContext.QUESTION_EXPLANATION) }
    private val followUpModel by lazy { buildTextModel(PersonaContext.FOLLOW_UP_DOUBT) }

    private val ttsModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.1-flash-tts-preview",
            generationConfig = generationConfig {
                responseModalities = listOf(ResponseModality.AUDIO)
                speechConfig = SpeechConfig(voice = Voice(personaManager.voiceName))
            }
        )
    }

    suspend fun generateArrivalGreeting(
        studentName: String,
        streakDays: Long,
        xp: Long,
        completedTopics: Int,
        totalTopics: Int,
        testsTaken: Int
    ): Result<String> = runCatching {
        val safeName = studentName.substringBefore(" ").ifBlank { "Scholar" }
        val prompt = personaManager.buildArrivalGreetingPrompt(
            studentName = studentName,
            streakDays = streakDays,
            xp = xp,
            completedTopics = completedTopics,
            totalTopics = totalTopics,
            testsTaken = testsTaken
        )

        val response = withTimeout(30_000L) {
            greetingModel.generateContent(prompt)
        }
        response.text?.trim()?.takeIf { it.isNotBlank() }
            ?: personaManager.fallbackGreeting(safeName, streakDays, testsTaken)
    }.onFailure { err ->
        if (err is CancellationException) throw err
        Log.e(MAKINA_TAG, "Arrival greeting generation failed", err)
    }

    fun fallbackGreeting(
        studentName: String,
        streakDays: Long,
        testsTaken: Int
    ): String = personaManager.fallbackGreeting(studentName, streakDays, testsTaken)

    suspend fun explainTestResult(report: TestReport): Result<String> = runCatching {
        val prompt = personaManager.buildTestExplanationPrompt(report)

        val response = withTimeout(45_000L) {
            testExplanationModel.generateContent(prompt)
        }
        response.text?.trim()?.takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("${personaManager.personaName} returned an empty explanation.")
    }.onFailure { err ->
        if (err is CancellationException) throw err
        Log.e(MAKINA_TAG, "Test result explanation failed", err)
    }

    suspend fun explainSpecificQuestion(
        item: QuestionResultItem,
        subjectTitle: String,
        unitTitle: String
    ): Result<String> = runCatching {
        val prompt = personaManager.buildQuestionExplanationPrompt(item, subjectTitle, unitTitle)

        val response = withTimeout(45_000L) {
            questionExplanationModel.generateContent(prompt)
        }
        response.text?.trim()?.takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("${personaManager.personaName} returned an empty question explanation.")
    }.onFailure { err ->
        if (err is CancellationException) throw err
        Log.e(MAKINA_TAG, "Question explanation failed", err)
    }

    suspend fun answerFollowUpDoubt(
        contextSummary: String,
        conversationHistory: List<Pair<String, String>>,
        studentDoubt: String
    ): Result<String> = runCatching {
        val prompt = personaManager.buildFollowUpPrompt(
            contextSummary = contextSummary,
            conversationHistory = conversationHistory,
            studentDoubt = studentDoubt
        )

        val response = withTimeout(45_000L) {
            followUpModel.generateContent(prompt)
        }
        response.text?.trim()?.takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("${personaManager.personaName} returned an empty follow-up response.")
    }.onFailure { err ->
        if (err is CancellationException) throw err
        Log.e(MAKINA_TAG, "Follow-up doubt failed", err)
    }

    suspend fun answerVoiceAudioDoubt(
        audioBytes: ByteArray,
        mimeType: String,
        contextSummary: String,
        conversationHistory: List<Pair<String, String>>
    ): Result<String> = runCatching {
        val prompt = personaManager.buildVoiceAudioDoubtPrompt(
            contextSummary = contextSummary,
            conversationHistory = conversationHistory
        )
        val inputContent = content {
            inlineData(audioBytes, mimeType)
            text(prompt)
        }
        val response = withTimeout(45_000L) {
            followUpModel.generateContent(inputContent)
        }
        response.text?.trim()?.takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("${personaManager.personaName} couldn't hear that clearly — please try again.")
    }.onFailure { err ->
        if (err is CancellationException) throw err
        Log.e(MAKINA_TAG, "Voice audio doubt failed", err)
    }

    /**
     * Generates expressive female speech audio using Gemini 3.1 Flash TTS via Firebase AI Logic.
     * Returns null if TTS fails or times out so the caller smoothly falls back to on-device TTS.
     */
    suspend fun synthesizeSpeech(rawText: String): MakinaAudioPayload? {
        val spoken = sanitizeForSpeech(rawText)
        if (spoken.isBlank()) return null
        return try {
            val response = withTimeout(25_000L) {
                ttsModel.generateContent(
                    personaManager.ttsDeliveryDirective(spoken)
                )
            }
            val audioPart = response.candidates
                .firstOrNull()
                ?.content
                ?.parts
                ?.filterIsInstance<InlineDataPart>()
                ?.firstOrNull { it.mimeType.startsWith("audio/", ignoreCase = true) }

            if (audioPart != null && audioPart.inlineData.isNotEmpty()) {
                MakinaAudioPayload(
                    audioBytes = audioPart.inlineData,
                    mimeType = audioPart.mimeType
                )
            } else {
                Log.w(MAKINA_TAG, "Gemini TTS returned no audio part; falling back to on-device TTS")
                null
            }
        } catch (e: TimeoutCancellationException) {
            Log.w(MAKINA_TAG, "Gemini TTS timed out; falling back to on-device TTS")
            null
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(MAKINA_TAG, "Gemini TTS unavailable (${e.localizedMessage}); falling back to on-device TTS")
            null
        }
    }

    companion object {
        fun sanitizeForSpeech(text: String): String {
            return text
                .replace(Regex("\\*\\*([^*]+)\\*\\*"), "$1")
                .replace(Regex("`([^`]+)`"), "$1")
                .replace(Regex("#{1,6}\\s*"), "")
                .replace("•", ",")
                .replace("~", "")
                .replace(Regex("\\s+"), " ")
                .trim()
                .take(750)
        }
    }
}
