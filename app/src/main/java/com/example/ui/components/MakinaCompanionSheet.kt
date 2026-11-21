package com.example.ui.components

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.example.R
import com.example.data.VoiceQuestionRecorder
import kotlinx.coroutines.launch
import com.example.ui.theme.AmberImprove
import com.example.ui.theme.EmeraldMastery
import com.example.ui.theme.FlameCore
import com.example.ui.theme.GlassCardSurface
import com.example.ui.theme.MidnightSurface
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonBlueBright
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.RoseWeak
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

enum class MakinaMode {
    GREETING_AND_TOUR,
    REPORT_EXPLAINER,
    QUESTION_EXPLAINER
}

data class MakinaMessage(
    val isMakina: Boolean,
    val text: String
)

data class MakinaCompanionUiState(
    val isVisible: Boolean = false,
    val mode: MakinaMode = MakinaMode.GREETING_AND_TOUR,
    val title: String = "Makina • AI Study Companion",
    val subtitle: String = "Your personal BEU study guide",
    val contextLabel: String = "",
    val contextSummary: String = "",
    val messages: List<MakinaMessage> = emptyList(),
    val isLoading: Boolean = false,
    val errorText: String? = null
)

private data class AppTourStep(
    val title: String,
    val highlight: String,
    val voiceLine: String,
    val icon: ImageVector,
    val accent: Color,
    val targetTabIndex: Int?, // null = Developer screen
    val ctaLabel: String
)

private val makinaTourSteps = listOf(
    AppTourStep(
        title = "1. Syllabus Mastery Tracker",
        highlight = "Track every BEU CSE unit across all 6 theory subjects & 3 labs. Tick off topics, watch your progress surge, or import custom syllabus PDFs.",
        voiceLine = "First up, your Syllabus tracker. Check off each unit as you conquer it — I'll be watching that completion bar climb.",
        icon = Icons.AutoMirrored.Filled.MenuBook,
        accent = NeonBlueBright,
        targetTabIndex = 1,
        ctaLabel = "Open Syllabus"
    ),
    AppTourStep(
        title = "2. Smart Tasks & Timetable",
        highlight = "Organize daily lectures, lab slots, and study targets. Turn weak syllabus topics into instant to-do items with one tap.",
        voiceLine = "Next is your Tasks and Timetable hub. Keep your schedule sharp so you never scramble the night before a lab.",
        icon = Icons.Filled.TaskAlt,
        accent = EmeraldMastery,
        targetTabIndex = 2,
        ctaLabel = "Open Tasks"
    ),
    AppTourStep(
        title = "3. Test Lab (2,561 Built-in Qs)",
        highlight = "Practice unit-wise MCQs, 5M/14M subjective papers, and all 973 BEEE, Physics & PPS Lab Vivas! After any test, tap Explain with AI on tricky questions and I'll walk you through them.",
        voiceLine = "Here's my favorite part — Test Lab with over two thousand five hundred built-in exam and lab viva questions. Finish a test, and tap Explain with AI on any question that gives you trouble.",
        icon = Icons.Filled.Quiz,
        accent = FlameCore,
        targetTabIndex = 3,
        ctaLabel = "Jump to Test Lab"
    ),
    AppTourStep(
        title = "4. Ranks, Streaks & Developer",
        highlight = "Climb the scholar leaderboard, unlock mastery badges, enable daily streak alerts, and meet StudyApp's creator Ankush Kumar anytime via the top </> button.",
        voiceLine = "Finally, climb the leaderboard, protect your streak flame, and check out the Developer page to meet Ankush Kumar. Ready to impress me?",
        icon = Icons.Filled.EmojiEvents,
        accent = AmberImprove,
        targetTabIndex = 4,
        ctaLabel = "View Ranks"
    )
)

private val MakinaGold = Color(0xFFFFC95C)
private val MakinaCoral = Color(0xFFFF6B81)

/**
 * Compact floating Makina avatar pill docked cleanly at bottom-end so the student can
 * summon her for the feature tour, greeting, or quick study help anytime.
 */
@Composable
fun MakinaFloatingAvatarButton(
    isSpeaking: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "makina_fab_pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isSpeaking) 1.09f else 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isSpeaking) 650 else 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "makina_scale"
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(999.dp),
        color = MidnightSurface.copy(alpha = 0.94f),
        border = BorderStroke(
            width = 1.6.dp,
            brush = Brush.horizontalGradient(listOf(MakinaCoral, MakinaGold, NeonBlueBright))
        ),
        shadowElevation = 8.dp,
        modifier = modifier
            .scale(pulse)
            .testTag("makina_floating_button")
    ) {
        Row(
            modifier = Modifier.padding(start = 5.dp, end = 12.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MakinaGold, CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_makina_avatar),
                    contentDescription = "Summon Makina AI Companion",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = "Makina AI",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isSpeaking) "Speaking..." else "Guide & Help",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSpeaking) MakinaGold else NeonBlueBright
                )
            }
        }
    }
}

