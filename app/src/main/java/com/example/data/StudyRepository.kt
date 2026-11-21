package com.example.data

import android.content.Context
import android.util.Log
import com.example.R
import com.example.firebase.OperationType
import com.example.firebase.handleFirestoreError
import com.example.model.BeeeQuestionBank
import com.example.model.CfetQuestionBank
import com.example.model.LeaderboardEntry
import com.example.model.Math1QuestionBank
import com.example.model.QuestionResultItem
import com.example.model.StudyBadges
import com.example.model.SubjectDoc
import com.example.model.SyllabusSeedData
import com.example.model.TestQuestion
import com.example.model.TestReport
import com.example.model.TimetableSlot
import com.example.model.TodoItem
import com.example.model.TopicItem
import com.example.model.UnitItem
import com.example.model.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private const val REPO_TAG = "StudyRepository"

class StudyRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    constructor(context: Context) : this(
        createOfflineEnabledFirestore(context.applicationContext)
    )

    companion object {
        fun createOfflineEnabledFirestore(appContext: Context): FirebaseFirestore {
            val databaseId = appContext.getString(R.string.firestore_database_id)
            val firestore = FirebaseFirestore.getInstance(databaseId)
            try {
                val settings = firestoreSettings {
                    setLocalCacheSettings(
                        persistentCacheSettings {
                            setSizeBytes(100L * 1024L * 1024L) // 100 MB offline study cache
                        }
                    )
                }
                firestore.firestoreSettings = settings
            } catch (e: IllegalStateException) {
                // Settings already applied for this Firestore instance
                Log.d(REPO_TAG, "Firestore persistent cache settings already configured.")
            }
            return firestore
        }

        fun todayIsoDate(): String {
            return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        }

        fun yesterdayIsoDate(): String {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, -1)
            return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.time)
        }
    }

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing Firestore.")
    }

    suspend fun setOfflineMode(offline: Boolean): Result<Unit> = try {
        if (offline) db.disableNetwork().await() else db.enableNetwork().await()
        Result.success(Unit)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Log.w(REPO_TAG, "Failed to toggle Firestore network state", e)
        Result.failure(e)
    }

    // --- Real-Time Observation Streams ---

    fun observeUserProfile(userId: String): Flow<UserProfile?> = flow {
        val path = "users/$userId"
        emitAll(
            db.collection("users").document(userId)
                .snapshots()
                .map { snap ->
                    if (snap.exists()) {
                        snap.toObject(UserProfile::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    } else null
                }
                .catch { error ->
                    logStreamErrorIfSignedIn(error, OperationType.GET, path, userId)
                    throw error
                }
        )
    }

    fun observeSubjects(userId: String): Flow<List<SubjectDoc>> = flow {
        val path = "users/$userId/subjects"
        emitAll(
            db.collection("users").document(userId).collection("subjects")
                .snapshots()
                .map { snap ->
                    snap.toObjects(SubjectDoc::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                        .sortedWith(compareBy<SubjectDoc> { it.sortOrder }.thenBy { it.courseCode })
                }
                .catch { error ->
                    logStreamErrorIfSignedIn(error, OperationType.LIST, path, userId)
                    throw error
                }
        )
    }

    fun observeTodos(userId: String): Flow<List<TodoItem>> = flow {
        val path = "users/$userId/todos"
        emitAll(
            db.collection("users").document(userId).collection("todos")
                .snapshots()
                .map { snap ->
                    snap.toObjects(TodoItem::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                        .sortedWith(
                            compareBy<TodoItem> { it.completed }
                                .thenBy { priorityRank(it.priority) }
                                .thenByDescending { it.createdAt?.seconds ?: 0L }
                        )
                }
                .catch { error ->
                    logStreamErrorIfSignedIn(error, OperationType.LIST, path, userId)
                    throw error
                }
        )
    }

    fun observeTimetable(userId: String): Flow<List<TimetableSlot>> = flow {
        val path = "users/$userId/timetable"
        emitAll(
            db.collection("users").document(userId).collection("timetable")
                .snapshots()
                .map { snap ->
                    snap.toObjects(TimetableSlot::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                        .sortedWith(compareBy<TimetableSlot> { it.dayOfWeek }.thenBy { it.startTime })
                }
                .catch { error ->
                    logStreamErrorIfSignedIn(error, OperationType.LIST, path, userId)
                    throw error
                }
        )
    }

    fun observeQuestions(userId: String): Flow<List<TestQuestion>> = flow {
        val path = "users/$userId/questions"
        emitAll(
            db.collection("users").document(userId).collection("questions")
                .snapshots()
                .map { snap ->
                    snap.toObjects(TestQuestion::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                        .sortedWith(
                            compareBy<TestQuestion> { it.subjectTitle }
                                .thenBy { it.unitTitle }
                                .thenBy { it.topicCode }
                        )
                }
                .catch { error ->
                    logStreamErrorIfSignedIn(error, OperationType.LIST, path, userId)
                    throw error
                }
        )
    }

    fun observeReports(userId: String): Flow<List<TestReport>> = flow {
        val path = "users/$userId/reports"
        emitAll(
            db.collection("users").document(userId).collection("reports")
                .snapshots()
                .map { snap ->
                    snap.toObjects(TestReport::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                        .sortedByDescending { it.createdAt?.seconds ?: 0L }
                }
                .catch { error ->
                    logStreamErrorIfSignedIn(error, OperationType.LIST, path, userId)
                    throw error
                }
        )
    }

    fun observeLeaderboard(userId: String): Flow<List<LeaderboardEntry>> = flow {
        val path = "leaderboard"
        emitAll(
            db.collection("leaderboard")
                .whereEqualTo("publicVisible", true)
                .limit(50)
                .snapshots()
                .map { snap ->
                    snap.toObjects(LeaderboardEntry::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                        .sortedWith(compareByDescending<LeaderboardEntry> { it.xp }.thenByDescending { it.streakDays })
                }
                .catch { error ->
                    logStreamErrorIfSignedIn(error, OperationType.LIST, path, userId)
                    throw error
                }
        )
    }

    private fun logStreamErrorIfSignedIn(
        error: Throwable,
        op: OperationType,
        path: String,
        expectedUid: String
    ) {
        val firestoreError = generateSequence(error) { it.cause }
            .filterIsInstance<FirebaseFirestoreException>()
            .firstOrNull()
        if (firestoreError == null && error is CancellationException) return
        val ex = firestoreError ?: (error as? Exception)
        if (ex != null && auth.currentUser?.uid == expectedUid) {
            handleFirestoreError(ex, op, path)
        }
    }

    private fun priorityRank(priority: String): Int = when (priority) {
        "HIGH" -> 0
        "MEDIUM" -> 1
        else -> 2
    }

    // --- Initialization & Seeding ---

    suspend fun ensureUserInitializedAndSeeded(displayName: String): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val safeName = displayName.trim().take(60).ifBlank { "Student" }
        val userRef = db.collection("users").document(uid)
        val lbRef = db.collection("leaderboard").document(uid)
        return try {
            val snap = userRef.get().await()
            if (!snap.exists()) {
                val profilePayload = mapOf(
                    "userId" to uid,
                    "displayName" to safeName,
                    "streakDays" to 1L,
                    "longestStreak" to 1L,
                    "xp" to 50L,
                    "lastStudyDate" to todayIsoDate(),
                    "unlockedBadges" to emptyList<String>(),
                    "dailyGoalTopics" to 4L,
                    "reminderHour" to 19L,
                    "reminderMinute" to 0L,
                    "remindersEnabled" to false,
                    "syllabusSeeded" to true,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                userRef.set(profilePayload).await()

                val lbPayload = mapOf(
                    "userId" to uid,
                    "displayName" to safeName,
                    "xp" to 50L,
                    "streakDays" to 1L,
                    "topicsCompleted" to 0L,
                    "testsCompleted" to 0L,
                    "badgesCount" to 0L,
                    "publicVisible" to true,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                lbRef.set(lbPayload).await()

                seedGroupADefaultData(uid)
            } else {
                val seeded = snap.getBoolean("syllabusSeeded") ?: false
                if (!seeded) {
                    seedGroupADefaultData(uid)
                    userRef.update(
                        mapOf(
                            "syllabusSeeded" to true,
                            "updatedAt" to FieldValue.serverTimestamp()
                        )
                    ).await()
                } else {
                    ensureBeeeQuestionBankSeeded(uid)
                    ensureCfetQuestionBankSeeded(uid)
                    ensureMath1QuestionBankSeeded(uid)
                    ensureCseATimetableSeeded(uid)
                }
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, userRef.path)
            Result.failure(e)
        }
    }

    suspend fun ensureBeeeQuestionBankSeeded(userId: String = requireUserId()): Result<Int> {
        val questionsCol = db.collection("users").document(userId).collection("questions")
        return try {
            val sentinel = questionsCol.document(BeeeQuestionBank.SENTINEL_QUESTION_ID).get().await()
            if (sentinel.exists()) {
                return Result.success(0)
            }
            val beeeQuestions = BeeeQuestionBank.allQuestions()
            beeeQuestions.chunked(100).forEach { chunk ->
                val batch = db.batch()
                chunk.forEach { q ->
                    batch.set(questionsCol.document(q.id), questionToCreateMap(q))
                }
                batch.commit().await()
            }
            Result.success(beeeQuestions.size)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, "users/$userId/questions")
            Result.failure(e)
        }
    }

    suspend fun ensureCfetQuestionBankSeeded(userId: String = requireUserId()): Result<Int> {
        val questionsCol = db.collection("users").document(userId).collection("questions")
        return try {
            val sentinel = questionsCol.document(CfetQuestionBank.SENTINEL_QUESTION_ID).get().await()
            if (sentinel.exists()) {
                return Result.success(0)
            }
            val cfetQuestions = CfetQuestionBank.allQuestions()
            cfetQuestions.chunked(100).forEach { chunk ->
                val batch = db.batch()
                chunk.forEach { q ->
                    batch.set(questionsCol.document(q.id), questionToCreateMap(q))
                }
                batch.commit().await()
            }
            Result.success(cfetQuestions.size)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, "users/$userId/questions")
            Result.failure(e)
        }
    }

    suspend fun ensureMath1QuestionBankSeeded(userId: String = requireUserId()): Result<Int> {
        val questionsCol = db.collection("users").document(userId).collection("questions")
        return try {
            val sentinel = questionsCol.document(Math1QuestionBank.SENTINEL_QUESTION_ID).get().await()
            if (sentinel.exists()) {
                return Result.success(0)
            }
            val math1Questions = Math1QuestionBank.allQuestions()
            math1Questions.chunked(100).forEach { chunk ->
                val batch = db.batch()
                chunk.forEach { q ->
                    batch.set(questionsCol.document(q.id), questionToCreateMap(q))
                }
                batch.commit().await()
            }
            Result.success(math1Questions.size)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, "users/$userId/questions")
            Result.failure(e)
        }
    }

    suspend fun ensureCseATimetableSeeded(
        userId: String = requireUserId(),
        forceReset: Boolean = false
    ): Result<Int> {
        val ttCol = db.collection("users").document(userId).collection("timetable")
        return try {
            if (!forceReset) {
                val sentinel = ttCol.document(SyllabusSeedData.CSE_A_TIMETABLE_SENTINEL_ID).get().await()
                if (sentinel.exists()) {
                    return Result.success(0)
                }
            }
            val existingDocs = ttCol.get().await()
            val batch = db.batch()
            existingDocs.documents.forEach { doc ->
                val isLegacyStarter = doc.id.startsWith("tt_mon_") ||
                    doc.id.startsWith("tt_tue_") ||
                    doc.id.startsWith("tt_wed_") ||
                    doc.id.startsWith("tt_thu_") ||
                    doc.id.startsWith("tt_fri_")
                val isOfficialCseA = doc.id.startsWith("tt_csea_")
                if (isLegacyStarter || (forceReset && isOfficialCseA)) {
                    batch.delete(doc.reference)
                }
            }
            val officialSlots = SyllabusSeedData.starterTimetableSlots()
            officialSlots.forEach { slot ->
                batch.set(ttCol.document(slot.id), timetableToCreateMap(slot))
            }
            batch.commit().await()
            Result.success(officialSlots.size)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, "users/$userId/timetable")
            Result.failure(e)
        }
    }

    suspend fun seedGroupADefaultData(userId: String = requireUserId()): Result<Unit> {
        return try {
            val batch = db.batch()
            val subjectsCol = db.collection("users").document(userId).collection("subjects")
            SyllabusSeedData.defaultGroupASyllabus().forEach { subject ->
                val docRef = subjectsCol.document(subject.id)
                batch.set(docRef, subjectToCreateMap(subject))
            }

            val timetableCol = db.collection("users").document(userId).collection("timetable")
            SyllabusSeedData.starterTimetableSlots().forEach { slot ->
                val docRef = timetableCol.document(slot.id)
                batch.set(docRef, timetableToCreateMap(slot))
            }

            val questionsCol = db.collection("users").document(userId).collection("questions")
            SyllabusSeedData.starterAdvancedQuestions()
                .filterNot {
                    it.subjectId == BeeeQuestionBank.SUBJECT_ID ||
                        it.subjectId == CfetQuestionBank.SUBJECT_ID ||
                        it.subjectId == Math1QuestionBank.SUBJECT_ID ||
                        it.subjectId == BundledQuestionBankLoader.PHYSICS_SUBJECT_ID ||
                        it.subjectId == BundledQuestionBankLoader.AI_SUBJECT_ID
                }
                .forEach { q ->
                    val docRef = questionsCol.document(q.id)
                    batch.set(docRef, questionToCreateMap(q))
                }
            batch.commit().await()

            // Seed all 252 BEEE Unit 1.0–5.0 questions in safe chunked batches
            BeeeQuestionBank.allQuestions().chunked(100).forEach { chunk ->
                val qBatch = db.batch()
                chunk.forEach { q ->
                    qBatch.set(questionsCol.document(q.id), questionToCreateMap(q))
                }
                qBatch.commit().await()
            }

            // Seed all 422 CFET Unit 1–5 questions in safe chunked batches
            CfetQuestionBank.allQuestions().chunked(100).forEach { chunk ->
                val qBatch = db.batch()
                chunk.forEach { q ->
                    qBatch.set(questionsCol.document(q.id), questionToCreateMap(q))
                }
                qBatch.commit().await()
            }

            // Seed all 170 Engineering Mathematics-I Unit 1.0–5.0 questions in safe chunked batches
            Math1QuestionBank.allQuestions().chunked(100).forEach { chunk ->
                val qBatch = db.batch()
                chunk.forEach { q ->
                    qBatch.set(questionsCol.document(q.id), questionToCreateMap(q))
                }
                qBatch.commit().await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, "users/$userId/subjects")
            Result.failure(e)
        }
    }

    // --- Syllabus Mutations ---

    suspend fun saveExtractedSubjects(subjects: List<SubjectDoc>): Result<Int> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        return try {
            val batch = db.batch()
            val col = db.collection("users").document(uid).collection("subjects")
            subjects.take(15).forEachIndexed { idx, subj ->
                val safeId = sanitizeDocId("ext_${subj.courseCode}_${System.currentTimeMillis()}_$idx")
                batch.set(col.document(safeId), subjectToCreateMap(subj))
            }
            batch.commit().await()
            Result.success(subjects.size)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, "users/$uid/subjects")
            Result.failure(e)
        }
    }

    suspend fun toggleSyllabusTopic(
        subject: SubjectDoc,
        unitId: String,
        topicId: String,
        newCompleted: Boolean
    ): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val docRef = db.collection("users").document(uid).collection("subjects").document(subject.id)
        return try {
            val updatedUnits = subject.units.map { unit ->
                if (unit.unitId == unitId) {
                    unit.copy(
                        topics = unit.topics.map { topic ->
                            if (topic.topicId == topicId) topic.copy(completed = newCompleted) else topic
                        }
                    )
                } else unit
            }
            docRef.update(
                mapOf(
                    "units" to unitsToFirestoreList(updatedUnits),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    // --- To-Do Mutations (with Automatic Syllabus Sync!) ---

    suspend fun addTodo(todo: TodoItem): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val docRef = db.collection("users").document(uid).collection("todos").document()
        return try {
            val payload = mapOf(
                "title" to todo.title.trim().take(200).ifBlank { "Study Task" },
                "notes" to todo.notes.trim().take(500),
                "sourceType" to todo.sourceType.takeIf { it in listOf("MANUAL", "SYLLABUS", "TIMETABLE") }.orEmpty().ifBlank { "MANUAL" },
                "subjectId" to todo.subjectId.take(128),
                "subjectTitle" to todo.subjectTitle.take(120),
                "unitId" to todo.unitId.take(128),
                "unitTitle" to todo.unitTitle.take(120),
                "topicId" to todo.topicId.take(128),
                "priority" to todo.priority.takeIf { it in listOf("LOW", "MEDIUM", "HIGH") }.orEmpty().ifBlank { "MEDIUM" },
                "dueLabel" to todo.dueLabel.trim().take(60).ifBlank { "Today" },
                "completed" to todo.completed,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun toggleTodoAndSyncSyllabus(
        todo: TodoItem,
        newCompleted: Boolean,
        linkedSubject: SubjectDoc?
    ): Result<Boolean> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val todoRef = db.collection("users").document(uid).collection("todos").document(todo.id)
        return try {
            val batch = db.batch()
            batch.update(
                todoRef,
                mapOf(
                    "completed" to newCompleted,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )
            var syncedSyllabus = false
            if (todo.sourceType == "SYLLABUS" &&
                todo.subjectId.isNotBlank() &&
                todo.unitId.isNotBlank() &&
                todo.topicId.isNotBlank() &&
                linkedSubject != null
            ) {
                val updatedUnits = linkedSubject.units.map { unit ->
                    if (unit.unitId == todo.unitId) {
                        unit.copy(
                            topics = unit.topics.map { topic ->
                                if (topic.topicId == todo.topicId) topic.copy(completed = newCompleted) else topic
                            }
                        )
                    } else unit
                }
                val subjectRef = db.collection("users").document(uid).collection("subjects").document(linkedSubject.id)
                batch.update(
                    subjectRef,
                    mapOf(
                        "units" to unitsToFirestoreList(updatedUnits),
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                )
                syncedSyllabus = true
            }
            batch.commit().await()
            Result.success(syncedSyllabus)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, todoRef.path)
            Result.failure(e)
        }
    }

    suspend fun deleteTodo(todoId: String): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val docRef = db.collection("users").document(uid).collection("todos").document(todoId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    // --- Timetable Mutations ---

    suspend fun addTimetableSlots(slots: List<TimetableSlot>): Result<Int> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        return try {
            val batch = db.batch()
            val col = db.collection("users").document(uid).collection("timetable")
            slots.take(35).forEach { slot ->
                batch.set(col.document(), timetableToCreateMap(slot))
            }
            batch.commit().await()
            Result.success(slots.size)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, "users/$uid/timetable")
            Result.failure(e)
        }
    }

    suspend fun deleteTimetableSlot(slotId: String): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val docRef = db.collection("users").document(uid).collection("timetable").document(slotId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    // --- Test Questions & Saved Report Cards ---

    suspend fun saveQuestions(questions: List<TestQuestion>): Result<Int> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        return try {
            val batch = db.batch()
            val col = db.collection("users").document(uid).collection("questions")
            questions.take(40).forEach { q ->
                batch.set(col.document(), questionToCreateMap(q))
            }
            batch.commit().await()
            Result.success(questions.size)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, "users/$uid/questions")
            Result.failure(e)
        }
    }

    suspend fun deleteQuestion(questionId: String): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val docRef = db.collection("users").document(uid).collection("questions").document(questionId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun saveTestReport(report: TestReport): Result<String> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val docRef = db.collection("users").document(uid).collection("reports").document()
        return try {
            val breakdownMaps = report.questionBreakdown.take(100).map { item ->
                mapOf(
                    "questionPrompt" to item.questionPrompt.take(500),
                    "topicTitle" to item.topicTitle.take(160),
                    "questionType" to item.questionType,
                    "awardedMarks" to item.awardedMarks,
                    "maxMarks" to item.maxMarks,
                    "feedback" to item.feedback.take(500),
                    "userAnswer" to item.userAnswer.take(800),
                    "modelAnswer" to item.modelAnswer.take(1500)
                )
            }
            val payload = mapOf(
                "subjectId" to report.subjectId.take(128).ifBlank { "general" },
                "subjectTitle" to report.subjectTitle.take(120).ifBlank { "General Subject" },
                "unitId" to report.unitId.take(128).ifBlank { "unit_all" },
                "unitTitle" to report.unitTitle.take(120).ifBlank { "Unit Assessment" },
                "obtainedMarks" to report.obtainedMarks.coerceIn(0.0, 1000.0),
                "totalMarks" to report.totalMarks.coerceIn(1.0, 1000.0),
                "percentage" to report.percentage.coerceIn(0.0, 100.0),
                "durationSeconds" to report.durationSeconds.coerceIn(0L, 86400L),
                "totalQuestions" to report.totalQuestions.coerceIn(1L, 200L),
                "correctCount" to report.correctCount.coerceIn(0L, 200L),
                "strongTopics" to report.strongTopics.take(30),
                "weakTopics" to report.weakTopics.take(30),
                "improveTopics" to report.improveTopics.take(30),
                "questionBreakdown" to breakdownMaps,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            Result.success(docRef.id)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    // --- Streak, XP, Badges & Leaderboard Sync ---

    suspend fun recordStudyProgress(
        currentProfile: UserProfile?,
        xpDelta: Long,
        totalTopicsCompleted: Long,
        totalTestsCompleted: Long,
        bestScorePercentage: Double
    ): Result<List<String>> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val profile = currentProfile ?: return Result.success(emptyList())
        val today = todayIsoDate()
        val yesterday = yesterdayIsoDate()

        val newStreak = when (profile.lastStudyDate) {
            today -> profile.streakDays.coerceAtLeast(1L)
            yesterday -> profile.streakDays + 1L
            else -> 1L
        }
        val newLongest = maxOf(profile.longestStreak, newStreak)
        val newXp = (profile.xp + xpDelta).coerceIn(0L, 10_000_000L)

        val badges = profile.unlockedBadges.toMutableSet()
        val newlyUnlocked = mutableListOf<String>()
        fun unlockIf(condition: Boolean, badgeId: String) {
            if (condition && badges.add(badgeId)) newlyUnlocked.add(badgeId)
        }
        unlockIf(totalTopicsCompleted >= 1L || xpDelta > 0L, "first_spark")
        unlockIf(newStreak >= 3L, "streak_3")
        unlockIf(newStreak >= 7L, "streak_7")
        unlockIf(totalTopicsCompleted >= 10L, "syllabus_10")
        unlockIf(totalTopicsCompleted >= 25L, "syllabus_25")
        unlockIf(totalTestsCompleted >= 1L, "first_test")
        unlockIf(bestScorePercentage >= 85.0, "ace_85")
        unlockIf(totalTestsCompleted >= 3L, "subjective_pro")

        val finalBadges = badges.take(30)
        val userRef = db.collection("users").document(uid)
        val lbRef = db.collection("leaderboard").document(uid)
        return try {
            userRef.update(
                mapOf(
                    "streakDays" to newStreak,
                    "longestStreak" to newLongest,
                    "xp" to newXp,
                    "lastStudyDate" to today,
                    "unlockedBadges" to finalBadges,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()

            val lbSnap = lbRef.get().await()
            if (lbSnap.exists()) {
                lbRef.update(
                    mapOf(
                        "displayName" to profile.displayName.take(60).ifBlank { "Student" },
                        "xp" to newXp,
                        "streakDays" to newStreak,
                        "topicsCompleted" to totalTopicsCompleted.coerceIn(0L, 10000L),
                        "testsCompleted" to totalTestsCompleted.coerceIn(0L, 10000L),
                        "badgesCount" to finalBadges.size.toLong(),
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                ).await()
            } else {
                lbRef.set(
                    mapOf(
                        "userId" to uid,
                        "displayName" to profile.displayName.take(60).ifBlank { "Student" },
                        "xp" to newXp,
                        "streakDays" to newStreak,
                        "topicsCompleted" to totalTopicsCompleted.coerceIn(0L, 10000L),
                        "testsCompleted" to totalTestsCompleted.coerceIn(0L, 10000L),
                        "badgesCount" to finalBadges.size.toLong(),
                        "publicVisible" to true,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                ).await()
            }
            Result.success(newlyUnlocked)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, userRef.path)
            Result.failure(e)
        }
    }

    suspend fun updateReminderSettings(enabled: Boolean, hour: Int, minute: Int): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(IllegalStateException("Not signed in"))
        val userRef = db.collection("users").document(uid)
        return try {
            userRef.update(
                mapOf(
                    "remindersEnabled" to enabled,
                    "reminderHour" to hour.toLong().coerceIn(0L, 23L),
                    "reminderMinute" to minute.toLong().coerceIn(0L, 59L),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, userRef.path)
            Result.failure(e)
        }
    }

    // --- Map Serialization Helpers (1:1 parity with firestore.rules) ---

    private fun unitsToFirestoreList(units: List<UnitItem>): List<Map<String, Any>> {
        return units.take(20).map { unit ->
            mapOf(
                "unitId" to unit.unitId,
                "unitNumber" to unit.unitNumber,
                "title" to unit.title,
                "hours" to unit.hours,
                "topics" to unit.topics.map { topic ->
                    mapOf(
                        "topicId" to topic.topicId,
                        "code" to topic.code,
                        "title" to topic.title,
                        "completed" to topic.completed
                    )
                }
            )
        }
    }

    private fun subjectToCreateMap(subject: SubjectDoc): Map<String, Any> = mapOf(
        "courseCode" to subject.courseCode.trim().take(30).ifBlank { "COURSE" },
        "title" to subject.title.trim().take(120).ifBlank { "Untitled Subject" },
        "credits" to subject.credits.coerceIn(0L, 20L),
        "category" to if (subject.category == "LAB") "LAB" else "THEORY",
        "sortOrder" to subject.sortOrder.coerceIn(0L, 500L),
        "units" to unitsToFirestoreList(subject.units),
        "createdAt" to FieldValue.serverTimestamp(),
        "updatedAt" to FieldValue.serverTimestamp()
    )

    private fun timetableToCreateMap(slot: TimetableSlot): Map<String, Any> = mapOf(
        "dayOfWeek" to slot.dayOfWeek.coerceIn(1L, 7L),
        "startTime" to slot.startTime.trim().take(20).ifBlank { "09:00" },
        "endTime" to slot.endTime.trim().take(20).ifBlank { "10:00" },
        "subjectId" to slot.subjectId.take(128),
        "subjectTitle" to slot.subjectTitle.trim().take(120).ifBlank { "Study Session" },
        "unitHint" to slot.unitHint.trim().take(120),
        "room" to slot.room.trim().take(60),
        "slotType" to slot.slotType.takeIf { it in listOf("LECTURE", "LAB", "SELF_STUDY") }.orEmpty().ifBlank { "LECTURE" },
        "createdAt" to FieldValue.serverTimestamp(),
        "updatedAt" to FieldValue.serverTimestamp()
    )

    private fun questionToCreateMap(q: TestQuestion): Map<String, Any> = mapOf(
        "subjectId" to q.subjectId.take(128).ifBlank { "general" },
        "subjectTitle" to q.subjectTitle.trim().take(120).ifBlank { "General Subject" },
        "unitId" to q.unitId.take(128).ifBlank { "unit_1" },
        "unitTitle" to q.unitTitle.trim().take(120).ifBlank { "Unit 1.0" },
        "topicCode" to q.topicCode.trim().take(30),
        "topicTitle" to q.topicTitle.trim().take(160).ifBlank { "Core Topic" },
        "questionType" to if (q.questionType == "SUBJECTIVE") "SUBJECTIVE" else "MCQ",
        "prompt" to q.prompt.trim().take(2000).ifBlank { "Solve the problem." },
        "formulaExpr" to q.formulaExpr.trim().take(500),
        "diagramKind" to q.diagramKind.trim().take(60).ifBlank { "NONE" },
        "imageUri" to q.imageUri.trim().take(500),
        "options" to q.options.take(6),
        "correctOptionIndex" to q.correctOptionIndex.coerceIn(-1L, 5L),
        "maxMarks" to q.maxMarks.coerceIn(1L, 50L),
        "modelAnswer" to q.modelAnswer.trim().take(2500),
        "markingScheme" to q.markingScheme.take(12),
        "createdAt" to FieldValue.serverTimestamp(),
        "updatedAt" to FieldValue.serverTimestamp()
    )

    private fun sanitizeDocId(raw: String): String {
        val cleaned = raw.replace(Regex("[^a-zA-Z0-9_\\-]"), "_")
        return cleaned.take(100).ifBlank { "doc_${System.currentTimeMillis()}" }
    }
}
