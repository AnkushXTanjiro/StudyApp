package com.example.model

import kotlin.math.roundToInt

data class BeuMockPaperSpec(
    val questions: List<TestQuestion>,
    val totalMarks: Int,
    val durationMinutes: Int,
    val mcqCount: Int,
    val shortCount: Int,
    val longCount: Int
)

data class SubjectPerformanceStat(
    val subjectId: String,
    val subjectTitle: String,
    val testsTaken: Int,
    val averagePercentage: Double,
    val bestPercentage: Double
)

/**
 * Pure domain builders for:
 * 1) Weak-Topic Retry Quiz / Mistakes Deck from past TestReports.
 * 2) BEU 70-Mark End-Semester Mock Paper Simulator spread across all units of a subject.
 */
object StudyExamBuilder {

    fun countWeakAndMissedSignals(reports: List<TestReport>): Int {
        val missedPrompts = reports.flatMap { r ->
            r.questionBreakdown.filter { item ->
                val ratio = if (item.maxMarks > 0.0) item.awardedMarks / item.maxMarks else 0.0
                ratio < 0.75
            }.map { it.questionPrompt.trim().lowercase() }
        }.toSet()

        val weakTopics = reports.flatMap { it.weakTopics + it.improveTopics }
            .map { it.trim().lowercase() }
            .filter { it.isNotBlank() }
            .toSet()

        return missedPrompts.size + weakTopics.size
    }

    /**
     * Collects missed questions (<75% marks) and weak/improve topics across recent reports
     * (or a single target report) and matches them against the full question bank.
     */
    fun buildWeakTopicRetryDeck(
        reports: List<TestReport>,
        allQuestions: List<TestQuestion>,
        maxQuestions: Int = 12
    ): List<TestQuestion> {
        if (reports.isEmpty() || allQuestions.isEmpty()) return emptyList()

        val orderedReports = reports.sortedByDescending { it.createdAt?.seconds ?: 0L }

        val missedPromptsOrder = LinkedHashSet<String>()
        val weakTopicSet = LinkedHashSet<String>()
        val improveTopicSet = LinkedHashSet<String>()
        val reportSubjectIds = LinkedHashSet<String>()

        orderedReports.forEach { report ->
            if (report.subjectId.isNotBlank()) reportSubjectIds.add(report.subjectId)
            report.questionBreakdown.forEach { item ->
                val ratio = if (item.maxMarks > 0.0) item.awardedMarks / item.maxMarks else 0.0
                if (ratio < 0.75 && item.questionPrompt.isNotBlank()) {
                    missedPromptsOrder.add(item.questionPrompt.trim().lowercase())
                }
            }
            report.weakTopics.forEach { t ->
                if (t.isNotBlank()) weakTopicSet.add(t.trim().lowercase())
            }
            report.improveTopics.forEach { t ->
                if (t.isNotBlank()) improveTopicSet.add(t.trim().lowercase())
            }
        }

        if (missedPromptsOrder.isEmpty() && weakTopicSet.isEmpty() && improveTopicSet.isEmpty()) {
            return emptyList()
        }

        val selected = LinkedHashMap<String, TestQuestion>()

        // Tier 1: Exact question prompts missed in reports
        for (promptKey in missedPromptsOrder) {
            if (selected.size >= maxQuestions) break
            val match = allQuestions.firstOrNull { q ->
                q.id !in selected && q.prompt.trim().lowercase() == promptKey
            }
            if (match != null) selected[match.id] = match
        }

        // Tier 2: Questions belonging to weakTopics (prefer same subjects as reports)
        val sameSubjectPool = allQuestions.filter { it.subjectId in reportSubjectIds }
        val searchPool = if (sameSubjectPool.isNotEmpty()) sameSubjectPool else allQuestions

        for (q in searchPool) {
            if (selected.size >= maxQuestions) break
            val topic = q.topicTitle.trim().lowercase()
            if (q.id !in selected && topic.isNotBlank() && weakTopicSet.any { w -> topic.contains(w) || w.contains(topic) }) {
                selected[q.id] = q
            }
        }

        // Tier 3: Questions belonging to improveTopics
        for (q in searchPool) {
            if (selected.size >= maxQuestions) break
            val topic = q.topicTitle.trim().lowercase()
            if (q.id !in selected && topic.isNotBlank() && improveTopicSet.any { imp -> topic.contains(imp) || imp.contains(topic) }) {
                selected[q.id] = q
            }
        }

        return selected.values.take(maxQuestions)
    }

    fun buildReportRetryDeck(
        report: TestReport,
        allQuestions: List<TestQuestion>,
        maxQuestions: Int = 12
    ): List<TestQuestion> {
        val scopedQuestions = allQuestions.filter {
            report.subjectId.isBlank() || it.subjectId == report.subjectId
        }.ifEmpty { allQuestions }
        return buildWeakTopicRetryDeck(
            reports = listOf(report),
            allQuestions = scopedQuestions,
            maxQuestions = maxQuestions
        )
    }

