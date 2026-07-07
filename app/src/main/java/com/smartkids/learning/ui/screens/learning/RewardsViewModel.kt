package com.smartkids.learning.ui.screens.rewards

import androidx.lifecycle.ViewModel
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

import kotlinx.coroutines.flow.map
import androidx.lifecycle.viewModelScope

@HiltViewModel
class RewardsViewModel @Inject constructor(
    userPrefs: UserPreferencesRepository
) : ViewModel() {
    val totalXP: StateFlow<Int> = userPrefs.userProfile.map { it.totalXP }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val totalCoins: StateFlow<Int> = userPrefs.userProfile.map { it.totalCoins }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val currentStreak: StateFlow<Int> = userPrefs.userProfile.map { it.currentStreak }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val longestStreak: StateFlow<Int> = userPrefs.userProfile.map { it.longestStreak }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val totalGames: StateFlow<Int> = userPrefs.userProfile.map { it.totalGamesPlayed }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}