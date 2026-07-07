package com.smartkids.learning.ui.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.data.quiz.QuizQuestionGenerator
import com.smartkids.learning.domain.model.QuizQuestion
import com.smartkids.learning.domain.repository.LearningRepository
import com.smartkids.learning.domain.repository.MistakeTrackingRepository
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import com.smartkids.learning.domain.usecase.CheckAchievementsUseCase
import com.smartkids.learning.domain.usecase.GetAdaptiveDifficultyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String? = null,
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val isComplete: Boolean = false,
    val isLoading: Boolean = true,
    val topicId: String = "",
    val topicName: String = "",
    val difficulty: Int = 1,
    val xpEarned: Int = 0,
    val coinsEarned: Int = 0,
    val isNewBestScore: Boolean = false,
    val showFeedback: Boolean = false
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionGenerator: QuizQuestionGenerator,
    private val learningRepository: LearningRepository,
    private val mistakeTrackingRepository: MistakeTrackingRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val checkAchievementsUseCase: CheckAchievementsUseCase,
    private val getAdaptiveDifficultyUseCase: GetAdaptiveDifficultyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val startTime = System.currentTimeMillis()

    fun startQuiz(topicId: String, difficulty: Int) {
        viewModelScope.launch {
            val profile = userPreferencesRepository.userProfile.first()
            val adaptedDifficulty = getAdaptiveDifficultyUseCase(topicId, profile.childAge)
            val finalDiff = if (difficulty > 1) difficulty else adaptedDifficulty
            val questionCount = when (finalDiff) {
                1 -> 5; 2 -> 8; 3 -> 10; else -> 12
            }
            val questions = questionGenerator.generateQuestionsForTopic(topicId, questionCount, finalDiff)
            _uiState.update {
                it.copy(
                    questions = questions,
                    topicId = topicId,
                    topicName = topicId.replace("_", " ").replaceFirstChar { c -> c.uppercase() },
                    difficulty = finalDiff,
                    isLoading = false
                )
            }
        }
    }

    fun selectAnswer(answer: String) {
        val state = _uiState.value
        if (state.isAnswered || state.questions.isEmpty()) return

        val currentQuestion = state.questions[state.currentIndex]
        val isCorrect = answer == currentQuestion.correctAnswer

        _uiState.update {
            it.copy(
                selectedAnswer = answer,
                isAnswered = true,
                isCorrect = isCorrect,
                showFeedback = true,
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
                wrongCount = if (!isCorrect) it.wrongCount + 1 else it.wrongCount,
                score = if (isCorrect) it.score + (10 * it.difficulty) else it.score
            )
        }

        viewModelScope.launch {
            userPreferencesRepository.recordAnswer(isCorrect)
            if (!isCorrect) {
                mistakeTrackingRepository.recordMistake(
                    state.topicId, currentQuestion.questionText, currentQuestion.correctAnswer, answer
                )
            } else {
                mistakeTrackingRepository.markAsCorrectAfterMistake(currentQuestion.questionText, currentQuestion.correctAnswer)
            }
            delay(1200)
            _uiState.update { it.copy(showFeedback = false) }
            if (state.currentIndex < state.questions.size - 1) {
                _uiState.update { it.copy(currentIndex = it.currentIndex + 1, selectedAnswer = null, isAnswered = false) }
            } else {
                completeQuiz()
            }
        }
    }

    private fun completeQuiz() {
        val state = _uiState.value
        val timeSpent = (System.currentTimeMillis() - startTime) / 1000
        val xpEarned = state.correctCount * 10 * state.difficulty
        val coinsEarned = state.correctCount * 5

        viewModelScope.launch {
            val progress = learningRepository.updateProgressAfterSession(
                topicId = state.topicId,
                topicName = state.topicName,
                categoryId = "general",
                correct = state.correctCount,
                wrong = state.wrongCount,
                timeSpent = timeSpent,
                difficulty = state.difficulty
            )
            val isNewBest = state.score >= progress.bestScore
            userPreferencesRepository.addTimeSpent(timeSpent)
            userPreferencesRepository.incrementGamesPlayed()
            checkAchievementsUseCase()

            _uiState.update {
                it.copy(
                    isComplete = true,
                    xpEarned = xpEarned,
                    coinsEarned = coinsEarned,
                    isNewBestScore = isNewBest
                )
            }
        }
    }

    fun resetQuiz() {
        _uiState.value = QuizUiState()
        startTime
    }
}