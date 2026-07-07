package com.smartkids.learning.ui.screens.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.data.model.*
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import com.smartkids.learning.util.HapticFeedback
import com.smartkids.learning.util.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

// ==================== DRAG & DROP ====================
data class DragDropState(val items: List<DragDropItem> = emptyList(), val targets: List<String> = emptyList(), val draggedIndex: Int? = null, val matchedCount: Int = 0, val totalPairs: Int = 0, val score: Int = 0, val isComplete: Boolean = false, val isLoading: Boolean = true, val xpEarned: Int = 0, val coinsEarned: Int = 0, val topicId: String = "")
data class DragDropItem(val text: String, val pairId: String, val isMatched: Boolean = false)

// ==================== SLIDING PUZZLE ====================
data class PuzzleState(val tiles: List<Int> = emptyList(), val emptyIndex: Int = 0, val moves: Int = 0, val isComplete: Boolean = false, val isLoading: Boolean = true, val size: Int = 3, val score: Int = 0, val xpEarned: Int = 0, val coinsEarned: Int = 0, val topicId: String = "")

// ==================== MAZE ====================
data class MazeState(val grid: List<List<Int>> = emptyList(), val playerPos: Pair<Int, Int> = 0 to 0, val goalPos: Pair<Int, Int> = 0 to 0, val moves: Int = 0, val isComplete: Boolean = false, val isLoading: Boolean = true, val gridSize: Int = 7, val score: Int = 0, val xpEarned: Int = 0, val coinsEarned: Int = 0)

// ==================== FIND OBJECT ====================
data class FindObjectState(val grid: List<String> = emptyList(), val targetEmoji: String = "", val targetCount: Int = 0, val foundCount: Int = 0, val score: Int = 0, val isComplete: Boolean = false, val isLoading: Boolean = true, val gridSize: Int = 6, val xpEarned: Int = 0, val coinsEarned: Int = 0, val foundPositions: Set<Int> = emptySet())

// ==================== FRUIT CATCH ====================
data class FruitCatchState(val fallingItems: List<FallingItem> = emptyList(), val basketX: Float = 0.5f, val score: Int = 0, val lives: Int = 3, val isComplete: Boolean = false, val isLoading: Boolean = true, val caughtCount: Int = 0, val missedCount: Int = 0, val xpEarned: Int = 0, val coinsEarned: Int = 0)
data class FallingItem(val id: Int, val emoji: String, val y: Float, val x: Float, val isGood: Boolean = true)

// ==================== WORD BUILDER ====================
data class WordBuilderState(val targetWord: String = "", val availableLetters: List<CharItem> = emptyList(), val builtWord: String = "", val score: Int = 0, val isComplete: Boolean = false, val isLoading: Boolean = true, val round: Int = 0, val totalRounds: Int = 5, val xpEarned: Int = 0, val coinsEarned: Int = 0, val showResult: Boolean = false, val lastCorrect: Boolean = false, val topicId: String = "")
data class CharItem(val char: String, val id: Int, val used: Boolean = false)

// ==================== COLOR MATCH ====================
data class ColorMatchState(val pairs: List<ColorMatchPair> = emptyList(), val selectedColor: Int? = null, val selectedName: Int? = null, val matchedCount: Int = 0, val score: Int = 0, val isComplete: Boolean = false, val isLoading: Boolean = true, val xpEarned: Int = 0, val coinsEarned: Int = 0)
data class ColorMatchPair(val color: Long, val name: String, val id: Int, val colorMatched: Boolean = false, val nameMatched: Boolean = false)

// ==================== BUBBLE POP ====================
data class BubblePopState(val bubbles: List<BubbleItem> = emptyList(), val score: Int = 0, val targetColor: String = "", val correctPops: Int = 0, val wrongPops: Int = 0, val round: Int = 1, val totalRounds: Int = 5, val isComplete: Boolean = false, val isLoading: Boolean = true, val xpEarned: Int = 0, val coinsEarned: Int = 0, val showRoundResult: Boolean = false, val roundCorrect: Boolean = false)
data class BubbleItem(val id: Int, val color: String, val colorHex: Long)

