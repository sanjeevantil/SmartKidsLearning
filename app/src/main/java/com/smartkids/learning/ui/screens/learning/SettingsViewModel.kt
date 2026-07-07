package com.smartkids.learning.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userPrefs: UserPreferencesRepository) : ViewModel() {
    val darkMode: StateFlow<Boolean> = userPrefs.darkMode.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    val soundEffects: StateFlow<Boolean> = userPrefs.soundEffects.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    val voiceInstructions: StateFlow<Boolean> = userPrefs.voiceInstructions.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    val hapticFeedback: StateFlow<Boolean> = userPrefs.hapticFeedback.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    fun setDarkMode(v: Boolean) { viewModelScope.launch { userPrefs.setDarkMode(v) } }
    fun setSoundEffects(v: Boolean) { viewModelScope.launch { userPrefs.setSoundEffects(v) } }
    fun setVoiceInstructions(v: Boolean) { viewModelScope.launch { userPrefs.setVoiceInstructions(v) } }
    fun setHapticFeedback(v: Boolean) { viewModelScope.launch { userPrefs.setHapticFeedback(v) } }
}