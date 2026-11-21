package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.EmeraldMastery
import com.example.ui.theme.FlameCore
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonBlueBright
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlin.math.PI
import kotlin.math.sin

/**
 * Styled mathematical expression box supporting integration (∫, ∬), differentiation (d/dx, ∂/∂x),
 * Greek variables (α, β, γ, Γ, λ, θ, π, ψ, Ω), powers, and matrices in crisp monospace styling.
 */
@Composable
fun MathFormulaBlock(
    expression: String,
    modifier: Modifier = Modifier
) {
    if (expression.isBlank()) return
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = ObsidianBg.copy(alpha = 0.92f),
        border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.42f))
    ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)) {
            Text(
                text = "EXPRESSION",
                style = MaterialTheme.typography.labelSmall,
                color = NeonBlue.copy(alpha = 0.85f)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = expression,
                fontFamily = JetBrainsMonoFontFamily,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Medium,
                color = NeonBlueBright,
                modifier = Modifier.horizontalScroll(rememberScrollState())
            )
        }
    }
}

/**
 * Renders either an attached user diagram image (via Coil) or a high-clarity vector scientific
 * schematic (Integral Area, Newton's Rings, Quantum Potential Well, RLC/Star-Delta Circuit, Logic/Search Tree).
 */
@Composable
fun QuestionDiagramView(
    diagramKind: String,
    imageUri: String,
    modifier: Modifier = Modifier
) {
    if (imageUri.isNotBlank()) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(170.dp),
            shape = RoundedCornerShape(14.dp),
            color = ObsidianBg,
            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.4f))
        ) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Question Reference Diagram",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize().padding(8.dp)
            )
        }
        return
    }

    if (diagramKind == "NONE" || diagramKind.isBlank()) return

    val caption = when (diagramKind) {
        "INTEGRAL_AREA" -> "Fig: Polar / Definite Integral Region  ∬ f(r, θ) r dr dθ"
        "NEWTON_RINGS" -> "Fig: Concentric Interference Rings  (Dₙ² = 4nλR)"
        "QUANTUM_WELL" -> "Fig: 1D Infinite Potential Well  ψ₁(x), ψ₂(x), ψ₃(x)"
        "RLC_CIRCUIT" -> "Fig: Delta (Δ) ↔ Star (Y) Impedance Network"
        "LOGIC_GATE" -> "Fig: Adversarial Minimax / α-β Pruning Tree"
        else -> "Fig: Scientific Diagram"
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = ObsidianBg.copy(alpha = 0.92f),
        border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.35f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF050A16))
            ) {
                Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                    val w = size.width
                    val h = size.height
                    when (diagramKind) {
                        "INTEGRAL_AREA" -> drawIntegralAreaDiagram(w, h)
                        "NEWTON_RINGS" -> drawNewtonRingsDiagram(w, h)
                        "QUANTUM_WELL" -> drawQuantumWellDiagram(w, h)
                        "RLC_CIRCUIT" -> drawCircuitDiagram(w, h)
                        "LOGIC_GATE" -> drawAlphaBetaTreeDiagram(w, h)
                    }
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = caption,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawIntegralAreaDiagram(w: Float, h: Float) {
    // Axes
    val originX = w * 0.14f
    val originY = h * 0.84f
    drawLine(TextSecondary, Offset(originX, originY), Offset(w * 0.92f, originY), strokeWidth = 2f)
    drawLine(TextSecondary, Offset(originX, originY), Offset(originX, h * 0.1f), strokeWidth = 2f)

    // Shaded curve under f(x) = sin/quarter-circle arc
    val fillPath = Path().apply {
        moveTo(originX, originY)
        val steps = 30
        val rx = w * 0.62f
        val ry = h * 0.65f
        for (i in 0..steps) {
            val t = i.toFloat() / steps
            val angle = (PI / 2.0 * t)
            val px = originX + (rx * kotlin.math.cos(PI / 2.0 - angle)).toFloat()
            val py = originY - (ry * kotlin.math.sin(PI / 2.0 - angle)).toFloat()
            lineTo(px, py)
        }
        lineTo(originX + rx, originY)
        close()
    }
    drawPath(
        fillPath,
        brush = Brush.verticalGradient(listOf(NeonBlue.copy(alpha = 0.42f), NeonBlue.copy(alpha = 0.06f)))
    )
    val curvePath = Path().apply {
        val steps = 30
        val rx = w * 0.62f
        val ry = h * 0.65f
        for (i in 0..steps) {
            val t = i.toFloat() / steps
            val angle = (PI / 2.0 * t)
            val px = originX + (rx * kotlin.math.cos(PI / 2.0 - angle)).toFloat()
            val py = originY - (ry * kotlin.math.sin(PI / 2.0 - angle)).toFloat()
            if (i == 0) moveTo(px, py) else lineTo(px, py)
        }
    }
    drawPath(curvePath, color = NeonBlueBright, style = Stroke(width = 3f, cap = StrokeCap.Round))
    // Radius vector r at angle θ
    drawLine(
        FlameCore,
        Offset(originX, originY),
        Offset(originX + w * 0.42f, originY - h * 0.46f),
        strokeWidth = 2.5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
    )
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawNewtonRingsDiagram(w: Float, h: Float) {
    val center = Offset(w * 0.5f, h * 0.5f)
    val maxR = minOf(w, h) * 0.44f
    drawCircle(Color(0xFF0A1429), radius = maxR * 1.05f, center = center)
    for (n in 1..6) {
        val r = maxR * kotlin.math.sqrt(n / 6.0).toFloat()
        drawCircle(
            color = if (n % 2 == 0) NeonBlue.copy(alpha = 0.85f) else FlameCore.copy(alpha = 0.55f),
            radius = r,
            center = center,
            style = Stroke(width = (3.4f - n * 0.35f).coerceAtLeast(1.2f))
        )
    }
    drawCircle(ObsidianBg, radius = 5f, center = center)
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawQuantumWellDiagram(w: Float, h: Float) {
    val leftX = w * 0.16f
    val rightX = w * 0.84f
    val bottomY = h * 0.86f
    val topY = h * 0.1f

    // Infinite potential walls
    drawLine(TextPrimary, Offset(leftX, topY), Offset(leftX, bottomY), strokeWidth = 3.5f)
    drawLine(TextPrimary, Offset(rightX, topY), Offset(rightX, bottomY), strokeWidth = 3.5f)
    drawLine(TextPrimary, Offset(leftX, bottomY), Offset(rightX, bottomY), strokeWidth = 3.5f)

    // Wavefunctions n = 1, 2, 3
    val colors = listOf(NeonBlue, FlameCore, EmeraldMastery)
    for (n in 1..3) {
        val levelY = bottomY - (h * 0.22f * n)
        drawLine(
            colors[n - 1].copy(alpha = 0.35f),
            Offset(leftX, levelY),
            Offset(rightX, levelY),
            strokeWidth = 1.5f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f))
        )
        val wave = Path()
        val span = rightX - leftX
        val amp = h * 0.1f
        for (i in 0..40) {
            val t = i.toFloat() / 40f
            val px = leftX + t * span
            val py = levelY - sin(n * PI * t).toFloat() * amp
            if (i == 0) wave.moveTo(px, py) else wave.lineTo(px, py)
        }
        drawPath(wave, color = colors[n - 1], style = Stroke(width = 2.8f, cap = StrokeCap.Round))
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCircuitDiagram(w: Float, h: Float) {
    // Delta triangle on left, Star Y on right
    val dTop = Offset(w * 0.24f, h * 0.16f)
    val dLeft = Offset(w * 0.1f, h * 0.82f)
    val dRight = Offset(w * 0.38f, h * 0.82f)
    drawLine(NeonBlue, dTop, dLeft, strokeWidth = 3f)
    drawLine(NeonBlue, dTop, dRight, strokeWidth = 3f)
    drawLine(NeonBlue, dLeft, dRight, strokeWidth = 3f)
    listOf(dTop, dLeft, dRight).forEach { pt ->
        drawCircle(FlameCore, radius = 5f, center = pt)
    }

    // Conversion arrow
    drawLine(TextSecondary, Offset(w * 0.45f, h * 0.5f), Offset(w * 0.55f, h * 0.5f), strokeWidth = 2.5f)

    // Star Y network
    val sCenter = Offset(w * 0.76f, h * 0.54f)
    val sTop = Offset(w * 0.76f, h * 0.16f)
    val sLeft = Offset(w * 0.62f, h * 0.82f)
    val sRight = Offset(w * 0.9f, h * 0.82f)
    drawLine(EmeraldMastery, sCenter, sTop, strokeWidth = 3f)
    drawLine(EmeraldMastery, sCenter, sLeft, strokeWidth = 3f)
    drawLine(EmeraldMastery, sCenter, sRight, strokeWidth = 3f)
    drawCircle(NeonBlueBright, radius = 5.5f, center = sCenter)
    listOf(sTop, sLeft, sRight).forEach { pt ->
        drawCircle(FlameCore, radius = 4.5f, center = pt)
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawAlphaBetaTreeDiagram(w: Float, h: Float) {
    val root = Offset(w * 0.5f, h * 0.18f)
    val leftMid = Offset(w * 0.28f, h * 0.52f)
    val rightMid = Offset(w * 0.72f, h * 0.52f)
    val leaves = listOf(
        Offset(w * 0.16f, h * 0.84f),
        Offset(w * 0.38f, h * 0.84f),
        Offset(w * 0.62f, h * 0.84f),
        Offset(w * 0.84f, h * 0.84f)
    )
    drawLine(NeonBlue, root, leftMid, strokeWidth = 2.5f)
    drawLine(NeonBlue, root, rightMid, strokeWidth = 2.5f)
    drawLine(NeonBlue, leftMid, leaves[0], strokeWidth = 2.2f)
    drawLine(NeonBlue, leftMid, leaves[1], strokeWidth = 2.2f)
    drawLine(NeonBlue, rightMid, leaves[2], strokeWidth = 2.2f)
    // Pruned edge in dashed flame orange
    drawLine(
        FlameOrange,
        rightMid,
        leaves[3],
        strokeWidth = 2.4f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f))
    )
    drawCircle(NeonBlueBright, radius = 8f, center = root)
    drawRect(FlameCore, topLeft = Offset(leftMid.x - 7f, leftMid.y - 7f), size = Size(14f, 14f))
    drawRect(FlameCore, topLeft = Offset(rightMid.x - 7f, rightMid.y - 7f), size = Size(14f, 14f))
    leaves.forEachIndexed { idx, pt ->
        drawCircle(
            color = if (idx == 3) FlameOrange.copy(alpha = 0.5f) else EmeraldMastery,
            radius = 6f,
            center = pt
        )
    }
}

/**
 * Interactive Math Symbol Keyboard Bar for typing subjective answers with integrals,
 * derivatives, Greek letters (α, β, γ, Γ, λ, θ, π, ψ, Σ, Ω), and powers effortlessly.
 */
@Composable
fun MathSymbolToolbar(
    onInsertSymbol: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val symbols = listOf(
        "∫", "∬", "∂/∂x", "d/dx", "α", "β", "γ", "Γ",
        "λ", "θ", "π", "ψ", "Σ", "Ω", "√", "∞",
        "²", "³", "ⁿ", "≤", "≥", "≠", "⇒", "∇"
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        symbols.forEach { sym ->
            Surface(
                onClick = { onInsertSymbol(sym) },
                shape = RoundedCornerShape(10.dp),
                color = ObsidianBg,
                border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.45f))
            ) {
                Text(
                    text = sym,
                    fontFamily = JetBrainsMonoFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NeonBlueBright,
                    modifier = Modifier.padding(horizontal = 11.dp, vertical = 7.dp)
                )
            }
        }
    }
}