    /**
     * Generates a BEU End-Sem Mock Paper targeting 70 marks across all units of [subject]:
     * - Section A: Up to 7 Objective MCQs (~14M) spread across units
     * - Section B/C: Short (<10M) & Long (>=10M) subjective questions up to ~70M total
     * Falls back cleanly for lab/viva banks or smaller custom banks.
     */
    fun buildBeuEndSemMockPaper(
        subject: SubjectDoc,
        allQuestions: List<TestQuestion>,
        targetTotalMarks: Int = 70
    ): BeuMockPaperSpec {
        val subjectQs = allQuestions.filter { it.subjectId == subject.id }
        if (subjectQs.isEmpty()) {
            return BeuMockPaperSpec(emptyList(), 0, 0, 0, 0, 0)
        }

        val unitOrder = subject.units.map { it.unitId }.ifEmpty {
            subjectQs.map { it.unitId }.distinct()
        }

        fun roundRobinByUnit(pool: List<TestQuestion>): List<TestQuestion> {
            val byUnit = pool.groupBy { it.unitId }.mapValues { it.value.toMutableList() }
            val result = mutableListOf<TestQuestion>()
            var added: Boolean
            do {
                added = false
                for (uId in unitOrder) {
                    val list = byUnit[uId]
                    if (!list.isNullOrEmpty()) {
                        result.add(list.removeAt(0))
                        added = true
                    }
                }
            } while (added)
            // Include any questions whose unitId wasn't in unitOrder
            val seenIds = result.map { it.id }.toSet()
            pool.forEach { q -> if (q.id !in seenIds) result.add(q) }
            return result
        }

        val mcqs = roundRobinByUnit(subjectQs.filter { it.questionType == "MCQ" })
        val longSubj = roundRobinByUnit(subjectQs.filter { it.questionType == "SUBJECTIVE" && it.maxMarks >= 10L })
        val shortSubj = roundRobinByUnit(subjectQs.filter { it.questionType == "SUBJECTIVE" && it.maxMarks < 10L })

        val chosen = mutableListOf<TestQuestion>()
        val chosenIds = HashSet<String>()
        var marksSum = 0

        // Section A: Target 14 marks (up to 7 MCQs)
        for (q in mcqs) {
            if (marksSum >= 14 || chosen.size >= 7) break
            chosen.add(q)
            chosenIds.add(q.id)
            marksSum += q.maxMarks.toInt().coerceAtLeast(1)
        }

        // Section B: Long 10-14M questions (target ~28-42M of paper)
        val longTargetCap = (targetTotalMarks - 14).coerceAtLeast(28)
        var longMarksAdded = 0
        for (q in longSubj) {
            val qMarks = q.maxMarks.toInt().coerceAtLeast(1)
            if (marksSum + qMarks > targetTotalMarks + 4) continue
            if (longMarksAdded >= longTargetCap && shortSubj.isNotEmpty()) break
            chosen.add(q)
            chosenIds.add(q.id)
            marksSum += qMarks
            longMarksAdded += qMarks
        }

        // Section C: Short 2-7M questions to fill up towards 70M
        for (q in shortSubj) {
            val qMarks = q.maxMarks.toInt().coerceAtLeast(1)
            if (marksSum >= targetTotalMarks) break
            if (marksSum + qMarks > targetTotalMarks + 4 && marksSum >= targetTotalMarks - 6) break
            chosen.add(q)
            chosenIds.add(q.id)
            marksSum += qMarks
        }

        // Top-up with remaining subjective or MCQ questions if bank is small and under 70M
        if (marksSum < targetTotalMarks) {
            val remaining = longSubj + shortSubj + mcqs
            for (q in remaining) {
                if (marksSum >= targetTotalMarks) break
                if (q.id in chosenIds) continue
                val qMarks = q.maxMarks.toInt().coerceAtLeast(1)
                if (marksSum + qMarks <= targetTotalMarks + 4) {
                    chosen.add(q)
                    chosenIds.add(q.id)
                    marksSum += qMarks
                }
            }
        }

        val mcqCount = chosen.count { it.questionType == "MCQ" }
        val shortCount = chosen.count { it.questionType == "SUBJECTIVE" && it.maxMarks < 10L }
        val longCount = chosen.count { it.questionType == "SUBJECTIVE" && it.maxMarks >= 10L }

        // Standard BEU 70M paper = 180 mins (3 hours); scale cleanly for smaller banks
        val durationMinutes = ((marksSum / 70.0) * 180.0).roundToInt().coerceIn(20, 180)

        return BeuMockPaperSpec(
            questions = chosen,
            totalMarks = marksSum,
            durationMinutes = durationMinutes,
            mcqCount = mcqCount,
            shortCount = shortCount,
            longCount = longCount
        )
    }

    /**
     * Computes subject-wise test accuracy and best score breakdown across all saved reports,
     * sorted from lowest average % to highest so weak subjects stand out first.
     */
    fun computeSubjectPerformance(reports: List<TestReport>): List<SubjectPerformanceStat> {
        if (reports.isEmpty()) return emptyList()
        return reports
            .groupBy { it.subjectId.ifBlank { it.subjectTitle } }
            .mapNotNull { (key, list) ->
                val title = list.firstOrNull()?.subjectTitle?.ifBlank { key } ?: return@mapNotNull null
                val avg = ((list.sumOf { it.percentage } / list.size) * 10.0).roundToInt() / 10.0
                val best = ((list.maxOf { it.percentage }) * 10.0).roundToInt() / 10.0
                SubjectPerformanceStat(
                    subjectId = list.firstOrNull()?.subjectId.orEmpty(),
                    subjectTitle = title,
                    testsTaken = list.size,
                    averagePercentage = avg,
                    bestPercentage = best
                )
            }
            .sortedBy { it.averagePercentage }
    }
}
