package com.smartkids.learning.ui.screens.learning

import androidx.lifecycle.ViewModel
import com.smartkids.learning.data.model.ABCItem
import com.smartkids.learning.data.model.LearningData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

data class ABCLearningState(val items: List<ABCItem> = emptyList(), val currentIndex: Int = 0, val showWord: Boolean = false)

@HiltViewModel
class ABCLearningViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ABCLearningState(items = LearningData.abcItems))
    val state: StateFlow<ABCLearningState> = _state.asStateFlow()

    fun nextLetter() {
        val s = _state.value
        if (s.currentIndex < s.items.size - 1) {
            _state.value = s.copy(currentIndex = s.currentIndex + 1, showWord = false)
        }
    }

    fun prevLetter() {
        val s = _state.value
        if (s.currentIndex > 0) {
            _state.value = s.copy(currentIndex = s.currentIndex - 1, showWord = false)
        }
    }

    fun toggleWord() { _state.value = _state.value.copy(showWord = !_state.value.showWord) }
}