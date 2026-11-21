package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.model.SubjectDoc
import com.example.model.TopicItem
import com.example.model.UnitItem
import com.example.ui.components.NeonGlassCard
import com.example.ui.theme.EmeraldMastery
import com.example.ui.theme.FlameCore
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
fun SyllabusScreen(
    listState: LazyListState,
    subjects: List<SubjectDoc>,
    extractionState: ExtractionBannerState,
    onToggleTopic: (SubjectDoc, UnitItem, TopicItem) -> Unit,
    onAddTopicToTodo: (SubjectDoc, UnitItem, TopicItem) -> Unit,
    onPracticeUnitTest: (SubjectDoc, UnitItem) -> Unit,
    onImportSyllabusFile: (ByteArray, String) -> Unit,
    onReloadDefaultSyllabus: () -> Unit,
    onDismissExtractionError: () -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var categoryFilter by remember { mutableStateOf("ALL") } // ALL, THEORY, LAB
    var expandedSubjectIds by remember { mutableStateOf(setOf("sub_100102")) }
    var expandedUnitIds by remember { mutableStateOf(setOf("u_100102_1", "u_100102_4")) }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val mimeType = context.contentResolver.getType(uri) ?: "application/pdf"
            val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            if (bytes != null && bytes.isNotEmpty()) {
                onImportSyllabusFile(bytes, mimeType)
            }
        }
    }

    val filteredSubjects = remember(subjects, searchQuery, categoryFilter) {
        subjects
            .filter { categoryFilter == "ALL" || it.category == categoryFilter }
            .filter { subj ->
                if (searchQuery.isBlank()) true
                else {
                    val q = searchQuery.trim().lowercase()
                    subj.title.lowercase().contains(q) ||
                        subj.courseCode.lowercase().contains(q) ||
                        subj.units.any { u ->
                            u.title.lowercase().contains(q) ||
                                u.topics.any { t -> t.title.lowercase().contains(q) }
                        }
                }
            }
            .sortedWith(compareBy<SubjectDoc> { it.sortOrder }.thenBy { it.courseCode })
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .testTag("syllabus_list"),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header + PDF Upload Button
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Syllabus Explorer",
                            style = MaterialTheme.typography.headlineMedium,
                            color = TextPrimary
                        )
                        Text(
                            text = "Organized: Subject → Unit → Topic",
                            style = MaterialTheme.typography.bodySmall,
                            color = NeonBlueBright
                        )
                    }
                    Button(
                        onClick = {
                            pdfPickerLauncher.launch(arrayOf("application/pdf", "image/*"))
                        },
                        enabled = !extractionState.isWorking,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonBlue,
                            contentColor = Color(0xFF001C27)
                        ),
                        modifier = Modifier.testTag("upload_syllabus_pdf_button")
                    ) {
                        Icon(Icons.Filled.PictureAsPdf, contentDescription = "Extract Syllabus PDF", modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Import PDF", style = MaterialTheme.typography.labelLarge)
                    }
                }

                Spacer(Modifier.height(10.dp))

                // AI Extraction Progress / Error Banner
                if (extractionState.isWorking) {
                    NeonGlassCard(accentColor = NeonBlue) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = NeonBlue,
                                strokeWidth = 2.5.dp
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = extractionState.statusText,
                                style = MaterialTheme.typography.bodySmall,
                                color = NeonBlueBright
                            )
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                } else if (extractionState.errorText != null) {
                    Surface(
                        color = RoseWeak.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, RoseWeak.copy(alpha = 0.55f)),
                        modifier = Modifier.fillMaxWidth().clickable { onDismissExtractionError() }
                    ) {
                        Text(
                            text = "${extractionState.errorText} (Tap to dismiss)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFFFB4C2),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }

                // Search bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search subject, unit, or topic...", color = TextSecondary) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = NeonBlue) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonBlue,
                        unfocusedBorderColor = SubtleBorder,
                        focusedContainerColor = ObsidianBg,
                        unfocusedContainerColor = ObsidianBg,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("syllabus_search_input")
                )

                Spacer(Modifier.height(8.dp))

                // Filter Chips
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("ALL" to "All (${subjects.size})", "THEORY" to "Theory", "LAB" to "Labs").forEach { (key, label) ->
                        FilterChip(
                            selected = categoryFilter == key,
                            onClick = { categoryFilter = key },
                            label = { Text(label, style = MaterialTheme.typography.labelMedium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NeonBlue.copy(alpha = 0.2f),
                                selectedLabelColor = NeonBlueBright,
                                containerColor = ObsidianBg,
                                labelColor = TextSecondary
                            )
                        )
                    }
                }
            }
        }

        if (filteredSubjects.isEmpty()) {
            item {
                NeonGlassCard(accentColor = HyperBlue) {
                    Text(
                        text = "No matching syllabus subjects found.",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = onReloadDefaultSyllabus,
                        border = BorderStroke(1.dp, NeonBlue)
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = null, tint = NeonBlue)
                        Spacer(Modifier.width(8.dp))
                        Text("Load B.Tech Group-A 1st Sem Syllabus", color = NeonBlueBright)
                    }
                }
            }
        }

        // Hierarchical Subject -> Unit -> Topic Cards
        items(filteredSubjects, key = { it.id }) { subject ->
            val isSubjectExpanded = subject.id in expandedSubjectIds
            val accent = if (subject.category == "LAB") FlameCore else NeonBlue

            NeonGlassCard(accentColor = accent) {
                // Subject Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expandedSubjectIds = if (isSubjectExpanded) {
                                expandedSubjectIds - subject.id
                            } else {
                                expandedSubjectIds + subject.id
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = accent.copy(alpha = 0.16f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "${subject.courseCode} • ${subject.credits} Cr",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = accent,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "${subject.completedTopicsCount}/${subject.totalTopicsCount}",
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldMastery
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = subject.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        imageVector = if (isSubjectExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = "Expand subject",
                        tint = NeonBlueBright
                    )
                }

                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { subject.progress },
                    modifier = Modifier.fillMaxWidth().height(5.dp),
                    color = accent,
                    trackColor = ObsidianBg,
                    strokeCap = StrokeCap.Round
                )

                // Units List
                AnimatedVisibility(visible = isSubjectExpanded) {
                    Column(
                        modifier = Modifier.padding(top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        subject.units.forEach { unit ->
                            val isUnitExpanded = unit.unitId in expandedUnitIds
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = ObsidianBg.copy(alpha = 0.85f),
                                border = BorderStroke(1.dp, SubtleBorder)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                expandedUnitIds = if (isUnitExpanded) {
                                                    expandedUnitIds - unit.unitId
                                                } else {
                                                    expandedUnitIds + unit.unitId
                                                }
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "${unit.unitNumber} (${unit.hours} hrs) • ${unit.completedTopicsCount}/${unit.totalTopicsCount}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = NeonBlue
                                            )
                                            Text(
                                                text = unit.title,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = TextPrimary,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                        IconButton(
                                            onClick = { onPracticeUnitTest(subject, unit) }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Quiz,
                                                contentDescription = "Take Test for ${unit.unitNumber}",
                                                tint = FlameCore
                                            )
                                        }
                                        Icon(
                                            imageVector = if (isUnitExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                            contentDescription = "Toggle Unit",
                                            tint = TextSecondary
                                        )
                                    }

                                    AnimatedVisibility(visible = isUnitExpanded) {
                                        Column(
                                            modifier = Modifier.padding(top = 8.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            unit.topics.forEach { topic ->
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable { onToggleTopic(subject, unit, topic) }
                                                        .padding(vertical = 2.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Checkbox(
                                                        checked = topic.completed,
                                                        onCheckedChange = { onToggleTopic(subject, unit, topic) },
                                                        colors = CheckboxDefaults.colors(
                                                            checkedColor = EmeraldMastery,
                                                            uncheckedColor = NeonBlue.copy(alpha = 0.7f),
                                                            checkmarkColor = ObsidianBg
                                                        )
                                                    )
                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Text(
                                                            text = "${topic.code}  ${topic.title}",
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = if (topic.completed) TextSecondary else TextPrimary,
                                                            textDecoration = if (topic.completed) TextDecoration.LineThrough else TextDecoration.None
                                                        )
                                                    }
                                                    IconButton(
                                                        onClick = { onAddTopicToTodo(subject, unit, topic) }
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Filled.AddTask,
                                                            contentDescription = "Add topic ${topic.code} to To-Do",
                                                            tint = NeonBlueBright,
                                                            modifier = Modifier.size(20.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(84.dp)) }
    }
}
