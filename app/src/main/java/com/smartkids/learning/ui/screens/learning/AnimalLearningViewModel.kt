package com.smartkids.learning.ui.screens.learning

import androidx.lifecycle.ViewModel
import com.smartkids.learning.data.model.AnimalItem
import com.smartkids.learning.data.model.LearningData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class AnimalLearningState(val items: List<AnimalItem> = emptyList(), val currentIndex: Int = 0, val showDetails: Boolean = false)

@HiltViewModel
class AnimalLearningViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(AnimalLearningState(items = LearningData.animalItems))
    val state: StateFlow<AnimalLearningState> = _state.asStateFlow()
    fun next() { val s = _state.value; if (s.currentIndex < s.items.size - 1) _state.value = s.copy(currentIndex = s.currentIndex + 1, showDetails = false) }
    fun prev() { val s = _state.value; if (s.currentIndex > 0) _state.value = s.copy(currentIndex = s.currentIndex - 1, showDetails = false) }
    fun toggleDetails() { _state.value = _state.value.copy(showDetails = !_state.value.showDetails) }
}