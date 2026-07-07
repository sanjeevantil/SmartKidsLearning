package com.smartkids.learning.ui.screens.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.domain.model.LearningProgress
import com.smartkids.learning.domain.model.UserProfile
import com.smartkids.learning.domain.repository.LearningRepository
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

data class ParentDashboardState(
    val profile: UserProfile = UserProfile(),
    val weakTopics: List<LearningProgress> = emptyList(),
    val strongTopics: List<LearningProgress> = emptyList(),
    val completedTopics: Int = 0,
    val averageScore: Float = 0f,
    val totalTime: Long = 0L,
    val isLoading: Boolean = true
)

@HiltViewModel
class ParentDashboardViewModel @Inject constructor(
    private val userPrefs: UserPreferencesRepository,
    private val learningRepo: LearningRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ParentDashboardState())
    val state: StateFlow<ParentDashboardState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val profile = userPrefs.userProfile.first()
            val weak = learningRepo.getWeakTopics().first()
            val strong = learningRepo.getStrongTopics().first()
            val completed = learningRepo.getCompletedTopicsCount().first()
            val avg = learningRepo.getAverageScore().first() ?: 0f
            val time = learningRepo.getTotalTimeSpent().first() ?: 0L
            _state.value = ParentDashboardState(profile, weak, strong, completed, avg, time, false)
        }
    }
}