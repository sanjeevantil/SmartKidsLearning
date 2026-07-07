package com.smartkids.learning.ui.screens.rewards

import androidx.lifecycle.ViewModel
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RewardsViewModel @Inject constructor(
    userPrefs: UserPreferencesRepository
) : ViewModel() {
    val totalXP: StateFlow<Int> = userPrefs.totalXP.stateIn(SharingStarted.WhileSubscribed(5000), 0)
    val totalCoins: StateFlow<Int> = userPrefs.totalCoins.stateIn(SharingStarted.WhileSubscribed(5000), 0)
    val currentStreak: StateFlow<Int> = userPrefs.currentStreak.stateIn(SharingStarted.WhileSubscribed(5000), 0)
    val longestStreak: StateFlow<Int> = userPrefs.longestStreak.stateIn(SharingStarted.WhileSubscribed(5000), 0)
    val totalGames: StateFlow<Int> = userPrefs.totalGamesPlayed.stateIn(SharingStarted.WhileSubscribed(5000), 0)
}