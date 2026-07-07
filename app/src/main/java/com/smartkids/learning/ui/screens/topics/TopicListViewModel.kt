package com.smartkids.learning.ui.screens.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.domain.model.Topic
import com.smartkids.learning.domain.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicListViewModel @Inject constructor(
    private val topicRepository: TopicRepository
) : ViewModel() {

    private val _topics = MutableStateFlow<List<Topic>>(emptyList())
    val topics: StateFlow<List<Topic>> = _topics.asStateFlow()

    private val _categoryName = MutableStateFlow("")
    val categoryName: StateFlow<String> = _categoryName.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadTopics(categoryId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            topicRepository.getTopicsByCategory(categoryId).collect { list ->
                _topics.value = list
                if (list.isNotEmpty()) _categoryName.value = list.first().categoryName
                _isLoading.value = false
            }
        }
    }
}