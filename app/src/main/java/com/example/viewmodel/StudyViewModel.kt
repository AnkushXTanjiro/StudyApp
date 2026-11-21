package com.example.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AiExtractionService
import com.example.data.BundledQuestionBankLoader
import com.example.data.MakinaCompanionService
import com.example.data.MakinaVoicePlayer
import com.example.data.StudyRepository
import com.example.model.LeaderboardEntry
import com.example.model.QuestionResultItem
import com.example.ui.components.MakinaCompanionUiState
import com.example.ui.components.MakinaMessage
import com.example.ui.components.MakinaMode
import com.example.model.StudyExamBuilder
import com.example.model.SubjectDoc
import com.example.model.TestQuestion
import com.example.model.TestReport
import com.example.model.TimetableSlot
import com.example.model.TodoItem
import com.example.model.TopicItem
import com.example.model.UiState
import com.example.model.UnitItem
import com.example.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val VM_TAG = "StudyViewModel"

data class ActiveTestSession(
    val subjectId: String,
    val subjectTitle: String,
    val unitId: String,
    val unitTitle: String,
    val questions: List<TestQuestion>,
    val currentIndex: Int = 0,
    val elapsedSeconds: Long = 0L,
    val mcqAnswers: Map<String, Int> = emptyMap(),
    val subjectiveAnswers: Map<String, String> = emptyMap(),
    val subjectivePhotoUris: Map<String, String> = emptyMap(),
    val subjectivePhotoBytes: Map<String, Pair<ByteArray, String>> = emptyMap(),
    // Offline/manual marking scheme checked step indices per questionId
    val checkedSchemeSteps: Map<String, Set<Int>> = emptyMap(),
    val flaggedQuestionIds: Set<String> = emptySet(),
    val isSubmitting: Boolean = false,
    val timeLimitSeconds: Long? = null,
    val examModeLabel: String = "PRACTICE"
)

data class ExtractionBannerState(
    val isWorking: Boolean = false,
    val statusText: String = "",
    val errorText: String? = null
)

