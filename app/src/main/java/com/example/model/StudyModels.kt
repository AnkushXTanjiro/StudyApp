package com.example.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}

@Keep
@IgnoreExtraProperties
data class UserProfile(
    @DocumentId val id: String = "",
    val userId: String = "",
    val displayName: String = "Student",
    val streakDays: Long = 0L,
    val longestStreak: Long = 0L,
    val xp: Long = 0L,
    val lastStudyDate: String = "",
    val unlockedBadges: List<String> = emptyList(),
    val dailyGoalTopics: Long = 4L,
    val reminderHour: Long = 19L,
    val reminderMinute: Long = 0L,
    val remindersEnabled: Boolean = false,
    val syllabusSeeded: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class LeaderboardEntry(
    @DocumentId val id: String = "",
    val userId: String = "",
    val displayName: String = "Student",
    val xp: Long = 0L,
    val streakDays: Long = 0L,
    val topicsCompleted: Long = 0L,
    val testsCompleted: Long = 0L,
    val badgesCount: Long = 0L,
    val publicVisible: Boolean = true,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class TopicItem(
    val topicId: String = "",
    val code: String = "",
    val title: String = "",
    val completed: Boolean = false
)

@Keep
@IgnoreExtraProperties
data class UnitItem(
    val unitId: String = "",
    val unitNumber: String = "",
    val title: String = "",
    val hours: Long = 8L,
    val topics: List<TopicItem> = emptyList()
) {
    val completedTopicsCount: Int get() = topics.count { it.completed }
    val totalTopicsCount: Int get() = topics.size
    val progress: Float
        get() = if (topics.isEmpty()) 0f else completedTopicsCount.toFloat() / topics.size
}

@Keep
@IgnoreExtraProperties
data class SubjectDoc(
    @DocumentId val id: String = "",
    val courseCode: String = "",
    val title: String = "",
    val credits: Long = 3L,
    val category: String = "THEORY", // "THEORY" or "LAB"
    val sortOrder: Long = 0L,
    val units: List<UnitItem> = emptyList(),
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
) {
    val totalTopicsCount: Int get() = units.sumOf { it.totalTopicsCount }
    val completedTopicsCount: Int get() = units.sumOf { it.completedTopicsCount }
    val progress: Float
        get() = if (totalTopicsCount == 0) 0f else completedTopicsCount.toFloat() / totalTopicsCount
}

@Keep
@IgnoreExtraProperties
data class TodoItem(
    @DocumentId val id: String = "",
    val title: String = "",
    val notes: String = "",
    val sourceType: String = "MANUAL", // "MANUAL", "SYLLABUS", "TIMETABLE"
    val subjectId: String = "",
    val subjectTitle: String = "",
    val unitId: String = "",
    val unitTitle: String = "",
    val topicId: String = "",
    val priority: String = "MEDIUM", // "LOW", "MEDIUM", "HIGH"
    val dueLabel: String = "Today",
    val completed: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class TimetableSlot(
    @DocumentId val id: String = "",
    val dayOfWeek: Long = 1L, // 1=Mon .. 7=Sun
    val startTime: String = "09:00",
    val endTime: String = "10:00",
    val subjectId: String = "",
    val subjectTitle: String = "",
    val unitHint: String = "",
    val room: String = "",
    val slotType: String = "LECTURE", // "LECTURE", "LAB", "SELF_STUDY"
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class TestQuestion(
    @DocumentId val id: String = "",
    val subjectId: String = "",
    val subjectTitle: String = "",
    val unitId: String = "",
    val unitTitle: String = "",
    val topicCode: String = "",
    val topicTitle: String = "",
    val questionType: String = "MCQ", // "MCQ" or "SUBJECTIVE"
    val prompt: String = "",
    val formulaExpr: String = "",
    val diagramKind: String = "NONE", // NONE, INTEGRAL_AREA, NEWTON_RINGS, QUANTUM_WELL, RLC_CIRCUIT, LOGIC_GATE, UPLOADED_IMAGE
    val imageUri: String = "",
    val options: List<String> = emptyList(),
    val correctOptionIndex: Long = -1L,
    val maxMarks: Long = 2L,
    val modelAnswer: String = "",
    val markingScheme: List<String> = emptyList(),
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class QuestionResultItem(
    val questionPrompt: String = "",
    val topicTitle: String = "",
    val questionType: String = "MCQ",
    val awardedMarks: Double = 0.0,
    val maxMarks: Double = 1.0,
    val feedback: String = "",
    val userAnswer: String = "",
    val modelAnswer: String = ""
)

@Keep
@IgnoreExtraProperties
data class TestReport(
    @DocumentId val id: String = "",
    val subjectId: String = "",
    val subjectTitle: String = "",
    val unitId: String = "",
    val unitTitle: String = "",
    val obtainedMarks: Double = 0.0,
    val totalMarks: Double = 1.0,
    val percentage: Double = 0.0,
    val durationSeconds: Long = 0L,
    val totalQuestions: Long = 1L,
    val correctCount: Long = 0L,
    val strongTopics: List<String> = emptyList(),
    val weakTopics: List<String> = emptyList(),
    val improveTopics: List<String> = emptyList(),
    val questionBreakdown: List<QuestionResultItem> = emptyList(),
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

data class BadgeDefinition(
    val id: String,
    val title: String,
    val description: String,
    val iconKey: String,
    val requirementLabel: String
)

object StudyBadges {
    val allBadges: List<BadgeDefinition> = listOf(
        BadgeDefinition(
            id = "first_spark",
            title = "First Spark",
            description = "Complete your first syllabus topic or study task.",
            iconKey = "flame",
            requirementLabel = "1 Topic"
        ),
        BadgeDefinition(
            id = "streak_3",
            title = "Neon Ember",
            description = "Maintain a 3-day consecutive study streak.",
            iconKey = "bolt",
            requirementLabel = "3-Day Streak"
        ),
        BadgeDefinition(
            id = "streak_7",
            title = "Plasma Flame",
            description = "Sustain a full 7-day academic streak.",
            iconKey = "fire_pro",
            requirementLabel = "7-Day Streak"
        ),
        BadgeDefinition(
            id = "syllabus_10",
            title = "Syllabus Architect",
            description = "Master 10 topics across your semester syllabus.",
            iconKey = "book",
            requirementLabel = "10 Topics"
        ),
        BadgeDefinition(
            id = "syllabus_25",
            title = "Unit Conqueror",
            description = "Complete 25 syllabus topics across units.",
            iconKey = "crown",
            requirementLabel = "25 Topics"
        ),
        BadgeDefinition(
            id = "first_test",
            title = "Exam Ready",
            description = "Finish your first interactive unit-wise test.",
            iconKey = "quiz",
            requirementLabel = "1 Test"
        ),
        BadgeDefinition(
            id = "ace_85",
            title = "Distinction Ace",
            description = "Score 85% or higher on any unit test report card.",
            iconKey = "star",
            requirementLabel = "85%+ Score"
        ),
        BadgeDefinition(
            id = "subjective_pro",
            title = "Rubric Scholar",
            description = "Evaluate and complete 3 unit test report cards.",
            iconKey = "trophy",
            requirementLabel = "3 Tests"
        )
    )
}
