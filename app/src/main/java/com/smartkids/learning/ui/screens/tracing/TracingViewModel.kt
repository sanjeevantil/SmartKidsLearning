package com.smartkids.learning.ui.screens.tracing

import androidx.lifecycle.ViewModel
import com.smartkids.learning.data.model.LearningData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

data class TracingState(val currentIndex: Int = 0, val isComplete: Boolean = false, val showGuide: Boolean = true)

@HiltViewModel
class TracingViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TracingState())
    val state: StateFlow<TracingState> = _state.asStateFlow()
    val letters = LearningData.abcItems

    fun next() { val s = _state.value; if (s.currentIndex < letters.size - 1) _state.value = s.copy(currentIndex = s.currentIndex + 1, isComplete = false, showGuide = true) }
    fun prev() { val s = _state.value; if (s.currentIndex > 0) _state.value = s.copy(currentIndex = s.currentIndex - 1, isComplete = false, showGuide = true) }
    fun markComplete() { _state.value = _state.value.copy(isComplete = true) }
    fun toggleGuide() { _state.value = _state.value.copy(showGuide = !_state.value.showGuide) }
}