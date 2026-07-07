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
import kotlinx.coroutines.flow.asStateFlow

data class MatchPair(val left: String, val right: String, val id: Int)
data class MatchItem(val text: String, val pairId: Int, val side: String, val isMatched: Boolean = false, val isSelected: Boolean = false)

data class MatchingGameState(
    val leftItems: List<MatchItem> = emptyList(),
    val rightItems: List<MatchItem> = emptyList(),
    val selectedLeft: Int? = null,
    val selectedRight: Int? = null,
    val matchedCount: Int = 0,
    val totalPairs: Int = 0,
    val moves: Int = 0,
    val isComplete: Boolean = false,
    val isLoading: Boolean = true,
    val score: Int = 0,
    val xpEarned: Int = 0,
    val coinsEarned: Int = 0,
    val showFeedback: Boolean = false,
    val feedbackCorrect: Boolean = false,
    val topicId: String = ""
)

@HiltViewModel
class MatchingGameViewModel @Inject constructor(
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MatchingGameState())
    val state: StateFlow<MatchingGameState> = _state.asStateFlow()

    fun startGame(topicId: String) {
        viewModelScope.launch {
            val pairs = getPairsForTopic(topicId).take(6)
            val left = pairs.mapIndexed { i, p -> MatchItem(p.left, i, "left") }.shuffled()
            val right = pairs.mapIndexed { i, p -> MatchItem(p.right, i, "right") }.shuffled()
            _state.update { it.copy(leftItems = left, rightItems = right, totalPairs = pairs.size, topicId = topicId, isLoading = false) }
        }
    }

    fun selectLeft(index: Int) {
        val s = _state.value
        if (s.leftItems[index].isMatched || s.showFeedback) return
        _state.update {
            it.copy(
                selectedLeft = index,
                leftItems = it.leftItems.mapIndexed { i, item -> if (i == index) item.copy(isSelected = true) else item.copy(isSelected = false) }
            )
        }
        tryMatch()
    }

    fun selectRight(index: Int) {
        val s = _state.value
        if (s.rightItems[index].isMatched || s.showFeedback) return
        _state.update {
            it.copy(
                selectedRight = index,
                rightItems = it.rightItems.mapIndexed { i, item -> if (i == index) item.copy(isSelected = true) else item.copy(isSelected = false) }
            )
        }
        tryMatch()
    }

    private fun tryMatch() {
        val s = _state.value
        if (s.selectedLeft == null || s.selectedRight == null) return
        val leftItem = s.leftItems[s.selectedLeft!!]
        val rightItem = s.rightItems[s.selectedRight!!]
        val isMatch = leftItem.pairId == rightItem.pairId

        _state.update { it.copy(showFeedback = true, feedbackCorrect = isMatch, moves = it.moves + 1) }

        viewModelScope.launch {
            delay(600)
            if (isMatch) {
                val newMatched = s.matchedCount + 1
                _state.update {
                    it.copy(
                        leftItems = it.leftItems.map { item -> if (item.pairId == leftItem.pairId) item.copy(isMatched = true, isSelected = false) else item.copy(isSelected = false) },
                        rightItems = it.rightItems.map { item -> if (item.pairId == rightItem.pairId) item.copy(isMatched = true, isSelected = false) else item.copy(isSelected = false) },
                        matchedCount = newMatched,
                        selectedLeft = null,
                        selectedRight = null,
                        showFeedback = false,
                        score = it.score + 15
                    )
                }
                if (newMatched >= s.totalPairs) completeGame()
            } else {
                _state.update {
                    it.copy(
                        leftItems = it.leftItems.map { item -> item.copy(isSelected = false) },
                        rightItems = it.rightItems.map { item -> item.copy(isSelected = false) },
                        selectedLeft = null,
                        selectedRight = null,
                        showFeedback = false
                    )
                }
            }
        }
    }

    private fun completeGame() {
        val s = _state.value
        val xp = s.score * 2
        val coins = s.score
        viewModelScope.launch {
            userPrefs.addXP(xp)
            userPrefs.addCoins(coins)
            userPrefs.incrementGamesPlayed()
            _state.update { it.copy(isComplete = true, xpEarned = xp, coinsEarned = coins) }
        }
    }

    fun resetGame() { _state.value = MatchingGameState(); startGame(_state.value.topicId) }

    private fun getPairsForTopic(topicId: String): List<MatchPair> = when (topicId) {
        "animals" -> LearningData.animalItems.take(6).mapIndexed { i, a -> MatchPair(a.name, a.sound, i) }
        "colors" -> LearningData.colorItems.take(6).mapIndexed { i, c -> MatchPair(c.name, c.exampleObject, i) }
        "fruits" -> LearningData.fruitItems.take(6).mapIndexed { i, f -> MatchPair(f.name, f.color, i) }
        "opposites" -> LearningData.oppositeItems.take(6).mapIndexed { i, o -> MatchPair(o.word1, o.word2, i) }
        "vehicles" -> LearningData.vehicleItems.take(6).mapIndexed { i, v -> MatchPair(v.name, v.usage, i) }
        "shapes" -> LearningData.shapeItems.take(6).mapIndexed { i, s -> MatchPair(s.name, "${s.sides} sides", i) }
        else -> LearningData.animalItems.take(6).mapIndexed { i, a -> MatchPair(a.name, a.habitat, i) }
    }
}