package com.smartkids.learning.ui.screens.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.domain.model.Achievement
import com.smartkids.learning.domain.repository.AchievementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(private val repo: AchievementRepository) : ViewModel() {
    init { viewModelScope.launch { repo.seedAchievements() } }
    val achievements: StateFlow<List<Achievement>> = repo.getAllAchievements().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val unlockedCount: StateFlow<Int> = repo.getUnlockedCount().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}