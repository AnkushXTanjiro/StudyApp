package com.example.model

/**
 * Complete B.Tech 1st Sem (BEU / GEC Sheikhpura) Basic Electrical & Electronics Engineering (BEEE)
 * Question Bank extracted from all 5 uploaded unit PDFs (Units 1.0 – 5.0, 252 questions total).
 */
object BeeeQuestionBank {
    const val SUBJECT_ID = "sub_100111"
    const val SUBJECT_TITLE = "Basics of Electrical & Electronics Engg"
    const val SENTINEL_QUESTION_ID = "beee_u5_q60"

    fun allQuestions(): List<TestQuestion> =
        beeeUnit1Questions() +
            beeeUnit2Questions() +
            beeeUnit3Questions() +
            beeeUnit4Questions() +
            beeeUnit5Questions()

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
            id = "beee_u${unitNum}_q%02d".format(qNum),
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
            maxMarks = 2L,
            modelAnswer = if (explanation.isNotBlank()) "$correctText — $explanation" else "Correct option: $correctText",
            markingScheme = listOf("2M: Select the correct objective answer ($correctText)")
        )
    }

    internal fun subj(
        unitNum: Int,
        unitId: String,
        unitTitle: String,
        qNum: Int,
        topicNum: Int,
        topicTitle: String,
        marks: Long,
        prompt: String,
        modelAnswer: String,
        scheme: List<String>,
        formula: String = "",
        diagram: String = "NONE"
    ): TestQuestion {
        val qTag = "Q%02d".format(qNum)
        val tierLabel = if (marks >= 10L) "Long" else "Short"
        return TestQuestion(
            id = "beee_u${unitNum}_q%02d".format(qNum),
            subjectId = SUBJECT_ID,
            subjectTitle = SUBJECT_TITLE,
            unitId = unitId,
            unitTitle = unitTitle,
            topicCode = "$unitNum.$topicNum $qTag",
            topicTitle = topicTitle,
            questionType = "SUBJECTIVE",
            prompt = "$qTag ($tierLabel • ${marks}M). $prompt",
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
