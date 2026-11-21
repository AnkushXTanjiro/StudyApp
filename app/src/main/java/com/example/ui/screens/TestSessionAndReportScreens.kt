package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed as gridItemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.QuestionResultItem
import com.example.model.TestReport
import com.example.ui.components.MathFormulaBlock
import com.example.ui.components.MathSymbolToolbar
import com.example.ui.components.NeonGlassCard
import com.example.ui.components.QuestionDiagramView
import com.example.ui.theme.AmberImprove
import com.example.ui.theme.EmeraldMastery
import com.example.ui.theme.FlameCore
import com.example.ui.theme.GlassCardSurface
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonBlueBright
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.RoseWeak
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.ActiveTestSession
import kotlinx.coroutines.launch

/**
 * Full-screen interactive timed Unit Test Mode with horizontal swipe gestures between questions,
 * math symbol toolbar, handwritten solution photo upload, and Marking Scheme grading.
 */
@Composable
fun InteractiveTestSessionScreen(
    session: ActiveTestSession,
    isOfflineMode: Boolean,
    onSelectMcq: (String, Int) -> Unit,
    onUpdateSubjective: (String, String) -> Unit,
    onAttachPhoto: (String, String, ByteArray, String) -> Unit,
    onToggleSchemeStep: (String, Int) -> Unit,
    onToggleFlagReview: (String) -> Unit = {},
    onChangeIndex: (Int) -> Unit,
    onSubmit: (Boolean) -> Unit,
    onExit: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = session.currentIndex) { session.questions.size }
    var useAiGrading by remember { mutableStateOf(!isOfflineMode) }
    var showExitConfirmDialog by remember { mutableStateOf(false) }
    var showQuestionPalette by remember { mutableStateOf(false) }

    BackHandler { showExitConfirmDialog = true }

    LaunchedEffect(pagerState.currentPage) {
        onChangeIndex(pagerState.currentPage)
    }

    val currentQuestion = session.questions.getOrNull(pagerState.currentPage)
    val solutionPhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        val q = currentQuestion ?: return@rememberLauncherForActivityResult
        if (uri != null) {
            val mime = context.contentResolver.getType(uri) ?: "image/jpeg"
            val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            if (bytes != null) {
                onAttachPhoto(q.id, uri.toString(), bytes, mime)
            }
        }
    }

    val answeredCount = session.questions.count { q ->
        if (q.questionType == "MCQ") {
            session.mcqAnswers.containsKey(q.id)
        } else {
            !session.subjectiveAnswers[q.id].isNullOrBlank() ||
                session.subjectivePhotoBytes.containsKey(q.id) ||
                !session.checkedSchemeSteps[q.id].isNullOrEmpty()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Test Bar with Exit Guard, Palette Grid & Live Timer
        Surface(
            color = GlassCardSurface,
            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.35f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { showExitConfirmDialog = true },
                    modifier = Modifier.testTag("exit_test_icon_button")
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Exit Test", tint = TextSecondary)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(session.subjectTitle, style = MaterialTheme.typography.labelSmall, color = NeonBlue, maxLines = 1)
                        if (session.examModeLabel != "PRACTICE") {
                            Surface(
                                color = if (session.examModeLabel == "BEU_70M") AmberImprove.copy(alpha = 0.2f) else RoseWeak.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = if (session.examModeLabel == "BEU_70M") "BEU 70M" else "MISTAKES",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (session.examModeLabel == "BEU_70M") AmberImprove else RoseWeak,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Text(session.unitTitle, style = MaterialTheme.typography.titleSmall, color = TextPrimary, maxLines = 1)
                }
                val limitSeconds = session.timeLimitSeconds
                val isCountdown = limitSeconds != null
                val displaySeconds = if (limitSeconds != null) {
                    (limitSeconds - session.elapsedSeconds).coerceAtLeast(0L)
                } else {
                    session.elapsedSeconds
                }
                val timerAccent = if (isCountdown && displaySeconds <= 300L) RoseWeak else FlameCore
                Surface(
                    color = timerAccent.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, timerAccent.copy(alpha = 0.55f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = if (isCountdown) "Time Remaining" else "Elapsed Time",
                            tint = timerAccent,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = if (isCountdown) "${formatDuration(displaySeconds)} left" else formatDuration(displaySeconds),
                            style = MaterialTheme.typography.labelMedium,
                            color = timerAccent
                        )
                    }
                }
                Spacer(Modifier.width(6.dp))
                Surface(
                    onClick = { showQuestionPalette = true },
                    color = NeonBlue.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.55f)),
                    modifier = Modifier.testTag("open_question_palette_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.GridView,
                            contentDescription = "Question Navigator",
                            tint = NeonBlueBright,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "$answeredCount/${session.questions.size}",
                            style = MaterialTheme.typography.labelMedium,
                            color = NeonBlueBright
                        )
                    }
                }
            }
        }

        // Progress Indicator
        LinearProgressIndicator(
            progress = { (pagerState.currentPage + 1).toFloat() / session.questions.size.coerceAtLeast(1) },
            modifier = Modifier.fillMaxWidth().height(4.dp),
            color = NeonBlue,
            trackColor = GlassCardSurface
        )

        // Swipeable Question Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIdx ->
            val q = session.questions[pageIdx]
            val isFlagged = q.id in session.flaggedQuestionIds
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${pageIdx + 1} of ${session.questions.size}",
                        style = MaterialTheme.typography.labelLarge,
                        color = NeonBlueBright
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            onClick = { onToggleFlagReview(q.id) },
                            color = if (isFlagged) AmberImprove.copy(alpha = 0.22f) else GlassCardSurface,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, if (isFlagged) AmberImprove else SubtleBorder),
                            modifier = Modifier.testTag("flag_review_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Flag,
                                    contentDescription = if (isFlagged) "Marked for Review" else "Mark for Review",
                                    tint = if (isFlagged) AmberImprove else TextSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = if (isFlagged) "Flagged" else "Review",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isFlagged) AmberImprove else TextSecondary
                                )
                            }
                        }
                        Surface(
                            color = if (q.questionType == "SUBJECTIVE") FlameCore.copy(alpha = 0.18f) else NeonBlue.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "${q.questionType} • ${q.maxMarks}M",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (q.questionType == "SUBJECTIVE") FlameCore else NeonBlueBright,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Text(
                    text = "Topic: ${q.topicTitle}",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )

                Text(
                    text = q.prompt,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )

                if (q.formulaExpr.isNotBlank()) {
                    MathFormulaBlock(q.formulaExpr)
                }

                if (q.diagramKind != "NONE" || q.imageUri.isNotBlank()) {
                    QuestionDiagramView(diagramKind = q.diagramKind, imageUri = q.imageUri)
                }

                if (q.questionType == "MCQ") {
                    val chosen = session.mcqAnswers[q.id] ?: -1
                    q.options.forEachIndexed { optIdx, optText ->
                        val selected = chosen == optIdx
                        Surface(
                            onClick = { onSelectMcq(q.id, optIdx) },
                            shape = RoundedCornerShape(14.dp),
                            color = if (selected) NeonBlue.copy(alpha = 0.18f) else GlassCardSurface,
                            border = BorderStroke(
                                width = if (selected) 1.5.dp else 1.dp,
                                color = if (selected) NeonBlue else SubtleBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selected,
                                    onClick = { onSelectMcq(q.id, optIdx) },
                                    colors = RadioButtonDefaults.colors(selectedColor = NeonBlue, unselectedColor = TextSecondary)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(optText, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                            }
                        }
                    }
                } else {
                    // Subjective Answer Editor with Math Symbol Bar & Photo Upload
                    val currentAns = session.subjectiveAnswers[q.id].orEmpty()
                    val attachedUri = session.subjectivePhotoUris[q.id].orEmpty()

                    Text("QUICK MATH SYMBOLS (TAP TO INSERT)", style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                    MathSymbolToolbar(onInsertSymbol = { sym -> onUpdateSubjective(q.id, currentAns + sym) })

                    OutlinedTextField(
                        value = currentAns,
                        onValueChange = { onUpdateSubjective(q.id, it) },
                        label = { Text("Write your step-by-step derivation / answer") },
                        minLines = 5,
                        maxLines = 10,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonBlue,
                            unfocusedBorderColor = SubtleBorder,
                            focusedContainerColor = GlassCardSurface,
                            unfocusedContainerColor = GlassCardSurface,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("subjective_answer_input")
                    )

                    OutlinedButton(
                        onClick = {
                            solutionPhotoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Image, contentDescription = null, tint = NeonBlueBright)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (attachedUri.isBlank()) "Upload Handwritten Answer Photo (Optional)" else "Handwritten Solution Attached",
                            color = NeonBlueBright
                        )
                    }

                    // Marking Scheme Rubric Panel (supports both AI auto-grading & Offline self-check)
                    NeonGlassCard(accentColor = EmeraldMastery) {
                        Text(
                            text = "MARKING SCHEME RUBRIC (${q.maxMarks} MARKS)",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldMastery
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = if (useAiGrading && !isOfflineMode) {
                                "AI will auto-grade your answer against these steps on Submit — or tick steps you completed for offline self-marking."
                            } else {
                                "Offline Marking Scheme: Tick each step you accurately completed to compute marks."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                        Spacer(Modifier.height(8.dp))
                        val checked = session.checkedSchemeSteps[q.id].orEmpty()
                        q.markingScheme.forEachIndexed { stepIdx, stepText ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onToggleSchemeStep(q.id, stepIdx) }
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = stepIdx in checked,
                                    onCheckedChange = { onToggleSchemeStep(q.id, stepIdx) },
                                    colors = CheckboxDefaults.colors(checkedColor = EmeraldMastery, uncheckedColor = TextSecondary)
                                )
                                Text(stepText, style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                            }
                        }
                    }
                }
            }
        }

        // Bottom Navigation & Submit Bar
        Surface(
            color = GlassCardSurface,
            border = BorderStroke(1.dp, SubtleBorder)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                if (session.questions.any { it.questionType == "SUBJECTIVE" } && !isOfflineMode) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = NeonBlue, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("AI Marking Scheme Grading", style = MaterialTheme.typography.labelMedium, color = TextPrimary)
                        }
                        Switch(
                            checked = useAiGrading,
                            onCheckedChange = { useAiGrading = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = ObsidianBg, checkedTrackColor = NeonBlue)
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                }
                val answeredCount = session.questions.count { q ->
                    if (q.questionType == "MCQ") {
                        session.mcqAnswers.containsKey(q.id)
                    } else {
                        !session.subjectiveAnswers[q.id].isNullOrBlank() ||
                            session.subjectivePhotoBytes.containsKey(q.id) ||
                            !session.checkedSchemeSteps[q.id].isNullOrEmpty()
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage((pagerState.currentPage - 1).coerceAtLeast(0)) }
                        },
                        enabled = pagerState.currentPage > 0 && !session.isSubmitting
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous", modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Prev")
                    }

                    if (pagerState.currentPage < session.questions.lastIndex) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            OutlinedButton(
                                onClick = { onSubmit(useAiGrading) },
                                enabled = !session.isSubmitting,
                                border = BorderStroke(1.dp, EmeraldMastery.copy(alpha = 0.7f)),
                                modifier = Modifier.testTag("finish_early_test_button")
                            ) {
                                Text(
                                    text = if (session.isSubmitting) "Grading..." else "Finish ($answeredCount/${session.questions.size})",
                                    color = EmeraldMastery,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                            Button(
                                onClick = {
                                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = NeonBlue, contentColor = Color(0xFF001C27))
                            ) {
                                Text("Next")
                                Spacer(Modifier.width(4.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next", modifier = Modifier.size(16.dp))
                            }
                        }
                    } else {
                        Button(
                            onClick = { onSubmit(useAiGrading) },
                            enabled = !session.isSubmitting,
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldMastery, contentColor = ObsidianBg),
                            modifier = Modifier.testTag("submit_test_button")
                        ) {
                            if (session.isSubmitting) {
                                CircularProgressIndicator(modifier = Modifier.size(18.dp), color = ObsidianBg, strokeWidth = 2.dp)
                                Spacer(Modifier.width(8.dp))
                                Text("Grading...")
                            } else {
                                Icon(Icons.Filled.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Submit ($answeredCount/${session.questions.size})", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }

    // 1. Exit Test Confirmation Dialog (prevents accidental back/close loss)
    if (showExitConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showExitConfirmDialog = false },
            containerColor = GlassCardSurface,
            title = { Text("Leave Active Test?", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "You have answered $answeredCount of ${session.questions.size} question(s)" +
                            if (session.flaggedQuestionIds.isNotEmpty()) " (${session.flaggedQuestionIds.size} flagged for review)." else ".",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Text(
                        text = "Submit now to save your report card, or discard and exit.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(
                        onClick = {
                            showExitConfirmDialog = false
                            onExit()
                        },
                        modifier = Modifier.testTag("confirm_exit_test_button")
                    ) {
                        Text("Discard & Exit", color = RoseWeak, fontWeight = FontWeight.SemiBold)
                    }
                    Button(
                        onClick = {
                            showExitConfirmDialog = false
                            onSubmit(useAiGrading)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldMastery, contentColor = ObsidianBg),
                        modifier = Modifier.testTag("confirm_submit_from_exit_button")
                    ) {
                        Text("Submit", fontWeight = FontWeight.Bold)
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showExitConfirmDialog = false },
                    modifier = Modifier.testTag("cancel_exit_test_button")
                ) {
                    Text("Keep Solving", color = NeonBlueBright)
                }
            }
        )
    }

    // 2. Question Navigator Palette Grid Dialog
    if (showQuestionPalette) {
        AlertDialog(
            onDismissRequest = { showQuestionPalette = false },
            containerColor = GlassCardSurface,
            title = {
                Column {
                    Text("Question Navigator", color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text(
                        text = "$answeredCount answered • ${session.flaggedQuestionIds.size} flagged • ${session.questions.size} total",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("question_palette_dialog"),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("■ Answered", style = MaterialTheme.typography.labelSmall, color = EmeraldMastery)
                        Text("■ Review", style = MaterialTheme.typography.labelSmall, color = AmberImprove)
                        Text("■ Pending", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 48.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                    ) {
                        gridItemsIndexed(session.questions, key = { idx, q -> "${q.id}_$idx" }) { idx, q ->
                            val isAnswered = if (q.questionType == "MCQ") {
                                session.mcqAnswers.containsKey(q.id)
                            } else {
                                !session.subjectiveAnswers[q.id].isNullOrBlank() ||
                                    session.subjectivePhotoBytes.containsKey(q.id) ||
                                    !session.checkedSchemeSteps[q.id].isNullOrEmpty()
                            }
                            val isFlagged = q.id in session.flaggedQuestionIds
                            val isCurrent = idx == pagerState.currentPage

                            val bgColor = when {
                                isFlagged -> AmberImprove.copy(alpha = 0.24f)
                                isAnswered -> EmeraldMastery.copy(alpha = 0.22f)
                                else -> ObsidianBg
                            }
                            val borderColor = when {
                                isCurrent -> NeonBlueBright
                                isFlagged -> AmberImprove
                                isAnswered -> EmeraldMastery.copy(alpha = 0.7f)
                                else -> SubtleBorder
                            }
                            val textColor = when {
                                isFlagged -> AmberImprove
                                isAnswered -> EmeraldMastery
                                else -> TextPrimary
                            }

                            Surface(
                                onClick = {
                                    showQuestionPalette = false
                                    scope.launch { pagerState.animateScrollToPage(idx) }
                                },
                                shape = RoundedCornerShape(10.dp),
                                color = bgColor,
                                border = BorderStroke(if (isCurrent) 2.dp else 1.dp, borderColor),
                                modifier = Modifier.testTag("palette_item_${idx + 1}")
                            ) {
                                Box(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (isFlagged) "${idx + 1}★" else "${idx + 1}",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = textColor,
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showQuestionPalette = false }) {
                    Text("Close", color = NeonBlueBright)
                }
            }
        )
    }
}

/**
 * Full saved Report Card Viewer showing Marks, Time Taken, Strong Topics, Weak Topics,
 * Topics to Improve, per-question marking scheme feedback, and one-tap Add Weak Topics to To-Do.
 */
@Composable
fun ReportCardDetailScreen(
    report: TestReport,
    onAddWeakTopicsToTodo: (TestReport) -> Unit,
    onRetryWeakTopics: (TestReport) -> Unit = {},
    onAskMakinaExplainReport: (TestReport) -> Unit = {},
    onExplainQuestionWithAi: (QuestionResultItem) -> Unit = {},
    onClose: () -> Unit
) {
    BackHandler { onClose() }
    var showNeedsReviewOnly by remember { mutableStateOf(false) }

    val indexedQuestions = remember(report.questionBreakdown) {
        report.questionBreakdown.mapIndexed { idx, item -> idx to item }
    }
    val reviewCount = remember(indexedQuestions) {
        indexedQuestions.count { (_, item) ->
            val ratio = if (item.maxMarks > 0) item.awardedMarks / item.maxMarks else 0.0
            ratio < 0.75
        }
    }
    val displayedQuestions = remember(indexedQuestions, showNeedsReviewOnly) {
        if (!showNeedsReviewOnly) indexedQuestions
        else indexedQuestions.filter { (_, item) ->
            val ratio = if (item.maxMarks > 0) item.awardedMarks / item.maxMarks else 0.0
            ratio < 0.75
        }
    }

    val grade = when {
        report.percentage >= 90.0 -> "A+ Outstanding"
        report.percentage >= 75.0 -> "A Distinction"
        report.percentage >= 60.0 -> "B+ Proficient"
        report.percentage >= 50.0 -> "B Developing"
        else -> "C Needs Revision"
    }
    val scoreColor = when {
        report.percentage >= 75.0 -> EmeraldMastery
        report.percentage >= 50.0 -> AmberImprove
        else -> RoseWeak
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Surface(color = GlassCardSurface, border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.3f))) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close Report", tint = NeonBlueBright)
                }
                Column {
                    Text("SAVED REPORT CARD", style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                    Text(report.unitTitle, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().testTag("report_card_detail"),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Score Summary Hero
            item {
                NeonGlassCard(accentColor = scoreColor) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(report.subjectTitle, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "${report.obtainedMarks} / ${report.totalMarks} Marks",
                                style = MaterialTheme.typography.headlineMedium,
                                color = TextPrimary
                            )
                            Text(grade, style = MaterialTheme.typography.titleSmall, color = scoreColor, fontWeight = FontWeight.Bold)
                        }
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = scoreColor.copy(alpha = 0.16f),
                            border = BorderStroke(1.5.dp, scoreColor)
                        ) {
                            Text(
                                text = "${report.percentage}%",
                                style = MaterialTheme.typography.headlineSmall,
                                color = scoreColor,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { (report.percentage / 100.0).toFloat().coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth().height(7.dp),
                        color = scoreColor,
                        trackColor = ObsidianBg,
                        strokeCap = StrokeCap.Round
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Time Taken: ${formatDuration(report.durationSeconds)}", style = MaterialTheme.typography.labelMedium, color = NeonBlueBright)
                        Text("Mastered: ${report.correctCount}/${report.totalQuestions} Qs", style = MaterialTheme.typography.labelMedium, color = TextPrimary)
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = { onAskMakinaExplainReport(report) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("ask_makina_explain_result_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFC95C).copy(alpha = 0.2f),
                            contentColor = TextPrimary
                        ),
                        border = BorderStroke(1.2.dp, Color(0xFFFFC95C))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_makina_avatar),
                            contentDescription = "Makina AI",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(26.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color(0xFFFFC95C), CircleShape)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "Ask Makina to Explain My Result",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color(0xFFFFD87D),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Strong / Weak / Topics to Improve Breakdown
            item {
                NeonGlassCard(accentColor = NeonBlue) {
                    Text("TOPIC DIAGNOSTICS", style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                    Spacer(Modifier.height(8.dp))

                    TopicDiagnosticSection("Strong Topics (≥75%)", report.strongTopics, EmeraldMastery)
                    Spacer(Modifier.height(8.dp))
                    TopicDiagnosticSection("Topics to Improve (50–74%)", report.improveTopics, AmberImprove)
                    Spacer(Modifier.height(8.dp))
                    TopicDiagnosticSection("Weak Topics (<50%)", report.weakTopics, RoseWeak)

                    if (report.weakTopics.isNotEmpty() || report.improveTopics.isNotEmpty() || reviewCount > 0) {
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = { onRetryWeakTopics(report) },
                            modifier = Modifier.fillMaxWidth().testTag("retry_weak_topics_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = FlameCore, contentColor = Color.White)
                        ) {
                            Icon(Icons.Filled.Replay, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Retry Weak & Missed Qs (${reviewCount.coerceAtLeast(1)} Qs)", fontWeight = FontWeight.Bold)
                        }
                    }
                    if (report.weakTopics.isNotEmpty() || report.improveTopics.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = { onAddWeakTopicsToTodo(report) },
                            modifier = Modifier.fillMaxWidth().testTag("add_weak_topics_todo_button"),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.7f))
                        ) {
                            Icon(Icons.Filled.AddTask, contentDescription = null, tint = NeonBlueBright, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Add Weak & Improve Topics to To-Do", color = NeonBlueBright, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Per-Question Rubric Review + Filter
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("QUESTION-BY-QUESTION MARKING REVIEW", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = !showNeedsReviewOnly,
                            onClick = { showNeedsReviewOnly = false },
                            label = { Text("All Qs (${indexedQuestions.size})") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NeonBlue.copy(alpha = 0.22f),
                                selectedLabelColor = NeonBlueBright,
                                containerColor = ObsidianBg,
                                labelColor = TextSecondary
                            ),
                            modifier = Modifier.testTag("report_filter_all")
                        )
                        FilterChip(
                            selected = showNeedsReviewOnly,
                            onClick = { showNeedsReviewOnly = true },
                            label = { Text("Needs Review ($reviewCount)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = RoseWeak.copy(alpha = 0.22f),
                                selectedLabelColor = RoseWeak,
                                containerColor = ObsidianBg,
                                labelColor = TextSecondary
                            ),
                            modifier = Modifier.testTag("report_filter_review")
                        )
                    }
                }
            }

            if (displayedQuestions.isEmpty()) {
                item {
                    NeonGlassCard(accentColor = EmeraldMastery) {
                        Text(
                            text = "Every question in this test is ≥75% mastered!",
                            style = MaterialTheme.typography.titleSmall,
                            color = EmeraldMastery,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                itemsIndexed(displayedQuestions, key = { _, pair -> "q_${pair.first}" }) { _, (originalIdx, item) ->
                    val ratio = if (item.maxMarks > 0) item.awardedMarks / item.maxMarks else 0.0
                    val accent = when {
                        ratio >= 0.75 -> EmeraldMastery
                        ratio >= 0.5 -> AmberImprove
                        else -> RoseWeak
                    }
                    NeonGlassCard(accentColor = accent) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Q${originalIdx + 1} • ${item.questionType} • ${item.topicTitle}", style = MaterialTheme.typography.labelSmall, color = accent)
                            Text("${item.awardedMarks}/${item.maxMarks}M", style = MaterialTheme.typography.labelLarge, color = accent)
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(item.questionPrompt, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                        Spacer(Modifier.height(6.dp))
                        Text("Your Answer: ${item.userAnswer}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Spacer(Modifier.height(4.dp))
                        Text("Evaluation: ${item.feedback}", style = MaterialTheme.typography.bodySmall, color = NeonBlueBright)
                        if (item.modelAnswer.isNotBlank()) {
                            Spacer(Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ObsidianBg.copy(alpha = 0.85f),
                                border = BorderStroke(1.dp, EmeraldMastery.copy(alpha = 0.35f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = "OFFICIAL MODEL ANSWER",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = EmeraldMastery,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(Modifier.height(3.dp))
                                    Text(
                                        text = item.modelAnswer,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            OutlinedButton(
                                onClick = { onExplainQuestionWithAi(item) },
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color(0xFFFFC95C).copy(alpha = 0.75f)),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("explain_ai_question_button_${originalIdx + 1}")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AutoAwesome,
                                    contentDescription = "Explain with AI",
                                    tint = Color(0xFFFFC95C),
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = "Explain with AI",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFFFFD87D),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(84.dp)) }
        }
    }
}

@Composable
private fun TopicDiagnosticSection(title: String, topics: List<String>, color: Color) {
    Text(title, style = MaterialTheme.typography.labelMedium, color = color, fontWeight = FontWeight.Bold)
    if (topics.isEmpty()) {
        Text("None recorded in this test", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
    } else {
        topics.forEach { topic ->
            Text("• $topic", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
        }
    }
}
