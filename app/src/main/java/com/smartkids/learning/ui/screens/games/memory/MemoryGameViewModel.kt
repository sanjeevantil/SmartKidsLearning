package com.smartkids.learning.ui.screens.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.data.model.*
import com.smartkids.learning.domain.repository.LearningRepository
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

data class MemoryCard(val id: String, val label: String, val pairId: Int)

data class MemoryGameState(
    val cards: List<MemoryCard> = emptyList(),
    val flippedCards: List<Int> = emptyList(),
    val matchedPairs: Set<Int> = emptySet(),
    val moves: Int = 0,
    val isComplete: Boolean = false,
    val isLoading: Boolean = true,
    val score: Int = 0,
    val xpEarned: Int = 0,
    val coinsEarned: Int = 0,
    val topicId: String = "",
    val canFlip: Boolean = true
)

@HiltViewModel
class MemoryGameViewModel @Inject constructor(
    private val learningRepository: LearningRepository,
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MemoryGameState())
    val state: StateFlow<MemoryGameState> = _state.asStateFlow()

    fun startGame(topicId: String) {
        viewModelScope.launch {
            val pairs = getPairsForTopic(topicId).take(8)
            val cards = pairs.flatMap { pair ->
                listOf(MemoryCard("${pair.first}_1", pair.first, pair.second),
                       MemoryCard("${pair.first}_2", pair.first, pair.second))
            }.shuffled()
            _state.update { it.copy(cards = cards, topicId = topicId, isLoading = false) }
        }
    }

    fun flipCard(index: Int) {
        val s = _state.value
        if (!s.canFlip || s.flippedCards.contains(index) || s.matchedPairs.contains(s.cards[index].pairId)) return
        if (s.flippedCards.size >= 2) return

        val newFlipped = s.flippedCards + index
        _state.update { it.copy(flippedCards = newFlipped) }

        if (newFlipped.size == 2) {
            val card1 = s.cards[newFlipped[0]]
            val card2 = s.cards[newFlipped[1]]
            if (card1.pairId == card2.pairId) {
                _state.update {
                    it.copy(
                        matchedPairs = it.matchedPairs + card1.pairId,
                        flippedCards = emptyList(),
                        moves = it.moves + 1,
                        score = it.score + 10
                    )
                }
                if (_state.value.matchedPairs.size == s.cards.size / 2) {
                    completeGame()
                }
            } else {
                _state.update { it.copy(canFlip = false, moves = it.moves + 1) }
                viewModelScope.launch {
                    delay(800)
                    _state.update { it.copy(flippedCards = emptyList(), canFlip = true) }
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

    fun resetGame() {
        _state.value = MemoryGameState()
        startGame(_state.value.topicId)
    }

    private fun getPairsForTopic(topicId: String): List<Pair<String, Int>> {
        val items: List<String> = when (topicId) {
            "animals" -> LearningData.animalItems.map { it.name }
            "fruits" -> LearningData.fruitItems.map { it.name }
            "colors" -> LearningData.colorItems.map { it.name }
            "shapes" -> LearningData.shapeItems.map { it.name }
            "numbers" -> LearningData.numberItems.take(10).map { it.word }
            "birds" -> LearningData.birdItems.map { it.name }
            "vehicles" -> LearningData.vehicleItems.map { it.name }
            "flowers" -> LearningData.flowerItems.map { it.name }
            "vegetables" -> LearningData.vegetableItems.map { it.name }
            else -> LearningData.animalItems.map { it.name }
        }
        return items.shuffled().take(8).mapIndexed { index, name -> name to index }
    }
}