class StudyViewModel(
    private val repository: StudyRepository,
    private val aiService: AiExtractionService,
    val currentUserId: String,
    private val initialDisplayName: String,
    private val bundledQuestionLoader: BundledQuestionBankLoader? = null,
    private val makinaService: MakinaCompanionService = MakinaCompanionService(),
    private val makinaVoicePlayer: MakinaVoicePlayer? = null
) : ViewModel() {

    private fun <T> kotlinx.coroutines.flow.Flow<T>.toSafeUiState(
        label: String
    ): StateFlow<UiState<T>> = this
        .map<T, UiState<T>> { UiState.Success(it) }
        .catch { error ->
            val firestoreError = generateSequence(error) { it.cause }
                .filterIsInstance<FirebaseFirestoreException>()
                .firstOrNull()
            if (firestoreError == null && error is CancellationException) throw error
            val rootError = firestoreError ?: error
            Log.w(VM_TAG, "Error observing $label", rootError)
            emit(UiState.Error(rootError.message ?: "Failed to load $label"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UiState.Loading
        )

    val profileState: StateFlow<UiState<UserProfile?>> =
        repository.observeUserProfile(currentUserId).toSafeUiState("profile")

    val subjectsState: StateFlow<UiState<List<SubjectDoc>>> =
        repository.observeSubjects(currentUserId).toSafeUiState("syllabus")

    val todosState: StateFlow<UiState<List<TodoItem>>> =
        repository.observeTodos(currentUserId).toSafeUiState("todos")

    val timetableState: StateFlow<UiState<List<TimetableSlot>>> =
        repository.observeTimetable(currentUserId).toSafeUiState("timetable")

    private val bundledQuestionsFlow = flow {
        emit(bundledQuestionLoader?.loadAll().orEmpty())
    }

    val questionsState: StateFlow<UiState<List<TestQuestion>>> =
        combine(
            bundledQuestionsFlow,
            repository.observeQuestions(currentUserId)
        ) { bundled, remote ->
            val bundledSubjects = bundled.map { it.subjectId }.toSet()
            val merged = LinkedHashMap<String, TestQuestion>(bundled.size + remote.size)
            bundled.forEach { q -> merged[q.id] = q }
            remote.forEach { q ->
                val isReplacedStarter = q.id in BundledQuestionBankLoader.LEGACY_STARTER_IDS_REPLACED &&
                    q.subjectId in bundledSubjects
                if (!isReplacedStarter) {
                    merged[q.id] = q
                }
            }
            merged.values.toList()
        }.toSafeUiState("questions")

    val reportsState: StateFlow<UiState<List<TestReport>>> =
        repository.observeReports(currentUserId).toSafeUiState("reports")

    val leaderboardState: StateFlow<UiState<List<LeaderboardEntry>>> =
        repository.observeLeaderboard(currentUserId).toSafeUiState("leaderboard")

    private val _offlineModeEnabled = MutableStateFlow(false)
    val offlineModeEnabled: StateFlow<Boolean> = _offlineModeEnabled.asStateFlow()

    private val _extractionState = MutableStateFlow(ExtractionBannerState())
    val extractionState: StateFlow<ExtractionBannerState> = _extractionState.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _activeTest = MutableStateFlow<ActiveTestSession?>(null)
    val activeTest: StateFlow<ActiveTestSession?> = _activeTest.asStateFlow()

    private val _selectedReport = MutableStateFlow<TestReport?>(null)
    val selectedReport: StateFlow<TestReport?> = _selectedReport.asStateFlow()

    private val _makinaState = MutableStateFlow(MakinaCompanionUiState())
    val makinaState: StateFlow<MakinaCompanionUiState> = _makinaState.asStateFlow()

    val makinaIsSpeaking: StateFlow<Boolean> =
        makinaVoicePlayer?.isSpeaking ?: MutableStateFlow(false).asStateFlow()
    val makinaIsLoadingVoice: StateFlow<Boolean> =
        makinaVoicePlayer?.isLoadingVoice ?: MutableStateFlow(false).asStateFlow()
    val makinaIsMuted: StateFlow<Boolean> =
        makinaVoicePlayer?.isMuted ?: MutableStateFlow(false).asStateFlow()
    val makinaAutoSpeak: StateFlow<Boolean> =
        makinaVoicePlayer?.autoSpeakEnabled ?: MutableStateFlow(true).asStateFlow()
    val makinaVoiceEngineLabel: StateFlow<String> =
        makinaVoicePlayer?.voiceEngineLabel ?: MutableStateFlow("Device Voice").asStateFlow()

    private var hasShownArrivalGreeting = false
    private var makinaJob: Job? = null

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            repository.ensureUserInitializedAndSeeded(initialDisplayName).onFailure { err ->
                Log.e(VM_TAG, "Initialization failed", err)
                _toastMessage.value = "Sync initialization notice: ${err.localizedMessage ?: "Working offline"}"
            }
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun clearExtractionError() {
        _extractionState.update { it.copy(errorText = null, statusText = "") }
    }

    fun toggleOfflineStudyMode(enableOffline: Boolean) {
        viewModelScope.launch {
            repository.setOfflineMode(enableOffline).onSuccess {
                _offlineModeEnabled.value = enableOffline
                _toastMessage.value = if (enableOffline) {
                    "Offline Study Mode active — all changes cached locally."
                } else {
                    "Online Cloud Sync restored."
                }
            }.onFailure { err ->
                _toastMessage.value = "Network toggle failed: ${err.localizedMessage}"
            }
        }
    }

    // --- Syllabus Actions ---

    fun toggleSyllabusTopic(subject: SubjectDoc, unit: UnitItem, topic: TopicItem) {
        val nextCompleted = !topic.completed
        viewModelScope.launch {
            repository.toggleSyllabusTopic(subject, unit.unitId, topic.topicId, nextCompleted)
                .onSuccess {
                    if (nextCompleted) {
                        awardProgress(xpDelta = 25L, extraTopics = 1L)
                        _toastMessage.value = "Completed ${topic.code} • +25 XP"
                    }
                }
                .onFailure { err ->
                    _toastMessage.value = "Update failed: ${err.localizedMessage}"
                }
        }
    }

    fun importSyllabusFromFile(bytes: ByteArray, mimeType: String) {
        if (_offlineModeEnabled.value) {
            _extractionState.value = ExtractionBannerState(
                isWorking = false,
                errorText = "Disable Offline Study Mode to run AI PDF/Image extraction."
            )
            return
        }
        viewModelScope.launch {
            _extractionState.value = ExtractionBannerState(
                isWorking = true,
                statusText = "Extracting Subject → Unit → Topic structure from document..."
            )
            val currentCount = (subjectsState.value as? UiState.Success)?.data?.size?.toLong() ?: 0L
            aiService.extractSyllabus(bytes, mimeType, currentCount + 1L)
                .onSuccess { extracted ->
                    if (extracted.isEmpty()) {
                        _extractionState.value = ExtractionBannerState(
                            errorText = "No syllabus subjects found in that file."
                        )
                    } else {
                        repository.saveExtractedSubjects(extracted)
                            .onSuccess { count ->
                                _extractionState.value = ExtractionBannerState()
                                _toastMessage.value = "Extracted & saved $count subject(s) into Syllabus!"
                            }
                            .onFailure { err ->
                                _extractionState.value = ExtractionBannerState(
                                    errorText = "Failed saving extracted syllabus: ${err.localizedMessage}"
                                )
                            }
                    }
                }
                .onFailure { err ->
                    _extractionState.value = ExtractionBannerState(
                        errorText = err.localizedMessage ?: "PDF extraction failed."
                    )
                }
        }
    }

    fun reloadGroupASyllabus() {
        viewModelScope.launch {
            repository.seedGroupADefaultData(currentUserId)
                .onSuccess {
                    _toastMessage.value = "B.Tech 1st Sem Group-A Syllabus loaded!"
                }
                .onFailure { err ->
                    _toastMessage.value = "Failed loading syllabus: ${err.localizedMessage}"
                }
        }
    }

    // --- To-Do List Actions (Manual, From Syllabus, From Timetable) ---

    fun addManualTodo(title: String, notes: String, priority: String, dueLabel: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.addTodo(
                TodoItem(
                    title = title.trim(),
                    notes = notes.trim(),
                    sourceType = "MANUAL",
                    priority = priority,
                    dueLabel = dueLabel.ifBlank { "Today" }
                )
            ).onSuccess {
                _toastMessage.value = "Task added to To-Do List"
            }.onFailure { err ->
                _toastMessage.value = "Could not add task: ${err.localizedMessage}"
            }
        }
    }

    fun addTodoFromSyllabus(subject: SubjectDoc, unit: UnitItem, topic: TopicItem, dueLabel: String = "Today") {
        viewModelScope.launch {
            repository.addTodo(
                TodoItem(
                    title = "${topic.code} ${topic.title}",
                    notes = "${subject.title} • ${unit.unitNumber}: ${unit.title}",
                    sourceType = "SYLLABUS",
                    subjectId = subject.id,
                    subjectTitle = subject.title,
                    unitId = unit.unitId,
                    unitTitle = "${unit.unitNumber}: ${unit.title}",
                    topicId = topic.topicId,
                    priority = "HIGH",
                    dueLabel = dueLabel,
                    completed = topic.completed
                )
            ).onSuccess {
                _toastMessage.value = "Added '${topic.code}' to To-Do (auto-syncs with Syllabus!)"
            }.onFailure { err ->
                _toastMessage.value = "Failed adding syllabus task: ${err.localizedMessage}"
            }
        }
    }

    fun addTodoFromTimetable(slot: TimetableSlot) {
        val dayName = dayOfWeekShort(slot.dayOfWeek.toInt())
        viewModelScope.launch {
            repository.addTodo(
                TodoItem(
                    title = "${slot.subjectTitle} (${slot.startTime}–${slot.endTime})",
                    notes = buildString {
                        if (slot.unitHint.isNotBlank()) append(slot.unitHint)
                        if (slot.room.isNotBlank()) {
                            if (isNotEmpty()) append(" • ")
                            append("Room ${slot.room}")
                        }
                    },
                    sourceType = "TIMETABLE",
                    subjectId = slot.subjectId,
                    subjectTitle = slot.subjectTitle,
                    priority = if (slot.slotType == "LAB") "HIGH" else "MEDIUM",
                    dueLabel = "$dayName ${slot.startTime}"
                )
            ).onSuccess {
                _toastMessage.value = "Added timetable slot to To-Do List"
            }.onFailure { err ->
                _toastMessage.value = "Failed adding slot: ${err.localizedMessage}"
            }
        }
    }

    fun toggleTodo(todo: TodoItem) {
        val nextCompleted = !todo.completed
        val subjects = (subjectsState.value as? UiState.Success)?.data.orEmpty()
        val linkedSubject = subjects.firstOrNull { it.id == todo.subjectId }
        viewModelScope.launch {
            repository.toggleTodoAndSyncSyllabus(todo, nextCompleted, linkedSubject)
                .onSuccess { syncedSyllabus ->
                    if (nextCompleted) {
                        awardProgress(xpDelta = if (syncedSyllabus) 30L else 15L, extraTopics = if (syncedSyllabus) 1L else 0L)
                        _toastMessage.value = if (syncedSyllabus) {
                            "Task done & Syllabus topic auto-marked completed! +30 XP"
                        } else {
                            "Task completed • +15 XP"
                        }
                    } else if (syncedSyllabus) {
                        _toastMessage.value = "Task unchecked & synced with Syllabus"
                    }
                }
                .onFailure { err ->
                    _toastMessage.value = "Failed updating task: ${err.localizedMessage}"
                }
        }
    }

    fun deleteTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.deleteTodo(todo.id)
                .onFailure { err -> _toastMessage.value = "Delete failed: ${err.localizedMessage}" }
        }
    }

    // --- Timetable Actions ---

    fun addManualTimetableSlot(
        dayOfWeek: Int,
        startTime: String,
        endTime: String,
        subjectTitle: String,
        unitHint: String,
        room: String,
        slotType: String
    ) {
        if (subjectTitle.isBlank()) return
        viewModelScope.launch {
            repository.addTimetableSlots(
                listOf(
                    TimetableSlot(
                        dayOfWeek = dayOfWeek.toLong(),
                        startTime = startTime.ifBlank { "09:00" },
                        endTime = endTime.ifBlank { "10:00" },
                        subjectTitle = subjectTitle.trim(),
                        unitHint = unitHint.trim(),
                        room = room.trim(),
                        slotType = slotType
                    )
                )
            ).onSuccess {
                _toastMessage.value = "Timetable slot saved"
            }.onFailure { err ->
                _toastMessage.value = "Could not save slot: ${err.localizedMessage}"
            }
        }
    }

    fun deleteTimetableSlot(slotId: String) {
        viewModelScope.launch {
            repository.deleteTimetableSlot(slotId)
        }
    }

    fun reloadOfficialCseATimetable() {
        viewModelScope.launch {
            repository.ensureCseATimetableSeeded(currentUserId, forceReset = true)
                .onSuccess { count ->
                    _toastMessage.value = "Loaded official GEC Sheikhpura CSE-A Batch 2K26 timetable ($count slots)!"
                }
                .onFailure { err ->
                    _toastMessage.value = "Could not load CSE-A timetable: ${err.localizedMessage}"
                }
        }
    }

    fun importTimetableFromFile(bytes: ByteArray, mimeType: String) {
        if (_offlineModeEnabled.value) {
            _extractionState.value = ExtractionBannerState(
                errorText = "Disable Offline Study Mode to extract Timetable from PDF/Image."
            )
            return
        }
        viewModelScope.launch {
            _extractionState.value = ExtractionBannerState(
                isWorking = true,
                statusText = "Extracting weekly timetable schedule from document..."
            )
            aiService.extractTimetable(bytes, mimeType)
                .onSuccess { slots ->
                    if (slots.isEmpty()) {
                        _extractionState.value = ExtractionBannerState(
                            errorText = "No timetable slots detected in this file."
                        )
                    } else {
                        repository.addTimetableSlots(slots)
                            .onSuccess { count ->
                                _extractionState.value = ExtractionBannerState()
                                _toastMessage.value = "Imported $count timetable slots!"
                            }
                            .onFailure { err ->
                                _extractionState.value = ExtractionBannerState(
                                    errorText = "Failed saving timetable: ${err.localizedMessage}"
                                )
                            }
                    }
                }
                .onFailure { err ->
                    _extractionState.value = ExtractionBannerState(
                        errorText = err.localizedMessage ?: "Timetable extraction failed."
                    )
                }
        }
    }

    // --- Test Questions Extraction & Manual Creation ---

    fun extractQuestionsForUnit(
        subject: SubjectDoc,
        unit: UnitItem,
        fileBytes: ByteArray?,
        mimeType: String?,
        pastedText: String,
        attachedImageUri: String = ""
    ) {
        if (_offlineModeEnabled.value) {
            _extractionState.value = ExtractionBannerState(
                errorText = "Disable Offline Study Mode to run AI question extraction."
            )
            return
        }
        viewModelScope.launch {
            _extractionState.value = ExtractionBannerState(
                isWorking = true,
                statusText = "Extracting unit-wise questions, math symbols & marking schemes..."
            )
            aiService.extractUnitQuestions(
                fileBytes = fileBytes,
                mimeType = mimeType,
                pastedText = pastedText,
                subjectId = subject.id,
                subjectTitle = subject.title,
                unitId = unit.unitId,
                unitTitle = "${unit.unitNumber}: ${unit.title}",
                attachedImageUri = attachedImageUri
            ).onSuccess { questions ->
                if (questions.isEmpty()) {
                    _extractionState.value = ExtractionBannerState(
                        errorText = "No questions found in the uploaded content."
                    )
                } else {
                    repository.saveQuestions(questions)
                        .onSuccess { count ->
                            _extractionState.value = ExtractionBannerState()
                            _toastMessage.value = "Extracted $count question(s) for ${unit.unitNumber}!"
                        }
                        .onFailure { err ->
                            _extractionState.value = ExtractionBannerState(
                                errorText = "Save failed: ${err.localizedMessage}"
                            )
                        }
                }
            }.onFailure { err ->
                _extractionState.value = ExtractionBannerState(
                    errorText = err.localizedMessage ?: "Question extraction failed."
                )
            }
        }
    }

    fun addManualQuestion(question: TestQuestion) {
        viewModelScope.launch {
            repository.saveQuestions(listOf(question))
                .onSuccess { _toastMessage.value = "Question saved to ${question.unitTitle}" }
                .onFailure { err -> _toastMessage.value = "Failed saving question: ${err.localizedMessage}" }
        }
    }

    fun deleteQuestion(questionId: String) {
        viewModelScope.launch {
            repository.deleteQuestion(questionId)
        }
    }

    // --- Interactive Test Mode Engine ---

    fun startUnitTest(
        subject: SubjectDoc,
        unit: UnitItem,
        unitQuestions: List<TestQuestion>,
        timeLimitSeconds: Long? = null,
        examModeLabel: String = "PRACTICE"
    ) {
        if (unitQuestions.isEmpty()) {
            _toastMessage.value = "Upload or add questions for ${unit.unitNumber} first!"
            return
        }
        timerJob?.cancel()
        _activeTest.value = ActiveTestSession(
            subjectId = subject.id,
            subjectTitle = subject.title,
            unitId = unit.unitId,
            unitTitle = "${unit.unitNumber}: ${unit.title}",
            questions = unitQuestions,
            timeLimitSeconds = timeLimitSeconds,
            examModeLabel = examModeLabel
        )
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                var timeExpired = false
                _activeTest.update { session ->
                    if (session == null || session.isSubmitting) return@update session
                    val nextElapsed = session.elapsedSeconds + 1L
                    if (session.timeLimitSeconds != null && nextElapsed >= session.timeLimitSeconds) {
                        timeExpired = true
                    }
                    session.copy(elapsedSeconds = nextElapsed)
                }
                if (timeExpired) {
                    _toastMessage.value = "Exam time is up! Submitting your paper automatically..."
                    submitActiveTest(useAiGradingForSubjective = !_offlineModeEnabled.value)
                    break
                }
            }
        }
    }

    /**
     * #2 Weak-Topic Retry Quiz / Mistakes Deck: builds a targeted revision quiz from past
     * missed questions (<75% marks) and weak/improve topics across reports (or a specific report).
     */
    fun startWeakTopicRetryQuiz(targetReport: TestReport? = null) {
        val allQuestions = (questionsState.value as? UiState.Success)?.data.orEmpty()
        val allReports = (reportsState.value as? UiState.Success)?.data.orEmpty()

        val deck = if (targetReport != null) {
            StudyExamBuilder.buildReportRetryDeck(targetReport, allQuestions)
        } else {
            StudyExamBuilder.buildWeakTopicRetryDeck(allReports, allQuestions)
        }

        if (deck.isEmpty()) {
            _toastMessage.value = "No weak or missed topics found yet — complete a unit quiz first!"
            return
        }

        val subjects = (subjectsState.value as? UiState.Success)?.data.orEmpty()
        val primarySubject = targetReport?.let { rep -> subjects.firstOrNull { it.id == rep.subjectId } }
            ?: deck.firstOrNull()?.let { firstQ -> subjects.firstOrNull { it.id == firstQ.subjectId } }

        _selectedReport.value = null
        startUnitTest(
            subject = primarySubject ?: SubjectDoc(
                id = targetReport?.subjectId ?: "weak_topics",
                title = targetReport?.subjectTitle ?: "Mistakes Deck"
            ),
            unit = UnitItem(
                unitId = "weak_topics_deck",
                unitNumber = "Mistakes Deck",
                title = targetReport?.let { "Retry • ${it.unitTitle}" } ?: "Weak Topics Revision (${deck.size} Qs)"
            ),
            unitQuestions = deck.shuffled(),
            timeLimitSeconds = null,
            examModeLabel = "WEAK_TOPICS"
        )
    }

    /**
     * #4 BEU End-Sem Mock Paper Mode: generates a timed 70-mark exam simulator across all units.
     */
    fun startBeuEndSemMock(subject: SubjectDoc) {
        val allQuestions = (questionsState.value as? UiState.Success)?.data.orEmpty()
        val spec = StudyExamBuilder.buildBeuEndSemMockPaper(subject, allQuestions)
        if (spec.questions.isEmpty()) {
            _toastMessage.value = "No built-in questions available yet for ${subject.title}."
            return
        }

        startUnitTest(
            subject = subject,
            unit = UnitItem(
                unitId = "beu_70m_${subject.id}",
                unitNumber = "BEU Mock",
                title = "End-Sem Paper (${spec.totalMarks}M • ${spec.mcqCount} MCQ + ${spec.shortCount + spec.longCount} Desc)"
            ),
            unitQuestions = spec.questions,
            timeLimitSeconds = spec.durationMinutes * 60L,
            examModeLabel = "BEU_70M"
        )
    }

    fun cancelActiveTest() {
        timerJob?.cancel()
        _activeTest.value = null
    }

    fun setTestQuestionIndex(index: Int) {
        _activeTest.update { s ->
            s?.copy(currentIndex = index.coerceIn(0, (s.questions.size - 1).coerceAtLeast(0)))
        }
    }

    fun selectMcqOption(questionId: String, optionIndex: Int) {
        _activeTest.update { s ->
            s?.copy(mcqAnswers = s.mcqAnswers + (questionId to optionIndex))
        }
    }

    fun updateSubjectiveAnswer(questionId: String, text: String) {
        _activeTest.update { s ->
            s?.copy(subjectiveAnswers = s.subjectiveAnswers + (questionId to text))
        }
    }

    fun attachSubjectiveSolutionPhoto(questionId: String, uri: String, bytes: ByteArray, mimeType: String) {
        _activeTest.update { s ->
            s?.copy(
                subjectivePhotoUris = s.subjectivePhotoUris + (questionId to uri),
                subjectivePhotoBytes = s.subjectivePhotoBytes + (questionId to (bytes to mimeType))
            )
        }
    }

    fun toggleMarkingSchemeStep(questionId: String, stepIndex: Int) {
        _activeTest.update { s ->
            if (s == null) return@update null
            val currentSet = s.checkedSchemeSteps[questionId].orEmpty()
            val updatedSet = if (stepIndex in currentSet) currentSet - stepIndex else currentSet + stepIndex
            s.copy(checkedSchemeSteps = s.checkedSchemeSteps + (questionId to updatedSet))
        }
    }

    fun toggleFlagQuestionForReview(questionId: String) {
        _activeTest.update { s ->
            if (s == null) return@update null
            val updated = if (questionId in s.flaggedQuestionIds) {
                s.flaggedQuestionIds - questionId
            } else {
                s.flaggedQuestionIds + questionId
            }
            s.copy(flaggedQuestionIds = updated)
        }
    }

    fun submitActiveTest(useAiGradingForSubjective: Boolean) {
        val session = _activeTest.value ?: return
        if (session.isSubmitting) return
        timerJob?.cancel()
        _activeTest.update { it?.copy(isSubmitting = true) }

        viewModelScope.launch {
            val results = mutableListOf<QuestionResultItem>()
            var totalMarks = 0.0
            var obtainedMarks = 0.0
            var correctCount = 0L

            for (q in session.questions) {
                val maxM = q.maxMarks.toDouble().coerceAtLeast(1.0)
                totalMarks += maxM

                if (q.questionType == "MCQ") {
                    val chosen = session.mcqAnswers[q.id] ?: -1
                    val isRight = chosen == q.correctOptionIndex.toInt()
                    val awarded = if (isRight) maxM else 0.0
                    obtainedMarks += awarded
                    if (isRight) correctCount++
                    val chosenText = q.options.getOrNull(chosen) ?: "Unanswered"
                    val correctText = q.options.getOrNull(q.correctOptionIndex.toInt()) ?: "N/A"
                    results.add(
                        QuestionResultItem(
                            questionPrompt = q.prompt,
                            topicTitle = q.topicTitle.ifBlank { q.unitTitle },
                            questionType = "MCQ",
                            awardedMarks = awarded,
                            maxMarks = maxM,
                            feedback = if (isRight) {
                                "Correct! ${q.modelAnswer}"
                            } else {
                                "Correct answer: $correctText. ${q.modelAnswer}"
                            },
                            userAnswer = chosenText,
                            modelAnswer = q.modelAnswer
                        )
                    )
                } else {
                    val typedAnswer = session.subjectiveAnswers[q.id].orEmpty().trim()
                    val photoPair = session.subjectivePhotoBytes[q.id]
                    val checkedSteps = session.checkedSchemeSteps[q.id].orEmpty()

                    var awarded = 0.0
                    var feedback = ""

                    val shouldRunAi = useAiGradingForSubjective &&
                        !_offlineModeEnabled.value &&
                        (typedAnswer.isNotBlank() || photoPair != null)

                    if (shouldRunAi) {
                        val aiEval = aiService.evaluateSubjectiveAnswer(
                            question = q,
                            studentAnswer = typedAnswer,
                            solutionImageBytes = photoPair?.first,
                            solutionMimeType = photoPair?.second
                        )
                        if (aiEval.isSuccess) {
                            val eval = aiEval.getOrThrow()
                            awarded = ((eval.awardedMarks * 2.0).roundToInt() / 2.0).coerceIn(0.0, maxM)
                            feedback = buildString {
                                append("AI Rubric Grade: ${eval.feedback}")
                                if (eval.matchedSteps.isNotEmpty()) {
                                    append(" | Matched: ${eval.matchedSteps.joinToString("; ")}")
                                }
                            }
                        } else {
                            // Fallback cleanly to checked marking scheme steps
                            val schemeCount = q.markingScheme.size.coerceAtLeast(1)
                            awarded = ((checkedSteps.size.toDouble() / schemeCount) * maxM * 2.0).roundToInt() / 2.0
                            feedback = "Marking scheme self-check (${checkedSteps.size}/$schemeCount steps). ${q.modelAnswer}"
                        }
                    } else {
                        val schemeCount = q.markingScheme.size.coerceAtLeast(1)
                        awarded = if (checkedSteps.isNotEmpty()) {
                            ((checkedSteps.size.toDouble() / schemeCount) * maxM * 2.0).roundToInt() / 2.0
                        } else 0.0
                        feedback = "Evaluated via Marking Scheme (${checkedSteps.size}/$schemeCount steps met). Model: ${q.modelAnswer}"
                    }

                    obtainedMarks += awarded
                    if (awarded >= maxM * 0.6) correctCount++
                    results.add(
                        QuestionResultItem(
                            questionPrompt = q.prompt,
                            topicTitle = q.topicTitle.ifBlank { q.unitTitle },
                            questionType = "SUBJECTIVE",
                            awardedMarks = awarded,
                            maxMarks = maxM,
                            feedback = feedback,
                            userAnswer = typedAnswer.ifBlank { if (photoPair != null) "[Handwritten image submitted]" else "No answer written" },
                            modelAnswer = q.modelAnswer
                        )
                    )
                }
            }

            // Compute Strong, Weak, and Topics to Improve by topic accuracy
            val topicScores = results.groupBy { it.topicTitle }
            val strongTopics = mutableListOf<String>()
            val improveTopics = mutableListOf<String>()
            val weakTopics = mutableListOf<String>()

            topicScores.forEach { (topic, items) ->
                val earned = items.sumOf { it.awardedMarks }
                val possible = items.sumOf { it.maxMarks }.coerceAtLeast(1.0)
                val pct = (earned / possible) * 100.0
                when {
                    pct >= 75.0 -> strongTopics.add(topic)
                    pct >= 50.0 -> improveTopics.add(topic)
                    else -> weakTopics.add(topic)
                }
            }

            val finalPct = if (totalMarks > 0.0) ((obtainedMarks / totalMarks) * 1000.0).roundToInt() / 10.0 else 0.0
            val report = TestReport(
                subjectId = session.subjectId,
                subjectTitle = session.subjectTitle,
                unitId = session.unitId,
                unitTitle = session.unitTitle,
                obtainedMarks = obtainedMarks,
                totalMarks = totalMarks.coerceAtLeast(1.0),
                percentage = finalPct,
                durationSeconds = session.elapsedSeconds,
                totalQuestions = session.questions.size.toLong(),
                correctCount = correctCount,
                strongTopics = strongTopics,
                weakTopics = weakTopics,
                improveTopics = improveTopics,
                questionBreakdown = results
            )

            repository.saveTestReport(report)
                .onSuccess { savedId ->
                    val reportWithId = report.copy(id = savedId)
                    _activeTest.value = null
                    _selectedReport.value = reportWithId
                    val earnedXp = 40L + (finalPct * 0.6).roundToInt()
                    awardProgress(
                        xpDelta = earnedXp,
                        extraTests = 1L,
                        latestScorePct = finalPct
                    )
                    _toastMessage.value = "Report Card saved! +$earnedXp XP"
                }
                .onFailure { err ->
                    _activeTest.update { it?.copy(isSubmitting = false) }
                    _toastMessage.value = "Failed saving report: ${err.localizedMessage}"
                }
        }
    }

    fun openReportCard(report: TestReport?) {
        _selectedReport.value = report
    }

    fun addWeakTopicsToTodo(report: TestReport) {
        val topics = (report.weakTopics + report.improveTopics).distinct()
        if (topics.isEmpty()) return
        viewModelScope.launch {
            topics.forEach { topic ->
                repository.addTodo(
                    TodoItem(
                        title = "Revise: $topic",
                        notes = "Flagged from ${report.subjectTitle} • ${report.unitTitle} (${report.percentage}%)",
                        sourceType = "MANUAL",
                        subjectId = report.subjectId,
                        subjectTitle = report.subjectTitle,
                        unitId = report.unitId,
                        unitTitle = report.unitTitle,
                        priority = "HIGH",
                        dueLabel = "Priority Revision"
                    )
                )
            }
            _toastMessage.value = "Added ${topics.size} revision topic(s) to To-Do List!"
        }
    }

    // --- Reminder Preferences ---

    fun saveReminderPreferences(enabled: Boolean, hour: Int, minute: Int) {
        viewModelScope.launch {
            repository.updateReminderSettings(enabled, hour, minute)
                .onSuccess {
                    _toastMessage.value = if (enabled) {
                        String.format("Daily streak reminder scheduled for %02d:%02d", hour, minute)
                    } else {
                        "Daily reminders paused"
                    }
                }
        }
    }

    // --- Streak, Badges & Leaderboard Updater ---

    private suspend fun awardProgress(
        xpDelta: Long,
        extraTopics: Long = 0L,
        extraTests: Long = 0L,
        latestScorePct: Double = 0.0
    ) {
        val currentProfile = (profileState.value as? UiState.Success)?.data
        val subjects = (subjectsState.value as? UiState.Success)?.data.orEmpty()
        val reports = (reportsState.value as? UiState.Success)?.data.orEmpty()

        val completedTopics = subjects.sumOf { it.completedTopicsCount }.toLong() + extraTopics
        val totalTests = reports.size.toLong() + extraTests
        val bestScore = maxOf(
            latestScorePct,
            reports.maxOfOrNull { it.percentage } ?: 0.0
        )

        repository.recordStudyProgress(
            currentProfile = currentProfile,
            xpDelta = xpDelta,
            totalTopicsCompleted = completedTopics,
            totalTestsCompleted = totalTests,
            bestScorePercentage = bestScore
        ).onSuccess { newBadges ->
            if (newBadges.isNotEmpty()) {
                _toastMessage.value = "Badge Unlocked! Check Ranks & Badges!"
            }
        }
    }

    // --- Makina AI Study Companion Actions ---

    fun triggerWelcomeGreetingIfNeeded(isDeviceOnline: Boolean) {
        if (hasShownArrivalGreeting) return
        hasShownArrivalGreeting = true
        openMakinaGuide(isDeviceOnline = isDeviceOnline)
    }

    fun openMakinaGuide(isDeviceOnline: Boolean) {
        val currentProfile = (profileState.value as? UiState.Success)?.data
        val subjects = (subjectsState.value as? UiState.Success)?.data.orEmpty()
        val reports = (reportsState.value as? UiState.Success)?.data.orEmpty()
        val name = currentProfile?.displayName?.ifBlank { initialDisplayName } ?: initialDisplayName
        val streak = currentProfile?.streakDays ?: 1L
        val xp = currentProfile?.xp ?: 0L
        val completedTopics = subjects.sumOf { it.completedTopicsCount }
        val totalTopics = subjects.sumOf { it.totalTopicsCount }
        val preferOffline = _offlineModeEnabled.value || !isDeviceOnline

        val instantGreeting = makinaService.fallbackGreeting(name, streak, reports.size)
        _makinaState.value = MakinaCompanionUiState(
            isVisible = true,
            mode = MakinaMode.GREETING_AND_TOUR,
            title = "Makina",
            subtitle = "Your Charming AI Study Guide",
            contextLabel = "Welcome back, ${name.substringBefore(" ")} • $streak-day streak • ${reports.size} tests",
            contextSummary = "Student $name is browsing StudyApp (BEU CSE B.Tech). Completed $completedTopics/$totalTopics topics, $streak-day streak, $xp XP, ${reports.size} tests.",
            messages = if (preferOffline) listOf(MakinaMessage(isMakina = true, text = instantGreeting)) else emptyList(),
            isLoading = !preferOffline,
            errorText = null
        )

        makinaJob?.cancel()
        if (preferOffline) {
            makinaVoicePlayer?.speak(instantGreeting, preferOffline = true)
            return
        }

        makinaJob = viewModelScope.launch {
            val result = makinaService.generateArrivalGreeting(
                studentName = name,
                streakDays = streak,
                xp = xp,
                completedTopics = completedTopics,
                totalTopics = totalTopics,
                testsTaken = reports.size
            )
            val reply = result.getOrElse { instantGreeting }
            _makinaState.update {
                it.copy(
                    messages = listOf(MakinaMessage(isMakina = true, text = reply)),
                    isLoading = false,
                    errorText = null
                )
            }
            makinaVoicePlayer?.speak(reply, preferOffline = false)
        }
    }

    fun explainReportWithMakina(report: TestReport, isDeviceOnline: Boolean) {
        val preferOffline = _offlineModeEnabled.value || !isDeviceOnline
        _makinaState.value = MakinaCompanionUiState(
            isVisible = true,
            mode = MakinaMode.REPORT_EXPLAINER,
            title = "Makina • Result Review",
            subtitle = "${report.subjectTitle} • ${report.percentage}%",
            contextLabel = "Result: ${report.obtainedMarks}/${report.totalMarks} marks (${report.percentage}%) in ${report.unitTitle}",
            contextSummary = "Test Report for ${report.subjectTitle} - ${report.unitTitle}. Score ${report.obtainedMarks}/${report.totalMarks} (${report.percentage}%). Strong: ${report.strongTopics.joinToString()}. Improve: ${report.improveTopics.joinToString()}. Weak: ${report.weakTopics.joinToString()}.",
            messages = emptyList(),
            isLoading = !preferOffline,
            errorText = if (preferOffline) "Offline Mode is active — switch online so Makina can analyze your report with Gemini AI." else null
        )
        if (preferOffline) return

        makinaJob?.cancel()
        makinaJob = viewModelScope.launch {
            makinaService.explainTestResult(report)
                .onSuccess { explanation ->
                    _makinaState.update {
                        it.copy(
                            messages = listOf(MakinaMessage(isMakina = true, text = explanation)),
                            isLoading = false,
                            errorText = null
                        )
                    }
                    makinaVoicePlayer?.speak(explanation, preferOffline = false)
                }
                .onFailure { err ->
                    _makinaState.update {
                        it.copy(
                            isLoading = false,
                            errorText = err.localizedMessage ?: "Couldn't reach Makina right now. Please try again."
                        )
                    }
                }
        }
    }

    fun explainQuestionWithMakina(
        item: QuestionResultItem,
        subjectTitle: String,
        unitTitle: String,
        isDeviceOnline: Boolean
    ) {
        val preferOffline = _offlineModeEnabled.value || !isDeviceOnline
        _makinaState.value = MakinaCompanionUiState(
            isVisible = true,
            mode = MakinaMode.QUESTION_EXPLAINER,
            title = "Makina • Explain with AI",
            subtitle = "${item.topicTitle} • ${item.awardedMarks}/${item.maxMarks}M",
            contextLabel = item.questionPrompt,
            contextSummary = "Question from $subjectTitle ($unitTitle, topic ${item.topicTitle}): ${item.questionPrompt}. Student wrote: ${item.userAnswer}. Rubric note: ${item.feedback}.",
            messages = emptyList(),
            isLoading = !preferOffline,
            errorText = if (preferOffline) "Offline Mode is active — switch online so Makina can explain this question with Gemini AI." else null
        )
        if (preferOffline) return

        makinaJob?.cancel()
        makinaJob = viewModelScope.launch {
            makinaService.explainSpecificQuestion(item, subjectTitle, unitTitle)
                .onSuccess { explanation ->
                    _makinaState.update {
                        it.copy(
                            messages = listOf(MakinaMessage(isMakina = true, text = explanation)),
                            isLoading = false,
                            errorText = null
                        )
                    }
                    makinaVoicePlayer?.speak(explanation, preferOffline = false)
                }
                .onFailure { err ->
                    _makinaState.update {
                        it.copy(
                            isLoading = false,
                            errorText = err.localizedMessage ?: "Couldn't explain this question right now. Please retry."
                        )
                    }
                }
        }
    }

    fun sendMakinaFollowUp(doubtText: String, isDeviceOnline: Boolean) {
        val trimmed = doubtText.trim()
        if (trimmed.isEmpty()) return
        val preferOffline = _offlineModeEnabled.value || !isDeviceOnline
        val current = _makinaState.value
        val updatedMessages = current.messages + MakinaMessage(isMakina = false, text = trimmed)

        if (preferOffline) {
            _makinaState.update {
                it.copy(
                    messages = updatedMessages,
                    errorText = "Turn off Offline Study Mode to chat live with Makina."
                )
            }
            return
        }

        _makinaState.update {
            it.copy(messages = updatedMessages, isLoading = true, errorText = null)
        }

        makinaJob?.cancel()
        makinaJob = viewModelScope.launch {
            val history = updatedMessages.map { m -> (if (m.isMakina) "Makina" else "Student") to m.text }
            makinaService.answerFollowUpDoubt(
                contextSummary = current.contextSummary,
                conversationHistory = history,
                studentDoubt = trimmed
            ).onSuccess { reply ->
                _makinaState.update {
                    it.copy(
                        messages = it.messages + MakinaMessage(isMakina = true, text = reply),
                        isLoading = false,
                        errorText = null
                    )
                }
                makinaVoicePlayer?.speak(reply, preferOffline = false)
            }.onFailure { err ->
                _makinaState.update {
                    it.copy(
                        isLoading = false,
                        errorText = err.localizedMessage ?: "Makina couldn't respond right now. Please try again."
                    )
                }
            }
        }
    }

    fun sendMakinaVoiceAudioDoubt(audioBytes: ByteArray, mimeType: String, isDeviceOnline: Boolean) {
        if (audioBytes.isEmpty()) return
        val preferOffline = _offlineModeEnabled.value || !isDeviceOnline
        val current = _makinaState.value
        val updatedMessages = current.messages + MakinaMessage(isMakina = false, text = "[Spoken voice question]")

        if (preferOffline) {
            _makinaState.update {
                it.copy(
                    messages = updatedMessages,
                    errorText = "Turn off Offline Study Mode so Makina can process your voice question with Gemini."
                )
            }
            return
        }

        _makinaState.update {
            it.copy(messages = updatedMessages, isLoading = true, errorText = null)
        }

        makinaJob?.cancel()
        makinaJob = viewModelScope.launch {
            val history = updatedMessages.map { m -> (if (m.isMakina) "Makina" else "Student") to m.text }
            makinaService.answerVoiceAudioDoubt(
                audioBytes = audioBytes,
                mimeType = mimeType,
                contextSummary = current.contextSummary,
                conversationHistory = history
            ).onSuccess { reply ->
                _makinaState.update {
                    it.copy(
                        messages = it.messages + MakinaMessage(isMakina = true, text = reply),
                        isLoading = false,
                        errorText = null
                    )
                }
                makinaVoicePlayer?.speak(reply, preferOffline = false)
            }.onFailure { err ->
                _makinaState.update {
                    it.copy(
                        isLoading = false,
                        errorText = err.localizedMessage ?: "Makina couldn't process your voice note right now."
                    )
                }
            }
        }
    }

    fun speakWithMakina(text: String, isDeviceOnline: Boolean) {
        val preferOffline = _offlineModeEnabled.value || !isDeviceOnline
        makinaVoicePlayer?.speak(text, preferOffline = preferOffline, forceSpeak = true)
    }

    fun stopMakinaVoice() {
        makinaVoicePlayer?.stop()
    }

    fun toggleMakinaMute() {
        makinaVoicePlayer?.toggleMute()
    }

    fun toggleMakinaAutoSpeak() {
        makinaVoicePlayer?.toggleAutoSpeak()
    }

    fun resetMakinaChat(isDeviceOnline: Boolean) {
        makinaJob?.cancel()
        makinaVoicePlayer?.stop()
        openMakinaGuide(isDeviceOnline)
    }

    fun dismissMakina() {
        makinaJob?.cancel()
        makinaVoicePlayer?.stop()
        _makinaState.update { it.copy(isVisible = false, isLoading = false) }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        makinaJob?.cancel()
        makinaVoicePlayer?.release()
    }
}

fun dayOfWeekShort(day: Int): String = when (day) {
    1 -> "Mon"
    2 -> "Tue"
    3 -> "Wed"
    4 -> "Thu"
    5 -> "Fri"
    6 -> "Sat"
    else -> "Sun"
}
