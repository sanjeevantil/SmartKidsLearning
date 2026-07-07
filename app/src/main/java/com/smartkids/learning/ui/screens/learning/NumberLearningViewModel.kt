package com.smartkids.learning.ui.screens.learning

import androidx.lifecycle.ViewModel
import com.smartkids.learning.data.model.NumberItem
import com.smartkids.learning.data.model.LearningData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

data class NumberLearningState(val items: List<NumberItem> = emptyList(), val currentIndex: Int = 0, val showCount: Boolean = false)

@HiltViewModel
class NumberLearningViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(NumberLearningState(items = LearningData.numberItems))
    val state: StateFlow<NumberLearningState> = _state.asStateFlow()
    fun next() { val s = _state.value; if (s.currentIndex < s.items.size - 1) _state.value = s.copy(currentIndex = s.currentIndex + 1, showCount = false) }
    fun prev() { val s = _state.value; if (s.currentIndex > 0) _state.value = s.copy(currentIndex = s.currentIndex - 1, showCount = false) }
    fun toggleCount() { _state.value = _state.value.copy(showCount = !_state.value.showCount) }
}