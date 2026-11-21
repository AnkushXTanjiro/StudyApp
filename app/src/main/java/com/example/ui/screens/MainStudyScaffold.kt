package com.example.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.firebase.signOutFromGoogle
import com.example.model.UiState
import com.example.ui.components.MakinaCompanionModal
import com.example.ui.components.MakinaFloatingAvatarButton
import com.example.ui.components.ScrollReactiveFireStreakRail
import com.example.ui.components.SyncStatusChip
import com.example.ui.theme.FlameCore
import com.example.ui.theme.GlassCardSurface
import com.example.ui.theme.MidnightSurface
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonBlueBright
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.StudyViewModel
import kotlinx.coroutines.launch

private data class StudyNavTab(
    val label: String,
    val icon: ImageVector
)

private val studyTabs = listOf(
    StudyNavTab("Home", Icons.Filled.Dashboard),
    StudyNavTab("Syllabus", Icons.AutoMirrored.Filled.MenuBook),
    StudyNavTab("Tasks", Icons.Filled.TaskAlt),
    StudyNavTab("Tests", Icons.Filled.Quiz),
    StudyNavTab("Ranks", Icons.Filled.EmojiEvents)
)

@Composable
fun MainStudyScaffold(
    viewModel: StudyViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = remember(context) { CredentialManager.create(context) }

    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val subjectsState by viewModel.subjectsState.collectAsStateWithLifecycle()
    val todosState by viewModel.todosState.collectAsStateWithLifecycle()
    val timetableState by viewModel.timetableState.collectAsStateWithLifecycle()
    val questionsState by viewModel.questionsState.collectAsStateWithLifecycle()
    val reportsState by viewModel.reportsState.collectAsStateWithLifecycle()
    val leaderboardState by viewModel.leaderboardState.collectAsStateWithLifecycle()
    val offlineMode by viewModel.offlineModeEnabled.collectAsStateWithLifecycle()
    val extractionState by viewModel.extractionState.collectAsStateWithLifecycle()
    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()
    val activeTest by viewModel.activeTest.collectAsStateWithLifecycle()
    val selectedReport by viewModel.selectedReport.collectAsStateWithLifecycle()
    val makinaState by viewModel.makinaState.collectAsStateWithLifecycle()
    val makinaIsSpeaking by viewModel.makinaIsSpeaking.collectAsStateWithLifecycle()
    val makinaIsLoadingVoice by viewModel.makinaIsLoadingVoice.collectAsStateWithLifecycle()
    val makinaIsMuted by viewModel.makinaIsMuted.collectAsStateWithLifecycle()
    val makinaAutoSpeak by viewModel.makinaAutoSpeak.collectAsStateWithLifecycle()
    val makinaVoiceLabel by viewModel.makinaVoiceEngineLabel.collectAsStateWithLifecycle()

    val isDeviceOnline = rememberNetworkConnectivity(context)
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeveloperScreen by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState(initialPage = 0) { studyTabs.size }

    LaunchedEffect(toastMessage) {
        val msg = toastMessage
        if (!msg.isNullOrBlank()) {
            snackbarHostState.showSnackbar(msg)
            viewModel.clearToast()
        }
    }

    // Trigger Makina's charming arrival greeting once the user arrives
    LaunchedEffect(profileState is UiState.Success) {
        if (profileState is UiState.Success) {
            viewModel.triggerWelcomeGreetingIfNeeded(isDeviceOnline)
        }
    }

    // Full-screen overlay for Developer & Creator Screen
    if (showDeveloperScreen) {
        DeveloperScreen(onClose = { showDeveloperScreen = false })
        return
    }

    // Full-screen overlay for Active Test Mode
    val currentTest = activeTest
    if (currentTest != null) {
        InteractiveTestSessionScreen(
            session = currentTest,
            isOfflineMode = offlineMode || !isDeviceOnline,
            onSelectMcq = viewModel::selectMcqOption,
            onUpdateSubjective = viewModel::updateSubjectiveAnswer,
            onAttachPhoto = viewModel::attachSubjectiveSolutionPhoto,
            onToggleSchemeStep = viewModel::toggleMarkingSchemeStep,
            onToggleFlagReview = viewModel::toggleFlagQuestionForReview,
            onChangeIndex = viewModel::setTestQuestionIndex,
            onSubmit = viewModel::submitActiveTest,
            onExit = viewModel::cancelActiveTest
        )
        return
    }

    // Full-screen overlay for Report Card Detail + Makina Explain with AI Modal
    val currentReport = selectedReport
    if (currentReport != null) {
        ReportCardDetailScreen(
            report = currentReport,
            onAddWeakTopicsToTodo = { rep ->
                viewModel.addWeakTopicsToTodo(rep)
                viewModel.openReportCard(null)
            },
            onRetryWeakTopics = { rep ->
                viewModel.startWeakTopicRetryQuiz(rep)
            },
            onAskMakinaExplainReport = { rep ->
                viewModel.explainReportWithMakina(rep, isDeviceOnline)
            },
            onExplainQuestionWithAi = { qItem ->
                viewModel.explainQuestionWithMakina(
                    item = qItem,
                    subjectTitle = currentReport.subjectTitle,
                    unitTitle = currentReport.unitTitle,
                    isDeviceOnline = isDeviceOnline
                )
            },
            onClose = {
                viewModel.dismissMakina()
                viewModel.openReportCard(null)
            }
        )
        MakinaCompanionModal(
            state = makinaState,
            isSpeaking = makinaIsSpeaking,
            isLoadingVoice = makinaIsLoadingVoice,
            isMuted = makinaIsMuted,
            isAutoSpeakEnabled = makinaAutoSpeak,
            voiceEngineLabel = makinaVoiceLabel,
            onSpeakText = { txt -> viewModel.speakWithMakina(txt, isDeviceOnline) },
            onToggleMute = viewModel::toggleMakinaMute,
            onToggleAutoSpeak = viewModel::toggleMakinaAutoSpeak,
            onResetChat = { viewModel.resetMakinaChat(isDeviceOnline) },
            onStopVoice = viewModel::stopMakinaVoice,
            onSendFollowUp = { doubt -> viewModel.sendMakinaFollowUp(doubt, isDeviceOnline) },
            onSendVoiceAudio = { bytes, mime -> viewModel.sendMakinaVoiceAudioDoubt(bytes, mime, isDeviceOnline) },
            onNavigateToTab = { tab ->
                viewModel.openReportCard(null)
                scope.launch { pagerState.animateScrollToPage(tab) }
            },
            onOpenDeveloper = {
                viewModel.openReportCard(null)
                showDeveloperScreen = true
            },
            onDismiss = viewModel::dismissMakina
        )
        return
    }

    val profile = (profileState as? UiState.Success)?.data
    val subjects = (subjectsState as? UiState.Success)?.data.orEmpty()
    val todos = (todosState as? UiState.Success)?.data.orEmpty()
    val timetable = (timetableState as? UiState.Success)?.data.orEmpty()
    val questions = (questionsState as? UiState.Success)?.data.orEmpty()
    val reports = (reportsState as? UiState.Success)?.data.orEmpty()
    val leaderboard = (leaderboardState as? UiState.Success)?.data.orEmpty()

    var preselectedTestSubjectId by remember { mutableStateOf<String?>(null) }
    var preselectedTestUnitId by remember { mutableStateOf<String?>(null) }
    val dashListState = rememberLazyListState()
    val syllabusListState = rememberLazyListState()
    val todoListState = rememberLazyListState()
    val testListState = rememberLazyListState()
    val ranksListState = rememberLazyListState()

    // Reactive scroll offset driving the moving fire streak lines across the entire app
    val activeScrollPx by remember {
        derivedStateOf {
            val activeState = when (pagerState.currentPage) {
                0 -> dashListState
                1 -> syllabusListState
                2 -> todoListState
                3 -> testListState
                else -> ranksListState
            }
            (activeState.firstVisibleItemIndex * 240 + activeState.firstVisibleItemScrollOffset +
                pagerState.currentPage * 180 + pagerState.currentPageOffsetFraction * 180f)
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFF050914), ObsidianBg, Color(0xFF0A1326)))
            )
    ) {
        val useNavRail = maxWidth >= 680.dp

        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                Surface(
                    color = MidnightSurface.copy(alpha = 0.92f),
                    tonalElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "StudyApp",
                            style = MaterialTheme.typography.titleLarge,
                            color = NeonBlueBright,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.width(10.dp))
                        // Mini live streak flame pill
                        Surface(
                            color = FlameCore.copy(alpha = 0.14f),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocalFireDepartment,
                                    contentDescription = "Streak",
                                    tint = FlameCore,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(Modifier.width(3.dp))
                                Text(
                                    text = "${profile?.streakDays ?: 1}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = FlameCore
                                )
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = { showDeveloperScreen = true },
                            modifier = Modifier.testTag("top_bar_developer_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Code,
                                contentDescription = "Developer Section",
                                tint = NeonBlueBright
                            )
                        }
                        Spacer(Modifier.width(4.dp))
                        SyncStatusChip(
                            isOfflineMode = offlineMode,
                            isDeviceOnline = isDeviceOnline,
                            onClick = { viewModel.toggleOfflineStudyMode(!offlineMode) }
                        )
                    }
                }
            },
            bottomBar = {
                if (!useNavRail) {
                    NavigationBar(
                        containerColor = MidnightSurface.copy(alpha = 0.96f),
                        contentColor = TextPrimary
                    ) {
                        studyTabs.forEachIndexed { index, tab ->
                            val selected = pagerState.currentPage == index
                            NavigationBarItem(
                                selected = selected,
                                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                icon = { Icon(tab.icon, contentDescription = tab.label) },
                                label = { Text(tab.label, style = MaterialTheme.typography.labelSmall) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = NeonBlueBright,
                                    selectedTextColor = NeonBlueBright,
                                    indicatorColor = NeonBlue.copy(alpha = 0.18f),
                                    unselectedIconColor = TextSecondary,
                                    unselectedTextColor = TextSecondary
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Global Scroll-Reactive Animated Fire Streak Lines along left & right edges
                ScrollReactiveFireStreakRail(
                    scrollProgressPx = activeScrollPx,
                    streakDays = profile?.streakDays ?: 1L
                )

                Row(modifier = Modifier.fillMaxSize()) {
                    if (useNavRail) {
                        NavigationRail(
                            containerColor = GlassCardSurface.copy(alpha = 0.95f),
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            studyTabs.forEachIndexed { index, tab ->
                                val selected = pagerState.currentPage == index
                                NavigationRailItem(
                                    selected = selected,
                                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                    icon = { Icon(tab.icon, contentDescription = tab.label) },
                                    label = { Text(tab.label) },
                                    colors = NavigationRailItemDefaults.colors(
                                        selectedIconColor = NeonBlueBright,
                                        selectedTextColor = NeonBlueBright,
                                        indicatorColor = NeonBlue.copy(alpha = 0.18f),
                                        unselectedIconColor = TextSecondary,
                                        unselectedTextColor = TextSecondary
                                    )
                                )
                            }
                        }
                    }

                    // Intuitive Touch-Gesture Horizontal Pager between Study Modules
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        when (page) {
                            0 -> DashboardScreen(
                                listState = dashListState,
                                profile = profile,
                                subjects = subjects,
                                todos = todos,
                                timetable = timetable,
                                reports = reports,
                                onNavigateTab = { target -> scope.launch { pagerState.animateScrollToPage(target) } },
                                onToggleTodo = viewModel::toggleTodo,
                                onOpenReport = viewModel::openReportCard,
                                onOpenMakina = { viewModel.openMakinaGuide(isDeviceOnline) },
                                onRetryWeakTopics = { viewModel.startWeakTopicRetryQuiz(null) }
                            )
                            1 -> SyllabusScreen(
                                listState = syllabusListState,
                                subjects = subjects,
                                extractionState = extractionState,
                                onToggleTopic = viewModel::toggleSyllabusTopic,
                                onAddTopicToTodo = { s, u, t -> viewModel.addTodoFromSyllabus(s, u, t) },
                                onPracticeUnitTest = { s, u ->
                                    preselectedTestSubjectId = s.id
                                    preselectedTestUnitId = u.unitId
                                    scope.launch { pagerState.animateScrollToPage(3) }
                                },
                                onImportSyllabusFile = viewModel::importSyllabusFromFile,
                                onReloadDefaultSyllabus = viewModel::reloadGroupASyllabus,
                                onDismissExtractionError = viewModel::clearExtractionError
                            )
                            2 -> TodoTimetableScreen(
                                listState = todoListState,
                                todos = todos,
                                subjects = subjects,
                                timetable = timetable,
                                extractionState = extractionState,
                                onAddManualTodo = viewModel::addManualTodo,
                                onAddSyllabusTodo = { s, u, t -> viewModel.addTodoFromSyllabus(s, u, t) },
                                onAddTimetableTodo = viewModel::addTodoFromTimetable,
                                onToggleTodo = viewModel::toggleTodo,
                                onDeleteTodo = viewModel::deleteTodo,
                                onAddTimetableSlot = viewModel::addManualTimetableSlot,
                                onDeleteTimetableSlot = viewModel::deleteTimetableSlot,
                                onImportTimetableFile = viewModel::importTimetableFromFile,
                                onReloadOfficialTimetable = viewModel::reloadOfficialCseATimetable
                            )
                            3 -> TestLabScreen(
                                listState = testListState,
                                subjects = subjects,
                                questions = questions,
                                reports = reports,
                                extractionState = extractionState,
                                preselectedSubjectId = preselectedTestSubjectId,
                                preselectedUnitId = preselectedTestUnitId,
                                onStartUnitTest = viewModel::startUnitTest,
                                onStartWeakTopicQuiz = { viewModel.startWeakTopicRetryQuiz(null) },
                                onStartBeuEndSemMock = viewModel::startBeuEndSemMock,
                                onExtractQuestions = viewModel::extractQuestionsForUnit,
                                onAddManualQuestion = viewModel::addManualQuestion,
                                onDeleteQuestion = viewModel::deleteQuestion,
                                onOpenReport = viewModel::openReportCard,
                                onDismissExtractionError = viewModel::clearExtractionError
                            )
                            else -> LeaderboardBadgesScreen(
                                listState = ranksListState,
                                currentUserId = viewModel.currentUserId,
                                profile = profile,
                                leaderboard = leaderboard,
                                isOfflineMode = offlineMode,
                                onToggleOfflineMode = viewModel::toggleOfflineStudyMode,
                                onSaveReminderSettings = viewModel::saveReminderPreferences,
                                onOpenDeveloper = { showDeveloperScreen = true },
                                onSignOut = {
                                    signOutFromGoogle(
                                        credentialManager = credentialManager,
                                        scope = scope
                                    )
                                }
                            )
                        }
                    }
                }

                // Floating Makina AI Avatar Pill (summon her anytime for greeting, tour, or help)
                MakinaFloatingAvatarButton(
                    isSpeaking = makinaIsSpeaking,
                    onClick = { viewModel.openMakinaGuide(isDeviceOnline) },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 14.dp)
                )
            }
        }

        // Makina AI Companion Modal Overlay
        MakinaCompanionModal(
            state = makinaState,
            isSpeaking = makinaIsSpeaking,
            isLoadingVoice = makinaIsLoadingVoice,
            isMuted = makinaIsMuted,
            isAutoSpeakEnabled = makinaAutoSpeak,
            voiceEngineLabel = makinaVoiceLabel,
            onSpeakText = { txt -> viewModel.speakWithMakina(txt, isDeviceOnline) },
            onToggleMute = viewModel::toggleMakinaMute,
            onToggleAutoSpeak = viewModel::toggleMakinaAutoSpeak,
            onResetChat = { viewModel.resetMakinaChat(isDeviceOnline) },
            onStopVoice = viewModel::stopMakinaVoice,
            onSendFollowUp = { doubt -> viewModel.sendMakinaFollowUp(doubt, isDeviceOnline) },
            onSendVoiceAudio = { bytes, mime -> viewModel.sendMakinaVoiceAudioDoubt(bytes, mime, isDeviceOnline) },
            onNavigateToTab = { tab -> scope.launch { pagerState.animateScrollToPage(tab) } },
            onOpenDeveloper = { showDeveloperScreen = true },
            onDismiss = viewModel::dismissMakina
        )
    }
}

@Composable
private fun rememberNetworkConnectivity(context: Context): Boolean {
    val connectivityManager = remember(context) {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    var isOnline by remember {
        val active = connectivityManager.activeNetwork
        val caps = active?.let { connectivityManager.getNetworkCapabilities(it) }
        mutableStateOf(caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)
    }

    DisposableEffect(connectivityManager) {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isOnline = true
            }
            override fun onLost(network: Network) {
                isOnline = false
            }
        }
        try {
            connectivityManager.registerDefaultNetworkCallback(callback)
        } catch (_: Exception) {
        }
        onDispose {
            try {
                connectivityManager.unregisterNetworkCallback(callback)
            } catch (_: Exception) {
            }
        }
    }
    return isOnline
}
