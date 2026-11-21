package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.model.StudyExamBuilder
import com.example.model.SubjectDoc
import com.example.model.TestReport
import com.example.model.TimetableSlot
import com.example.model.TodoItem
import com.example.model.UserProfile
import com.example.ui.components.AnimatedFireStreakHero
import com.example.ui.components.NeonGlassCard
import com.example.ui.theme.AmberImprove
import com.example.ui.theme.EmeraldMastery
import com.example.ui.theme.FlameCore
import com.example.ui.theme.HyperBlue
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonBlueBright
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.RoseWeak
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.dayOfWeekShort
import java.util.Calendar

@Composable
fun DashboardScreen(
    listState: LazyListState,
    profile: UserProfile?,
    subjects: List<SubjectDoc>,
    todos: List<TodoItem>,
    timetable: List<TimetableSlot>,
    reports: List<TestReport>,
    onNavigateTab: (Int) -> Unit,
    onToggleTodo: (TodoItem) -> Unit,
    onOpenReport: (TestReport) -> Unit,
    onOpenMakina: () -> Unit = {},
    onRetryWeakTopics: () -> Unit = {}
) {
    val scrollOffsetPx by remember {
        derivedStateOf {
            (listState.firstVisibleItemIndex * 220 + listState.firstVisibleItemScrollOffset).toFloat()
        }
    }

    val totalTopics = subjects.sumOf { it.totalTopicsCount }
    val doneTopics = subjects.sumOf { it.completedTopicsCount }
    val masteryRatio = if (totalTopics == 0) 0f else doneTopics.toFloat() / totalTopics
    val pendingTodos = todos.filter { !it.completed }

    val todayCalendarDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    val todayIsoDay = when (todayCalendarDay) {
        Calendar.MONDAY -> 1
        Calendar.TUESDAY -> 2
        Calendar.WEDNESDAY -> 3
        Calendar.THURSDAY -> 4
        Calendar.FRIDAY -> 5
        Calendar.SATURDAY -> 6
        else -> 7
    }
    val hasTodayClasses = timetable.any { it.dayOfWeek.toInt() == todayIsoDay }
    val todaySlots = timetable.filter { it.dayOfWeek.toInt() == todayIsoDay }.ifEmpty { timetable.take(3) }
    val latestReport = reports.firstOrNull()
    val weakSignalCount = remember(reports) { StudyExamBuilder.countWeakAndMissedSignals(reports) }
    val topWeakTopics = remember(reports) {
        reports.flatMap { it.weakTopics + it.improveTopics }
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .distinct()
            .take(3)
    }
    val subjectStats = remember(reports) { StudyExamBuilder.computeSubjectPerformance(reports) }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .testTag("dashboard_list"),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Greeting & Scroll-Synced Animated Fire Streak Hero
        item {
            Column {
                Text(
                    text = "Welcome back, ${profile?.displayName?.substringBefore(" ") ?: "Scholar"}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Swipe left or right anytime to switch study modules",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(Modifier.height(12.dp))
                AnimatedFireStreakHero(
                    streakDays = profile?.streakDays ?: 1L,
                    longestStreak = profile?.longestStreak ?: 1L,
                    xp = profile?.xp ?: 0L,
                    scrollOffsetPx = scrollOffsetPx
                )
            }
        }

        // 2. Semester Mastery & Quick Stats Card
        item {
            NeonGlassCard(accentColor = NeonBlue) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "SYLLABUS MASTERY",
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonBlue
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "$doneTopics / $totalTopics Topics Completed",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "${subjects.size} courses • ${reports.size} tests • ${profile?.unlockedBadges?.size ?: 0} badges",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(66.dp)) {
                        CircularProgressIndicator(
                            progress = { masteryRatio },
                            modifier = Modifier.fillMaxSize(),
                            color = NeonBlue,
                            trackColor = ObsidianBg,
                            strokeWidth = 6.dp,
                            strokeCap = StrokeCap.Round
                        )
                        Text(
                            text = "${(masteryRatio * 100).toInt()}%",
                            style = MaterialTheme.typography.labelLarge,
                            color = NeonBlueBright
                        )
                    }
                }
            }
        }

        // 3. Quick Module Jump Grid (2x2 clean grid including Ask Makina)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionTile(
                        icon = Icons.AutoMirrored.Filled.MenuBook,
                        label = "Syllabus",
                        caption = "${subjects.size} Courses",
                        accent = NeonBlue,
                        modifier = Modifier.weight(1f).testTag("quick_syllabus_btn"),
                        onClick = { onNavigateTab(1) }
                    )
                    QuickActionTile(
                        icon = Icons.Filled.Quiz,
                        label = "Test Lab",
                        caption = "Unit Quizzes",
                        accent = HyperBlue,
                        modifier = Modifier.weight(1f).testTag("quick_test_btn"),
                        onClick = { onNavigateTab(3) }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionTile(
                        icon = Icons.Filled.EmojiEvents,
                        label = "Ranks",
                        caption = "${profile?.xp ?: 0} XP",
                        accent = FlameCore,
                        modifier = Modifier.weight(1f).testTag("quick_ranks_btn"),
                        onClick = { onNavigateTab(4) }
                    )
                    QuickActionTile(
                        icon = Icons.Filled.AutoAwesome,
                        label = "Ask Makina",
                        caption = "AI Tour & Doubts",
                        accent = Color(0xFFFFC95C),
                        modifier = Modifier.weight(1f).testTag("dashboard_ask_makina_btn"),
                        onClick = onOpenMakina
                    )
                }
            }
        }

        // 4. Priority To-Do Focus (Synced with Syllabus)
        item {
            NeonGlassCard(accentColor = EmeraldMastery) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.TaskAlt, contentDescription = "Pending Tasks", tint = EmeraldMastery)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Up Next (${pendingTodos.size})",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary
                        )
                    }
                    Surface(
                        onClick = { onNavigateTab(2) },
                        shape = RoundedCornerShape(10.dp),
                        color = NeonBlue.copy(alpha = 0.14f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Open All", style = MaterialTheme.typography.labelSmall, color = NeonBlueBright)
                            Spacer(Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NeonBlueBright, modifier = Modifier.size(14.dp))
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
                if (pendingTodos.isEmpty()) {
                    Text(
                        text = "No pending tasks yet. Pick any topic from Syllabus or Timetable to auto-sync your daily goals!",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                } else {
                    pendingTodos.take(3).forEach { task ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onToggleTodo(task) },
                            shape = RoundedCornerShape(12.dp),
                            color = ObsidianBg.copy(alpha = 0.72f),
                            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = "Complete Task",
                                    tint = TextSecondary.copy(alpha = 0.6f),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = task.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextPrimary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "${task.sourceType} • ${task.dueLabel}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (task.sourceType == "SYLLABUS") NeonBlue else TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Latest Report Card Preview
        if (latestReport != null) {
            item {
                val scoreColor = when {
                    latestReport.percentage >= 75.0 -> EmeraldMastery
                    latestReport.percentage >= 50.0 -> AmberImprove
                    else -> RoseWeak
                }
                NeonGlassCard(
                    accentColor = scoreColor,
                    modifier = Modifier.clickable { onOpenReport(latestReport) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Assessment, contentDescription = "Latest Report", tint = scoreColor)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Latest Report Card",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary
                            )
                        }
                        Text(
                            text = "${latestReport.percentage}%",
                            style = MaterialTheme.typography.titleLarge,
                            color = scoreColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "${latestReport.subjectTitle} • ${latestReport.unitTitle}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { (latestReport.percentage / 100.0).toFloat().coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth().height(6.dp),
                        color = scoreColor,
                        trackColor = ObsidianBg,
                        strokeCap = StrokeCap.Round
                    )
                    if (latestReport.weakTopics.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Focus areas: ${latestReport.weakTopics.joinToString(", ")}",
                            style = MaterialTheme.typography.labelSmall,
                            color = RoseWeak,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // 5b. Weak-Topic Retry Quiz / Mistakes Deck
        if (weakSignalCount > 0) {
            item {
                NeonGlassCard(accentColor = RoseWeak) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "MISTAKES DECK • WEAK TOPICS",
                                style = MaterialTheme.typography.labelSmall,
                                color = RoseWeak
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Targeted Revision Quiz Ready",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            if (topWeakTopics.isNotEmpty()) {
                                Spacer(Modifier.height(2.dp))
                                Text(
                                    text = topWeakTopics.joinToString(" • "),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(Modifier.width(10.dp))
                        Button(
                            onClick = onRetryWeakTopics,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = FlameCore, contentColor = Color.White),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                            modifier = Modifier.testTag("dashboard_retry_weak_topics_btn")
                        ) {
                            Icon(Icons.Filled.Replay, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(5.dp))
                            Text("Practice", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 5c. Subject-Wise Accuracy Breakdown & Trend Card
        if (subjectStats.isNotEmpty()) {
            item {
                NeonGlassCard(
                    accentColor = NeonBlue,
                    modifier = Modifier.testTag("subject_performance_card")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SUBJECT PERFORMANCE",
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonBlue
                        )
                        Text(
                            text = "${subjectStats.size} Subject(s)",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    subjectStats.forEachIndexed { idx, stat ->
                        val statColor = when {
                            stat.averagePercentage >= 75.0 -> EmeraldMastery
                            stat.averagePercentage >= 50.0 -> AmberImprove
                            else -> RoseWeak
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateTab(3) }
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stat.subjectTitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Avg ${stat.averagePercentage}% • Best ${stat.bestPercentage}% (${stat.testsTaken})",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = statColor,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { (stat.averagePercentage / 100.0).toFloat().coerceIn(0f, 1f) },
                                modifier = Modifier.fillMaxWidth().height(5.dp),
                                color = statColor,
                                trackColor = ObsidianBg,
                                strokeCap = StrokeCap.Round
                            )
                        }
                        if (idx < subjectStats.lastIndex) {
                            Spacer(Modifier.height(4.dp))
                        }
                    }
                }
            }
        }

        // 6. Today's Timetable Preview
        item {
            NeonGlassCard(accentColor = HyperBlue) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.CalendarMonth, contentDescription = "Timetable", tint = HyperBlue)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (hasTodayClasses) "Today's CSE-A Schedule" else "Upcoming CSE-A Classes",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary
                        )
                    }
                    Text(
                        text = if (hasTodayClasses) dayOfWeekShort(todayIsoDay) else "Mon Preview",
                        style = MaterialTheme.typography.labelMedium,
                        color = NeonBlueBright
                    )
                }
                Spacer(Modifier.height(10.dp))
                todaySlots.take(6).forEach { slot ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = HyperBlue.copy(alpha = 0.16f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "${slot.startTime}–${slot.endTime}",
                                style = MaterialTheme.typography.labelSmall,
                                color = NeonBlueBright,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = slot.subjectTitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            val meta = listOf(slot.unitHint, slot.room).filter { it.isNotBlank() }.joinToString(" • ")
                            if (meta.isNotBlank()) {
                                Text(
                                    text = meta,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextSecondary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(84.dp)) }
    }
}

@Composable
private fun QuickActionTile(
    icon: ImageVector,
    label: String,
    caption: String,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF111C33),
        border = BorderStroke(1.dp, accent.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, tint = accent, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(6.dp))
            Text(label, style = MaterialTheme.typography.labelLarge, color = TextPrimary)
            Text(caption, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
        }
    }
}
