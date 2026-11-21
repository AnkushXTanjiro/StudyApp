package com.example.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import com.example.model.LeaderboardEntry
import com.example.model.StudyBadges
import com.example.model.UserProfile
import com.example.notifications.StudyNotificationScheduler
import com.example.ui.components.NeonGlassCard
import com.example.ui.theme.EmeraldMastery
import com.example.ui.theme.FlameCore
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.HyperBlue
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonBlueBright
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.RoseWeak
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun LeaderboardBadgesScreen(
    listState: LazyListState,
    currentUserId: String,
    profile: UserProfile?,
    leaderboard: List<LeaderboardEntry>,
    isOfflineMode: Boolean,
    onToggleOfflineMode: (Boolean) -> Unit,
    onSaveReminderSettings: (Boolean, Int, Int) -> Unit,
    onOpenDeveloper: () -> Unit = {},
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    var remindersEnabled by remember(profile?.remindersEnabled) { mutableStateOf(profile?.remindersEnabled ?: false) }
    var reminderHour by remember(profile?.reminderHour) { mutableIntStateOf((profile?.reminderHour ?: 19L).toInt()) }
    var notificationStatus by remember { mutableStateOf("") }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            StudyNotificationScheduler.scheduleDailyReminder(context, reminderHour, 0)
            remindersEnabled = true
            onSaveReminderSettings(true, reminderHour, 0)
            notificationStatus = "Daily streak reminder active at ${String.format("%02d:00", reminderHour)}"
        } else {
            notificationStatus = "Notification permission denied. Enable it in system settings to receive alerts."
        }
    }

    val unlockedSet = profile?.unlockedBadges?.toSet().orEmpty()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .testTag("ranks_badges_list"),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text("Leaderboard & Badges", style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                Text(
                    "Compete on daily study streaks, XP & unlock academic mastery badges",
                    style = MaterialTheme.typography.bodySmall,
                    color = NeonBlueBright
                )
            }
        }

        // 1. Live Student Leaderboard
        item {
            NeonGlassCard(accentColor = FlameCore) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.EmojiEvents, contentDescription = "Leaderboard", tint = FlameCore)
                        Spacer(Modifier.width(8.dp))
                        Text("Live Scholar Leaderboard", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                    }
                    Text("${leaderboard.size} active", style = MaterialTheme.typography.labelSmall, color = NeonBlueBright)
                }
                Spacer(Modifier.height(10.dp))

                if (leaderboard.isEmpty()) {
                    Text(
                        text = "Complete a syllabus topic or unit test to claim the #1 spot on the cloud leaderboard!",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                } else {
                    leaderboard.take(15).forEachIndexed { idx, entry ->
                        val isMe = entry.userId == currentUserId
                        val rankColor = when (idx) {
                            0 -> FlameCore
                            1 -> NeonBlueBright
                            2 -> FlameOrange
                            else -> TextSecondary
                        }
                        Surface(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = if (isMe) NeonBlue.copy(alpha = 0.16f) else ObsidianBg.copy(alpha = 0.78f),
                            border = BorderStroke(1.dp, if (isMe) NeonBlue else SubtleBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(rankColor.copy(alpha = 0.18f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("#${idx + 1}", style = MaterialTheme.typography.labelSmall, color = rankColor, fontWeight = FontWeight.Bold)
                                }
                                Spacer(Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (isMe) "${entry.displayName} (You)" else entry.displayName,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = if (isMe) NeonBlueBright else TextPrimary,
                                        fontWeight = if (isMe) FontWeight.Bold else FontWeight.Medium
                                    )
                                    Text(
                                        text = "${entry.topicsCompleted} topics • ${entry.testsCompleted} tests • ${entry.badgesCount} badges",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("${entry.xp} XP", style = MaterialTheme.typography.labelLarge, color = NeonBlueBright)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Filled.LocalFireDepartment, contentDescription = null, tint = FlameCore, modifier = Modifier.size(13.dp))
                                        Text("${entry.streakDays}d", style = MaterialTheme.typography.labelSmall, color = FlameCore)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Achievement Badges Grid
        item {
            Text(
                text = "ACHIEVEMENT BADGES (${unlockedSet.size}/${StudyBadges.allBadges.size})",
                style = MaterialTheme.typography.labelMedium,
                color = NeonBlueBright
            )
        }

        itemsIndexed(StudyBadges.allBadges.chunked(2)) { _, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                pair.forEach { badge ->
                    val unlocked = badge.id in unlockedSet
                    val accent = if (unlocked) FlameCore else SubtleBorder
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        color = if (unlocked) Color(0xFF19223B) else ObsidianBg.copy(alpha = 0.85f),
                        border = BorderStroke(1.dp, accent.copy(alpha = if (unlocked) 0.7f else 0.45f))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = when {
                                    !unlocked -> Icons.Filled.Lock
                                    badge.iconKey == "bolt" -> Icons.Filled.Bolt
                                    badge.iconKey == "star" -> Icons.Filled.Star
                                    else -> Icons.Filled.WorkspacePremium
                                },
                                contentDescription = badge.title,
                                tint = if (unlocked) FlameCore else TextSecondary.copy(alpha = 0.55f),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = badge.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = if (unlocked) TextPrimary else TextSecondary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = badge.requirementLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (unlocked) EmeraldMastery else TextSecondary
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = badge.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary,
                                maxLines = 2
                            )
                        }
                    }
                }
                if (pair.size == 1) Spacer(Modifier.weight(1f))
            }
        }

        // 3. Push Reminders & Offline Study Controls
        item {
            NeonGlassCard(accentColor = NeonBlue) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Filled.NotificationsActive, contentDescription = null, tint = NeonBlue)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text("Daily Study Notifications", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                            Text("Streak reminder at ${String.format("%02d:00", reminderHour)}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        }
                    }
                    Switch(
                        checked = remindersEnabled,
                        onCheckedChange = { wantEnable ->
                            if (wantEnable) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    StudyNotificationScheduler.scheduleDailyReminder(context, reminderHour, 0)
                                    remindersEnabled = true
                                    onSaveReminderSettings(true, reminderHour, 0)
                                }
                            } else {
                                StudyNotificationScheduler.cancelDailyReminder(context)
                                remindersEnabled = false
                                onSaveReminderSettings(false, reminderHour, 0)
                            }
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = ObsidianBg, checkedTrackColor = NeonBlue)
                    )
                }

                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(7, 18, 20, 22).forEach { hr ->
                        Surface(
                            onClick = {
                                reminderHour = hr
                                if (remindersEnabled) {
                                    StudyNotificationScheduler.scheduleDailyReminder(context, hr, 0)
                                    onSaveReminderSettings(true, hr, 0)
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            color = if (reminderHour == hr) NeonBlue.copy(alpha = 0.22f) else ObsidianBg,
                            border = BorderStroke(1.dp, if (reminderHour == hr) NeonBlue else SubtleBorder)
                        ) {
                            Text(
                                text = String.format("%02d:00", hr),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (reminderHour == hr) NeonBlueBright else TextSecondary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
                        val sent = StudyNotificationScheduler.showInstantNotification(
                            context = context,
                            title = "StudyApp Streak Active (${profile?.streakDays ?: 1} Days)",
                            body = "Your syllabus & offline quiz bank are synced. Keep the neon flame burning!"
                        )
                        notificationStatus = if (sent) {
                            "Test push reminder delivered!"
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                            "Requesting notification permission..."
                        }
                    },
                    border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth().testTag("send_test_notification_button")
                ) {
                    Text("Send Test Reminder Notification", color = NeonBlueBright)
                }

                if (notificationStatus.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(notificationStatus, style = MaterialTheme.typography.labelSmall, color = EmeraldMastery)
                }
            }
        }

        // 4. Offline Study Mode & Sign Out
        item {
            NeonGlassCard(accentColor = HyperBlue) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Filled.CloudOff, contentDescription = null, tint = FlameCore)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text("Offline Study Mode", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                            Text(
                                "Study without internet using 100MB local Firestore cache; auto-syncs when turned off.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                    }
                    Switch(
                        checked = isOfflineMode,
                        onCheckedChange = onToggleOfflineMode,
                        colors = SwitchDefaults.colors(checkedThumbColor = ObsidianBg, checkedTrackColor = FlameCore),
                        modifier = Modifier.testTag("offline_mode_switch")
                    )
                }

                Spacer(Modifier.height(14.dp))
                Button(
                    onClick = onSignOut,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RoseWeak.copy(alpha = 0.18f),
                        contentColor = Color(0xFFFFB4C2)
                    ),
                    border = BorderStroke(1.dp, RoseWeak.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().testTag("sign_out_button")
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Sign Out", modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Sign Out of Google Account")
                }
            }
        }

        // 5. Meet the Developer Shortcut Card
        item {
            Surface(
                onClick = onOpenDeveloper,
                shape = RoundedCornerShape(18.dp),
                color = ObsidianBg.copy(alpha = 0.9f),
                border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.55f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ranks_developer_shortcut_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(NeonBlue.copy(alpha = 0.16f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Code,
                            contentDescription = "Developer",
                            tint = NeonBlueBright,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Developer & Creator",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Ankush Kumar • GEC Sheikhpura (Batch 2026–2030)",
                            style = MaterialTheme.typography.bodySmall,
                            color = NeonBlueBright
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Open Developer page",
                        tint = NeonBlueBright
                    )
                }
            }
        }

        item { Spacer(Modifier.height(84.dp)) }
    }
}