/**
 * Full interactive Makina AI Companion overlay supporting:
 * - Playful arrival greeting + 4-step interactive App Feature Tour
 * - Complete Test Result breakdown
 * - On-demand per-question "Explain with AI" walkthrough + multi-turn follow-up doubts
 * - Gemini AI pretty female voice with automatic offline TTS fallback & Mute/Replay controls
 */
@Composable
fun MakinaCompanionModal(
    state: MakinaCompanionUiState,
    isSpeaking: Boolean,
    isLoadingVoice: Boolean,
    isMuted: Boolean,
    isAutoSpeakEnabled: Boolean = true,
    voiceEngineLabel: String,
    onSpeakText: (String) -> Unit,
    onToggleMute: () -> Unit,
    onToggleAutoSpeak: () -> Unit = {},
    onResetChat: () -> Unit = {},
    onStopVoice: () -> Unit = {},
    onSendFollowUp: (String) -> Unit,
    onSendVoiceAudio: (ByteArray, String) -> Unit = { _, _ -> },
    onNavigateToTab: (Int) -> Unit,
    onOpenDeveloper: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!state.isVisible) return

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var followUpInput by remember { mutableStateOf("") }
    var tourStepIndex by remember(state.mode) { mutableIntStateOf(0) }
    var showTourCard by remember(state.mode) { mutableStateOf(state.mode == MakinaMode.GREETING_AND_TOUR) }

    var isListeningVoice by remember { mutableStateOf(false) }
    var isRawAudioRecording by remember { mutableStateOf(false) }
    var voiceStatusBanner by remember { mutableStateOf<String?>(null) }
    val voiceRecorder = remember { VoiceQuestionRecorder() }
    var speechRecognizer by remember { mutableStateOf<SpeechRecognizer?>(null) }

    DisposableEffect(state.isVisible) {
        onDispose {
            isListeningVoice = false
            isRawAudioRecording = false
            voiceRecorder.release()
            speechRecognizer?.destroy()
            speechRecognizer = null
        }
    }

    fun startRawAudioFallback() {
        val started = voiceRecorder.startRecording(scope) { err ->
            isListeningVoice = false
            isRawAudioRecording = false
            voiceStatusBanner = err
        }
        if (started) {
            isListeningVoice = true
            isRawAudioRecording = true
            voiceStatusBanner = "Recording voice note... Tap Mic again to send to Makina"
        }
    }

    fun startVoiceRecognition() {
        onStopVoice()
        voiceStatusBanner = null
        val canRecognize = try {
            SpeechRecognizer.isRecognitionAvailable(context)
        } catch (_: Exception) {
            false
        }
        if (!canRecognize) {
            startRawAudioFallback()
            return
        }

        try {
            speechRecognizer?.destroy()
            val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer = recognizer
            recognizer.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    isListeningVoice = true
                    isRawAudioRecording = false
                    voiceStatusBanner = "Listening... Ask Makina your question"
                }

                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {
                    voiceStatusBanner = "Processing speech..."
                }

                override fun onError(error: Int) {
                    isListeningVoice = false
                    val shouldFallbackToAudio = error == SpeechRecognizer.ERROR_CLIENT ||
                        error == SpeechRecognizer.ERROR_NETWORK ||
                        error == SpeechRecognizer.ERROR_SERVER
                    if (shouldFallbackToAudio) {
                        startRawAudioFallback()
                    } else {
                        voiceStatusBanner = when (error) {
                            SpeechRecognizer.ERROR_AUDIO -> "Microphone audio error. Try again or type below."
                            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Microphone permission required."
                            SpeechRecognizer.ERROR_NO_MATCH, SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                                "Didn't catch that — tap Mic and speak clearly."
                            else -> "Voice input stopped (code $error). Try again."
                        }
                    }
                }

                override fun onResults(results: Bundle?) {
                    isListeningVoice = false
                    voiceStatusBanner = null
                    val spoken = results
                        ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        ?.firstOrNull()
                        ?.trim()
                        .orEmpty()
                    if (spoken.isNotBlank()) {
                        followUpInput = ""
                        onSendFollowUp(spoken)
                    } else {
                        voiceStatusBanner = "Didn't catch any words — tap Mic to try again."
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {
                    val partial = partialResults
                        ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        ?.firstOrNull()
                        ?.trim()
                        .orEmpty()
                    if (partial.isNotBlank()) {
                        followUpInput = partial
                        voiceStatusBanner = "Heard: \"$partial\""
                    }
                }

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, java.util.Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            }
            isListeningVoice = true
            voiceStatusBanner = "Starting microphone..."
            recognizer.startListening(intent)
        } catch (_: Exception) {
            startRawAudioFallback()
        }
    }

    val micPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            startVoiceRecognition()
        } else {
            voiceStatusBanner = "Microphone permission is needed to talk to Makina verbally."
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "makina_speaking_ring")
    val ringScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isSpeaking) 1.08f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(550, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ring_scale"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            // Scrim backdrop as sibling so inner controls keep full TalkBack & click semantics
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.65f))
                    .clickable(onClick = onDismiss)
            )
            Surface(
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                color = MidnightSurface,
                border = BorderStroke(
                    width = 1.4.dp,
                    brush = Brush.verticalGradient(
                        listOf(MakinaGold.copy(alpha = 0.8f), NeonBlue.copy(alpha = 0.5f))
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 680.dp)
                    .navigationBarsPadding()
                    .imePadding()
                    .testTag("makina_companion_modal")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    // Header: Makina Portrait + Name + Voice Controls + Close
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(62.dp)
                                .scale(ringScale)
                                .clip(CircleShape)
                                .border(
                                    width = 2.2.dp,
                                    brush = Brush.linearGradient(listOf(MakinaCoral, MakinaGold, NeonBlueBright)),
                                    shape = CircleShape
                                )
                                .padding(2.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_makina_avatar),
                                contentDescription = "Makina AI Portrait",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = state.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Text(
                                text = state.subtitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = MakinaGold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(3.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.GraphicEq,
                                    contentDescription = null,
                                    tint = when {
                                        isMuted -> TextSecondary
                                        isSpeaking -> MakinaCoral
                                        isLoadingVoice -> MakinaGold
                                        else -> NeonBlueBright
                                    },
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = when {
                                        isMuted -> "Voice Muted"
                                        isLoadingVoice -> "Preparing voice..."
                                        isSpeaking -> "Speaking ($voiceEngineLabel)"
                                        else -> "$voiceEngineLabel Ready"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSpeaking) MakinaCoral else TextSecondary
                                )
                            }
                        }

                        // Replay latest Makina reply
                        val lastMakinaText = state.messages.lastOrNull { it.isMakina }?.text
                        IconButton(
                            onClick = {
                                if (!lastMakinaText.isNullOrBlank()) {
                                    if (isMuted) onToggleMute()
                                    onSpeakText(lastMakinaText)
                                }
                            },
                            enabled = !lastMakinaText.isNullOrBlank(),
                            modifier = Modifier.testTag("makina_replay_voice_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Replay,
                                contentDescription = "Replay Makina voice",
                                tint = NeonBlueBright
                            )
                        }

                        // Mute / Unmute voice
                        IconButton(
                            onClick = onToggleMute,
                            modifier = Modifier.testTag("makina_mute_voice_button")
                        ) {
                            Icon(
                                imageVector = if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = if (isMuted) "Unmute Makina" else "Mute Makina",
                                tint = if (isMuted) RoseWeak else MakinaGold
                            )
                        }

                        // Close modal
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.testTag("makina_close_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close Makina",
                                tint = TextSecondary
                            )
                        }
                    }

                    // Quick Voice & Chat Utility Bar (Auto-Voice toggle + New/Reset Chat)
                    Spacer(Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            onClick = onToggleAutoSpeak,
                            shape = RoundedCornerShape(999.dp),
                            color = if (isAutoSpeakEnabled && !isMuted) MakinaGold.copy(alpha = 0.16f) else ObsidianBg,
                            border = BorderStroke(
                                1.dp,
                                if (isAutoSpeakEnabled && !isMuted) MakinaGold.copy(alpha = 0.7f) else SubtleBorder
                            ),
                            modifier = Modifier.testTag("makina_auto_speak_chip")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.GraphicEq,
                                    contentDescription = null,
                                    tint = if (isAutoSpeakEnabled && !isMuted) MakinaGold else TextSecondary,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(Modifier.width(5.dp))
                                Text(
                                    text = if (isAutoSpeakEnabled) "Auto-Voice: ON" else "Auto-Voice: OFF",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isAutoSpeakEnabled && !isMuted) MakinaGold else TextSecondary
                                )
                            }
                        }

                        Surface(
                            onClick = onResetChat,
                            shape = RoundedCornerShape(999.dp),
                            color = NeonBlue.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.45f)),
                            modifier = Modifier.testTag("makina_reset_chat_chip")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = "Reset Makina conversation",
                                    tint = NeonBlueBright,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(Modifier.width(5.dp))
                                Text(
                                    text = "Reset Chat",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = NeonBlueBright
                                )
                            }
                        }
                    }

                    // Context badge (e.g., Specific Question or Test Result Title)
                    if (state.contextLabel.isNotBlank()) {
                        Spacer(Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ObsidianBg.copy(alpha = 0.85f),
                            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AutoAwesome,
                                    contentDescription = null,
                                    tint = MakinaGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = state.contextLabel,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = NeonBlueBright,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    // Scrollable Conversation + Optional Interactive Feature Tour
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = false)
                            .testTag("makina_modal_list"),
                        contentPadding = PaddingValues(vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.messages) { msg ->
                            val bubbleColor = if (msg.isMakina) {
                                GlassCardSurface.copy(alpha = 0.92f)
                            } else {
                                NeonBlue.copy(alpha = 0.18f)
                            }
                            val borderColor = if (msg.isMakina) {
                                MakinaGold.copy(alpha = 0.4f)
                            } else {
                                NeonBlue.copy(alpha = 0.65f)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (msg.isMakina) Arrangement.Start else Arrangement.End
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (msg.isMakina) 4.dp else 16.dp,
                                        bottomEnd = if (msg.isMakina) 16.dp else 4.dp
                                    ),
                                    color = bubbleColor,
                                    border = BorderStroke(1.dp, borderColor),
                                    modifier = Modifier.fillMaxWidth(0.94f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = if (msg.isMakina) "Makina" else "You",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (msg.isMakina) MakinaGold else NeonBlueBright,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            text = msg.text,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextPrimary
                                        )
                                    }
                                }
                            }
                        }

                        if (state.isLoading) {
                            item {
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = ObsidianBg.copy(alpha = 0.8f),
                                    border = BorderStroke(1.dp, MakinaGold.copy(alpha = 0.45f))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(18.dp),
                                            color = MakinaGold,
                                            strokeWidth = 2.dp
                                        )
                                        Spacer(Modifier.width(10.dp))
                                        Text(
                                            text = "Makina is thinking...",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MakinaGold
                                        )
                                    }
                                }
                            }
                        }

                        if (!state.errorText.isNullOrBlank()) {
                            item {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = RoseWeak.copy(alpha = 0.16f),
                                    border = BorderStroke(1.dp, RoseWeak.copy(alpha = 0.6f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = state.errorText,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFFFB4C2),
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                        }

                        // Interactive App Feature Tour (when in Greeting / Tour mode)
                        if (showTourCard && state.mode == MakinaMode.GREETING_AND_TOUR) {
                            item {
                                val step = makinaTourSteps[tourStepIndex]
                                Surface(
                                    shape = RoundedCornerShape(18.dp),
                                    color = ObsidianBg.copy(alpha = 0.9f),
                                    border = BorderStroke(1.2.dp, step.accent.copy(alpha = 0.65f)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("makina_feature_tour_card")
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = step.icon,
                                                    contentDescription = null,
                                                    tint = step.accent,
                                                    modifier = Modifier.size(19.dp)
                                                )
                                                Spacer(Modifier.width(8.dp))
                                                Text(
                                                    text = step.title,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    color = TextPrimary,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Text(
                                                text = "${tourStepIndex + 1}/${makinaTourSteps.size}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = step.accent
                                            )
                                        }
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            text = step.highlight,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TextSecondary
                                        )
                                        Spacer(Modifier.height(12.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            OutlinedButton(
                                                onClick = {
                                                    val next = (tourStepIndex + 1) % makinaTourSteps.size
                                                    tourStepIndex = next
                                                    onSpeakText(makinaTourSteps[next].voiceLine)
                                                },
                                                border = BorderStroke(1.dp, step.accent.copy(alpha = 0.7f)),
                                                shape = RoundedCornerShape(12.dp),
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .testTag("makina_tour_next_button")
                                            ) {
                                                Text("Next Feature", color = step.accent, style = MaterialTheme.typography.labelMedium)
                                            }
                                            Button(
                                                onClick = {
                                                    val tab = step.targetTabIndex
                                                    onDismiss()
                                                    if (tab != null) onNavigateToTab(tab) else onOpenDeveloper()
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = step.accent,
                                                    contentColor = ObsidianBg
                                                ),
                                                shape = RoundedCornerShape(12.dp),
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .testTag("makina_tour_go_button")
                                            ) {
                                                Text(step.ctaLabel, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                                                Spacer(Modifier.width(4.dp))
                                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Quick Developer Shortcut on Tour mode
                    if (state.mode == MakinaMode.GREETING_AND_TOUR) {
                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showTourCard = !showTourCard },
                                border = BorderStroke(1.dp, SubtleBorder),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = if (showTourCard) "Hide Tour" else "Show App Tour",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = NeonBlueBright
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    onDismiss()
                                    onOpenDeveloper()
                                },
                                border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.55f)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Code, contentDescription = null, tint = NeonBlueBright, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(5.dp))
                                Text("Developer Page", style = MaterialTheme.typography.labelSmall, color = NeonBlueBright)
                            }
                        }
                    }

                    // Follow-up Doubt Input Row with Microphone Speech-to-Gemini (available across ALL modes)
                    Spacer(Modifier.height(10.dp))
                    if (voiceStatusBanner != null) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = (if (isListeningVoice) MakinaCoral else NeonBlue).copy(alpha = 0.16f),
                            border = BorderStroke(
                                1.dp,
                                (if (isListeningVoice) MakinaCoral else NeonBlue).copy(alpha = 0.55f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .testTag("makina_voice_listening_banner")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Mic,
                                    contentDescription = null,
                                    tint = if (isListeningVoice) MakinaCoral else NeonBlueBright,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = voiceStatusBanner.orEmpty(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = followUpInput,
                            onValueChange = { followUpInput = it },
                            placeholder = {
                                Text(
                                    text = when (state.mode) {
                                        MakinaMode.QUESTION_EXPLAINER -> "Tap Mic or type a follow-up doubt..."
                                        MakinaMode.REPORT_EXPLAINER -> "Ask Makina verbally how to improve..."
                                        MakinaMode.GREETING_AND_TOUR -> "Tap Mic or ask Makina anything..."
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MakinaGold,
                                unfocusedBorderColor = SubtleBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                cursorColor = MakinaGold
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("makina_followup_input")
                        )
                        Spacer(Modifier.width(6.dp))
                        Button(
                            onClick = {
                                if (isListeningVoice) {
                                    if (isRawAudioRecording) {
                                        isListeningVoice = false
                                        isRawAudioRecording = false
                                        voiceStatusBanner = "Sending voice question to Makina..."
                                        scope.launch {
                                            val wav = voiceRecorder.stopAndEncodeWav()
                                            voiceStatusBanner = null
                                            if (wav != null) {
                                                onSendVoiceAudio(wav, "audio/wav")
                                            } else {
                                                voiceStatusBanner = "Hold Mic a bit longer and speak clearly."
                                            }
                                        }
                                    } else {
                                        isListeningVoice = false
                                        speechRecognizer?.stopListening()
                                        voiceStatusBanner = "Finishing transcription..."
                                    }
                                } else {
                                    val granted = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.RECORD_AUDIO
                                    ) == PackageManager.PERMISSION_GRANTED
                                    if (granted) {
                                        startVoiceRecognition()
                                    } else {
                                        micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                    }
                                }
                            },
                            enabled = !state.isLoading || isListeningVoice,
                            shape = RoundedCornerShape(14.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isListeningVoice) MakinaCoral else NeonBlue.copy(alpha = 0.22f),
                                contentColor = if (isListeningVoice) Color.White else NeonBlueBright
                            ),
                            modifier = Modifier.testTag("makina_mic_button")
                        ) {
                            Icon(
                                imageVector = if (isListeningVoice) Icons.Filled.Stop else Icons.Filled.Mic,
                                contentDescription = if (isListeningVoice) "Stop voice input" else "Ask Makina using microphone",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(Modifier.width(6.dp))
                        Button(
                            onClick = {
                                val trimmed = followUpInput.trim()
                                if (trimmed.isNotEmpty() && !state.isLoading) {
                                    followUpInput = ""
                                    onSendFollowUp(trimmed)
                                }
                            },
                            enabled = followUpInput.isNotBlank() && !state.isLoading,
                            shape = RoundedCornerShape(14.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MakinaGold,
                                contentColor = ObsidianBg
                            ),
                            modifier = Modifier.testTag("makina_send_followup_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send doubt to Makina",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
