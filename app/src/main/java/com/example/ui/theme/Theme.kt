package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val StudyDarkColorScheme = darkColorScheme(
    primary = NeonBlue,
    onPrimary = Color(0xFF001F2A),
    primaryContainer = Color(0xFF0A334D),
    onPrimaryContainer = NeonBlueBright,
    secondary = HyperBlue,
    onSecondary = Color(0xFFF8FAFC),
    secondaryContainer = Color(0xFF172B56),
    onSecondaryContainer = Color(0xFFBFDBFE),
    tertiary = FlameOrange,
    onTertiary = Color(0xFF2B0E00),
    tertiaryContainer = Color(0xFF3E1C0B),
    onTertiaryContainer = FlameCore,
    background = ObsidianBg,
    onBackground = TextPrimary,
    surface = MidnightSurface,
    onSurface = TextPrimary,
    surfaceVariant = GlassCardSurface,
    onSurfaceVariant = TextSecondary,
    surfaceContainerLowest = ObsidianBg,
    surfaceContainerLow = MidnightSurface,
    surfaceContainer = GlassCardSurface,
    surfaceContainerHigh = ElevatedSlate,
    surfaceContainerHighest = Color(0xFF223357),
    outline = SubtleBorder,
    outlineVariant = Color(0xFF1A2847),
    error = RoseWeak,
    onError = Color(0xFF2B000A),
    errorContainer = Color(0xFF471222),
    onErrorContainer = Color(0xFFFFB4C2)
)

private val StudyShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun StudyAppTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, view).apply {
                    isAppearanceLightStatusBars = false
                    isAppearanceLightNavigationBars = false
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = StudyDarkColorScheme,
        typography = StudyTypography,
        shapes = StudyShapes,
        content = content
    )
}
