package com.example.data

import android.content.Context
import android.util.Log
import com.example.model.TestQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

private const val LOADER_TAG = "BundledQuestionBank"

/**
 * Loads offline-first bundled question banks from JSON files inside the
 * `assets/question_banks` directory. Keeps large subject question banks instant
 * and zero-cost on Firestore writes while merging seamlessly with user-created
 * and AI-extracted Firestore questions.
 */
class BundledQuestionBankLoader(
    private val appContext: Context
) {
    private val lock = Any()

    @Volatile
    private var cachedQuestions: List<TestQuestion>? = null

    suspend fun loadAll(): List<TestQuestion> = withContext(Dispatchers.IO) {
        loadAllBlocking()
    }

    fun loadAllBlocking(): List<TestQuestion> {
        cachedQuestions?.let { return it }
        synchronized(lock) {
            cachedQuestions?.let { return it }
            val loaded = parseAllAssets()
            cachedQuestions = loaded
            return loaded
        }
    }

    private fun parseAllAssets(): List<TestQuestion> {
        val assetManager = appContext.assets
        val files = try {
            assetManager.list(ASSETS_DIR)
                ?.filter { it.endsWith(".json", ignoreCase = true) }
                ?.sorted()
                .orEmpty()
        } catch (e: Exception) {
            Log.w(LOADER_TAG, "Unable to list $ASSETS_DIR assets", e)
            emptyList()
        }

        val result = ArrayList<TestQuestion>(512)
        for (fileName in files) {
            try {
                val rawJson = assetManager.open("$ASSETS_DIR/$fileName")
                    .bufferedReader(Charsets.UTF_8)
                    .use { it.readText() }
                val array = JSONArray(rawJson)
                for (i in 0 until array.length()) {
                    val obj = array.optJSONObject(i) ?: continue
                    result.add(parseQuestion(obj))
                }
            } catch (e: Exception) {
                Log.w(LOADER_TAG, "Failed to parse bundled question bank $fileName", e)
            }
        }
        return result
    }

    private fun parseQuestion(obj: JSONObject): TestQuestion {
        val optionsJson = obj.optJSONArray("options")
        val options = if (optionsJson != null) {
            List(optionsJson.length()) { idx -> optionsJson.optString(idx, "") }
                .filter { it.isNotBlank() }
        } else {
            emptyList()
        }

        val schemeJson = obj.optJSONArray("markingScheme")
        val markingScheme = if (schemeJson != null) {
            List(schemeJson.length()) { idx -> schemeJson.optString(idx, "") }
                .filter { it.isNotBlank() }
        } else {
            emptyList()
        }

        return TestQuestion(
            id = obj.optString("id", ""),
            subjectId = obj.optString("subjectId", ""),
            subjectTitle = obj.optString("subjectTitle", ""),
            unitId = obj.optString("unitId", ""),
            unitTitle = obj.optString("unitTitle", ""),
            topicCode = obj.optString("topicCode", ""),
            topicTitle = obj.optString("topicTitle", ""),
            questionType = obj.optString("questionType", "MCQ"),
            prompt = obj.optString("prompt", ""),
            formulaExpr = obj.optString("formulaExpr", ""),
            diagramKind = obj.optString("diagramKind", "NONE"),
            imageUri = obj.optString("imageUri", ""),
            options = options,
            correctOptionIndex = obj.optLong("correctOptionIndex", -1L),
            maxMarks = obj.optLong("maxMarks", 1L),
            modelAnswer = obj.optString("modelAnswer", ""),
            markingScheme = markingScheme
        )
    }

    companion object {
        const val ASSETS_DIR = "question_banks"
        const val PHYSICS_SUBJECT_ID = "sub_100104"
        const val PHYSICS_UNIT1_ID = "u_100104_1"
        const val AI_SUBJECT_ID = "sub_100105"
        const val AI_UNIT1_ID = "u_100105_1"
        const val UHV_SUBJECT_ID = "sub_100109"
        const val UHV_UNIT1_ID = "u_100109_1"
        const val PHYSICS_LAB_SUBJECT_ID = "sub_100104P"
        const val PHYSICS_LAB_UNIT1_ID = "u_100104P_1"
        const val BEEE_LAB_SUBJECT_ID = "sub_100111P"
        const val BEEE_LAB_UNIT1_ID = "u_100111P_1"
        const val PPS_LAB_SUBJECT_ID = "sub_100112P"
        const val PPS_LAB_UNIT1_ID = "u_100112P_1"

        val LEGACY_STARTER_IDS_REPLACED: Set<String> = setOf(
            "q_phy_u1_1",
            "q_phy_u1_2",
            "q_phy_u4_1",
            "q_ai_u2_1"
        )

        private val BUNDLED_ID_PREFIXES: List<String> = listOf(
            "phys_",
            "ai_",
            "uhv_",
            "phylab_",
            "beeelab_",
            "ppslab_"
        )

        fun isBundledQuestionId(id: String): Boolean =
            BUNDLED_ID_PREFIXES.any { id.startsWith(it) }
    }
}
