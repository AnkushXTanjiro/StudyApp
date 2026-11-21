package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.BundledQuestionBankLoader
import com.example.model.BeeeQuestionBank
import com.example.model.CfetQuestionBank
import com.example.model.Math1QuestionBank
import com.example.model.StudyExamBuilder
import com.example.model.SubjectDoc
import com.example.model.TestQuestion
import com.example.model.TestReport
import com.example.model.UnitItem
import com.example.ui.components.MathFormulaBlock
import com.example.ui.components.MathSymbolToolbar
import com.example.ui.components.NeonGlassCard
import com.example.ui.components.QuestionDiagramView
import com.example.ui.theme.AmberImprove
import com.example.ui.theme.EmeraldMastery
import com.example.ui.theme.FlameCore
import com.example.ui.theme.GlassCardSurface
import com.example.ui.theme.HyperBlue
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonBlueBright
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.RoseWeak
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.ExtractionBannerState

@Composable
fun TestLabScreen(
    listState: LazyListState,
    subjects: List<SubjectDoc>,
    questions: List<TestQuestion>,
    reports: List<TestReport>,
    extractionState: ExtractionBannerState,
    preselectedSubjectId: String?,
    preselectedUnitId: String?,
    onStartUnitTest: (SubjectDoc, UnitItem, List<TestQuestion>) -> Unit,
    onStartWeakTopicQuiz: () -> Unit = {},
    onStartBeuEndSemMock: (SubjectDoc) -> Unit = {},
    onExtractQuestions: (SubjectDoc, UnitItem, ByteArray?, String?, String, String) -> Unit,
    onAddManualQuestion: (TestQuestion) -> Unit,
    onDeleteQuestion: (String) -> Unit,
    onOpenReport: (TestReport) -> Unit,
    onDismissExtractionError: () -> Unit
) {
    val context = LocalContext.current
    var activeSegment by remember { mutableIntStateOf(0) } // 0 = Unit Tests, 1 = Saved Report Cards
    var selectedSubjectId by remember { mutableStateOf(preselectedSubjectId ?: Math1QuestionBank.SUBJECT_ID) }
    var selectedUnitId by remember { mutableStateOf(preselectedUnitId ?: "u_100102_1") }
    var selectedTypeFilter by remember { mutableStateOf("ALL") } // ALL, MCQ, SHORT, LONG
    var selectedTopicFilter by remember { mutableStateOf("ALL") }
    var searchQuery by remember { mutableStateOf("") }
    var quizSizeLimit by remember { mutableIntStateOf(20) } // 10, 20, or 0 (= All)
    var shuffleQuestions by remember { mutableStateOf(true) }

    var showExtractDialog by remember { mutableStateOf(false) }
    var showAddQuestionDialog by remember { mutableStateOf(false) }

    LaunchedEffect(preselectedSubjectId, preselectedUnitId) {
        if (!preselectedSubjectId.isNullOrBlank()) selectedSubjectId = preselectedSubjectId
        if (!preselectedUnitId.isNullOrBlank()) selectedUnitId = preselectedUnitId
    }

    val selectedSubject = subjects.firstOrNull { it.id == selectedSubjectId } ?: subjects.firstOrNull()
    val selectedUnit = selectedSubject?.units?.firstOrNull { it.unitId == selectedUnitId }
        ?: selectedSubject?.units?.firstOrNull()

    val allUnitQuestions = remember(questions, selectedSubject?.id, selectedUnit?.unitId) {
        if (selectedSubject == null || selectedUnit == null) emptyList()
        else questions.filter { it.subjectId == selectedSubject.id && it.unitId == selectedUnit.unitId }
    }

    val unitTopics = remember(allUnitQuestions) {
        allUnitQuestions.map { it.topicTitle }.filter { it.isNotBlank() }.distinct()
    }

    LaunchedEffect(selectedSubject?.id, selectedUnit?.unitId) {
        selectedTypeFilter = "ALL"
        selectedTopicFilter = "ALL"
        searchQuery = ""
    }

    val unitQuestions = remember(allUnitQuestions, selectedTypeFilter, selectedTopicFilter, searchQuery) {
        val qTrim = searchQuery.trim().lowercase()
        allUnitQuestions.filter { q ->
            val typeOk = when (selectedTypeFilter) {
                "MCQ" -> q.questionType == "MCQ"
                "SHORT" -> q.questionType == "SUBJECTIVE" && q.maxMarks < 10L
                "LONG" -> q.questionType == "SUBJECTIVE" && q.maxMarks >= 10L
                else -> true
            }
            val topicOk = selectedTopicFilter == "ALL" || q.topicTitle == selectedTopicFilter
            val searchOk = qTrim.isBlank() ||
                q.prompt.lowercase().contains(qTrim) ||
                q.topicTitle.lowercase().contains(qTrim) ||
                q.formulaExpr.lowercase().contains(qTrim)
            typeOk && topicOk && searchOk
        }
    }

    val launchQuestionsCount = if (quizSizeLimit <= 0) unitQuestions.size else minOf(quizSizeLimit, unitQuestions.size)
    val weakSignals = remember(reports) { StudyExamBuilder.countWeakAndMissedSignals(reports) }
    val beuSpec = remember(questions, selectedSubject?.id) {
        selectedSubject?.let { StudyExamBuilder.buildBeuEndSemMockPaper(it, questions) }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .testTag("test_lab_list"),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Test Lab & Reports",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary
                )
                Text(
                    text = "Unit-wise math (∫, ∂/∂x, α, β), diagrams, subjective marking schemes & saved report cards",
                    style = MaterialTheme.typography.bodySmall,
                    color = NeonBlueBright
                )
                Spacer(Modifier.height(12.dp))
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    listOf("Unit Test Builder", "Saved Reports (${reports.size})").forEachIndexed { idx, label ->
                        SegmentedButton(
                            selected = activeSegment == idx,
                            onClick = { activeSegment = idx },
                            shape = SegmentedButtonDefaults.itemShape(idx, 2),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = NeonBlue.copy(alpha = 0.22f),
                                activeContentColor = NeonBlueBright,
                                inactiveContainerColor = ObsidianBg,
                                inactiveContentColor = TextSecondary
                            )
                        ) {
                            Text(label, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }

        if (extractionState.isWorking) {
            item {
                NeonGlassCard(accentColor = NeonBlue) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.size(22.dp), color = NeonBlue, strokeWidth = 2.5.dp)
                        Spacer(Modifier.width(12.dp))
                        Text(extractionState.statusText, style = MaterialTheme.typography.bodySmall, color = NeonBlueBright)
                    }
                }
            }
        } else if (extractionState.errorText != null) {
            item {
                Surface(
                    color = RoseWeak.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, RoseWeak.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth().clickable { onDismissExtractionError() }
                ) {
                    Text(
                        text = "${extractionState.errorText} (Tap to dismiss)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFFB4C2),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        if (activeSegment == 0) {
            // 0a. Weak-Topic Retry / Mistakes Deck Quick Banner
            if (weakSignals > 0) {
                item {
                    NeonGlassCard(accentColor = RoseWeak) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("MISTAKES DECK", style = MaterialTheme.typography.labelSmall, color = RoseWeak)
                                Text(
                                    text = "Retry Weak & Missed Topics",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Auto-builds a focused quiz from past <75% questions",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(
                                onClick = onStartWeakTopicQuiz,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = FlameCore, contentColor = Color.White),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                modifier = Modifier.testTag("test_lab_mistakes_deck_button")
                            ) {
                                Icon(Icons.Filled.Replay, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Retry", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // 0b. BEU 70-Mark End-Sem Mock Paper Simulator Card
            if (selectedSubject != null && beuSpec != null && beuSpec.questions.isNotEmpty()) {
                item {
                    NeonGlassCard(accentColor = AmberImprove) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "BEU END-SEM EXAM SIMULATOR",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AmberImprove
                                    )
                                    Text(
                                        text = "${shortSubjectLabel(selectedSubject)} • ${beuSpec.totalMarks} Marks Mock Paper",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Full ${selectedSubject.units.size} units • ${beuSpec.mcqCount} MCQ + ${beuSpec.shortCount} Short + ${beuSpec.longCount} Long • ${beuSpec.durationMinutes} min timer",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary
                                    )
                                }
                                Spacer(Modifier.width(10.dp))
                                Button(
                                    onClick = { onStartBeuEndSemMock(selectedSubject) },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = AmberImprove, contentColor = Color(0xFF1A1200)),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                    modifier = Modifier.testTag("start_beu_mock_button")
                                ) {
                                    Icon(Icons.Filled.Timer, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text("${beuSpec.totalMarks}M", fontWeight = FontWeight.Bold)
                                }
                        }
                    }
                }
            }

            // 1. Hierarchical Subject -> Unit Selector
            item {
                NeonGlassCard(accentColor = NeonBlue) {
                    Text("1. SELECT SUBJECT", style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                    Spacer(Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        subjects.forEach { subj ->
                            val subjQCount = questions.count { it.subjectId == subj.id }
                            FilterChip(
                                selected = selectedSubject?.id == subj.id,
                                onClick = {
                                    selectedSubjectId = subj.id
                                    selectedUnitId = subj.units.firstOrNull()?.unitId ?: ""
                                },
                                label = {
                                    val shortName = shortSubjectLabel(subj)
                                    Text(
                                        if (subjQCount > 0) "$shortName ($subjQCount)"
                                        else shortName
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = NeonBlue.copy(alpha = 0.22f),
                                    selectedLabelColor = NeonBlueBright,
                                    containerColor = ObsidianBg,
                                    labelColor = TextSecondary
                                )
                            )
                        }
                    }

                    if (selectedSubject != null) {
                        Spacer(Modifier.height(10.dp))
                        Text("2. SELECT UNIT (${selectedSubject.title})", style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                        Spacer(Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            selectedSubject.units.forEach { u ->
                                val qCount = questions.count { it.subjectId == selectedSubject.id && it.unitId == u.unitId }
                                FilterChip(
                                    selected = selectedUnit?.unitId == u.unitId,
                                    onClick = { selectedUnitId = u.unitId },
                                    label = { Text("${u.unitNumber} ($qCount)") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = FlameCore.copy(alpha = 0.22f),
                                        selectedLabelColor = FlameCore,
                                        containerColor = ObsidianBg,
                                        labelColor = TextSecondary
                                    )
                                )
                            }
                        }
                    }

                    if (allUnitQuestions.isNotEmpty()) {
                        Spacer(Modifier.height(10.dp))
                        Text("3. TEST MODE & TOPIC FILTER", style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                        Spacer(Modifier.height(6.dp))
                        val mcqCnt = allUnitQuestions.count { it.questionType == "MCQ" }
                        val shortCnt = allUnitQuestions.count { it.questionType == "SUBJECTIVE" && it.maxMarks < 10L }
                        val longCnt = allUnitQuestions.count { it.questionType == "SUBJECTIVE" && it.maxMarks >= 10L }
                        val isLabSubject = selectedSubject?.category == "LAB"
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                "ALL" to "All (${allUnitQuestions.size})",
                                "MCQ" to "Objective MCQ ($mcqCnt)",
                                "SHORT" to if (isLabSubject) "Viva 2M ($shortCnt)" else "Short 5M ($shortCnt)",
                                "LONG" to "Long 10-14M ($longCnt)"
                            ).forEach { (key, label) ->
                                FilterChip(
                                    selected = selectedTypeFilter == key,
                                    onClick = { selectedTypeFilter = key },
                                    label = { Text(label) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = EmeraldMastery.copy(alpha = 0.22f),
                                        selectedLabelColor = EmeraldMastery,
                                        containerColor = ObsidianBg,
                                        labelColor = TextSecondary
                                    )
                                )
                            }
                        }
                        if (unitTopics.size > 1) {
                            Spacer(Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                FilterChip(
                                    selected = selectedTopicFilter == "ALL",
                                    onClick = { selectedTopicFilter = "ALL" },
                                    label = { Text("All Topics") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = NeonBlue.copy(alpha = 0.22f),
                                        selectedLabelColor = NeonBlueBright,
                                        containerColor = ObsidianBg,
                                        labelColor = TextSecondary
                                    )
                                )
                                unitTopics.forEach { topic ->
                                    FilterChip(
                                        selected = selectedTopicFilter == topic,
                                        onClick = { selectedTopicFilter = topic },
                                        label = { Text(topic.take(26)) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = NeonBlue.copy(alpha = 0.22f),
                                            selectedLabelColor = NeonBlueBright,
                                            containerColor = ObsidianBg,
                                            labelColor = TextSecondary
                                        )
                                    )
                                }
                            }
                        }
                    }

                    if (selectedSubject != null && selectedUnit != null) {
                        Spacer(Modifier.height(10.dp))
                        // Search within selected unit question bank
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    "Search ${allUnitQuestions.size} questions by topic or keyword...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            },
                            leadingIcon = {
                                Icon(Icons.Filled.Search, contentDescription = "Search questions", tint = NeonBlueBright, modifier = Modifier.size(18.dp))
                            },
                            trailingIcon = {
                                if (searchQuery.isNotBlank()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Filled.Close, contentDescription = "Clear search", tint = TextSecondary, modifier = Modifier.size(18.dp))
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonBlue,
                                unfocusedBorderColor = SubtleBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("test_lab_search_input")
                        )

                        Spacer(Modifier.height(10.dp))
                        Text("4. QUIZ LENGTH & ORDER", style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                        Spacer(Modifier.height(6.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(10 to "Quick 10 Qs", 20 to "Mock 20 Qs", 0 to "All (${unitQuestions.size})").forEach { (limit, label) ->
                                FilterChip(
                                    selected = quizSizeLimit == limit,
                                    onClick = { quizSizeLimit = limit },
                                    label = { Text(label) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = HyperBlue.copy(alpha = 0.25f),
                                        selectedLabelColor = NeonBlueBright,
                                        containerColor = ObsidianBg,
                                        labelColor = TextSecondary
                                    )
                                )
                            }
                            FilterChip(
                                selected = shuffleQuestions,
                                onClick = { shuffleQuestions = !shuffleQuestions },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Shuffle,
                                        contentDescription = null,
                                        modifier = Modifier.size(15.dp)
                                    )
                                },
                                label = { Text(if (shuffleQuestions) "Shuffled" else "Ordered") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FlameCore.copy(alpha = 0.22f),
                                    selectedLabelColor = FlameCore,
                                    containerColor = ObsidianBg,
                                    labelColor = TextSecondary
                                )
                            )
                        }

                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "${selectedUnit.unitNumber}: ${selectedUnit.title}",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Matching: ${unitQuestions.size} Qs • Test will launch $launchQuestionsCount Q(s)",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val pool = if (shuffleQuestions) unitQuestions.shuffled() else unitQuestions
                                val selectedBatch = if (quizSizeLimit > 0) pool.take(quizSizeLimit) else pool
                                onStartUnitTest(selectedSubject, selectedUnit, selectedBatch)
                            },
                            enabled = unitQuestions.isNotEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("start_unit_test_button"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonBlue,
                                contentColor = Color(0xFF001C27)
                            )
                        ) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            val isLab = selectedSubject.category == "LAB"
                            Text(
                                text = when (selectedTypeFilter) {
                                    "MCQ" -> "Start MCQ Quiz ($launchQuestionsCount Qs)"
                                    "SHORT" -> if (isLab) "Start Lab Viva ($launchQuestionsCount Qs)" else "Start Short Test ($launchQuestionsCount Qs)"
                                    "LONG" -> "Start Long Answer Test ($launchQuestionsCount Qs)"
                                    else -> if (isLab) "Start Interactive Lab Viva ($launchQuestionsCount Qs)" else "Start Unit Test ($launchQuestionsCount Qs)"
                                },
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showExtractDialog = true },
                                modifier = Modifier.weight(1f).testTag("extract_questions_button"),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.65f))
                            ) {
                                Icon(Icons.Filled.UploadFile, contentDescription = null, tint = NeonBlueBright, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(5.dp))
                                Text("Upload PDF/Img", style = MaterialTheme.typography.labelMedium, color = NeonBlueBright)
                            }
                            OutlinedButton(
                                onClick = { showAddQuestionDialog = true },
                                modifier = Modifier.weight(1f).testTag("add_custom_question_button"),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, FlameCore.copy(alpha = 0.65f))
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = null, tint = FlameCore, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(5.dp))
                                Text("Add Question", style = MaterialTheme.typography.labelMedium, color = FlameCore)
                            }
                        }
                    }
                }
            }

            // 2. Unit-wise Question Preview Bank
            if (unitQuestions.isEmpty()) {
                item {
                    NeonGlassCard(accentColor = HyperBlue) {
                        Text("No questions in this unit yet", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Upload your question paper PDF/photo or paste questions for ${selectedUnit?.unitNumber ?: "this unit"} — AI will extract every MCQ and Subjective question with math symbols and marking schemes automatically!",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                items(unitQuestions, key = { it.id }) { q ->
                    val typeColor = if (q.questionType == "SUBJECTIVE") FlameCore else NeonBlue
                    NeonGlassCard(accentColor = typeColor) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Surface(color = typeColor.copy(alpha = 0.16f), shape = RoundedCornerShape(6.dp)) {
                                    Text(
                                        text = "${q.questionType} • ${q.maxMarks}M",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = typeColor,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                Surface(color = ObsidianBg, shape = RoundedCornerShape(6.dp)) {
                                    Text(
                                        text = q.topicTitle.take(26),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }
                            if (!BundledQuestionBankLoader.isBundledQuestionId(q.id)) {
                                IconButton(onClick = { onDeleteQuestion(q.id) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete question", tint = TextSecondary.copy(alpha = 0.65f))
                                }
                            }
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(q.prompt, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                        if (q.formulaExpr.isNotBlank()) {
                            Spacer(Modifier.height(8.dp))
                            MathFormulaBlock(q.formulaExpr)
                        }
                        if (q.diagramKind != "NONE" || q.imageUri.isNotBlank()) {
                            Spacer(Modifier.height(8.dp))
                            QuestionDiagramView(diagramKind = q.diagramKind, imageUri = q.imageUri)
                        }
                        if (q.markingScheme.isNotEmpty()) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Marking Scheme: ${q.markingScheme.size} criteria",
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldMastery
                            )
                        }
                    }
                }
            }
        } else {
            // Saved Report Cards History
            if (reports.isEmpty()) {
                item {
                    NeonGlassCard(accentColor = HyperBlue) {
                        Text("No Report Cards Yet", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Complete any unit-wise test to automatically generate and save a full report card with marks, time taken, weak topics, strong topics, and improvement goals.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                items(reports, key = { it.id }) { report ->
                    val scoreColor = when {
                        report.percentage >= 75.0 -> EmeraldMastery
                        report.percentage >= 50.0 -> AmberImprove
                        else -> RoseWeak
                    }
                    NeonGlassCard(
                        accentColor = scoreColor,
                        modifier = Modifier.clickable { onOpenReport(report) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(report.subjectTitle, style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                                Text(report.unitTitle, style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                                Text(
                                    text = "Marks: ${report.obtainedMarks}/${report.totalMarks} • Time: ${formatDuration(report.durationSeconds)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                            Surface(
                                color = scoreColor.copy(alpha = 0.16f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, scoreColor.copy(alpha = 0.6f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.Assessment, contentDescription = null, tint = scoreColor, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text("${report.percentage}%", style = MaterialTheme.typography.labelLarge, color = scoreColor)
                                }
                            }
                        }
                        if (report.strongTopics.isNotEmpty() || report.weakTopics.isNotEmpty()) {
                            Spacer(Modifier.height(8.dp))
                            if (report.strongTopics.isNotEmpty()) {
                                Text("Strong: ${report.strongTopics.joinToString(", ")}", style = MaterialTheme.typography.labelSmall, color = EmeraldMastery)
                            }
                            if (report.weakTopics.isNotEmpty()) {
                                Text("Weak: ${report.weakTopics.joinToString(", ")}", style = MaterialTheme.typography.labelSmall, color = RoseWeak)
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(84.dp)) }
    }

    // Dialog: Extract Unit-wise Questions from PDF / Image / Text
    if (showExtractDialog && selectedSubject != null && selectedUnit != null) {
        var pastedText by remember { mutableStateOf("") }
        var pickedBytes by remember { mutableStateOf<ByteArray?>(null) }
        var pickedMime by remember { mutableStateOf<String?>(null) }
        var pickedLabel by remember { mutableStateOf("No PDF/Image selected yet") }
        var pickedImageUri by remember { mutableStateOf("") }

        val docLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                val mime = context.contentResolver.getType(uri) ?: "application/pdf"
                val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                if (bytes != null) {
                    pickedBytes = bytes
                    pickedMime = mime
                    pickedLabel = "Attached file (${bytes.size / 1024} KB)"
                    if (mime.startsWith("image/")) pickedImageUri = uri.toString()
                }
            }
        }

        AlertDialog(
            onDismissRequest = { showExtractDialog = false },
            containerColor = GlassCardSurface,
            title = { Text("Extract Questions for ${selectedUnit.unitNumber}", color = TextPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Upload a PDF/photo of exam questions (with ∫, ∂/∂x, α, β, diagrams) or paste questions below.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    OutlinedButton(
                        onClick = { docLauncher.launch(arrayOf("application/pdf", "image/*")) },
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, NeonBlue)
                    ) {
                        Icon(Icons.Filled.UploadFile, contentDescription = null, tint = NeonBlueBright)
                        Spacer(Modifier.width(8.dp))
                        Text(pickedLabel, color = NeonBlueBright)
                    }
                    OutlinedTextField(
                        value = pastedText,
                        onValueChange = { pastedText = it },
                        label = { Text("Or paste questions / marking scheme text") },
                        minLines = 3,
                        maxLines = 5,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (pickedBytes != null || pastedText.isNotBlank()) {
                            onExtractQuestions(
                                selectedSubject,
                                selectedUnit,
                                pickedBytes,
                                pickedMime,
                                pastedText,
                                pickedImageUri
                            )
                            showExtractDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonBlue, contentColor = Color(0xFF001C27))
                ) {
                    Text("Extract Unit Test")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExtractDialog = false }) { Text("Cancel", color = TextSecondary) }
            }
        )
    }

    // Dialog: Manually Add Advanced Math/Subjective Question + Diagram Image
    if (showAddQuestionDialog && selectedSubject != null && selectedUnit != null) {
        var qType by remember { mutableStateOf("SUBJECTIVE") }
        var topicTitle by remember { mutableStateOf(selectedUnit.topics.firstOrNull()?.title ?: selectedUnit.title) }
        var prompt by remember { mutableStateOf("") }
        var formula by remember { mutableStateOf("") }
        var marksStr by remember { mutableStateOf("5") }
        var modelAnswer by remember { mutableStateOf("") }
        var schemeText by remember { mutableStateOf("1M: State core formula\n2M: Derivation / substitution\n2M: Final simplified answer") }
        var optA by remember { mutableStateOf("") }
        var optB by remember { mutableStateOf("") }
        var optC by remember { mutableStateOf("") }
        var optD by remember { mutableStateOf("") }
        var correctIdx by remember { mutableIntStateOf(0) }
        var pickedPhotoUri by remember { mutableStateOf("") }

        val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) pickedPhotoUri = uri.toString()
        }

        AlertDialog(
            onDismissRequest = { showAddQuestionDialog = false },
            containerColor = GlassCardSurface,
            title = { Text("Add ${selectedUnit.unitNumber} Question", color = TextPrimary) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth().height(390.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        item {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("SUBJECTIVE", "MCQ").forEach { t ->
                                    FilterChip(selected = qType == t, onClick = { qType = t }, label = { Text(t) })
                                }
                            }
                        }
                        item {
                            OutlinedTextField(value = topicTitle, onValueChange = { topicTitle = it }, label = { Text("Topic") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                        }
                        item {
                            OutlinedTextField(value = prompt, onValueChange = { prompt = it }, label = { Text("Question Prompt") }, minLines = 2, modifier = Modifier.fillMaxWidth())
                        }
                        item {
                            MathSymbolToolbar(onInsertSymbol = { sym -> formula += sym })
                            Spacer(Modifier.height(4.dp))
                            OutlinedTextField(value = formula, onValueChange = { formula = it }, label = { Text("Math Formula (∫, ∂/∂x, α, β...)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                        }
                        item {
                            OutlinedButton(
                                onClick = { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Filled.Image, contentDescription = null, tint = NeonBlueBright)
                                Spacer(Modifier.width(6.dp))
                                Text(if (pickedPhotoUri.isBlank()) "Attach Figure / Diagram Photo" else "Diagram Attached", color = NeonBlueBright)
                            }
                        }
                        if (qType == "MCQ") {
                            item {
                                OutlinedTextField(value = optA, onValueChange = { optA = it }, label = { Text("Option A") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                                OutlinedTextField(value = optB, onValueChange = { optB = it }, label = { Text("Option B") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                                OutlinedTextField(value = optC, onValueChange = { optC = it }, label = { Text("Option C") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                                OutlinedTextField(value = optD, onValueChange = { optD = it }, label = { Text("Option D") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    listOf("A", "B", "C", "D").forEachIndexed { idx, l ->
                                        FilterChip(selected = correctIdx == idx, onClick = { correctIdx = idx }, label = { Text("Ans: $l") })
                                    }
                                }
                            }
                        } else {
                            item {
                                OutlinedTextField(value = marksStr, onValueChange = { marksStr = it.filter { c -> c.isDigit() } }, label = { Text("Max Marks") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                                OutlinedTextField(value = modelAnswer, onValueChange = { modelAnswer = it }, label = { Text("Model Answer") }, minLines = 2, modifier = Modifier.fillMaxWidth())
                                OutlinedTextField(value = schemeText, onValueChange = { schemeText = it }, label = { Text("Marking Scheme (one step per line)") }, minLines = 3, modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (prompt.isNotBlank()) {
                            val opts = if (qType == "MCQ") listOf(optA, optB, optC, optD).filter { it.isNotBlank() } else emptyList()
                            val scheme = schemeText.lines().map { it.trim() }.filter { it.isNotBlank() }
                            onAddManualQuestion(
                                TestQuestion(
                                    subjectId = selectedSubject.id,
                                    subjectTitle = selectedSubject.title,
                                    unitId = selectedUnit.unitId,
                                    unitTitle = "${selectedUnit.unitNumber}: ${selectedUnit.title}",
                                    topicTitle = topicTitle.ifBlank { selectedUnit.title },
                                    questionType = qType,
                                    prompt = prompt.trim(),
                                    formulaExpr = formula.trim(),
                                    diagramKind = if (pickedPhotoUri.isNotBlank()) "UPLOADED_IMAGE" else "NONE",
                                    imageUri = pickedPhotoUri,
                                    options = opts,
                                    correctOptionIndex = if (qType == "MCQ") correctIdx.toLong() else -1L,
                                    maxMarks = marksStr.toLongOrNull()?.coerceIn(1L, 50L) ?: 2L,
                                    modelAnswer = modelAnswer.trim(),
                                    markingScheme = scheme
                                )
                            )
                            showAddQuestionDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonBlue, contentColor = Color(0xFF001C27))
                ) {
                    Text("Save Question")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddQuestionDialog = false }) { Text("Cancel", color = TextSecondary) }
            }
        )
    }
}

fun formatDuration(seconds: Long): String {
    val safe = seconds.coerceAtLeast(0L)
    val hrs = safe / 3600L
    val mins = (safe % 3600L) / 60L
    val secs = safe % 60L
    return if (hrs > 0L) {
        String.format("%d:%02d:%02d", hrs, mins, secs)
    } else {
        String.format("%02d:%02d", mins, secs)
    }
}

private fun shortSubjectLabel(subj: SubjectDoc): String = when (subj.courseCode.uppercase()) {
    "100102" -> "Math-I"
    "100104" -> "Physics"
    "100105" -> "Intro to AI"
    "100108" -> "CFET"
    "100109" -> "UHV"
    "100111" -> "BEEE"
    "100104P" -> "Physics Lab"
    "100111P" -> "BEEE Lab"
    "100112P" -> "PPS Lab"
    else -> subj.title.take(18)
}
