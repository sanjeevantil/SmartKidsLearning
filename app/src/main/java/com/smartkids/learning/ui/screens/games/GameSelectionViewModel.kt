package com.smartkids.learning.ui.screens.games

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class GameType(val id: String, val name: String, val description: String, val icon: String, val color: Long, val topicOptions: List<String>)

@HiltViewModel
class GameSelectionViewModel @Inject constructor() : ViewModel() {
    private val _games = MutableStateFlow(
    listOf(
        GameType("memory", "Memory Match", "Flip cards and find matching pairs", "memory", 0xFF7B2FF7, listOf("animals","fruits","colors","shapes","numbers","birds","vehicles","flowers","vegetables")),
        GameType("balloon", "Balloon Pop", "Pop the correct balloon", "balloon", 0xFFEF476F, listOf("animals","fruits","colors","shapes","numbers","birds","vegetables","vehicles")),
        GameType("matching", "Matching Game", "Match related items", "match", 0xFF00B4D8, listOf("animals","colors","fruits","opposites","vehicles","shapes")),
        GameType("rapid", "Rapid Quiz", "Answer as many as you can!", "rapid", 0xFFFF9F1C, listOf("animals","fruits","general_knowledge","colors","shapes","numbers","addition","vegetables")),
        GameType("timed", "Timed Challenge", "60 seconds of questions", "timed", 0xFFFF6B35, listOf("addition","subtraction","multiplication","numbers","general_knowledge","animals","fruits","shapes")),
        GameType("dragdrop", "Drag & Drop", "Drag items to correct targets", "drag_drop", 0xFF06D6A0, listOf("animals","fruits","colors","shapes","vegetables","birds","vehicles","body_parts","opposites","flowers")),
        GameType("puzzle", "Sliding Puzzle", "Arrange tiles in order", "puzzle", 0xFFFFD166, listOf("puzzle")),
        GameType("maze", "Maze Runner", "Navigate through the maze", "maze", 0xFF2D3436, listOf("maze")),
        GameType("findobject", "Find the Object", "Find all matching items in grid", "find_object", 0xFFFF6B9D, listOf("fruits","animals","shapes","colors")),
        GameType("fruitcatch", "Fruit Catch", "Catch falling fruits, avoid bombs!", "fruit_catch", 0xFF06D6A0, listOf("fruitcatch")),
        GameType("wordbuilder", "Word Builder", "Build words from letters", "word_builder", 0xFF7B2FF7, listOf("animals","fruits","colors","shapes","vegetables","birds","body_parts","vehicles","flowers")),
        GameType("colormatch", "Color Match", "Match colors to their names", "color_match", 0xFFFF6B35, listOf("colormatch")),
        GameType("bubblepopgame", "Bubble Pop", "Pop bubbles of the right color", "bubble_pop", 0xFF00B4D8, listOf("bubblepopgame")),
        GameType("spotdiff", "Spot Difference", "Find the different emoji", "spot_difference", 0xFFEF476F, listOf("spotdiff")),
        GameType("tracing", "Letter Tracing", "Trace letters with your finger", "draw", 0xFFFF6B35, listOf("tracing"))
    )
)
    val games: StateFlow<List<GameType>> = _games
}