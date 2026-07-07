package com.smartkids.learning.ui.screens.learning

import androidx.lifecycle.ViewModel
import com.smartkids.learning.data.model.ColorItem
import com.smartkids.learning.data.model.LearningData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

data class ColorLearningState(val items: List<ColorItem> = emptyList(), val currentIndex: Int = 0, val showExample: Boolean = false)

@HiltViewModel
class ColorLearningViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ColorLearningState(items = LearningData.colorItems))
    val state: StateFlow<ColorLearningState> = _state.asStateFlow()
    fun next() { val s = _state.value; if (s.currentIndex < s.items.size - 1) _state.value = s.copy(currentIndex = s.currentIndex + 1, showExample = false) }
    fun prev() { val s = _state.value; if (s.currentIndex > 0) _state.value = s.copy(currentIndex = s.currentIndex - 1, showExample = false) }
    fun toggleExample() { _state.value = _state.value.copy(showExample = !_state.value.showExample) }
}