// ==================== SPOT DIFFERENCE ====================
data class SpotDiffState(val leftGrid: List<String> = emptyList(), val rightGrid: List<String> = emptyList(), val diffPosition: Int = -1, val found: Boolean = false, val score: Int = 0, val round: Int = 1, val totalRounds: Int = 5, val isComplete: Boolean = false, val isLoading: Boolean = true, val xpEarned: Int = 0, val coinsEarned: Int = 0, val showRoundResult: Boolean = false)

@HiltViewModel
class ExtraGamesViewModel @Inject constructor(
    private val userPrefs: UserPreferencesRepository,
    private val soundManager: SoundManager,
    private val haptic: HapticFeedback
) : ViewModel() {

    // ========== DRAG & DROP ==========
    private val _dragDrop = MutableStateFlow(DragDropState())
    val dragDrop: StateFlow<DragDropState> = _dragDrop.asStateFlow()

    fun startDragDrop(topicId: String) {
        viewModelScope.launch {
            val pairs = getPairs(topicId).take(6)
            val items = pairs.mapIndexed { i, (left, right) -> DragDropItem(left, right) }.shuffled()
            val targets = pairs.map { it.second }.shuffled()
            _dragDrop.value = DragDropState(items = items, targets = targets, totalPairs = pairs.size, topicId = topicId, isLoading = false)
        }
    }

    fun dragDropMatch(itemIndex: Int, targetIndex: Int) {
        val s = _dragDrop.value
        if (s.items[itemIndex].isMatched) return
        if (s.items[itemIndex].pairId == s.targets[targetIndex]) {
            soundManager.playCorrect(); haptic.success()
            val newItems = s.items.mapIndexed { i, item -> if (i == itemIndex) item.copy(isMatched = true) else item }
            val newMatched = s.matchedCount + 1
            _dragDrop.value = s.copy(items = newItems, matchedCount = newMatched, score = s.score + 15, draggedIndex = null)
            if (newMatched >= s.totalPairs) completeDragDrop()
        } else { soundManager.playWrong(); haptic.error(); _dragDrop.value = s.copy(draggedIndex = null) }
    }

    fun setDraggedIndex(i: Int?) { _dragDrop.value = _dragDrop.value.copy(draggedIndex = i) }

    private fun completeDragDrop() {
        val s = _dragDrop.value
        viewModelScope.launch { userPrefs.addXP(s.score * 2); userPrefs.addCoins(s.score); userPrefs.incrementGamesPlayed(); _dragDrop.value = s.copy(isComplete = true, xpEarned = s.score * 2, coinsEarned = s.score) }
    }

    fun resetDragDrop() { _dragDrop.value = DragDropState(); startDragDrop(_dragDrop.value.topicId) }

    // ========== PUZZLE ==========
    private val _puzzle = MutableStateFlow(PuzzleState())
    val puzzle: StateFlow<PuzzleState> = _puzzle.asStateFlow()

    fun startPuzzle(topicId: String) {
        val size = 3; val total = size * size
        val tiles = (0 until total - 1).toMutableList().apply { add(-1) }.shuffled()
        val emptyIdx = tiles.indexOf(-1)
        _puzzle.value = PuzzleState(tiles = tiles, emptyIndex = emptyIdx, size = size, topicId = topicId, isLoading = false)
    }

    fun moveTile(index: Int) {
        val s = _puzzle.value; val empty = s.emptyIndex
        val row = index / s.size; val col = index % s.size; val eRow = empty / s.size; val eCol = empty % s.size
        if ((kotlin.math.abs(row - eRow) == 1 && col == eCol) || (kotlin.math.abs(col - eCol) == 1 && row == eRow)) {
            soundManager.playClick(); haptic.lightTap()
            val newTiles = s.tiles.toMutableList(); newTiles[empty] = newTiles[index]; newTiles[index] = -1
            val isSolved = newTiles.dropLast(1).mapIndexed { i, v -> v == i }.all { it }
            _puzzle.value = s.copy(tiles = newTiles, emptyIndex = index, moves = s.moves + 1, isComplete = isSolved)
            if (isSolved) { soundManager.playLevelUp(); haptic.celebration(); viewModelScope.launch { val xp = 100; userPrefs.addXP(xp); userPrefs.addCoins(50); userPrefs.incrementGamesPlayed(); _puzzle.value = _puzzle.value.copy(xpEarned = xp, coinsEarned = 50) } }
        }
    }

    fun resetPuzzle() { _puzzle.value = PuzzleState(); startPuzzle(_puzzle.value.topicId) }

    // ========== MAZE ==========
    private val _maze = MutableStateFlow(MazeState())
    val maze: StateFlow<MazeState> = _maze.asStateFlow()

    fun startMaze() {
        val size = 7; val grid = generateMaze(size); val start = 0 to 0; val goal = size - 1 to size - 1
        _maze.value = MazeState(grid = grid, playerPos = start, goalPos = goal, gridSize = size, isLoading = false)
    }

    fun moveMazePlayer(direction: String) {
        val s = _maze.value; var (r, c) = s.playerPos
        when (direction) {
            "up" -> if (r > 0 && s.grid[r - 1][c] == 0) r--
            "down" -> if (r < s.gridSize - 1 && s.grid[r + 1][c] == 0) r++
            "left" -> if (c > 0 && s.grid[r][c - 1] == 0) c--
            "right" -> if (c < s.gridSize - 1 && s.grid[r][c + 1] == 0) c++
        }
        if (r to c != s.playerPos) { soundManager.playClick(); haptic.lightTap() }
        val isGoal = r to c == s.goalPos
        _maze.value = s.copy(playerPos = r to c, moves = s.moves + 1, isComplete = isGoal)
        if (isGoal) { soundManager.playLevelUp(); haptic.celebration(); viewModelScope.launch { val xp = 80; userPrefs.addXP(xp); userPrefs.addCoins(40); userPrefs.incrementGamesPlayed(); _maze.value = _maze.value.copy(xpEarned = xp, coinsEarned = 40) } }
    }

    private fun generateMaze(size: Int): List<List<Int>> {
        val grid = MutableList(size) { MutableList(size) { 0 } }
        for (i in 1 until size - 1) for (j in 1 until size - 1) if (Random.nextFloat() < 0.3f) grid[i][j] = 1
        grid[0][0] = 0; grid[size - 1][size - 1] = 0
        var r = 0; var c = 0; grid[r][c] = 0
        while (r != size - 1 || c != size - 1) {
            val moves = mutableListOf<Pair<Int, Int>>()
            if (r < size - 1 && grid[r + 1][c] == 0) moves.add(r + 1 to c)
            if (c < size - 1 && grid[r][c + 1] == 0) moves.add(r to c + 1)
            if (r > 0 && grid[r - 1][c] == 0) moves.add(r - 1 to c)
            if (c > 0 && grid[r][c - 1] == 0) moves.add(r to c - 1)
            if (moves.isEmpty()) { if (r < size - 1) { grid[r + 1][c] = 0; r++ } else if (c < size - 1) { grid[r][c + 1] = 0; c++ } }
            else { val (nr, nc) = moves.random(); r = nr; c = nc }
        }
        return grid
    }

    fun resetMaze() { _maze.value = MazeState(); startMaze() }

    // ========== FIND OBJECT ==========
    private val _findObj = MutableStateFlow(FindObjectState())
    val findObj: StateFlow<FindObjectState> = _findObj.asStateFlow()

    fun startFindObject(topicId: String) {
        val emojis = getEmojisForTopic(topicId)
        val target = emojis.random()
        val grid = MutableList(36) { emojis.random() }
        val positions = (0..35).shuffled().take(3 + Random.nextInt(3))
        positions.forEach { grid[it] = target }
        _findObj.value = FindObjectState(grid = grid, targetEmoji = target, targetCount = positions.size, gridSize = 6)
    }

    fun tapFindObject(pos: Int) {
        val s = _findObj.value
        if (s.foundPositions.contains(pos) || s.isComplete) return
        if (s.grid[pos] == s.targetEmoji && !s.foundPositions.contains(pos)) {
            soundManager.playPop(); haptic.success()
            val newFound = s.foundPositions + pos; val newCount = newFound.size
            _findObj.value = s.copy(foundPositions = newFound, foundCount = newCount, score = s.score + 20)
            if (newCount >= s.targetCount) { soundManager.playLevelUp(); haptic.celebration(); viewModelScope.launch { val xp = s.score * 2; userPrefs.addXP(xp); userPrefs.addCoins(s.score); userPrefs.incrementGamesPlayed(); _findObj.value = _findObj.value.copy(isComplete = true, xpEarned = xp, coinsEarned = s.score) } }
        } else { soundManager.playWrong(); haptic.error() }
    }

    fun resetFindObject(topicId: String) { _findObj.value = FindObjectState(); startFindObject(topicId) }

    // ========== FRUIT CATCH ==========
    private val _fruitCatch = MutableStateFlow(FruitCatchState())
    val fruitCatch: StateFlow<FruitCatchState> = _fruitCatch.asStateFlow()
    private var fruitCatchJob: kotlinx.coroutines.Job? = null

    fun startFruitCatch() {
        fruitCatchJob?.cancel()
        _fruitCatch.value = FruitCatchState()
        fruitCatchJob = viewModelScope.launch {
            var id = 0
            while (_fruitCatch.value.lives > 0 && !_fruitCatch.value.isComplete) {
                delay(600)
                val s = _fruitCatch.value
                if (s.isComplete) break
                val isGood = Random.nextFloat() > 0.2f
                val newItem = FallingItem(id++, if (isGood) listOf("🍎","🍊","🍋","🍇","🍓","🍑").random() else "💣", 0f, Random.nextFloat() * 0.8f + 0.1f, isGood)
                val moved = s.fallingItems.map { it.copy(y = it.y + 0.08f) }.filter { it.y < 1.0f }
                val missed = moved.filter { it.y >= 0.9f && it.isGood }
                if (missed.isNotEmpty()) { val newLives = s.lives - missed.size; if (newLives <= 0) { _fruitCatch.value = s.copy(fallingItems = moved, lives = 0, missedCount = s.missedCount + missed.size, isComplete = true); break } }
                _fruitCatch.value = s.copy(fallingItems = moved + newItem, missedCount = s.missedCount + missed.size, lives = if (missed.isNotEmpty()) s.lives - missed.size else s.lives)
            }
        }
    }

    fun moveBasket(x: Float) { _fruitCatch.value = _fruitCatch.value.copy(basketX = x.coerceIn(0.05f, 0.95f)) }

    fun catchFruit() {
        val s = _fruitCatch.value; val basketY = 0.85f
        val caught = s.fallingItems.filter { it.y >= basketY - 0.05f && kotlin.math.abs(it.x - s.basketX) < 0.1f }
        if (caught.any { it.isGood }) { soundManager.playPop(); haptic.success() }
        else if (caught.any { !it.isGood }) { soundManager.playWrong(); haptic.error() }
        val newItems = s.fallingItems.filterNot { caught.contains(it) }
        val goodCaught = caught.count { it.isGood }
        val newScore = s.score + goodCaught * 10
        val newCaughtCount = s.caughtCount + goodCaught
        _fruitCatch.value = s.copy(fallingItems = newItems, score = newScore, caughtCount = newCaughtCount)
    }

    fun stopFruitCatch() {
        fruitCatchJob?.cancel()
        val s = _fruitCatch.value
        viewModelScope.launch { userPrefs.addXP(s.score * 2); userPrefs.addCoins(s.score); userPrefs.incrementGamesPlayed(); _fruitCatch.value = s.copy(isComplete = true, xpEarned = s.score * 2, coinsEarned = s.score) }
    }

    fun resetFruitCatch() { fruitCatchJob?.cancel(); _fruitCatch.value = FruitCatchState(); startFruitCatch() }

    // ========== WORD BUILDER ==========
    private val _wordBuilder = MutableStateFlow(WordBuilderState())
    val wordBuilder: StateFlow<WordBuilderState> = _wordBuilder.asStateFlow()

    fun startWordBuilder(topicId: String) {
        val words = getWordsForTopic(topicId).filter { it.length >= 3 && it.length <= 6 }.shuffled()
        if (words.isEmpty()) return
        startWordBuilderRound(words)
    }

    private fun startWordBuilderRound(words: List<String>) {
        if (words.isEmpty()) return
        val word = words.first()
        val shuffled = word.toList().mapIndexed { i, c -> CharItem(c.toString(), i, false) }.shuffled()
        _wordBuilder.value = _wordBuilder.value.copy(targetWord = word, availableLetters = shuffled, builtWord = "", showResult = false)
    }

    fun tapWordBuilderChar(index: Int) {
        val s = _wordBuilder.value
        if (s.availableLetters[index].used || s.showResult) return
        soundManager.playClick(); haptic.lightTap()
        val newLetters = s.availableLetters.mapIndexed { i, c -> if (i == index) c.copy(used = true) else c }
        val newWord = s.builtWord + s.availableLetters[index].char
        _wordBuilder.value = s.copy(availableLetters = newLetters, builtWord = newWord)
        if (newWord.length == s.targetWord.length) {
            val correct = newWord == s.targetWord
            soundManager.playClick()
            _wordBuilder.value = _wordBuilder.value.copy(showResult = true, lastCorrect = correct, score = if (correct) s.score + 20 else s.score)
            viewModelScope.launch {
                delay(800)
                val words = getWordsForTopic(s.topicId).filter { it.length >= 3 && it.length <= 6 }.shuffled()
                val nextRound = s.round + 1
                if (nextRound >= s.totalRounds || words.isEmpty()) {
                    userPrefs.addXP(s.score * 2); userPrefs.addCoins(s.score); userPrefs.incrementGamesPlayed()
                    _wordBuilder.value = _wordBuilder.value.copy(isComplete = true, xpEarned = s.score * 2, coinsEarned = s.score, showResult = false)
                } else {
                    _wordBuilder.value = _wordBuilder.value.copy(round = nextRound, showResult = false)
                    startWordBuilderRound(words)
                }
            }
        }
    }

    fun resetWordBuilderChar() {
        val s = _wordBuilder.value
        if (s.builtWord.isEmpty()) return
        val lastChar = s.builtWord.last()
        val newLetters = s.availableLetters.map { if (it.char == lastChar && it.used) it.copy(used = false) else it }
        _wordBuilder.value = s.copy(availableLetters = newLetters, builtWord = s.builtWord.dropLast(1))
    }

    fun resetWordBuilder(topicId: String) { _wordBuilder.value = WordBuilderState(topicId = topicId); startWordBuilder(topicId) }

    // ========== COLOR MATCH ==========
    private val _colorMatch = MutableStateFlow(ColorMatchState())
    val colorMatch: StateFlow<ColorMatchState> = _colorMatch.asStateFlow()

    fun startColorMatch() {
        val colors = LearningData.colorItems.shuffled().take(6)
        val pairs = colors.mapIndexed { i, c -> ColorMatchPair(android.graphics.Color.parseColor(c.hexCode).toLong(), c.name, i) }
        _colorMatch.value = ColorMatchState(pairs = pairs)
    }

    fun selectColorMatchColor(index: Int) {
        val s = _colorMatch.value
        if (s.pairs[index].colorMatched) return
        soundManager.playClick(); haptic.lightTap()
        val newPairs = s.pairs.mapIndexed { i, p -> if (i == index) p.copy(colorMatched = true) else p.copy(colorMatched = false) }
        val selName = s.pairs.find { it.nameMatched }
        if (selName != null && selName.id == index) {
            val matched = newPairs.mapIndexed { i, p -> if (i == index) p.copy(nameMatched = true) else p }
            val count = matched.count { it.colorMatched && it.nameMatched }
            soundManager.playCorrect(); haptic.success()
            _colorMatch.value = s.copy(pairs = matched, selectedColor = null, selectedName = null, matchedCount = count, score = s.score + 15)
            if (count >= s.pairs.size) { soundManager.playLevelUp(); haptic.celebration(); viewModelScope.launch { val xp = s.score * 2; userPrefs.addXP(xp); userPrefs.addCoins(s.score); userPrefs.incrementGamesPlayed(); _colorMatch.value = _colorMatch.value.copy(isComplete = true, xpEarned = xp, coinsEarned = s.score) } }
        } else _colorMatch.value = s.copy(pairs = newPairs, selectedColor = index, selectedName = null)
    }

    fun selectColorMatchName(index: Int) {
        val s = _colorMatch.value
        if (s.pairs[index].nameMatched) return
        soundManager.playClick(); haptic.lightTap()
        val newPairs = s.pairs.mapIndexed { i, p -> if (i == index) p.copy(nameMatched = true) else p.copy(colorMatched = false) }
        val selColor = s.pairs.find { it.colorMatched }
        if (selColor != null && selColor.id == index) {
            val matched = newPairs.mapIndexed { i, p -> if (i == index) p.copy(colorMatched = true) else p }
            val count = matched.count { it.colorMatched && it.nameMatched }
            soundManager.playCorrect(); haptic.success()
            _colorMatch.value = s.copy(pairs = matched, selectedColor = null, selectedName = null, matchedCount = count, score = s.score + 15)
            if (count >= s.pairs.size) { soundManager.playLevelUp(); haptic.celebration(); viewModelScope.launch { val xp = s.score * 2; userPrefs.addXP(xp); userPrefs.addCoins(s.score); userPrefs.incrementGamesPlayed(); _colorMatch.value = _colorMatch.value.copy(isComplete = true, xpEarned = xp, coinsEarned = s.score) } }
        } else _colorMatch.value = s.copy(pairs = newPairs, selectedColor = null, selectedName = index)
    }

    fun resetColorMatch() { _colorMatch.value = ColorMatchState(); startColorMatch() }

    // ========== BUBBLE POP ==========
    private val _bubblePop = MutableStateFlow(BubblePopState())
    val bubblePop: StateFlow<BubblePopState> = _bubblePop.asStateFlow()

    fun startBubblePop() { startBubblePopRound() }

    private fun startBubblePopRound() {
        val s = _bubblePop.value
        val colors = LearningData.colorItems.shuffled().take(4)
        val target = colors.random()
        val bubbles = colors.flatMap { c -> List(2) { BubbleItem(it.hashCode() + Random.nextInt(), c.name, android.graphics.Color.parseColor(c.hexCode).toLong()) } }.shuffled()
        _bubblePop.value = s.copy(bubbles = bubbles, targetColor = target.name, showRoundResult = false)
    }

    fun popBubble(index: Int) {
        val s = _bubblePop.value; if (s.showRoundResult) return
        val bubble = s.bubbles[index]
        if (bubble.color == s.targetColor) { soundManager.playPop(); haptic.success(); _bubblePop.value = s.copy(bubbles = s.bubbles.filter { it.id != bubble.id }, correctPops = s.correctPops + 1, score = s.score + 10) }
        else { soundManager.playWrong(); haptic.error(); _bubblePop.value = s.copy(bubbles = s.bubbles.filter { it.id != bubble.id }, wrongPops = s.wrongPops + 1) }
        val remaining = _bubblePop.value.bubbles.count { it.color == s.targetColor }
        if (remaining == 0 || _bubblePop.value.correctPops >= 1) {
            _bubblePop.value = _bubblePop.value.copy(showRoundResult = true, roundCorrect = _bubblePop.value.correctPops >= 1)
            viewModelScope.launch {
                delay(1000)
                val nextRound = s.round + 1
                if (nextRound > s.totalRounds) { val xp = s.score * 2; userPrefs.addXP(xp); userPrefs.addCoins(s.score); userPrefs.incrementGamesPlayed(); _bubblePop.value = _bubblePop.value.copy(isComplete = true, round = nextRound, xpEarned = xp, coinsEarned = s.score, showRoundResult = false) }
                else { _bubblePop.value = _bubblePop.value.copy(round = nextRound, showRoundResult = false); startBubblePopRound() }
            }
        }
    }

    fun resetBubblePop() { _bubblePop.value = BubblePopState(); startBubblePop() }

    // ========== SPOT DIFFERENCE ==========
    private val _spotDiff = MutableStateFlow(SpotDiffState())
    val spotDiff: StateFlow<SpotDiffState> = _spotDiff.asStateFlow()

    fun startSpotDiff() { startSpotDiffRound() }

    private fun startSpotDiffRound() {
        val s = _spotDiff.value
        val emojis = listOf("🐶","🐱","🐰","🦊","🐻","🐼","🐨","🦁","🐸","🐵")
        val base = MutableList(9) { emojis.random() }
        val diffPos = Random.nextInt(9)
        val diffEmoji = (emojis - base[diffPos]).random()
        val right = base.toMutableList(); right[diffPos] = diffEmoji
        _spotDiff.value = s.copy(leftGrid = base, rightGrid = right, diffPosition = diffPos, found = false, showRoundResult = false)
    }

    fun tapSpotDiff(grid: String, pos: Int) {
        val s = _spotDiff.value; if (s.found || s.showRoundResult) return
        if (grid == "right" && pos == s.diffPosition) {
            soundManager.playCorrect(); haptic.success()
            _spotDiff.value = s.copy(found = true, score = s.score + 25, showRoundResult = true, roundCorrect = true)
            viewModelScope.launch {
                delay(1000)
                val nextRound = s.round + 1
                if (nextRound > s.totalRounds) { val xp = s.score * 2; userPrefs.addXP(xp); userPrefs.addCoins(s.score); userPrefs.incrementGamesPlayed(); _spotDiff.value = _spotDiff.value.copy(isComplete = true, xpEarned = xp, coinsEarned = s.score, showRoundResult = false) }
                else { _spotDiff.value = _spotDiff.value.copy(round = nextRound, showRoundResult = false); startSpotDiffRound() }
            }
        } else { soundManager.playWrong(); haptic.error() }
    }

    fun resetSpotDiff() { _spotDiff.value = SpotDiffState(); startSpotDiff() }

    // ========== HELPERS ==========
    private fun getPairs(topicId: String): List<Pair<String, String>> = when (topicId) {
        "animals" -> LearningData.animalItems.take(6).map { it.name to it.sound }
        "fruits" -> LearningData.fruitItems.take(6).map { it.name to it.color }
        "colors" -> LearningData.colorItems.take(6).map { it.name to it.exampleObject }
        "shapes" -> LearningData.shapeItems.take(6).map { it.name to "${it.sides} sides" }
        "vegetables" -> LearningData.vegetableItems.take(6).map { it.name to it.benefit }
        "vehicles" -> LearningData.vehicleItems.take(6).map { it.name to it.usage }
        "birds" -> LearningData.birdItems.take(6).map { it.name to if (it.canFly) "Can fly" else "Cannot fly" }
        "body_parts" -> LearningData.bodyPartItems.take(6).map { it.name to it.function }
        "flowers" -> LearningData.flowerItems.take(6).map { it.name to it.season }
        "opposites" -> LearningData.oppositeItems.take(6).map { it.word1 to it.word2 }
        else -> LearningData.animalItems.take(6).map { it.name to it.habitat }
    }

    private fun getEmojisForTopic(topicId: String): List<String> = when (topicId) {
        "fruits" -> listOf("🍎","🍊","🍋","🍇","🍓","🍑","🥝","🍌","🍉","🫐")
        "animals" -> listOf("🐶","🐱","🐰","🦊","🐻","🐼","🐨","🦁","🐸","🐵")
        "shapes" -> listOf("⭕","⬜","🔺","💎","⭐","❤️","🔶","🔷")
        "colors" -> listOf("🔴","🔵","🟢","🟡","🟣","🟠","🩷","🟤")
        else -> listOf("🍎","🍊","🍋","🍇","🍓","🐶","🐱","🐰","🦊","⭐")
    }

    private fun getWordsForTopic(topicId: String): List<String> = when (topicId) {
        "animals" -> LearningData.animalItems.map { it.name.lowercase() }.filter { it.length in 3..6 }
        "fruits" -> LearningData.fruitItems.map { it.name.lowercase() }.filter { it.length in 3..6 }
        "colors" -> LearningData.colorItems.map { it.name.lowercase() }
        "shapes" -> LearningData.shapeItems.map { it.name.lowercase() }.filter { it.length in 3..6 }
        "vegetables" -> LearningData.vegetableItems.map { it.name.lowercase() }.filter { it.length in 3..8 }
        "birds" -> LearningData.birdItems.map { it.name.lowercase() }.filter { it.length in 3..6 }
        "body_parts" -> listOf("head","eyes","ears","nose","mouth","hands","legs","feet","arms","knee")
        "vehicles" -> listOf("car","bus","bike","train","boat","truck","ship","plane")
        "flowers" -> listOf("rose","lily","lotus","tulip","daisy","iris","poppy","orchid")
        else -> listOf("cat","dog","sun","moon","star","tree","fish","bird","red","blue","green","ball")
    }
}