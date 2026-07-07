package com.smartkids.learning.ui.screens.learning

import androidx.lifecycle.ViewModel
import com.smartkids.learning.data.model.ShapeItem
import com.smartkids.learning.data.model.LearningData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

data class ShapeLearningState(val items: List<ShapeItem> = emptyList(), val currentIndex: Int = 0, val showInfo: Boolean = false)

@HiltViewModel
class ShapeLearningViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ShapeLearningState(items = LearningData.shapeItems))
    val state: StateFlow<ShapeLearningState> = _state.asStateFlow()
    fun next() { val s = _state.value; if (s.currentIndex < s.items.size - 1) _state.value = s.copy(currentIndex = s.currentIndex + 1, showInfo = false) }
    fun prev() { val s = _state.value; if (s.currentIndex > 0) _state.value = s.copy(currentIndex = s.currentIndex - 1, showInfo = false) }
    fun toggleInfo() { _state.value = _state.value.copy(showInfo = !_state.value.showInfo) }
}