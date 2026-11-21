package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.model.SubjectDoc
import com.example.model.TimetableSlot
import com.example.model.TodoItem
import com.example.model.TopicItem
import com.example.model.UnitItem
import com.example.ui.components.NeonGlassCard
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
import com.example.viewmodel.dayOfWeekShort
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTimetableScreen(
    listState: LazyListState,
    todos: List<TodoItem>,
    subjects: List<SubjectDoc>,
    timetable: List<TimetableSlot>,
    extractionState: ExtractionBannerState,
    onAddManualTodo: (String, String, String, String) -> Unit,
    onAddSyllabusTodo: (SubjectDoc, UnitItem, TopicItem) -> Unit,
    onAddTimetableTodo: (TimetableSlot) -> Unit,
    onToggleTodo: (TodoItem) -> Unit,
    onDeleteTodo: (TodoItem) -> Unit,
    onAddTimetableSlot: (Int, String, String, String, String, String, String) -> Unit,
    onDeleteTimetableSlot: (String) -> Unit,
    onImportTimetableFile: (ByteArray, String) -> Unit,
    onReloadOfficialTimetable: () -> Unit = {}
) {
    val context = LocalContext.current
    var activeSegment by remember { mutableIntStateOf(0) } // 0 = To-Do List, 1 = Timetable
    var showManualDialog by remember { mutableStateOf(false) }
    var showSyllabusPickerDialog by remember { mutableStateOf(false) }
    var showAddSlotDialog by remember { mutableStateOf(false) }
    var selectedDay by remember {
        val calDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val iso = when (calDay) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            else -> 7
        }
        mutableIntStateOf(iso)
    }

    val timetablePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val mime = context.contentResolver.getType(uri) ?: "application/pdf"
            val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            if (bytes != null && bytes.isNotEmpty()) {
                onImportTimetableFile(bytes, mime)
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .testTag("todo_timetable_list"),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = if (activeSegment == 0) "Smart To-Do List" else "Weekly Timetable",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary
                )
                Text(
                    text = if (activeSegment == 0) {
                        "Ticking a Syllabus task automatically marks it complete in Syllabus"
                    } else {
                        "Upload PDF/image timetable or tap '+' to push any slot into your To-Do"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = NeonBlueBright
                )
                Spacer(Modifier.height(12.dp))
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    listOf("To-Do (${todos.count { !it.completed }})", "Timetable (${timetable.size})").forEachIndexed { idx, label ->
                        SegmentedButton(
                            selected = activeSegment == idx,
                            onClick = { activeSegment = idx },
                            shape = SegmentedButtonDefaults.itemShape(index = idx, count = 2),
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
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = NeonBlue, strokeWidth = 2.dp)
                        Spacer(Modifier.width(12.dp))
                        Text(extractionState.statusText, style = MaterialTheme.typography.bodySmall, color = NeonBlueBright)
                    }
                }
            }
        }

        if (activeSegment == 0) {
            // Creation source action bar: Write Myself | From Syllabus | From Timetable
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { showManualDialog = true },
                        modifier = Modifier.weight(1f).testTag("add_manual_todo_button"),
                        shape = RoundedCornerShape(13.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonBlue,
                            contentColor = Color(0xFF001C27)
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Write", style = MaterialTheme.typography.labelMedium)
                    }
                    OutlinedButton(
                        onClick = { showSyllabusPickerDialog = true },
                        modifier = Modifier.weight(1f).testTag("add_from_syllabus_button"),
                        shape = RoundedCornerShape(13.dp),
                        border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.6f)),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, tint = NeonBlueBright, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Syllabus", style = MaterialTheme.typography.labelMedium, color = NeonBlueBright)
                    }
                    OutlinedButton(
                        onClick = { activeSegment = 1 },
                        modifier = Modifier.weight(1f).testTag("add_from_timetable_button"),
                        shape = RoundedCornerShape(13.dp),
                        border = BorderStroke(1.dp, FlameCore.copy(alpha = 0.6f)),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                    ) {
                        Icon(Icons.Filled.CalendarMonth, contentDescription = null, tint = FlameCore, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Timetable", style = MaterialTheme.typography.labelMedium, color = FlameCore)
                    }
                }
            }

            if (todos.isEmpty()) {
                item {
                    NeonGlassCard(accentColor = NeonBlue) {
                        Text("Your study queue is clear!", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Create tasks manually, import topics directly from your Syllabus, or pick classes from your Timetable. Swipe left on any task to remove, or swipe right to mark complete.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }

            items(todos, key = { it.id }) { todo ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { value ->
                        when (value) {
                            SwipeToDismissBoxValue.EndToStart -> {
                                onDeleteTodo(todo)
                                true
                            }
                            SwipeToDismissBoxValue.StartToEnd -> {
                                onToggleTodo(todo)
                                false
                            }
                            else -> false
                        }
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        val isCompleteSwipe = dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (isCompleteSwipe) EmeraldMastery.copy(alpha = 0.25f)
                                    else RoseWeak.copy(alpha = 0.25f)
                                )
                                .padding(horizontal = 20.dp),
                            contentAlignment = if (isCompleteSwipe) Alignment.CenterStart else Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = if (isCompleteSwipe) Icons.Filled.CheckCircle else Icons.Filled.Delete,
                                contentDescription = null,
                                tint = if (isCompleteSwipe) EmeraldMastery else RoseWeak
                            )
                        }
                    }
                ) {
                    val accent = when (todo.sourceType) {
                        "SYLLABUS" -> NeonBlue
                        "TIMETABLE" -> FlameCore
                        else -> HyperBlue
                    }
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = GlassCardSurface,
                        border = BorderStroke(
                            1.dp,
                            if (todo.completed) EmeraldMastery.copy(alpha = 0.45f) else accent.copy(alpha = 0.4f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onToggleTodo(todo) }
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = todo.completed,
                                onCheckedChange = { onToggleTodo(todo) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = EmeraldMastery,
                                    uncheckedColor = accent,
                                    checkmarkColor = ObsidianBg
                                )
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = todo.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = if (todo.completed) TextSecondary else TextPrimary,
                                    textDecoration = if (todo.completed) TextDecoration.LineThrough else TextDecoration.None
                                )
                                if (todo.notes.isNotBlank()) {
                                    Text(
                                        text = todo.notes,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary,
                                        maxLines = 2
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = accent.copy(alpha = 0.16f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (todo.sourceType == "SYLLABUS") {
                                                Icon(Icons.Filled.Sync, contentDescription = null, tint = accent, modifier = Modifier.size(11.dp))
                                                Spacer(Modifier.width(3.dp))
                                            }
                                            Text(
                                                text = if (todo.sourceType == "SYLLABUS") "SYLLABUS SYNC" else todo.sourceType,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = accent
                                            )
                                        }
                                    }
                                    Text(
                                        text = "${todo.priority} • ${todo.dueLabel}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = when (todo.priority) {
                                            "HIGH" -> RoseWeak
                                            "MEDIUM" -> AmberImprove
                                            else -> TextSecondary
                                        }
                                    )
                                }
                            }
                            IconButton(onClick = { onDeleteTodo(todo) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete Task", tint = TextSecondary.copy(alpha = 0.7f))
                            }
                        }
                    }
                }
            }
        } else {
            // Timetable Section
            item {
                NeonGlassCard(accentColor = HyperBlue) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "GEC Sheikhpura • CSE-A Sem 1",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Batch 2K26 • Room 104 • Recess 13:00–14:00",
                                style = MaterialTheme.typography.labelSmall,
                                color = NeonBlueBright
                            )
                        }
                        OutlinedButton(
                            onClick = onReloadOfficialTimetable,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.6f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("sync_official_timetable_button")
                        ) {
                            Icon(Icons.Filled.Sync, contentDescription = "Load CSE-A Timetable", tint = NeonBlueBright, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("CSE-A", style = MaterialTheme.typography.labelSmall, color = NeonBlueBright)
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { timetablePicker.launch(arrayOf("application/pdf", "image/*")) },
                        modifier = Modifier.weight(1f).testTag("upload_timetable_pdf_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonBlue, contentColor = Color(0xFF001C27))
                    ) {
                        Icon(Icons.Filled.PictureAsPdf, contentDescription = null, modifier = Modifier.size(17.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Import Timetable", style = MaterialTheme.typography.labelLarge)
                    }
                    OutlinedButton(
                        onClick = { showAddSlotDialog = true },
                        modifier = Modifier.weight(1f).testTag("add_timetable_slot_button"),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.65f))
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null, tint = NeonBlueBright, modifier = Modifier.size(17.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Add Slot", style = MaterialTheme.typography.labelLarge, color = NeonBlueBright)
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    (1..7).forEach { day ->
                        FilterChip(
                            selected = selectedDay == day,
                            onClick = { selectedDay = day },
                            label = { Text(dayOfWeekShort(day)) },
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

            val daySlots = timetable.filter { it.dayOfWeek.toInt() == selectedDay }
            if (daySlots.isEmpty()) {
                item {
                    NeonGlassCard(accentColor = HyperBlue) {
                        Text(
                            text = "No slots scheduled for ${dayOfWeekShort(selectedDay)}.",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Sunday is free for revision, or tap 'Add Slot' / 'CSE-A' above to manage your schedule.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                items(daySlots, key = { it.id }) { slot ->
                    val slotAccent = when (slot.slotType) {
                        "LAB" -> FlameCore
                        "SELF_STUDY" -> EmeraldMastery
                        else -> NeonBlue
                    }
                    NeonGlassCard(accentColor = slotAccent) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${slot.startTime} – ${slot.endTime} • ${slot.slotType}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = slotAccent
                                )
                                Spacer(Modifier.height(3.dp))
                                Text(
                                    text = slot.subjectTitle,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                if (slot.unitHint.isNotBlank() || slot.room.isNotBlank()) {
                                    Text(
                                        text = listOf(slot.unitHint, slot.room).filter { it.isNotBlank() }.joinToString(" • "),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary
                                    )
                                }
                            }
                            IconButton(onClick = { onAddTimetableTodo(slot) }) {
                                Icon(
                                    imageVector = Icons.Filled.AddTask,
                                    contentDescription = "Add timetable slot to To-Do",
                                    tint = NeonBlueBright
                                )
                            }
                            IconButton(onClick = { onDeleteTimetableSlot(slot.id) }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete timetable slot",
                                    tint = TextSecondary.copy(alpha = 0.65f)
                                )
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(84.dp)) }
    }

    // Dialog 1: Write Manual To-Do
    if (showManualDialog) {
        var title by remember { mutableStateOf("") }
        var notes by remember { mutableStateOf("") }
        var priority by remember { mutableStateOf("HIGH") }
        var dueLabel by remember { mutableStateOf("Today") }
        AlertDialog(
            onDismissRequest = { showManualDialog = false },
            containerColor = GlassCardSurface,
            title = { Text("New Study Task", color = TextPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Task Title") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes (optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = dueLabel,
                        onValueChange = { dueLabel = it },
                        label = { Text("Due / Target Time") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("HIGH", "MEDIUM", "LOW").forEach { p ->
                            FilterChip(
                                selected = priority == p,
                                onClick = { priority = p },
                                label = { Text(p) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            onAddManualTodo(title, notes, priority, dueLabel)
                            showManualDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonBlue, contentColor = Color(0xFF001C27))
                ) {
                    Text("Save Task")
                }
            },
            dismissButton = {
                TextButton(onClick = { showManualDialog = false }) { Text("Cancel", color = TextSecondary) }
            }
        )
    }

    // Dialog 2: Pick Topic from Syllabus (Subject -> Unit -> Topic)
    if (showSyllabusPickerDialog && subjects.isNotEmpty()) {
        var chosenSubject by remember { mutableStateOf(subjects.first()) }
        var chosenUnit by remember { mutableStateOf(chosenSubject.units.firstOrNull()) }

        AlertDialog(
            onDismissRequest = { showSyllabusPickerDialog = false },
            containerColor = GlassCardSurface,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = NeonBlue)
                    Spacer(Modifier.width(8.dp))
                    Text("Add From Syllabus", color = TextPrimary)
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("1. Subject", style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        subjects.forEach { subj ->
                            FilterChip(
                                selected = chosenSubject.id == subj.id,
                                onClick = {
                                    chosenSubject = subj
                                    chosenUnit = subj.units.firstOrNull()
                                },
                                label = { Text(subj.courseCode) }
                            )
                        }
                    }
                    Text(chosenSubject.title, style = MaterialTheme.typography.titleSmall, color = TextPrimary)

                    Text("2. Unit", style = MaterialTheme.typography.labelSmall, color = NeonBlue)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        chosenSubject.units.forEach { u ->
                            FilterChip(
                                selected = chosenUnit?.unitId == u.unitId,
                                onClick = { chosenUnit = u },
                                label = { Text(u.unitNumber) }
                            )
                        }
                    }

                    Text("3. Tap a Topic to Add (Auto-Syncs)", style = MaterialTheme.typography.labelSmall, color = EmeraldMastery)
                    val unit = chosenUnit
                    if (unit != null) {
                        Column(
                            modifier = Modifier.height(210.dp).fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(unit.topics) { topic ->
                                    Surface(
                                        onClick = {
                                            onAddSyllabusTodo(chosenSubject, unit, topic)
                                            showSyllabusPickerDialog = false
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        color = ObsidianBg,
                                        border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.35f)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "${topic.code}  ${topic.title}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TextPrimary,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSyllabusPickerDialog = false }) { Text("Close", color = NeonBlueBright) }
            }
        )
    }

    // Dialog 3: Add Manual Timetable Slot
    if (showAddSlotDialog) {
        var subjectTitle by remember { mutableStateOf("") }
        var startTime by remember { mutableStateOf("09:00") }
        var endTime by remember { mutableStateOf("10:00") }
        var unitHint by remember { mutableStateOf("") }
        var room by remember { mutableStateOf("") }
        var slotType by remember { mutableStateOf("LECTURE") }

        AlertDialog(
            onDismissRequest = { showAddSlotDialog = false },
            containerColor = GlassCardSurface,
            title = { Text("Add ${dayOfWeekShort(selectedDay)} Slot", color = TextPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = subjectTitle, onValueChange = { subjectTitle = it }, label = { Text("Subject / Session") }, singleLine = true)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = startTime, onValueChange = { startTime = it }, label = { Text("Start") }, singleLine = true, modifier = Modifier.weight(1f))
                        OutlinedTextField(value = endTime, onValueChange = { endTime = it }, label = { Text("End") }, singleLine = true, modifier = Modifier.weight(1f))
                    }
                    OutlinedTextField(value = unitHint, onValueChange = { unitHint = it }, label = { Text("Unit / Topic Focus") }, singleLine = true)
                    OutlinedTextField(value = room, onValueChange = { room = it }, label = { Text("Room / Lab") }, singleLine = true)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("LECTURE", "LAB", "SELF_STUDY").forEach { t ->
                            FilterChip(selected = slotType == t, onClick = { slotType = t }, label = { Text(t.replace("_", " ")) })
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (subjectTitle.isNotBlank()) {
                            onAddTimetableSlot(selectedDay, startTime, endTime, subjectTitle, unitHint, room, slotType)
                            showAddSlotDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonBlue, contentColor = Color(0xFF001C27))
                ) {
                    Text("Save Slot")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddSlotDialog = false }) { Text("Cancel", color = TextSecondary) }
            }
        )
    }
}
