package com.smartkids.learning.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.domain.model.DailyChallenge
import com.smartkids.learning.domain.model.Topic
import com.smartkids.learning.domain.model.UserProfile
import com.smartkids.learning.domain.repository.DailyChallengeRepository
import com.smartkids.learning.domain.repository.TopicRepository
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import com.smartkids.learning.domain.usecase.CheckAchievementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val topicRepository: TopicRepository,
    private val dailyChallengeRepository: DailyChallengeRepository,
    private val checkAchievementsUseCase: CheckAchievementsUseCase
) : ViewModel() {

    val userProfile: StateFlow<UserProfile> = userPreferencesRepository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    val todayChallenge: StateFlow<DailyChallenge?> = dailyChallengeRepository.getTodayChallenge()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _featuredTopics = MutableStateFlow<List<Topic>>(emptyList())
    val featuredTopics: StateFlow<List<Topic>> = _featuredTopics.asStateFlow()

    private val _categories = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val categories: StateFlow<List<Pair<String, String>>> = _categories.asStateFlow()

    private val _showAchievementPopup = MutableStateFlow<String?>(null)
    val showAchievementPopup: StateFlow<String?> = _showAchievementPopup.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            userPreferencesRepository.checkAndResetDailyStreak()
            dailyChallengeRepository.generateTodayChallenge()

            topicRepository.getAllTopics().collect { topics ->
                _featuredTopics.value = topics.take(6)
                val cats = topics.map { it.categoryId to it.categoryName }.distinctBy { it.first }
                _categories.value = cats
            }
        }
    }

    fun dismissAchievement() {
        _showAchievementPopup.value = null
    }
}