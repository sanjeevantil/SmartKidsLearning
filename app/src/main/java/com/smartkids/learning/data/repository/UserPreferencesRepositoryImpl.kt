package com.smartkids.learning.data.repository

import com.smartkids.learning.data.local.datastore.UserPreferencesDataStore
import com.smartkids.learning.domain.model.UserProfile
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: UserPreferencesDataStore
) : UserPreferencesRepository {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override val userProfile: Flow<UserProfile> = combine(
        dataStore.childName,
        dataStore.childAge,
        dataStore.avatarIndex,
        dataStore.totalXP,
        dataStore.totalCoins,
        dataStore.currentStreak,
        dataStore.longestStreak,
        dataStore.totalGamesPlayed,
        dataStore.totalCorrectAnswers,
        dataStore.totalWrongAnswers,
        dataStore.totalTimeSpent
    ) { values ->
        val name = values[0] as String
        val age = values[1] as Int
        val avatar = values[2] as Int
        val xp = values[3] as Int
        val coins = values[4] as Int
        val streak = values[5] as Int
        val longest = values[6] as Int
        val games = values[7] as Int
        val correct = values[8] as Int
        val wrong = values[9] as Int
        val time = values[10] as Long

        val (level, xpInLevel) = UserProfile.calculateLevel(xp)
        UserProfile(
            childName = name,
            childAge = age,
            avatarIndex = avatar,
            totalXP = xp,
            totalCoins = coins,
            currentStreak = streak,
            longestStreak = longest,
            level = level,
            xpToNextLevel = 100,
            xpInCurrentLevel = xpInLevel,
            totalGamesPlayed = games,
            totalCorrectAnswers = correct,
            totalWrongAnswers = wrong,
            totalTimeSpentSeconds = time
        )
    }

    override val childName: Flow<String> = dataStore.childName
    override val childAge: Flow<Int> = dataStore.childAge
    override val avatarIndex: Flow<Int> = dataStore.avatarIndex
    override val darkMode: Flow<Boolean> = dataStore.darkMode
    override val soundEffects: Flow<Boolean> = dataStore.soundEffects
    override val voiceInstructions: Flow<Boolean> = dataStore.voiceInstructions
    override val hapticFeedback: Flow<Boolean> = dataStore.hapticFeedback
    override val parentPin: Flow<String> = dataStore.parentPin
    override val isPinSet: Flow<Boolean> = dataStore.isPinSet

    override suspend fun setChildName(name: String) = dataStore.setChildName(name)
    override suspend fun setChildAge(age: Int) = dataStore.setChildAge(age)
    override suspend fun setAvatarIndex(index: Int) = dataStore.setAvatarIndex(index)
    override suspend fun setParentPin(pin: String) = dataStore.setParentPin(pin)
    override suspend fun setDarkMode(enabled: Boolean) = dataStore.setDarkMode(enabled)
    override suspend fun setSoundEffects(enabled: Boolean) = dataStore.setSoundEffects(enabled)
    override suspend fun setVoiceInstructions(enabled: Boolean) = dataStore.setVoiceInstructions(enabled)
    override suspend fun setHapticFeedback(enabled: Boolean) = dataStore.setHapticFeedback(enabled)
    override suspend fun addXP(amount: Int) = dataStore.addXP(amount)
    override suspend fun addCoins(amount: Int) = dataStore.addCoins(amount)
    override suspend fun spendCoins(amount: Int) = dataStore.spendCoins(amount)
    override suspend fun incrementStreak() = dataStore.incrementStreak()
    override suspend fun resetStreak() = dataStore.resetStreak()
    override suspend fun incrementGamesPlayed() = dataStore.incrementGamesPlayed()
    override suspend fun recordAnswer(correct: Boolean) = dataStore.recordAnswer(correct)
    override suspend fun addTimeSpent(seconds: Long) = dataStore.addTimeSpent(seconds)

    override suspend fun checkAndResetDailyStreak() {
        val today = dateFormat.format(Date())
        val last = dataStore.lastActiveDate.first()
        when {
            last.isEmpty() -> {
                dataStore.incrementStreak()
            }
            last == today -> {
            }
            else -> {
                try {
                    val lastD = dateFormat.parse(last)
                    val todayD = dateFormat.parse(today)
                    val diff = ((todayD?.time ?: 0) - (lastD?.time ?: 0)) / (24 * 60 * 60 * 1000)
                    if (diff == 1L) {
                        dataStore.incrementStreak()
                    } else if (diff > 1L) {
                        dataStore.resetStreak()
                        dataStore.incrementStreak()
                    }
                } catch (_: Exception) {
                    dataStore.resetStreak()
                    dataStore.incrementStreak()
                }
            }
        }
        dataStore.setLastActiveDate(today)
    }

    override suspend fun exportData(): Map<String, Any> = dataStore.exportAllPreferences()
    override suspend fun importData(data: Map<String, Any>) = dataStore.importPreferences(data)
}