package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.data.AiExtractionService
import com.example.data.BundledQuestionBankLoader
import com.example.data.MakinaCompanionService
import com.example.data.MakinaVoicePlayer
import com.example.data.StudyRepository
import com.example.firebase.authStateFlow
import com.example.firebase.configureAppCheck
import com.example.notifications.StudyNotificationScheduler
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.MainStudyScaffold
import com.example.ui.theme.StudyAppTheme
import com.example.viewmodel.StudyViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // STEP 1: Configure Firebase App Check with launch intent BEFORE any UI, ViewModel, or Firebase call
        configureAppCheck(this, intent)

        // Ensure notification channel exists for local study reminders
        StudyNotificationScheduler.ensureChannel(this)

        enableEdgeToEdge()

        setContent {
            StudyAppTheme {
                StudyAppNavigation()
            }
        }
    }
}

@Composable
fun StudyAppNavigation(auth: FirebaseAuth = Firebase.auth) {
    val currentUser by auth.authStateFlow().collectAsStateWithLifecycle(initialValue = auth.currentUser)
    val user = currentUser

    if (user == null) {
        AuthScreen(onAuthSuccess = { /* AuthStateListener reactively transitions to MainStudyScaffold */ })
    } else {
        key(user.uid) {
            val viewModel: StudyViewModel = viewModel(
                key = user.uid,
                factory = viewModelFactory {
                    initializer {
                        val app = checkNotNull(this[APPLICATION_KEY]) {
                            "APPLICATION_KEY missing from CreationExtras"
                        }
                        val db = StudyRepository.createOfflineEnabledFirestore(app)
                        val makinaService = MakinaCompanionService()
                        StudyViewModel(
                            repository = StudyRepository(db),
                            aiService = AiExtractionService(),
                            currentUserId = user.uid,
                            initialDisplayName = user.displayName ?: user.email?.substringBefore("@") ?: "Student",
                            bundledQuestionLoader = BundledQuestionBankLoader(app.applicationContext),
                            makinaService = makinaService,
                            makinaVoicePlayer = MakinaVoicePlayer(app.applicationContext, makinaService)
                        )
                    }
                }
            )
            MainStudyScaffold(viewModel = viewModel)
        }
    }
}
