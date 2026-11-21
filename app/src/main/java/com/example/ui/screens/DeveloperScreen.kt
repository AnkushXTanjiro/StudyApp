package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.components.NeonGlassCard
import com.example.ui.theme.AmberImprove
import com.example.ui.theme.EmeraldMastery
import com.example.ui.theme.FlameCore
import com.example.ui.theme.GlassCardSurface
import com.example.ui.theme.HyperBlue
import com.example.ui.theme.MidnightSurface
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonBlueBright
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

private data class DeveloperSkill(
    val label: String,
    val badgeColor: Color
)

private val developerSkills = listOf(
    DeveloperSkill("HTML5", Color(0xFFFF6D3B)),
    DeveloperSkill("CSS3", Color(0xFF38BDF8)),
    DeveloperSkill("JavaScript", Color(0xFFFACC15)),
    DeveloperSkill("Python", Color(0xFF60A5FA)),
    DeveloperSkill("C/C++", Color(0xFF818CF8)),
    DeveloperSkill("Kotlin", Color(0xFFA78BFA)),
    DeveloperSkill("Jetpack Compose", Color(0xFF34D399)),
    DeveloperSkill("Android", Color(0xFF4ADE80)),
    DeveloperSkill("Web Dev", NeonBlueBright),
    DeveloperSkill("Git & GitHub", Color(0xFFE2E8F0))
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DeveloperScreen(
    onClose: () -> Unit
) {
    BackHandler { onClose() }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF040917), ObsidianBg, Color(0xFF08142C))
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Navigation Bar
        Surface(
            color = MidnightSurface.copy(alpha = 0.94f),
            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.testTag("developer_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = NeonBlueBright
                    )
                }
                Icon(
                    imageVector = Icons.Filled.Code,
                    contentDescription = null,
                    tint = NeonBlue,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Developer & Creator",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "StudyApp • BEU CSE Edition",
                        style = MaterialTheme.typography.labelSmall,
                        color = NeonBlueBright
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("developer_screen_list"),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Hero Identity Banner
            item {
                NeonGlassCard(accentColor = NeonBlue) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Role pill
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = NeonBlue.copy(alpha = 0.14f),
                            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.6f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Code,
                                    contentDescription = null,
                                    tint = NeonBlueBright,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = "Developer & Creator",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = NeonBlueBright,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        // Glowing circular anime avatar with </> badge
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Box(
                                modifier = Modifier
                                    .size(134.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.radialGradient(
                                            listOf(NeonBlue.copy(alpha = 0.45f), Color.Transparent)
                                        )
                                    )
                                    .border(
                                        width = 2.5.dp,
                                        brush = Brush.linearGradient(listOf(NeonBlueBright, HyperBlue)),
                                        shape = CircleShape
                                    )
                                    .padding(4.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_dev_ankush),
                                    contentDescription = "Ankush Kumar Developer Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                )
                            }
                            Surface(
                                shape = CircleShape,
                                color = MidnightSurface,
                                border = BorderStroke(1.5.dp, NeonBlueBright),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Filled.Code,
                                        contentDescription = "Code badge",
                                        tint = NeonBlueBright,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(14.dp))

                        // Name in white + neon blue matching mockup
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = TextPrimary, fontWeight = FontWeight.ExtraBold)) {
                                    append("Ankush ")
                                }
                                withStyle(SpanStyle(color = NeonBlueBright, fontWeight = FontWeight.ExtraBold)) {
                                    append("Kumar")
                                }
                            },
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "Learn • Practice • Grow",
                            style = MaterialTheme.typography.labelMedium,
                            color = NeonBlue,
                            fontStyle = FontStyle.Italic
                        )

                        Spacer(Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append("Hi! I'm Ankush Kumar, the developer of this ")
                                    withStyle(SpanStyle(color = NeonBlueBright, fontWeight = FontWeight.Bold)) {
                                        append("StudyApp")
                                    }
                                    append(". I love building helpful tools for students and always strive to make learning easier and smarter.")
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.RocketLaunch,
                                contentDescription = null,
                                tint = FlameCore,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "Building smarter academic tools for BEU students",
                                style = MaterialTheme.typography.labelSmall,
                                color = FlameCore
                            )
                        }
                    }
                }
            }

            // 2. About the Developer Card
            item {
                NeonGlassCard(accentColor = HyperBlue) {
                    SectionTitleRow(
                        icon = Icons.Filled.Person,
                        whitePart = "About the ",
                        accentPart = "Developer"
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(108.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    Brush.verticalGradient(listOf(NeonBlueBright, HyperBlue))
                                )
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = buildAnnotatedString {
                                    append("Hello! I'm ")
                                    withStyle(SpanStyle(color = TextPrimary, fontWeight = FontWeight.Bold)) {
                                        append("Ankush Kumar")
                                    }
                                    append(", a B.Tech student at ")
                                    withStyle(SpanStyle(color = NeonBlueBright, fontWeight = FontWeight.SemiBold)) {
                                        append("BEU Affiliated College, GEC Sheikhpura, Batch 2026–2030")
                                    }
                                    append(".")
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                            Text(
                                text = "This platform is built to help students like you get better resources, practice tests, lab viva prep, and useful study materials — all in one place.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(22.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(NeonBlue)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "I'm passionate about technology, education, and continuous learning.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary
                        )
                    }
                }
            }

            // 3. Skills & Technologies Card
            item {
                NeonGlassCard(accentColor = NeonBlue) {
                    SectionTitleRow(
                        icon = Icons.Filled.Layers,
                        whitePart = "Skills & ",
                        accentPart = "Technologies"
                    )
                    Spacer(Modifier.height(12.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        developerSkills.forEach { skill ->
                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = ObsidianBg.copy(alpha = 0.88f),
                                border = BorderStroke(1.dp, skill.badgeColor.copy(alpha = 0.55f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(skill.badgeColor)
                                    )
                                    Spacer(Modifier.width(7.dp))
                                    Text(
                                        text = skill.label,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 4. Connect With Me (Telegram)
            item {
                NeonGlassCard(accentColor = NeonBlueBright) {
                    SectionTitleRow(
                        icon = Icons.AutoMirrored.Filled.Send,
                        whitePart = "Connect ",
                        accentPart = "With Me"
                    )
                    Spacer(Modifier.height(14.dp))
                    Button(
                        onClick = { openTelegramLink(context) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("developer_telegram_button"),
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonBlue.copy(alpha = 0.22f),
                            contentColor = TextPrimary
                        ),
                        border = BorderStroke(1.5.dp, NeonBlueBright)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Telegram",
                            tint = NeonBlueBright,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "Join on Telegram",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = NeonBlueBright,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { copyTelegramHandle(context) }
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Telegram: ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                        Text(
                            text = "@Lord_Yoriche",
                            style = MaterialTheme.typography.bodyMedium,
                            color = NeonBlueBright,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy Telegram handle",
                            tint = TextSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Let's learn and grow together!",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // 5. App Credits Card
            item {
                NeonGlassCard(accentColor = HyperBlue) {
                    SectionTitleRow(
                        icon = Icons.Filled.Description,
                        whitePart = "App ",
                        accentPart = "Credits"
                    )
                    Spacer(Modifier.height(12.dp))
                    CreditInfoRow(Icons.Filled.Verified, "Version", "1.0.0", EmeraldMastery)
                    CreditInfoRow(Icons.Filled.CalendarMonth, "Last Updated", "Nov 2026", NeonBlueBright)
                    CreditInfoRow(Icons.Filled.School, "College", "BEU Affiliated College, GEC Sheikhpura", AmberImprove)
                    CreditInfoRow(Icons.Filled.Groups, "Batch", "2026 – 2030", NeonBlue)

                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = SubtleBorder)
                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "© 2026 StudyApp. All rights reserved.",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("Designed & Developed by ")
                            withStyle(SpanStyle(color = NeonBlueBright, fontWeight = FontWeight.Bold)) {
                                append("Ankush Kumar")
                            }
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = TextPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // 6. Footer Brand Banner
            item {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = GlassCardSurface.copy(alpha = 0.8f),
                    border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.School,
                                contentDescription = null,
                                tint = NeonBlueBright,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(color = TextPrimary, fontWeight = FontWeight.Bold)) {
                                        append("Study")
                                    }
                                    withStyle(SpanStyle(color = NeonBlueBright, fontWeight = FontWeight.Bold)) {
                                        append("App")
                                    }
                                },
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = "Study Smart, Score High!",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(18.dp)) }
        }
    }
}

@Composable
private fun SectionTitleRow(
    icon: ImageVector,
    whitePart: String,
    accentPart: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = NeonBlueBright,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = TextPrimary, fontWeight = FontWeight.Bold)) {
                    append(whitePart)
                }
                withStyle(SpanStyle(color = NeonBlueBright, fontWeight = FontWeight.Bold)) {
                    append(accentPart)
                }
            },
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun CreditInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    iconTint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            modifier = Modifier.width(92.dp)
        )
        Text(
            text = ":  $value",
            style = MaterialTheme.typography.bodySmall,
            color = TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun openTelegramLink(context: Context) {
    val telegramUri = Uri.parse("https://t.me/Lord_Yoriche")
    try {
        val intent = Intent(Intent.ACTION_VIEW, telegramUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (_: Exception) {
        copyTelegramHandle(context)
    }
}

private fun copyTelegramHandle(context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    clipboard?.setPrimaryClip(ClipData.newPlainText("Telegram", "@Lord_Yoriche"))
    Toast.makeText(context, "Copied @Lord_Yoriche to clipboard", Toast.LENGTH_SHORT).show()
}
