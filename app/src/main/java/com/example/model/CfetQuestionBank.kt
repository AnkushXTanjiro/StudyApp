package com.example.model

/**
 * Complete B.Tech 1st Sem (BEU CSE) Computer Fundamentals & Emerging Technologies (100108)
 * Question Bank extracted from all 5 uploaded unit PDFs (Units 1.0 – 5.0, 422 questions total):
 * - Unit 1: Fundamentals of Computer Systems (51 MCQ + 17 Short + 10 Long = 78 Qs)
 * - Unit 2: Data Representation (53 MCQ + 14 Short + 7 Long = 74 Qs)
 * - Unit 3: Computer Software & Operating Systems (54 MCQ + 15 Short + 9 Long = 78 Qs)
 * - Unit 4: Computer Networks & Internet Basics (74 MCQ + 24 Short + 12 Long = 110 Qs)
 * - Unit 5: Emerging Technologies (55 MCQ + 20 Short + 7 Long = 82 Qs)
 */
object CfetQuestionBank {
    const val SUBJECT_ID = "sub_100108"
    const val SUBJECT_TITLE = "Computer Fundamentals & Emerging Tech"
    const val SENTINEL_QUESTION_ID = "cfet_u5_l07"

    fun allQuestions(): List<TestQuestion> =
        cfetUnit1Questions() +
            cfetUnit2Questions() +
            cfetUnit3Questions() +
            cfetUnit4Questions() +
            cfetUnit5Questions()

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
            id = "cfet_u${unitNum}_q%02d".format(qNum),
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
            id = "cfet_u${unitNum}_s%02d".format(sNum),
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
            id = "cfet_u${unitNum}_l%02d".format(lNum),
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
