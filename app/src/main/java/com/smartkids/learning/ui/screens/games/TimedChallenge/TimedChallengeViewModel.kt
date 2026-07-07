package com.smartkids.learning.ui.screens.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.data.quiz.QuizQuestionGenerator
import com.smartkids.learning.domain.model.QuizQuestion
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

data class TimedChallengeState(
    val question: QuizQuestion? = null,
    val score: Int = 0,
    val correctCount: Int = 0,
    val totalAnswered: Int = 0,
    val timeLeft: Int = 60,
    val isRunning: Boolean = false,
    val isComplete: Boolean = false,
    val isLoading: Boolean = true,
    val xpEarned: Int = 0,
    val coinsEarned: Int = 0,
    val topicId: String = "",
    val showFeedback: Boolean = false,
    val lastCorrect: Boolean = false
)

@HiltViewModel
class TimedChallengeViewModel @Inject constructor(
    private val questionGenerator: QuizQuestionGenerator,
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TimedChallengeState())
    val state: StateFlow<TimedChallengeState> = _state.asStateFlow()
    private var questionPool: List<QuizQuestion> = emptyList()

    fun startGame(topicId: String) {
        questionPool = questionGenerator.generateQuestionsForTopic(topicId, 30, 3).shuffled()
        val firstQ = questionPool.firstOrNull()
        _state.update { it.copy(question = firstQ, topicId = topicId, isLoading = false, isRunning = true, timeLeft = 60) }
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_state.value.isRunning && _state.value.timeLeft > 0) {
                delay(1000)
                if (_state.value.isRunning) {
                    _state.update { it.copy(timeLeft = it.timeLeft - 1) }
                    if (_state.value.timeLeft <= 0) completeGame()
                }
            }
        }
    }

    fun answer(answer: String) {
        val s = _state.value
        if (!s.isRunning || s.showFeedback || s.question == null) return
        val correct = answer == s.question.correctAnswer
        _state.update {
            it.copy(
                showFeedback = true,
                lastCorrect = correct,
                correctCount = if (correct) it.correctCount + 1 else it.correctCount,
                score = if (correct) it.score + 20 else it.score,
                totalAnswered = it.totalAnswered + 1
            )
        }
        viewModelScope.launch {
            userPrefs.recordAnswer(correct)
            delay(500)
            val nextQ = questionPool.random()
            _state.update { it.copy(question = nextQ, showFeedback = false) }
        }
    }

    private fun completeGame() {
        val s = _state.value
        val xp = s.correctCount * 20
        val coins = s.correctCount * 10
        viewModelScope.launch {
            userPrefs.addXP(xp); userPrefs.addCoins(coins); userPrefs.incrementGamesPlayed()
            _state.update { it.copy(isComplete = true, isRunning = false, xpEarned = xp, coinsEarned = coins) }
        }
    }

    fun resetGame() { _state.value = TimedChallengeState(); startGame(_state.value.topicId) }
}