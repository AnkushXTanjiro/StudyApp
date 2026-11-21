package com.example

import android.content.Context
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.data.BundledQuestionBankLoader
import com.example.data.MakinaCompanionService
import com.example.data.PersonaContext
import com.example.data.PersonaManager
import com.example.data.VoiceQuestionRecorder
import com.example.model.BeeeQuestionBank
import com.example.model.CfetQuestionBank
import com.example.model.Math1QuestionBank
import com.example.model.QuestionResultItem
import com.example.model.StudyExamBuilder
import com.example.model.SyllabusSeedData
import com.example.model.TestReport
import com.example.model.UserProfile
import com.example.ui.components.MakinaCompanionModal
import com.example.ui.components.MakinaCompanionUiState
import com.example.ui.components.MakinaMessage
import com.example.ui.components.MakinaMode
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DeveloperScreen
import com.example.ui.screens.InteractiveTestSessionScreen
import com.example.ui.screens.ReportCardDetailScreen
import com.example.ui.theme.StudyAppTheme
import com.example.viewmodel.ActiveTestSession
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("StudyApp", appName)
    }

    @Test
    fun `beee question bank contains all 252 questions across 5 units`() {
        val questions = BeeeQuestionBank.allQuestions()
        assertEquals(252, questions.size)

        val uniqueIds = questions.map { it.id }.toSet()
        assertEquals(questions.size, uniqueIds.size)

        val unitIds = questions.map { it.unitId }.toSet()
        assertEquals(
            setOf("u_100111_1", "u_100111_2", "u_100111_3", "u_100111_4", "u_100111_5"),
            unitIds
        )

        questions.forEach { q ->
            assertTrue("Question prompt blank for ${q.id}", q.prompt.isNotBlank())
            if (q.questionType == "MCQ") {
                assertTrue("MCQ missing options for ${q.id}", q.options.size >= 4)
                assertTrue(
                    "Invalid correctOptionIndex for ${q.id}",
                    q.correctOptionIndex.toInt() in q.options.indices
                )
            } else {
                assertTrue("Subjective missing marking scheme for ${q.id}", q.markingScheme.isNotEmpty())
            }
        }
    }

    @Test
    fun `cfet question bank contains all 422 questions across 5 units`() {
        val questions = CfetQuestionBank.allQuestions()
        assertEquals(422, questions.size)

        val uniqueIds = questions.map { it.id }.toSet()
        assertEquals(questions.size, uniqueIds.size)
        assertTrue(
            "Sentinel question ID missing",
            CfetQuestionBank.SENTINEL_QUESTION_ID in uniqueIds
        )

        val byUnit = questions.groupBy { it.unitId }
        assertEquals(
            setOf("u_100108_1", "u_100108_2", "u_100108_3", "u_100108_4", "u_100108_5"),
            byUnit.keys
        )
        assertEquals(78, byUnit["u_100108_1"]?.size)
        assertEquals(74, byUnit["u_100108_2"]?.size)
        assertEquals(78, byUnit["u_100108_3"]?.size)
        assertEquals(110, byUnit["u_100108_4"]?.size)
        assertEquals(82, byUnit["u_100108_5"]?.size)

        val idRegex = Regex("^[a-zA-Z0-9_\\-]+$")
        questions.forEach { q ->
            assertTrue("Invalid Firestore ID for ${q.id}", idRegex.matches(q.id))
            assertEquals(CfetQuestionBank.SUBJECT_ID, q.subjectId)
            assertTrue("Prompt length invalid for ${q.id}", q.prompt.isNotBlank() && q.prompt.length <= 2000)
            assertTrue("topicCode too long for ${q.id}", q.topicCode.length <= 30)
            assertTrue("topicTitle invalid for ${q.id}", q.topicTitle.isNotBlank() && q.topicTitle.length <= 160)
            assertTrue("modelAnswer too long for ${q.id}", q.modelAnswer.length <= 2500)
            assertTrue("maxMarks out of range for ${q.id}", q.maxMarks in 1L..50L)
            assertTrue("markingScheme too large for ${q.id}", q.markingScheme.size <= 12)
            if (q.questionType == "MCQ") {
                assertEquals("MCQ must have 4 options for ${q.id}", 4, q.options.size)
                assertTrue(
                    "Invalid correctOptionIndex for ${q.id}",
                    q.correctOptionIndex.toInt() in q.options.indices
                )
            } else {
                assertTrue("Subjective missing modelAnswer for ${q.id}", q.modelAnswer.isNotBlank())
                assertTrue("Subjective missing marking scheme for ${q.id}", q.markingScheme.isNotEmpty())
            }
        }
    }

    @Test
    fun `math1 question bank contains all 170 questions across 5 units`() {
        val questions = Math1QuestionBank.allQuestions()
        assertEquals(170, questions.size)

        val uniqueIds = questions.map { it.id }.toSet()
        assertEquals(questions.size, uniqueIds.size)
        assertTrue(
            "Sentinel question ID missing",
            Math1QuestionBank.SENTINEL_QUESTION_ID in uniqueIds
        )

        val byUnit = questions.groupBy { it.unitId }
        assertEquals(
            setOf("u_100102_1", "u_100102_2", "u_100102_3", "u_100102_4", "u_100102_5"),
            byUnit.keys
        )
        assertEquals(51, byUnit["u_100102_1"]?.size)
        assertEquals(34, byUnit["u_100102_2"]?.size)
        assertEquals(32, byUnit["u_100102_3"]?.size)
        assertEquals(34, byUnit["u_100102_4"]?.size)
        assertEquals(19, byUnit["u_100102_5"]?.size)

        val idRegex = Regex("^[a-zA-Z0-9_\\-]+$")
        questions.forEach { q ->
            assertTrue("Invalid Firestore ID for ${q.id}", idRegex.matches(q.id))
            assertEquals(Math1QuestionBank.SUBJECT_ID, q.subjectId)
            assertTrue("Prompt length invalid for ${q.id}", q.prompt.isNotBlank() && q.prompt.length <= 2000)
            assertTrue("topicCode too long for ${q.id}", q.topicCode.length <= 30)
            assertTrue("topicTitle invalid for ${q.id}", q.topicTitle.isNotBlank() && q.topicTitle.length <= 160)
            assertTrue("modelAnswer too long for ${q.id}", q.modelAnswer.length <= 2500)
            assertTrue("maxMarks out of range for ${q.id}", q.maxMarks in 1L..50L)
            assertTrue("markingScheme too large for ${q.id}", q.markingScheme.size <= 12)
            if (q.questionType == "MCQ") {
                assertEquals("MCQ must have 4 options for ${q.id}", 4, q.options.size)
                assertTrue(
                    "Invalid correctOptionIndex for ${q.id}",
                    q.correctOptionIndex.toInt() in q.options.indices
                )
            } else {
                assertTrue("Subjective missing modelAnswer for ${q.id}", q.modelAnswer.isNotBlank())
                assertTrue("Subjective missing marking scheme for ${q.id}", q.markingScheme.isNotEmpty())
            }
        }
    }

    @Test
    fun `physics question bank loads all 284 questions cleanly from bundled json assets`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = BundledQuestionBankLoader(context)
        val bundled = loader.loadAllBlocking()
        val physics = bundled.filter { it.subjectId == BundledQuestionBankLoader.PHYSICS_SUBJECT_ID }
        assertEquals(284, physics.size)

        val uniqueIds = physics.map { it.id }.toSet()
        assertEquals(physics.size, uniqueIds.size)

        // Ensure zero ID collisions across all built-in subject banks (BEEE + CFET + Math-I + Physics)
        val allBuiltInIds = BeeeQuestionBank.allQuestions().map { it.id } +
            CfetQuestionBank.allQuestions().map { it.id } +
            Math1QuestionBank.allQuestions().map { it.id } +
            physics.map { it.id }
        assertEquals(1128, allBuiltInIds.size)
        assertEquals(allBuiltInIds.size, allBuiltInIds.toSet().size)

        val byUnit = physics.groupBy { it.unitId }
        assertEquals(
            setOf("u_100104_1", "u_100104_2", "u_100104_3", "u_100104_4", "u_100104_5"),
            byUnit.keys
        )

        val expectedUnitCounts = mapOf(
            "u_100104_1" to Triple(30, 15, 9),
            "u_100104_2" to Triple(30, 16, 11),
            "u_100104_3" to Triple(24, 12, 7),
            "u_100104_4" to Triple(36, 16, 8),
            "u_100104_5" to Triple(42, 19, 9)
        )
        expectedUnitCounts.forEach { (unitId, expected) ->
            val unitQs = byUnit[unitId].orEmpty()
            val mcqCnt = unitQs.count { it.questionType == "MCQ" }
            val shortCnt = unitQs.count { it.questionType == "SUBJECTIVE" && it.maxMarks < 10L }
            val longCnt = unitQs.count { it.questionType == "SUBJECTIVE" && it.maxMarks >= 10L }
            assertEquals("MCQ count mismatch for $unitId", expected.first, mcqCnt)
            assertEquals("Short count mismatch for $unitId", expected.second, shortCnt)
            assertEquals("Long count mismatch for $unitId", expected.third, longCnt)
        }

        val idRegex = Regex("^[a-zA-Z0-9_\\-]+$")
        physics.forEach { q ->
            assertTrue("Invalid ID format for ${q.id}", idRegex.matches(q.id))
            assertTrue("Should be marked as bundled ID: ${q.id}", BundledQuestionBankLoader.isBundledQuestionId(q.id))
            assertEquals("Engineering Physics", q.subjectTitle)
            assertTrue("Prompt blank for ${q.id}", q.prompt.isNotBlank())
            assertTrue("topicTitle blank for ${q.id}", q.topicTitle.isNotBlank())
            assertTrue("maxMarks out of range for ${q.id}", q.maxMarks in 1L..50L)
            assertTrue("modelAnswer blank for ${q.id}", q.modelAnswer.isNotBlank())
            assertTrue("markingScheme empty for ${q.id}", q.markingScheme.isNotEmpty())

            val allText = buildString {
                append(q.prompt).append('\n')
                append(q.formulaExpr).append('\n')
                append(q.modelAnswer).append('\n')
                q.options.forEach { append(it).append('\n') }
                q.markingScheme.forEach { append(it).append('\n') }
            }
            assertTrue("Null glyph found in ${q.id}", '\u0000' !in allText)
            assertTrue("Replacement char found in ${q.id}", '\uFFFD' !in allText)

            if (q.questionType == "MCQ") {
                assertEquals("MCQ must have 4 options for ${q.id}", 4, q.options.size)
                assertTrue(
                    "Invalid correctOptionIndex for ${q.id}",
                    q.correctOptionIndex.toInt() in q.options.indices
                )
            }
        }
    }

    @Test
    fun `ai question bank loads all 289 questions cleanly from bundled json assets`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = BundledQuestionBankLoader(context)
        val bundled = loader.loadAllBlocking()
        val ai = bundled.filter { it.subjectId == BundledQuestionBankLoader.AI_SUBJECT_ID }
        assertEquals(289, ai.size)

        val uniqueIds = ai.map { it.id }.toSet()
        assertEquals(ai.size, uniqueIds.size)

        // Ensure zero ID collisions across BEEE + CFET + Math-I + Physics + AI
        val physics = bundled.filter { it.subjectId == BundledQuestionBankLoader.PHYSICS_SUBJECT_ID }
        val allBuiltInIds = BeeeQuestionBank.allQuestions().map { it.id } +
            CfetQuestionBank.allQuestions().map { it.id } +
            Math1QuestionBank.allQuestions().map { it.id } +
            physics.map { it.id } +
            ai.map { it.id }
        assertEquals(1417, allBuiltInIds.size)
        assertEquals(allBuiltInIds.size, allBuiltInIds.toSet().size)

        val byUnit = ai.groupBy { it.unitId }
        assertEquals(
            setOf("u_100105_1", "u_100105_2", "u_100105_3", "u_100105_4", "u_100105_5"),
            byUnit.keys
        )

        val expectedUnitCounts = mapOf(
            "u_100105_1" to Triple(34, 10, 6),
            "u_100105_2" to Triple(44, 15, 9),
            "u_100105_3" to Triple(33, 12, 6),
            "u_100105_4" to Triple(29, 9, 5),
            "u_100105_5" to Triple(52, 15, 10)
        )
        expectedUnitCounts.forEach { (unitId, expected) ->
            val unitQs = byUnit[unitId].orEmpty()
            val mcqCnt = unitQs.count { it.questionType == "MCQ" }
            val shortCnt = unitQs.count { it.questionType == "SUBJECTIVE" && it.maxMarks < 10L }
            val longCnt = unitQs.count { it.questionType == "SUBJECTIVE" && it.maxMarks >= 10L }
            assertEquals("MCQ count mismatch for $unitId", expected.first, mcqCnt)
            assertEquals("Short count mismatch for $unitId", expected.second, shortCnt)
            assertEquals("Long count mismatch for $unitId", expected.third, longCnt)
        }

        val idRegex = Regex("^[a-zA-Z0-9_\\-]+$")
        ai.forEach { q ->
            assertTrue("Invalid ID format for ${q.id}", idRegex.matches(q.id))
            assertTrue("Should be marked as bundled ID: ${q.id}", BundledQuestionBankLoader.isBundledQuestionId(q.id))
            assertEquals("Introduction to AI", q.subjectTitle)
            assertTrue("Prompt blank for ${q.id}", q.prompt.isNotBlank())
            assertTrue("topicTitle blank for ${q.id}", q.topicTitle.isNotBlank())
            assertTrue("maxMarks out of range for ${q.id}", q.maxMarks in 1L..50L)
            assertTrue("modelAnswer blank for ${q.id}", q.modelAnswer.isNotBlank())
            assertTrue("markingScheme empty for ${q.id}", q.markingScheme.isNotEmpty())

            val allText = buildString {
                append(q.prompt).append('\n')
                append(q.formulaExpr).append('\n')
                append(q.modelAnswer).append('\n')
                q.options.forEach { append(it).append('\n') }
                q.markingScheme.forEach { append(it).append('\n') }
            }
            assertTrue("Null glyph found in ${q.id}", '\u0000' !in allText)
            assertTrue("Replacement char found in ${q.id}", '\uFFFD' !in allText)

            if (q.questionType == "MCQ") {
                assertEquals("MCQ must have 4 options for ${q.id}", 4, q.options.size)
                assertTrue(
                    "Invalid correctOptionIndex for ${q.id}",
                    q.correctOptionIndex.toInt() in q.options.indices
                )
            }
        }
    }

    @Test
    fun `uhv question bank loads all 171 questions cleanly from bundled json assets`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = BundledQuestionBankLoader(context)
        val bundled = loader.loadAllBlocking()
        val uhv = bundled.filter { it.subjectId == BundledQuestionBankLoader.UHV_SUBJECT_ID }
        assertEquals(171, uhv.size)

        val uniqueIds = uhv.map { it.id }.toSet()
        assertEquals(uhv.size, uniqueIds.size)

        // Ensure zero ID collisions across all 6 theory subject banks (BEEE + CFET + Math-I + Physics + AI + UHV)
        val physics = bundled.filter { it.subjectId == BundledQuestionBankLoader.PHYSICS_SUBJECT_ID }
        val ai = bundled.filter { it.subjectId == BundledQuestionBankLoader.AI_SUBJECT_ID }
        val allBuiltInIds = BeeeQuestionBank.allQuestions().map { it.id } +
            CfetQuestionBank.allQuestions().map { it.id } +
            Math1QuestionBank.allQuestions().map { it.id } +
            physics.map { it.id } +
            ai.map { it.id } +
            uhv.map { it.id }
        assertEquals(1588, allBuiltInIds.size)
        assertEquals(allBuiltInIds.size, allBuiltInIds.toSet().size)

        val byUnit = uhv.groupBy { it.unitId }
        assertEquals(
            setOf("u_100109_1", "u_100109_2", "u_100109_3", "u_100109_4", "u_100109_5"),
            byUnit.keys
        )

        val expectedUnitCounts = mapOf(
            "u_100109_1" to Triple(18, 9, 3),
            "u_100109_2" to Triple(19, 9, 4),
            "u_100109_3" to Triple(14, 6, 4),
            "u_100109_4" to Triple(24, 12, 4),
            "u_100109_5" to Triple(25, 15, 5)
        )
        expectedUnitCounts.forEach { (unitId, expected) ->
            val unitQs = byUnit[unitId].orEmpty()
            val mcqCnt = unitQs.count { it.questionType == "MCQ" }
            val shortCnt = unitQs.count { it.questionType == "SUBJECTIVE" && it.maxMarks < 10L }
            val longCnt = unitQs.count { it.questionType == "SUBJECTIVE" && it.maxMarks >= 10L }
            assertEquals("MCQ count mismatch for $unitId", expected.first, mcqCnt)
            assertEquals("Short count mismatch for $unitId", expected.second, shortCnt)
            assertEquals("Long count mismatch for $unitId", expected.third, longCnt)
        }

        val idRegex = Regex("^[a-zA-Z0-9_\\-]+$")
        uhv.forEach { q ->
            assertTrue("Invalid ID format for ${q.id}", idRegex.matches(q.id))
            assertTrue("Should be marked as bundled ID: ${q.id}", BundledQuestionBankLoader.isBundledQuestionId(q.id))
            assertEquals("Universal Human Values", q.subjectTitle)
            assertTrue("Prompt blank for ${q.id}", q.prompt.isNotBlank())
            assertTrue("topicTitle blank for ${q.id}", q.topicTitle.isNotBlank())
            assertTrue("maxMarks out of range for ${q.id}", q.maxMarks in 1L..50L)
            assertTrue("modelAnswer blank for ${q.id}", q.modelAnswer.isNotBlank())
            assertTrue("markingScheme empty for ${q.id}", q.markingScheme.isNotEmpty())

            val allText = buildString {
                append(q.prompt).append('\n')
                append(q.formulaExpr).append('\n')
                append(q.modelAnswer).append('\n')
                q.options.forEach { append(it).append('\n') }
                q.markingScheme.forEach { append(it).append('\n') }
            }
            assertTrue("Null glyph found in ${q.id}", '\u0000' !in allText)
            assertTrue("Replacement char found in ${q.id}", '\uFFFD' !in allText)

            if (q.questionType == "MCQ") {
                assertEquals("MCQ must have 4 options for ${q.id}", 4, q.options.size)
                assertTrue(
                    "Invalid correctOptionIndex for ${q.id}",
                    q.correctOptionIndex.toInt() in q.options.indices
                )
            }
        }
    }

    @Test
    fun `lab viva question banks load all 973 BEEE Physics and PPS lab questions cleanly`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = BundledQuestionBankLoader(context)
        val bundled = loader.loadAllBlocking()

        val beeeLab = bundled.filter { it.subjectId == BundledQuestionBankLoader.BEEE_LAB_SUBJECT_ID }
        val physLab = bundled.filter { it.subjectId == BundledQuestionBankLoader.PHYSICS_LAB_SUBJECT_ID }
        val ppsLab = bundled.filter { it.subjectId == BundledQuestionBankLoader.PPS_LAB_SUBJECT_ID }

        assertEquals(327, beeeLab.size)
        assertEquals(366, physLab.size)
        assertEquals(280, ppsLab.size)

        val allLab = beeeLab + physLab + ppsLab
        assertEquals(973, allLab.size)
        assertEquals(allLab.size, allLab.map { it.id }.toSet().size)

        // Ensure zero ID collisions across the ENTIRE built-in bank (all 9 subjects: 1588 theory + 973 lab = 2561)
        val allBuiltInIds = BeeeQuestionBank.allQuestions().map { it.id } +
            CfetQuestionBank.allQuestions().map { it.id } +
            Math1QuestionBank.allQuestions().map { it.id } +
            bundled.map { it.id }
        assertEquals(2561, allBuiltInIds.size)
        assertEquals(allBuiltInIds.size, allBuiltInIds.toSet().size)

        val beeeByUnit = beeeLab.groupBy { it.unitId }
        assertEquals(setOf("u_100111P_1", "u_100111P_2"), beeeByUnit.keys)
        assertEquals(165, beeeByUnit["u_100111P_1"]?.size)
        assertEquals(162, beeeByUnit["u_100111P_2"]?.size)

        val physByUnit = physLab.groupBy { it.unitId }
        assertEquals(setOf("u_100104P_1", "u_100104P_2"), physByUnit.keys)
        assertEquals(187, physByUnit["u_100104P_1"]?.size)
        assertEquals(179, physByUnit["u_100104P_2"]?.size)

        val ppsByUnit = ppsLab.groupBy { it.unitId }
        assertEquals(setOf("u_100112P_1", "u_100112P_2"), ppsByUnit.keys)
        assertEquals(147, ppsByUnit["u_100112P_1"]?.size)
        assertEquals(133, ppsByUnit["u_100112P_2"]?.size)

        val idRegex = Regex("^[a-zA-Z0-9_\\-]+$")
        allLab.forEach { q ->
            assertTrue("Invalid ID format for ${q.id}", idRegex.matches(q.id))
            assertTrue("Should be marked as bundled ID: ${q.id}", BundledQuestionBankLoader.isBundledQuestionId(q.id))
            assertEquals("Expected SUBJECTIVE viva question for ${q.id}", "SUBJECTIVE", q.questionType)
            assertEquals("Expected 2 marks for viva question ${q.id}", 2L, q.maxMarks)
            assertTrue("Prompt blank for ${q.id}", q.prompt.isNotBlank())
            assertTrue("topicCode blank for ${q.id}", q.topicCode.isNotBlank())
            assertTrue("topicTitle blank for ${q.id}", q.topicTitle.isNotBlank())
            assertTrue("modelAnswer blank for ${q.id}", q.modelAnswer.isNotBlank())
            assertTrue("markingScheme empty for ${q.id}", q.markingScheme.isNotEmpty())

            val allText = buildString {
                append(q.prompt).append('\n')
                append(q.topicTitle).append('\n')
                append(q.modelAnswer).append('\n')
                q.markingScheme.forEach { append(it).append('\n') }
            }
            assertTrue("Null glyph found in ${q.id}", '\u0000' !in allText)
            assertTrue("Replacement char found in ${q.id}", '\uFFFD' !in allText)
            assertTrue("PUA glyph found in ${q.id}", allText.none { it in '\uE000'..'\uF8FF' })
        }
    }

    @Test
    fun `group A syllabus seed includes all 6 theory subjects and 3 lab subjects`() {
        val syllabus = SyllabusSeedData.defaultGroupASyllabus()
        assertTrue(syllabus.isNotEmpty())
        val uhv = syllabus.firstOrNull { it.id == "sub_100109" }
        assertTrue("UHV subject missing from syllabus seed", uhv != null)
        assertEquals(5, uhv!!.units.size)

        val ai = syllabus.firstOrNull { it.id == "sub_100105" }
        assertTrue("AI subject missing from syllabus seed", ai != null)
        assertEquals(5, ai!!.units.size)

        val physics = syllabus.firstOrNull { it.id == "sub_100104" }
        assertTrue("Physics subject missing from syllabus seed", physics != null)
        assertEquals(5, physics!!.units.size)

        val math1 = syllabus.firstOrNull { it.id == "sub_100102" }
        assertTrue("Math-I subject missing from syllabus seed", math1 != null)
        assertEquals(5, math1!!.units.size)

        val beee = syllabus.firstOrNull { it.id == "sub_100111" }
        assertTrue("BEEE subject missing from syllabus seed", beee != null)
        assertEquals(5, beee!!.units.size)

        val cfet = syllabus.firstOrNull { it.id == "sub_100108" }
        assertTrue("CFET subject missing from syllabus seed", cfet != null)
        assertEquals(5, cfet!!.units.size)

        val physLab = syllabus.firstOrNull { it.id == "sub_100104P" }
        assertTrue("Physics Lab missing from syllabus seed", physLab != null)
        assertEquals(2, physLab!!.units.size)

        val beeeLab = syllabus.firstOrNull { it.id == "sub_100111P" }
        assertTrue("BEEE Lab missing from syllabus seed", beeeLab != null)
        assertEquals(2, beeeLab!!.units.size)

        val ppsLab = syllabus.firstOrNull { it.id == "sub_100112P" }
        assertTrue("PPS Lab missing from syllabus seed", ppsLab != null)
        assertEquals(2, ppsLab!!.units.size)
    }

    @Test
    fun `auth screen renders cleanly with StudyApp theme`() {
        composeTestRule.setContent {
            StudyAppTheme {
                AuthScreen(onAuthSuccess = {})
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("StudyApp").assertIsDisplayed()
        composeTestRule.onNodeWithTag("google_sign_in_button")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun `developer screen renders Ankush Kumar profile skills Telegram and credits`() {
        var closed = false
        composeTestRule.setContent {
            StudyAppTheme {
                DeveloperScreen(onClose = { closed = true })
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Ankush Kumar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("developer_screen_list")
            .performScrollToNode(hasTestTag("developer_telegram_button"))
        composeTestRule.onNodeWithTag("developer_telegram_button").assertIsDisplayed()
        composeTestRule.onNodeWithText("@Lord_Yoriche").assertIsDisplayed()
        composeTestRule.onNodeWithTag("developer_back_button").performClick()
        assertTrue(closed)
    }

    @Test
    fun `report card detail screen supports Makina result explanation and on-demand per-question explain with AI`() {
        val sampleReport = TestReport(
            id = "rep_test_1",
            subjectId = "sub_100111",
            subjectTitle = "Basic Electrical & Electronics Engineering",
            unitId = "u_100111_1",
            unitTitle = "Unit 1: DC Circuits",
            totalQuestions = 2,
            correctCount = 1,
            obtainedMarks = 5.0,
            totalMarks = 10.0,
            percentage = 50.0,
            durationSeconds = 180,
            strongTopics = listOf("Ohm's Law"),
            weakTopics = listOf("Thevenin Theorem"),
            questionBreakdown = listOf(
                QuestionResultItem(
                    topicTitle = "Ohm's Law",
                    questionType = "MCQ",
                    questionPrompt = "What is the SI unit of resistance?",
                    userAnswer = "Ohm",
                    awardedMarks = 5.0,
                    maxMarks = 5.0,
                    feedback = "Exact match!"
                ),
                QuestionResultItem(
                    topicTitle = "Thevenin Theorem",
                    questionType = "SUBJECTIVE",
                    questionPrompt = "State Thevenin's theorem for linear bilateral networks.",
                    userAnswer = "Equivalent voltage source.",
                    awardedMarks = 0.0,
                    maxMarks = 5.0,
                    feedback = "Missing Rth series impedance condition."
                )
            )
        )

        var explainedReportId: String? = null
        val explainedTopics = mutableListOf<String>()

        composeTestRule.setContent {
            StudyAppTheme {
                ReportCardDetailScreen(
                    report = sampleReport,
                    onAddWeakTopicsToTodo = {},
                    onAskMakinaExplainReport = { explainedReportId = it.id },
                    onExplainQuestionWithAi = { explainedTopics.add(it.topicTitle) },
                    onClose = {}
                )
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("ask_makina_explain_result_button")
            .assertIsDisplayed()
            .performClick()
        assertEquals("rep_test_1", explainedReportId)

        // Tap ONLY Q2's Explain with AI button and verify only Q2 is requested
        composeTestRule.onNodeWithTag("report_card_detail")
            .performScrollToNode(hasTestTag("explain_ai_question_button_2"))
        composeTestRule.onNodeWithTag("explain_ai_question_button_2")
            .assertIsDisplayed()
            .performClick()
        assertEquals(listOf("Thevenin Theorem"), explainedTopics)
    }

    @Test
    fun `report card detail screen supports Needs Review filter model answer and Explain with AI`() {
        val sampleReport = TestReport(
            id = "rep_test_2",
            subjectId = "sub_100111",
            subjectTitle = "Basic Electrical & Electronics Engineering",
            unitId = "u_100111_1",
            unitTitle = "Unit 1: DC Circuits",
            totalQuestions = 2,
            correctCount = 1,
            obtainedMarks = 5.0,
            totalMarks = 10.0,
            percentage = 50.0,
            durationSeconds = 180,
            strongTopics = listOf("Ohm's Law"),
            weakTopics = listOf("Thevenin Theorem"),
            questionBreakdown = listOf(
                QuestionResultItem(
                    topicTitle = "Ohm's Law",
                    questionType = "MCQ",
                    questionPrompt = "What is the SI unit of resistance?",
                    userAnswer = "Ohm",
                    modelAnswer = "Ohm (Ω)",
                    awardedMarks = 5.0,
                    maxMarks = 5.0,
                    feedback = "Exact match!"
                ),
                QuestionResultItem(
                    topicTitle = "Thevenin Theorem",
                    questionType = "SUBJECTIVE",
                    questionPrompt = "State Thevenin's theorem for linear bilateral networks.",
                    userAnswer = "Equivalent voltage source.",
                    modelAnswer = "Any linear bilateral network can be replaced by Voc in series with Rth.",
                    awardedMarks = 0.0,
                    maxMarks = 5.0,
                    feedback = "Missing Rth series impedance condition."
                )
            )
        )

        val explainedTopics = mutableListOf<String>()

        composeTestRule.setContent {
            StudyAppTheme {
                ReportCardDetailScreen(
                    report = sampleReport,
                    onAddWeakTopicsToTodo = {},
                    onAskMakinaExplainReport = {},
                    onExplainQuestionWithAi = { explainedTopics.add(it.topicTitle) },
                    onClose = {}
                )
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("report_card_detail")
            .performScrollToNode(hasTestTag("report_filter_review"))
        composeTestRule.onNodeWithTag("report_filter_review")
            .assertIsDisplayed()
            .performClick()

        // Q2 keeps its original index (explain_ai_question_button_2) and renders its model answer
        composeTestRule.onNodeWithTag("report_card_detail")
            .performScrollToNode(hasTestTag("explain_ai_question_button_2"))
        composeTestRule.onNodeWithText("Any linear bilateral network can be replaced by Voc in series with Rth.")
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("explain_ai_question_button_2")
            .assertIsDisplayed()
            .performClick()
        assertEquals(listOf("Thevenin Theorem"), explainedTopics)
    }

    @Test
    fun `makina companion modal renders greeting tour voice controls and follow-up doubt chat`() {
        var mutedToggled = false
        var autoSpeakToggled = false
        var chatResetClicked = false
        var sentDoubt: String? = null
        var replayedText: String? = null

        val uiState = MakinaCompanionUiState(
            isVisible = true,
            mode = MakinaMode.GREETING_AND_TOUR,
            title = "Makina • AI Study Companion",
            subtitle = "Your personal BEU study guide",
            messages = listOf(
                MakinaMessage(
                    isMakina = true,
                    text = "Welcome back, scholar! Ready to ace your BEU subjects today?"
                )
            )
        )

        composeTestRule.setContent {
            StudyAppTheme {
                MakinaCompanionModal(
                    state = uiState,
                    isSpeaking = false,
                    isLoadingVoice = false,
                    isMuted = false,
                    isAutoSpeakEnabled = true,
                    voiceEngineLabel = "Gemini AI Voice",
                    onSpeakText = { replayedText = it },
                    onToggleMute = { mutedToggled = true },
                    onToggleAutoSpeak = { autoSpeakToggled = true },
                    onResetChat = { chatResetClicked = true },
                    onSendFollowUp = { sentDoubt = it },
                    onNavigateToTab = {},
                    onOpenDeveloper = {},
                    onDismiss = {}
                )
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("makina_companion_modal").assertIsDisplayed()
        composeTestRule.onNodeWithTag("makina_auto_speak_chip").assertIsDisplayed().performClick()
        assertTrue(autoSpeakToggled)
        composeTestRule.onNodeWithTag("makina_reset_chat_chip").assertIsDisplayed().performClick()
        assertTrue(chatResetClicked)

        composeTestRule.onNodeWithTag("makina_modal_list")
            .performScrollToNode(hasTestTag("makina_feature_tour_card"))
        composeTestRule.onNodeWithTag("makina_feature_tour_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("makina_replay_voice_button").performClick()
        assertEquals("Welcome back, scholar! Ready to ace your BEU subjects today?", replayedText)

        composeTestRule.onNodeWithTag("makina_mute_voice_button").performClick()
        assertTrue(mutedToggled)

        composeTestRule.onNodeWithTag("makina_followup_input")
            .performTextInput("Explain Thevenin resistance simply")
        composeTestRule.onNodeWithTag("makina_send_followup_button").performClick()
        assertEquals("Explain Thevenin resistance simply", sentDoubt)
    }

    @Test
    fun `official CSE-A Batch 2K26 timetable seed has all 30 Mon-Sat slots with valid Firestore fields`() {
        val slots = SyllabusSeedData.starterTimetableSlots()
        assertEquals(30, slots.size)
        assertEquals(slots.size, slots.map { it.id }.toSet().size)
        assertTrue(slots.any { it.id == SyllabusSeedData.CSE_A_TIMETABLE_SENTINEL_ID })

        val byDay = slots.groupBy { it.dayOfWeek }
        assertEquals(setOf(1L, 2L, 3L, 4L, 5L, 6L), byDay.keys)
        assertEquals(6, byDay[1L]?.size) // Mon: EP, BEEE, M-I, AI, CFET, EIC
        assertEquals(4, byDay[2L]?.size) // Tue: AI, PPS Lab (2h), BEEE Lab/MM (2h), EIC
        assertEquals(5, byDay[3L]?.size) // Wed: BEEE, UHV, EP, BEEE Lab/MM (2h), Sports
        assertEquals(5, byDay[4L]?.size) // Thu: UHV, BEEE, M-I, Mentor Meeting (2h), Sports
        assertEquals(5, byDay[5L]?.size) // Fri: AI, CFET, EIC, EP Lab (2h), Sports
        assertEquals(5, byDay[6L]?.size) // Sat: M-I, EP, CFET, Club Activities (2h), Sports

        val validTypes = setOf("LECTURE", "LAB", "SELF_STUDY")
        slots.forEach { slot ->
            assertTrue("Invalid slotType for ${slot.id}: ${slot.slotType}", slot.slotType in validTypes)
            assertTrue("Subject title length invalid for ${slot.id}", slot.subjectTitle.length in 1..120)
            assertTrue("unitHint too long for ${slot.id}", slot.unitHint.length <= 120)
            assertTrue("room too long for ${slot.id}", slot.room.length <= 60)
            assertTrue("Invalid dayOfWeek for ${slot.id}", slot.dayOfWeek in 1L..7L)
        }

        val ppsLab = slots.first { it.id == "tt_csea_tue_2" }
        assertEquals("Room 109", ppsLab.room)
        assertEquals("LAB", ppsLab.slotType)
        assertEquals("11:00", ppsLab.startTime)
        assertEquals("13:00", ppsLab.endTime)

        val eicSlots = slots.filter { it.subjectTitle.contains("EIC 100110") }
        assertEquals(3, eicSlots.size)
        val mentor = slots.first { it.id == "tt_csea_thu_4" }
        assertEquals("Mentor Meeting", mentor.subjectTitle)
        val club = slots.first { it.id == "tt_csea_sat_4" }
        assertEquals("Club Activities", club.subjectTitle)
    }

    @Test
    fun `weak topic retry deck prioritizes missed questions and weak topics from past reports`() {
        val mathQuestions = Math1QuestionBank.allQuestions()
        val missedQ = mathQuestions.first()
        val weakTopic = mathQuestions[15].topicTitle

        val report = TestReport(
            id = "rep_weak_1",
            subjectId = Math1QuestionBank.SUBJECT_ID,
            subjectTitle = "Engineering Mathematics - I",
            unitId = "u_100102_1",
            unitTitle = "Unit 1.0",
            weakTopics = listOf(weakTopic),
            questionBreakdown = listOf(
                QuestionResultItem(
                    questionPrompt = missedQ.prompt,
                    topicTitle = missedQ.topicTitle,
                    questionType = missedQ.questionType,
                    awardedMarks = 0.0,
                    maxMarks = missedQ.maxMarks.toDouble()
                )
            )
        )

        val deck = StudyExamBuilder.buildWeakTopicRetryDeck(listOf(report), mathQuestions, maxQuestions = 10)
        assertTrue(deck.isNotEmpty())
        assertEquals(missedQ.id, deck.first().id)
        assertTrue(deck.any { it.topicTitle == weakTopic })
        assertTrue(StudyExamBuilder.countWeakAndMissedSignals(listOf(report)) >= 2)
    }

    @Test
    fun `beu end-sem mock builder generates balanced 70-mark multi-unit paper with 3h timer`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = BundledQuestionBankLoader(context)
        val bundled = loader.loadAllBlocking()
        val physicsSubj = SyllabusSeedData.defaultGroupASyllabus().first { it.id == BundledQuestionBankLoader.PHYSICS_SUBJECT_ID }

        val spec = StudyExamBuilder.buildBeuEndSemMockPaper(physicsSubj, bundled)
        assertTrue("Expected mock questions", spec.questions.isNotEmpty())
        assertTrue("Total marks should be around 70 (got ${spec.totalMarks})", spec.totalMarks in 66..74)
        assertEquals(180, spec.durationMinutes)
        assertTrue("Expected MCQs in Section A", spec.mcqCount >= 5)
        assertTrue("Expected descriptive questions in Section B/C", spec.shortCount + spec.longCount >= 4)
        assertTrue("Expected multi-unit coverage", spec.questions.map { it.unitId }.toSet().size >= 4)
    }

    @Test
    fun `report card detail screen retry weak topics button triggers callback`() {
        val sampleQ = Math1QuestionBank.allQuestions().first()
        val sampleReport = TestReport(
            id = "rep_retry_1",
            subjectId = sampleQ.subjectId,
            subjectTitle = sampleQ.subjectTitle,
            unitId = sampleQ.unitId,
            unitTitle = sampleQ.unitTitle,
            weakTopics = listOf(sampleQ.topicTitle),
            questionBreakdown = listOf(
                QuestionResultItem(
                    questionPrompt = sampleQ.prompt,
                    topicTitle = sampleQ.topicTitle,
                    questionType = sampleQ.questionType,
                    awardedMarks = 0.0,
                    maxMarks = 2.0
                )
            )
        )

        var retriedReportId: String? = null
        composeTestRule.setContent {
            StudyAppTheme {
                ReportCardDetailScreen(
                    report = sampleReport,
                    onAddWeakTopicsToTodo = {},
                    onRetryWeakTopics = { retriedReportId = it.id },
                    onClose = {}
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("report_card_detail")
            .performScrollToNode(hasTestTag("retry_weak_topics_button"))
        composeTestRule.onNodeWithTag("retry_weak_topics_button").performClick()
        assertEquals("rep_retry_1", retriedReportId)
    }

    @Test
    fun `beu 70M countdown session displays mode badge and remaining timer`() {
        val sampleQ = Math1QuestionBank.allQuestions().first()
        composeTestRule.setContent {
            StudyAppTheme {
                InteractiveTestSessionScreen(
                    session = ActiveTestSession(
                        subjectId = sampleQ.subjectId,
                        subjectTitle = sampleQ.subjectTitle,
                        unitId = "beu_70m_sub_100102",
                        unitTitle = "BEU Mock: End-Sem Paper (70M)",
                        questions = listOf(sampleQ),
                        elapsedSeconds = 60L,
                        timeLimitSeconds = 10800L,
                        examModeLabel = "BEU_70M"
                    ),
                    isOfflineMode = true,
                    onSelectMcq = { _, _ -> },
                    onUpdateSubjective = { _, _ -> },
                    onAttachPhoto = { _, _, _, _ -> },
                    onToggleSchemeStep = { _, _ -> },
                    onChangeIndex = {},
                    onSubmit = {},
                    onExit = {}
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("BEU 70M").assertIsDisplayed()
        composeTestRule.onNodeWithText("2:59:00 left").assertIsDisplayed()
    }

    @Test
    fun `persona manager defines Makina flirty confident tone and injects context system prompts`() {
        val manager = PersonaManager()
        assertEquals("Makina", manager.personaName)
        assertEquals("Aoede", manager.voiceName)
        assertTrue("confident" in manager.profile.traits)
        assertTrue("playfully flirty" in manager.profile.traits)

        val greetingSystem = manager.systemPromptFor(PersonaContext.ARRIVAL_GREETING)
        assertTrue(greetingSystem.contains("Makina"))
        assertTrue(greetingSystem.contains("Confident", ignoreCase = true))
        assertTrue(greetingSystem.contains("flirty", ignoreCase = true))
        assertTrue(greetingSystem.contains("Arrival Greeting"))

        val resultSystem = manager.systemPromptFor(PersonaContext.TEST_RESULT_EXPLANATION)
        assertTrue(resultSystem.contains("Test Result Breakdown"))
        assertTrue(resultSystem.contains("Explain with AI"))

        val questionSystem = manager.systemPromptFor(PersonaContext.QUESTION_EXPLANATION)
        assertTrue(questionSystem.contains("Question Walkthrough"))

        val greetingPrompt = manager.buildArrivalGreetingPrompt(
            studentName = "Ankush Kumar",
            streakDays = 5L,
            xp = 420L,
            completedTopics = 12,
            totalTopics = 40,
            testsTaken = 3
        )
        assertTrue(greetingPrompt.contains("Ankush"))
        assertTrue(greetingPrompt.contains("5 day(s)"))

        val service = MakinaCompanionService(manager)
        val fallback = service.fallbackGreeting("Ankush Kumar", streakDays = 5L, testsTaken = 2)
        assertTrue(fallback.contains("Ankush"))
        assertTrue(fallback.contains("5-day streak"))

        val reportPrompt = manager.buildTestExplanationPrompt(
            TestReport(
                subjectTitle = "Engineering Physics",
                unitTitle = "Unit 1: Wave Optics",
                obtainedMarks = 16.0,
                totalMarks = 20.0,
                percentage = 80.0,
                strongTopics = listOf("Newton's Rings"),
                weakTopics = listOf("Diffraction Grating")
            )
        )
        assertTrue(reportPrompt.contains("Engineering Physics"))
        assertTrue(reportPrompt.contains("Diffraction Grating"))

        val voicePrompt = manager.buildVoiceAudioDoubtPrompt(
            contextSummary = "Student reviewing Physics Test",
            conversationHistory = listOf("Makina" to "Ready when you are!")
        )
        assertTrue(voicePrompt.contains("Makina"))
        assertTrue(voicePrompt.contains("Student reviewing Physics Test"))

        val pcmSample = ByteArray(100)
        val wav = VoiceQuestionRecorder.encodePcmToWavBytes(pcmSample, sampleRate = 16000)
        assertEquals(144, wav.size)
        assertEquals("RIFF", String(wav, 0, 4, Charsets.US_ASCII))
        assertEquals("WAVE", String(wav, 8, 4, Charsets.US_ASCII))
    }

    @Test
    fun `makina modal renders microphone button in post-test question explainer mode`() {
        composeTestRule.setContent {
            StudyAppTheme {
                MakinaCompanionModal(
                    state = MakinaCompanionUiState(
                        isVisible = true,
                        mode = MakinaMode.QUESTION_EXPLAINER,
                        title = "Makina • Explain with AI",
                        subtitle = "Ohm's Law • 0/2M",
                        messages = listOf(MakinaMessage(isMakina = true, text = "Let me walk you through this one."))
                    ),
                    isSpeaking = false,
                    isLoadingVoice = false,
                    isMuted = false,
                    voiceEngineLabel = "Gemini Voice",
                    onSpeakText = {},
                    onToggleMute = {},
                    onSendFollowUp = {},
                    onNavigateToTab = {},
                    onOpenDeveloper = {},
                    onDismiss = {}
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("makina_mic_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("makina_send_followup_button").assertIsDisplayed()
    }

    @Test
    fun `computeSubjectPerformance calculates average best and sorts weakest first`() {
        val reports = listOf(
            TestReport(id = "r1", subjectId = "sub_math", subjectTitle = "Calculus & Linear Algebra", percentage = 85.0),
            TestReport(id = "r2", subjectId = "sub_math", subjectTitle = "Calculus & Linear Algebra", percentage = 95.0),
            TestReport(id = "r3", subjectId = "sub_phys", subjectTitle = "Engineering Physics", percentage = 42.5),
            TestReport(id = "r4", subjectId = "sub_phys", subjectTitle = "Engineering Physics", percentage = 57.5)
        )

        val stats = StudyExamBuilder.computeSubjectPerformance(reports)
        assertEquals(2, stats.size)

        assertEquals("sub_phys", stats[0].subjectId)
        assertEquals(2, stats[0].testsTaken)
        assertEquals(50.0, stats[0].averagePercentage, 0.01)
        assertEquals(57.5, stats[0].bestPercentage, 0.01)

        assertEquals("sub_math", stats[1].subjectId)
        assertEquals(2, stats[1].testsTaken)
        assertEquals(90.0, stats[1].averagePercentage, 0.01)
        assertEquals(95.0, stats[1].bestPercentage, 0.01)
    }

    @Test
    fun `dashboard renders subject performance card when reports exist`() {
        val reports = listOf(
            TestReport(id = "r1", subjectId = "sub_phys", subjectTitle = "Engineering Physics", percentage = 62.0),
            TestReport(id = "r2", subjectId = "sub_phys", subjectTitle = "Engineering Physics", percentage = 82.0)
        )

        composeTestRule.setContent {
            StudyAppTheme {
                DashboardScreen(
                    listState = rememberLazyListState(),
                    profile = UserProfile(displayName = "Ankush Kumar", xp = 250L, streakDays = 4L),
                    subjects = SyllabusSeedData.defaultGroupASyllabus(),
                    todos = emptyList(),
                    timetable = emptyList(),
                    reports = reports,
                    onNavigateTab = {},
                    onToggleTodo = {},
                    onOpenReport = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("dashboard_list")
            .performScrollToNode(hasTestTag("subject_performance_card"))
        composeTestRule.onNodeWithTag("subject_performance_card").assertIsDisplayed()
        composeTestRule.onNodeWithText("SUBJECT PERFORMANCE").assertIsDisplayed()
        composeTestRule.onNodeWithText("Avg 72.0% • Best 82.0% (2)").assertIsDisplayed()
    }

    @Test
    fun `exit test button shows confirmation guard before discarding active test`() {
        val sampleQuestions = Math1QuestionBank.allQuestions().take(2)
        var exited = false

        composeTestRule.setContent {
            StudyAppTheme {
                InteractiveTestSessionScreen(
                    session = ActiveTestSession(
                        subjectId = sampleQuestions.first().subjectId,
                        subjectTitle = sampleQuestions.first().subjectTitle,
                        unitId = sampleQuestions.first().unitId,
                        unitTitle = "Unit 1 Practice",
                        questions = sampleQuestions
                    ),
                    isOfflineMode = true,
                    onSelectMcq = { _, _ -> },
                    onUpdateSubjective = { _, _ -> },
                    onAttachPhoto = { _, _, _, _ -> },
                    onToggleSchemeStep = { _, _ -> },
                    onChangeIndex = {},
                    onSubmit = {},
                    onExit = { exited = true }
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("exit_test_icon_button").performClick()
        composeTestRule.waitForIdle()

        assertFalse("Should not exit immediately without confirmation", exited)
        composeTestRule.onNodeWithText("Leave Active Test?").assertIsDisplayed()

        composeTestRule.onNodeWithTag("confirm_exit_test_button").performClick()
        composeTestRule.waitForIdle()
        assertTrue("Confirming discard should invoke onExit", exited)
    }

    @Test
    fun `question palette and mark for review work inside active test session`() {
        val sampleQuestions = Math1QuestionBank.allQuestions().take(2)
        var toggledQuestionId: String? = null

        composeTestRule.setContent {
            StudyAppTheme {
                InteractiveTestSessionScreen(
                    session = ActiveTestSession(
                        subjectId = sampleQuestions.first().subjectId,
                        subjectTitle = sampleQuestions.first().subjectTitle,
                        unitId = sampleQuestions.first().unitId,
                        unitTitle = "Unit 1 Practice",
                        questions = sampleQuestions
                    ),
                    isOfflineMode = true,
                    onSelectMcq = { _, _ -> },
                    onUpdateSubjective = { _, _ -> },
                    onAttachPhoto = { _, _, _, _ -> },
                    onToggleSchemeStep = { _, _ -> },
                    onToggleFlagReview = { toggledQuestionId = it },
                    onChangeIndex = {},
                    onSubmit = {},
                    onExit = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("flag_review_button").performClick()
        assertEquals(sampleQuestions.first().id, toggledQuestionId)

        composeTestRule.onNodeWithTag("open_question_palette_button").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("question_palette_dialog").assertIsDisplayed()
        composeTestRule.onNodeWithTag("palette_item_1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("palette_item_2").assertIsDisplayed()
    }
}


