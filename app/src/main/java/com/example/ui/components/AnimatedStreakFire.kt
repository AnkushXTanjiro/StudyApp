package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.EmeraldMastery
import com.example.ui.theme.FlameCore
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.FlameRed
import com.example.ui.theme.GlassCardSurface
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonBlueBright
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Scroll-reactive Animated Fire Streak Lines overlay.
 * Renders glowing neon-blue & solar-flame energy ribbons that shift, ripple, and rise dynamically
 * as the user scrolls through any study module.
 */
@Composable
fun ScrollReactiveFireStreakRail(
    scrollProgressPx: Float,
    streakDays: Long,
    modifier: Modifier = Modifier
) {
    val infinite = rememberInfiniteTransition(label = "fire_streak_motion")
    val wavePhase by infinite.animateFloat(
        initialValue = 0f,
        targetValue = (2.0 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase"
    )
    val emberPhase by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ember_phase"
    )

    val scrollShift = (scrollProgressPx * 0.014f)

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        if (w <= 0f || h <= 0f) return@Canvas

        val intensity = (0.55f + (streakDays.coerceAtMost(14L) / 28f)).coerceAtMost(1f)

        // Left vertical plasma-fire streak filament reacting to scroll
        val leftFlamePath = Path()
        val steps = 28
        for (i in 0..steps) {
            val t = i.toFloat() / steps
            val y = t * h
            val x = 6.dp.toPx() + sin(t * 7f - wavePhase - scrollShift) * 4.dp.toPx()
            if (i == 0) leftFlamePath.moveTo(x, y) else leftFlamePath.lineTo(x, y)
        }
        drawPath(
            path = leftFlamePath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    FlameCore.copy(alpha = 0.08f * intensity),
                    FlameOrange.copy(alpha = 0.45f * intensity),
                    NeonBlue.copy(alpha = 0.35f * intensity),
                    FlameRed.copy(alpha = 0.05f * intensity)
                )
            ),
            style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
        )

        // Right neon-blue streak line counter-moving with scroll
        val rightNeonPath = Path()
        for (i in 0..steps) {
            val t = i.toFloat() / steps
            val y = t * h
            val x = w - 6.dp.toPx() + cos(t * 6f + wavePhase + scrollShift * 1.15f) * 3.5.dp.toPx()
            if (i == 0) rightNeonPath.moveTo(x, y) else rightNeonPath.lineTo(x, y)
        }
        drawPath(
            path = rightNeonPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    NeonBlue.copy(alpha = 0.05f),
                    NeonBlue.copy(alpha = 0.42f * intensity),
                    FlameOrange.copy(alpha = 0.32f * intensity),
                    NeonBlue.copy(alpha = 0.08f)
                )
            ),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )

        // Floating rising fire embers drifting along the scroll axis
        for (idx in 0 until 7) {
            val seed = idx * 0.145f
            val rawY = ((1f - ((emberPhase + seed + (scrollProgressPx * 0.00045f)) % 1f)) + 1f) % 1f
            val y = rawY * h
            val isLeft = idx % 2 == 0
            val baseX = if (isLeft) 11.dp.toPx() else w - 11.dp.toPx()
            val x = baseX + sin(wavePhase + idx * 1.3f + scrollShift) * 5.dp.toPx()
            val radius = (2.2f + (idx % 3)).dp.toPx()
            drawCircle(
                color = if (idx % 3 == 0) NeonBlueBright.copy(alpha = 0.5f) else FlameCore.copy(alpha = 0.55f),
                radius = radius,
                center = Offset(x, y)
            )
        }
    }
}

/**
 * Hero Streak Banner displaying animated flame waves, streak day counter, XP, and level bar.
 */
@Composable
fun AnimatedFireStreakHero(
    streakDays: Long,
    longestStreak: Long,
    xp: Long,
    scrollOffsetPx: Float,
    modifier: Modifier = Modifier
) {
    val infinite = rememberInfiniteTransition(label = "hero_flame")
    val phase by infinite.animateFloat(
        initialValue = 0f,
        targetValue = (2.0 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "hero_phase"
    )
    val pulse by infinite.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flame_pulse"
    )

    val scrollWave = scrollOffsetPx * 0.015f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF101D38),
                        Color(0xFF16213E),
                        Color(0xFF231728)
                    )
                )
            )
    ) {
        // Animated multi-ribbon fire streak lines across the card reacting to scroll
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height
            if (w <= 0f || h <= 0f) return@Canvas

            for (lineIdx in 0..2) {
                val path = Path()
                val baseY = h * (0.28f + lineIdx * 0.24f)
                val amp = (6f + lineIdx * 3f).dp.toPx()
                val segments = 32
                for (i in 0..segments) {
                    val t = i.toFloat() / segments
                    val x = t * w
                    val y = baseY + sin(t * 9f - phase * (1f + lineIdx * 0.25f) - scrollWave) * amp
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                drawPath(
                    path = path,
                    brush = Brush.horizontalGradient(
                        colors = when (lineIdx) {
                            0 -> listOf(NeonBlue.copy(alpha = 0.08f), NeonBlue.copy(alpha = 0.45f), FlameCore.copy(alpha = 0.35f))
                            1 -> listOf(FlameOrange.copy(alpha = 0.12f), FlameCore.copy(alpha = 0.5f), FlameRed.copy(alpha = 0.25f))
                            else -> listOf(NeonBlue.copy(alpha = 0.05f), FlameOrange.copy(alpha = 0.32f), NeonBlueBright.copy(alpha = 0.4f))
                        }
                    ),
                    style = Stroke(width = (2.2f - lineIdx * 0.3f).dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        Surface(
            color = Color.Transparent,
            shape = RoundedCornerShape(22.dp),
            border = BorderStroke(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(NeonBlue.copy(alpha = 0.6f), FlameOrange.copy(alpha = 0.65f))
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size((52 * pulse).dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        FlameCore.copy(alpha = 0.95f),
                                        FlameOrange.copy(alpha = 0.7f),
                                        FlameRed.copy(alpha = 0.25f)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalFireDepartment,
                            contentDescription = "Active Study Streak Flame",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "$streakDays-DAY STREAK",
                            style = MaterialTheme.typography.headlineSmall,
                            color = FlameCore,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Best: $longestStreak days • Scroll-synced energy",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Surface(
                        color = NeonBlue.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.55f))
                    ) {
                        Text(
                            text = "$xp XP",
                            style = MaterialTheme.typography.labelLarge,
                            color = NeonBlueBright,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    val level = (xp / 250L) + 1L
                    Text(
                        text = "Level $level Scholar",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextPrimary.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Composable
fun NeonGlassCard(
    modifier: Modifier = Modifier,
    accentColor: Color = NeonBlue,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = GlassCardSurface.copy(alpha = 0.94f),
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.linearGradient(
                listOf(accentColor.copy(alpha = 0.45f), SubtleBorder.copy(alpha = 0.65f))
            )
        ),
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun SyncStatusChip(
    isOfflineMode: Boolean,
    isDeviceOnline: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeOffline = isOfflineMode || !isDeviceOnline
    val color = if (activeOffline) FlameCore else EmeraldMastery
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = ObsidianBg.copy(alpha = 0.85f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.65f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (activeOffline) Icons.Filled.CloudOff else Icons.Filled.CloudDone,
                contentDescription = if (activeOffline) "Offline Mode" else "Cloud Synced",
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = if (isOfflineMode) "Offline Study" else if (!isDeviceOnline) "Cached Offline" else "Synced",
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
    }
}
