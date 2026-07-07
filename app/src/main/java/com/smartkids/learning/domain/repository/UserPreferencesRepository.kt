package com.smartkids.learning.domain.repository

import com.smartkids.learning.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val userProfile: Flow<UserProfile>
    val childName: Flow<String>
    val childAge: Flow<Int>
    val avatarIndex: Flow<Int>
    val darkMode: Flow<Boolean>
    val soundEffects: Flow<Boolean>
    val voiceInstructions: Flow<Boolean>
    val hapticFeedback: Flow<Boolean>
    val parentPin: Flow<String>
    val isPinSet: Flow<Boolean>

    suspend fun setChildName(name: String)
    suspend fun setChildAge(age: Int)
    suspend fun setAvatarIndex(index: Int)
    suspend fun setParentPin(pin: String)
    suspend fun setDarkMode(enabled: Boolean)
    suspend fun setSoundEffects(enabled: Boolean)
    suspend fun setVoiceInstructions(enabled: Boolean)
    suspend fun setHapticFeedback(enabled: Boolean)
    suspend fun addXP(amount: Int)
    suspend fun addCoins(amount: Int)
    suspend fun spendCoins(amount: Int): Boolean
    suspend fun incrementStreak()
    suspend fun resetStreak()
    suspend fun checkAndResetDailyStreak()
    suspend fun incrementGamesPlayed()
    suspend fun recordAnswer(correct: Boolean)
    suspend fun addTimeSpent(seconds: Long)
    suspend fun exportData(): Map<String, Any>
    suspend fun importData(data: Map<String, Any>)
}