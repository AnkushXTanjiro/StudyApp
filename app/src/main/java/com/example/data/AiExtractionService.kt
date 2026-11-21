package com.example.data

import android.util.Log
import com.example.model.SubjectDoc
import com.example.model.TestQuestion
import com.example.model.TimetableSlot
import com.example.model.TopicItem
import com.example.model.UnitItem
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Schema
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import org.json.JSONObject

private const val AI_TAG = "AiExtractionService"
private const val MAX_INLINE_BYTES = 18 * 1024 * 1024 // Keep strictly under 20MB limit

data class SubjectiveEvaluation(
    val awardedMarks: Double,
    val feedback: String,
    val matchedSteps: List<String>
)

class AiExtractionService {

    private val syllabusModel by lazy {
        val topicSchema = Schema.obj(
            properties = mapOf(
                "code" to Schema.string(description = "Topic numbering like 1.1, 1.2, LE-1"),
                "title" to Schema.string(description = "Concise syllabus topic title")
            )
        )
        val unitSchema = Schema.obj(
            properties = mapOf(
                "unitNumber" to Schema.string(description = "Unit label such as Unit 1.0"),
                "title" to Schema.string(description = "Unit title"),
                "hours" to Schema.integer(description = "Allocated lecture hours"),
                "topics" to Schema.array(items = topicSchema)
            )
        )
        val subjectSchema = Schema.obj(
            properties = mapOf(
                "courseCode" to Schema.string(description = "Course code like 100102"),
                "title" to Schema.string(description = "Subject title"),
                "credits" to Schema.integer(description = "Credits count"),
                "category" to Schema.enumeration(values = listOf("THEORY", "LAB")),
                "units" to Schema.array(items = unitSchema)
            )
        )
        val rootSchema = Schema.obj(
            properties = mapOf(
                "subjects" to Schema.array(items = subjectSchema)
            )
        )
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = rootSchema
            }
        )
    }

    private val timetableModel by lazy {
        val slotSchema = Schema.obj(
            properties = mapOf(
                "dayOfWeek" to Schema.integer(description = "1=Monday through 7=Sunday"),
                "startTime" to Schema.string(description = "HH:mm 24h format e.g. 09:00"),
                "endTime" to Schema.string(description = "HH:mm 24h format e.g. 10:00"),
                "subjectTitle" to Schema.string(description = "Subject or lab title"),
                "unitHint" to Schema.string(description = "Topic/unit focus or instructor"),
                "room" to Schema.string(description = "Classroom or lab code"),
                "slotType" to Schema.enumeration(values = listOf("LECTURE", "LAB", "SELF_STUDY"))
            )
        )
        val rootSchema = Schema.obj(
            properties = mapOf(
                "slots" to Schema.array(items = slotSchema)
            )
        )
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = rootSchema
            }
        )
    }

    private val questionExtractionModel by lazy {
        val questionSchema = Schema.obj(
            properties = mapOf(
                "topicCode" to Schema.string(description = "Syllabus topic code e.g. 4.1"),
                "topicTitle" to Schema.string(description = "Specific topic name"),
                "questionType" to Schema.enumeration(values = listOf("MCQ", "SUBJECTIVE")),
                "prompt" to Schema.string(description = "Full question text using proper Unicode math symbols ∫, ∬, ∂/∂x, d/dx, α, β, γ, Γ, λ, θ, π, Σ, Ω, ψ, √, ², ³, ⁿ"),
                "formulaExpr" to Schema.string(description = "Standalone mathematical equation/expression in clean Unicode math notation, or empty string"),
                "diagramKind" to Schema.enumeration(
                    values = listOf("NONE", "INTEGRAL_AREA", "NEWTON_RINGS", "QUANTUM_WELL", "RLC_CIRCUIT", "LOGIC_GATE")
                ),
                "options" to Schema.array(items = Schema.string(), description = "4 choices for MCQ, empty list for SUBJECTIVE"),
                "correctOptionIndex" to Schema.integer(description = "0-3 for MCQ, -1 for SUBJECTIVE"),
                "maxMarks" to Schema.integer(description = "Maximum marks for the question (1-20)"),
                "modelAnswer" to Schema.string(description = "Complete step-by-step model solution with Unicode math symbols"),
                "markingScheme" to Schema.array(
                    items = Schema.string(),
                    description = "Step-wise marking rubric items e.g. '1M: State formula', '2M: Correct integration steps'"
                )
            )
        )
        val rootSchema = Schema.obj(
            properties = mapOf(
                "questions" to Schema.array(items = questionSchema)
            )
        )
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = rootSchema
            }
        )
    }

    private val subjectiveGradingModel by lazy {
        val gradeSchema = Schema.obj(
            properties = mapOf(
                "awardedMarks" to Schema.double(description = "Marks awarded between 0 and maxMarks"),
                "feedback" to Schema.string(description = "Constructive step-wise feedback referencing the marking scheme"),
                "matchedSteps" to Schema.array(items = Schema.string(), description = "Rubric criteria satisfied by the student")
            )
        )
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = gradeSchema
            }
        )
    }

    suspend fun extractSyllabus(
        fileBytes: ByteArray,
        mimeType: String,
        startingSortOrder: Long
    ): Result<List<SubjectDoc>> {
        if (fileBytes.size > MAX_INLINE_BYTES) {
            return Result.failure(IllegalArgumentException("File exceeds 18 MB limit. Please pick a smaller PDF or image."))
        }
        return try {
            val response = withTimeout(60_000L) {
                syllabusModel.generateContent(
                    content {
                        inlineData(fileBytes, mimeType)
                        text(
                            "Extract the complete academic syllabus organized strictly by Subject -> Unit -> Topic. " +
                                "Preserve exact course codes, unit numbers (e.g. Unit 1.0), lecture hours, and topic codes (e.g. 1.1, 1.2, LE-1). " +
                                "Return every subject and unit clearly in the requested JSON schema."
                        )
                    }
                )
            }
            val raw = response.text ?: throw IllegalStateException("AI returned an empty syllabus response.")
            val root = JSONObject(raw)
            val subjectsArray = root.getJSONArray("subjects")
            val result = mutableListOf<SubjectDoc>()
            for (i in 0 until subjectsArray.length()) {
                val sObj = subjectsArray.getJSONObject(i)
                val courseCode = sObj.optString("courseCode", "SUB${i + 1}").take(30)
                val title = sObj.optString("title", "Subject ${i + 1}").take(120)
                val credits = sObj.optLong("credits", 3L).coerceIn(0L, 20L)
                val category = sObj.optString("category", "THEORY")
                val unitsArr = sObj.optJSONArray("units")
                val units = mutableListOf<UnitItem>()
                if (unitsArr != null) {
                    for (u in 0 until unitsArr.length()) {
                        val uObj = unitsArr.getJSONObject(u)
                        val topicsArr = uObj.optJSONArray("topics")
                        val topics = mutableListOf<TopicItem>()
                        if (topicsArr != null) {
                            for (t in 0 until topicsArr.length()) {
                                val tObj = topicsArr.getJSONObject(t)
                                topics.add(
                                    TopicItem(
                                        topicId = "t_${courseCode}_${u + 1}_${t + 1}",
                                        code = tObj.optString("code", "${u + 1}.${t + 1}").take(20),
                                        title = tObj.optString("title", "Topic").take(180),
                                        completed = false
                                    )
                                )
                            }
                        }
                        units.add(
                            UnitItem(
                                unitId = "u_${courseCode}_${u + 1}",
                                unitNumber = uObj.optString("unitNumber", "Unit ${u + 1}.0").take(30),
                                title = uObj.optString("title", "Unit ${u + 1}").take(120),
                                hours = uObj.optLong("hours", 8L).coerceIn(1L, 60L),
                                topics = topics
                            )
                        )
                    }
                }
                result.add(
                    SubjectDoc(
                        courseCode = courseCode,
                        title = title,
                        credits = credits,
                        category = if (category == "LAB") "LAB" else "THEORY",
                        sortOrder = startingSortOrder + i,
                        units = units
                    )
                )
            }
            Result.success(result)
        } catch (e: TimeoutCancellationException) {
            Log.e(AI_TAG, "Syllabus extraction timed out", e)
            Result.failure(Exception("Syllabus extraction timed out after 60 seconds. Try a smaller PDF."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(AI_TAG, "Syllabus extraction failed", e)
            Result.failure(e)
        }
    }

    suspend fun extractTimetable(
        fileBytes: ByteArray,
        mimeType: String
    ): Result<List<TimetableSlot>> {
        if (fileBytes.size > MAX_INLINE_BYTES) {
            return Result.failure(IllegalArgumentException("Timetable file exceeds 18 MB limit."))
        }
        return try {
            val response = withTimeout(60_000L) {
                timetableModel.generateContent(
                    content {
                        inlineData(fileBytes, mimeType)
                        text(
                            "Extract every class, lab, or study period from this academic timetable into dayOfWeek (1=Monday..7=Sunday), " +
                                "24-hour startTime/endTime (HH:mm), subjectTitle, unitHint, room, and slotType (LECTURE/LAB/SELF_STUDY)."
                        )
                    }
                )
            }
            val raw = response.text ?: throw IllegalStateException("AI returned an empty timetable response.")
            val arr = JSONObject(raw).getJSONArray("slots")
            val slots = (0 until arr.length()).map { i ->
                val obj = arr.getJSONObject(i)
                TimetableSlot(
                    dayOfWeek = obj.optLong("dayOfWeek", 1L).coerceIn(1L, 7L),
                    startTime = obj.optString("startTime", "09:00").take(20),
                    endTime = obj.optString("endTime", "10:00").take(20),
                    subjectTitle = obj.optString("subjectTitle", "Class").take(120),
                    unitHint = obj.optString("unitHint", "").take(120),
                    room = obj.optString("room", "").take(60),
                    slotType = obj.optString("slotType", "LECTURE")
                )
            }
            Result.success(slots)
        } catch (e: TimeoutCancellationException) {
            Log.e(AI_TAG, "Timetable extraction timed out", e)
            Result.failure(Exception("Timetable extraction timed out. Please try again."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(AI_TAG, "Timetable extraction failed", e)
            Result.failure(e)
        }
    }

    suspend fun extractUnitQuestions(
        fileBytes: ByteArray?,
        mimeType: String?,
        pastedText: String,
        subjectId: String,
        subjectTitle: String,
        unitId: String,
        unitTitle: String,
        attachedImageUri: String = ""
    ): Result<List<TestQuestion>> {
        if (fileBytes != null && fileBytes.size > MAX_INLINE_BYTES) {
            return Result.failure(IllegalArgumentException("Question file exceeds 18 MB limit."))
        }
        return try {
            val instruction = """
                Extract all exam/practice questions for Subject: "$subjectTitle", Unit: "$unitTitle".
                CRITICAL FORMATTING RULES:
                1. Preserve all advanced mathematics symbols using crisp Unicode: integrals (∫, ∬, ∭), derivatives (d/dx, ∂/∂x, d²y/dx²), Greek symbols (α, β, γ, Γ, Δ, λ, θ, π, Σ, Ω, ψ, μ, ∇), roots (√), powers (², ³, ⁿ), infinity (∞).
                2. Support BOTH MCQ (4 options + correctOptionIndex 0..3) and SUBJECTIVE questions (options = [], correctOptionIndex = -1, detailed modelAnswer, and step-by-step markingScheme rubric e.g. ["1M: Formula", "2M: Integration", "1M: Final result"]).
                3. If a question involves an integral region, Newton's rings, quantum potential well, electrical circuit, or logic gate, pick the closest diagramKind; otherwise use NONE.
                4. Tag every question with the exact syllabus topicTitle so the report card can compute Strong vs Weak topics accurately.
                ${if (pastedText.isNotBlank()) "Additional question content:\n$pastedText" else ""}
            """.trimIndent()

            val response = withTimeout(60_000L) {
                questionExtractionModel.generateContent(
                    content {
                        if (fileBytes != null && !mimeType.isNullOrBlank()) {
                            inlineData(fileBytes, mimeType)
                        }
                        text(instruction)
                    }
                )
            }
            val raw = response.text ?: throw IllegalStateException("AI returned empty questions.")
            val arr = JSONObject(raw).getJSONArray("questions")
            val parsed = (0 until arr.length()).map { i ->
                val qObj = arr.getJSONObject(i)
                val qType = if (qObj.optString("questionType") == "SUBJECTIVE") "SUBJECTIVE" else "MCQ"
                val optsArr = qObj.optJSONArray("options")
                val opts = if (qType == "MCQ" && optsArr != null) {
                    (0 until optsArr.length()).map { optsArr.getString(it) }
                } else emptyList()
                val schemeArr = qObj.optJSONArray("markingScheme")
                val scheme = if (schemeArr != null) {
                    (0 until schemeArr.length()).map { schemeArr.getString(it) }
                } else emptyList()
                val diagram = qObj.optString("diagramKind", "NONE")

                TestQuestion(
                    subjectId = subjectId,
                    subjectTitle = subjectTitle,
                    unitId = unitId,
                    unitTitle = unitTitle,
                    topicCode = qObj.optString("topicCode", "").take(30),
                    topicTitle = qObj.optString("topicTitle", unitTitle).take(160),
                    questionType = qType,
                    prompt = qObj.optString("prompt", "Solve the following question.").take(2000),
                    formulaExpr = qObj.optString("formulaExpr", "").take(500),
                    diagramKind = if (attachedImageUri.isNotBlank() && diagram == "NONE") "UPLOADED_IMAGE" else diagram,
                    imageUri = attachedImageUri,
                    options = opts,
                    correctOptionIndex = if (qType == "MCQ") qObj.optLong("correctOptionIndex", 0L).coerceIn(0L, 5L) else -1L,
                    maxMarks = qObj.optLong("maxMarks", if (qType == "SUBJECTIVE") 5L else 2L).coerceIn(1L, 50L),
                    modelAnswer = qObj.optString("modelAnswer", "").take(2500),
                    markingScheme = scheme.ifEmpty { listOf("${qObj.optLong("maxMarks", 2L)}M: Complete accurate derivation and final answer") }
                )
            }
            Result.success(parsed)
        } catch (e: TimeoutCancellationException) {
            Log.e(AI_TAG, "Question extraction timed out", e)
            Result.failure(Exception("Question extraction timed out after 60 seconds."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(AI_TAG, "Question extraction failed", e)
            Result.failure(e)
        }
    }

    suspend fun evaluateSubjectiveAnswer(
        question: TestQuestion,
        studentAnswer: String,
        solutionImageBytes: ByteArray? = null,
        solutionMimeType: String? = null
    ): Result<SubjectiveEvaluation> {
        return try {
            val prompt = buildString {
                appendLine("Grade this student's subjective exam answer strictly using the Marking Scheme.")
                appendLine("Question (${question.maxMarks} marks): ${question.prompt}")
                if (question.formulaExpr.isNotBlank()) appendLine("Expression: ${question.formulaExpr}")
                appendLine("Model Answer: ${question.modelAnswer}")
                appendLine("Marking Scheme Rubric:")
                question.markingScheme.forEachIndexed { idx, step ->
                    appendLine("${idx + 1}. $step")
                }
                appendLine("Student's Typed Answer: ${studentAnswer.ifBlank { "(See attached handwritten solution image if provided)" }}")
                appendLine("Award fair partial step marks between 0 and ${question.maxMarks}. Provide concise constructive feedback.")
            }
            val response = withTimeout(60_000L) {
                subjectiveGradingModel.generateContent(
                    content {
                        if (solutionImageBytes != null && !solutionMimeType.isNullOrBlank() && solutionImageBytes.size <= MAX_INLINE_BYTES) {
                            inlineData(solutionImageBytes, solutionMimeType)
                        }
                        text(prompt)
                    }
                )
            }
            val raw = response.text ?: throw IllegalStateException("Empty evaluation response.")
            val json = JSONObject(raw)
            val awarded = json.optDouble("awardedMarks", 0.0).coerceIn(0.0, question.maxMarks.toDouble())
            val feedback = json.optString("feedback", "Evaluated against marking scheme.")
            val matchedArr = json.optJSONArray("matchedSteps")
            val matched = if (matchedArr != null) {
                (0 until matchedArr.length()).map { matchedArr.getString(it) }
            } else emptyList()
            Result.success(SubjectiveEvaluation(awarded, feedback, matched))
        } catch (e: TimeoutCancellationException) {
            Log.e(AI_TAG, "Subjective grading timed out", e)
            Result.failure(Exception("AI grading timed out; use Marking Scheme self-check below."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(AI_TAG, "Subjective grading failed", e)
            Result.failure(e)
        }
    }
}
