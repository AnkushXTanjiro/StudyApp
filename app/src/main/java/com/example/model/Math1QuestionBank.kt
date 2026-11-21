package com.example.model

/**
 * Complete B.Tech 1st Sem (BEU CSE) Engineering Mathematics - I (100102)
 * Question Bank extracted from all 5 uploaded unit PDFs (Units 1.0 – 5.0, 170 questions total):
 * - Unit 1.0: Basic Linear Algebra (31 MCQ + 13 Short + 7 Long = 51 Qs)
 * - Unit 2.0: Differential Calculus: Single Variable (20 MCQ + 10 Short + 4 Long = 34 Qs)
 * - Unit 3.0: Differential Calculus: Several Variables (20 MCQ + 8 Short + 4 Long = 32 Qs)
 * - Unit 4.0: Integral Calculus (21 MCQ + 9 Short + 4 Long = 34 Qs)
 * - Unit 5.0: Vector Calculus (11 MCQ + 5 Short + 3 Long = 19 Qs)
 */
object Math1QuestionBank {
    const val SUBJECT_ID = "sub_100102"
    const val SUBJECT_TITLE = "Engineering Mathematics - I"
    const val SENTINEL_QUESTION_ID = "math1_u5_l03"

    fun allQuestions(): List<TestQuestion> =
        math1Unit1Questions() +
            math1Unit2Questions() +
            math1Unit3Questions() +
            math1Unit4Questions() +
            math1Unit5Questions()

    internal fun mcq(
        unitNum: Int,
        unitId: String,
        unitTitle: String,
        qNum: Int,
        topicNum: Int,
        topicTitle: String,
        prompt: String,
        options: List<String>,
        correctIdx: Int,
        formula: String = "",
        diagram: String = "NONE",
        explanation: String = ""
    ): TestQuestion {
        val qTag = "Q%02d".format(qNum)
        val correctText = options.getOrNull(correctIdx).orEmpty()
        return TestQuestion(
            id = "math1_u${unitNum}_q%02d".format(qNum),
            subjectId = SUBJECT_ID,
            subjectTitle = SUBJECT_TITLE,
            unitId = unitId,
            unitTitle = unitTitle,
            topicCode = "$unitNum.$topicNum $qTag",
            topicTitle = topicTitle,
            questionType = "MCQ",
            prompt = "$qTag. $prompt",
            formulaExpr = formula,
            diagramKind = diagram,
            options = options,
            correctOptionIndex = correctIdx.toLong(),
            maxMarks = 1L,
            modelAnswer = if (explanation.isNotBlank()) "$correctText — $explanation" else "Correct option: $correctText",
            markingScheme = listOf("1M: Select the correct objective answer ($correctText)")
        )
    }

    internal fun shortQ(
        unitNum: Int,
        unitId: String,
        unitTitle: String,
        sNum: Int,
        topicNum: Int,
        topicTitle: String,
        prompt: String,
        modelAnswer: String,
        scheme: List<String>,
        formula: String = "",
        diagram: String = "NONE",
        marks: Long = 5L
    ): TestQuestion {
        val sTag = "S%02d".format(sNum)
        return TestQuestion(
            id = "math1_u${unitNum}_s%02d".format(sNum),
            subjectId = SUBJECT_ID,
            subjectTitle = SUBJECT_TITLE,
            unitId = unitId,
            unitTitle = unitTitle,
            topicCode = "$unitNum.$topicNum $sTag",
            topicTitle = topicTitle,
            questionType = "SUBJECTIVE",
            prompt = "$sTag (Short • ${marks}M). $prompt",
            formulaExpr = formula,
            diagramKind = diagram,
            options = emptyList(),
            correctOptionIndex = -1L,
            maxMarks = marks,
            modelAnswer = modelAnswer,
            markingScheme = scheme
        )
    }

    internal fun longQ(
        unitNum: Int,
        unitId: String,
        unitTitle: String,
        lNum: Int,
        topicNum: Int,
        topicTitle: String,
        marks: Long,
        prompt: String,
        modelAnswer: String,
        scheme: List<String>,
        formula: String = "",
        diagram: String = "NONE"
    ): TestQuestion {
        val lTag = "L%02d".format(lNum)
        return TestQuestion(
            id = "math1_u${unitNum}_l%02d".format(lNum),
            subjectId = SUBJECT_ID,
            subjectTitle = SUBJECT_TITLE,
            unitId = unitId,
            unitTitle = unitTitle,
            topicCode = "$unitNum.$topicNum $lTag",
            topicTitle = topicTitle,
            questionType = "SUBJECTIVE",
            prompt = "$lTag (Long • ${marks}M). $prompt",
            formulaExpr = formula,
            diagramKind = diagram,
            options = emptyList(),
            correctOptionIndex = -1L,
            maxMarks = marks,
            modelAnswer = modelAnswer,
            markingScheme = scheme
        )
    }
}
