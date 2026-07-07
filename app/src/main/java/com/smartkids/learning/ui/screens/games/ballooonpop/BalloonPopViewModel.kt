package com.smartkids.learning.ui.screens.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.data.model.*
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BalloonItem(val id: Int, val label: String, val isCorrect: Boolean, val offsetX: Float, val offsetY: Float, val color: Long)

data class BalloonPopState(
    val balloons: List<BalloonItem> = emptyList(),
    val score: Int = 0,
    val targetLabel: String = "",
    val totalPopped: Int = 0,
    val correctPopped: Int = 0,
    val round: Int = 1,
    val totalRounds: Int = 5,
    val isComplete: Boolean = false,
    val isLoading: Boolean = true,
    val xpEarned: Int = 0,
    val coinsEarned: Int = 0,
    val showRoundResult: Boolean = false,
    val roundCorrect: Int = 0,
    val roundWrong: Int = 0,
    val topicId: String = ""
)

@HiltViewModel
class BalloonPopViewModel @Inject constructor(
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BalloonPopState())
    val state: StateFlow<BalloonPopState> = _state.asStateFlow()

    private var items: List<String> = emptyList()
    private var roundCorrectCount = 0
    private var roundWrongCount = 0

    fun startGame(topicId: String) {
        items = getItemsForTopic(topicId).shuffled()
        _state.update { it.copy(topicId = topicId, isLoading = false) }
        startRound()
    }

    private fun startRound() {
        if (items.isEmpty()) return
        val target = items.random()
        val others = items.filter { it != target }.shuffled().take(7)
        val balloonItems = (listOf(target) + others).shuffled().mapIndexed { index, label ->
            val colors = listOf(0xFFFF6B35, 0xFF00B4D8, 0xFF7B2FF7, 0xFF06D6A0, 0xFFFFD166, 0xFFFF6B9D, 0xFFEF476F, 0xFFFF9F1C)
            BalloonItem(
                id = index,
                label = label,
                isCorrect = label == target,
                offsetX = (index % 4) * 0.25f + 0.05f,
                offsetY = (index / 4) * 0.45f + 0.1f,
                color = colors[index % colors.size]
            )
        }
        roundCorrectCount = 0
        roundWrongCount = 0
        _state.update {
            it.copy(
                balloons = balloonItems,
                targetLabel = target,
                showRoundResult = false,
                round = it.round
            )
        }
    }

    fun popBalloon(balloon: BalloonItem) {
        val s = _state.value
        if (s.showRoundResult) return
        val newBalloons = s.balloons.filter { it.id != balloon.id }
        if (balloon.isCorrect) {
            roundCorrectCount++
            _state.update { it.copy(balloons = newBalloons, score = it.score + 10, correctPopped = it.correctPopped + 1) }
        } else {
            roundWrongCount++
            _state.update { it.copy(balloons = newBalloons, wrongCount = it.wrongCount + 1) }
        }
        _state.update { it.copy(totalPopped = it.totalPopped + 1) }
        if (newBalloons.isEmpty() || roundCorrectCount >= 1) {
            _state.update { it.copy(showRoundResult = true, roundCorrect = roundCorrectCount, roundWrong = roundWrongCount) }
            viewModelScope.launch {
                delay(1500)
                if (s.round < s.totalRounds) {
                    _state.update { it.copy(round = it.round + 1) }
                    startRound()
                } else {
                    completeGame()
                }
            }
        }
    }

    private fun completeGame() {
        val s = _state.value
        val xp = s.correctPopped * 15
        val coins = s.correctPopped * 8
        viewModelScope.launch {
            userPrefs.addXP(xp)
            userPrefs.addCoins(coins)
            userPrefs.incrementGamesPlayed()
            _state.update { it.copy(isComplete = true, xpEarned = xp, coinsEarned = coins) }
        }
    }

    fun resetGame() {
        _state.value = BalloonPopState()
        startGame(_state.value.topicId)
    }

    private fun getItemsForTopic(topicId: String): List<String> = when (topicId) {
        "animals" -> LearningData.animalItems.map { it.name }
        "fruits" -> LearningData.fruitItems.map { it.name }
        "colors" -> LearningData.colorItems.map { it.name }
        "shapes" -> LearningData.shapeItems.map { it.name }
        "numbers" -> LearningData.numberItems.take(10).map { it.word }
        "birds" -> LearningData.birdItems.map { it.name }
        "vegetables" -> LearningData.vegetableItems.map { it.name }
        "vehicles" -> LearningData.vehicleItems.map { it.name }
        else -> LearningData.animalItems.map { it.name }
    }
}