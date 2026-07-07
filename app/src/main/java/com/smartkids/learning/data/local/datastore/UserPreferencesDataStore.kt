package com.smartkids.learning.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val CHILD_NAME = stringPreferencesKey("child_name")
        val CHILD_AGE = intPreferencesKey("child_age")
        val AVATAR_INDEX = intPreferencesKey("avatar_index")
        val PARENT_PIN = stringPreferencesKey("parent_pin")
        val IS_PIN_SET = booleanPreferencesKey("is_pin_set")
        val TOTAL_XP = intPreferencesKey("total_xp")
        val TOTAL_COINS = intPreferencesKey("total_coins")
        val CURRENT_STREAK = intPreferencesKey("current_streak")
        val LONGEST_STREAK = intPreferencesKey("longest_streak")
        val LAST_ACTIVE_DATE = stringPreferencesKey("last_active_date")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val SOUND_EFFECTS = booleanPreferencesKey("sound_effects")
        val VOICE_INSTRUCTIONS = booleanPreferencesKey("voice_instructions")
        val HAPTIC_FEEDBACK = booleanPreferencesKey("haptic_feedback")
        val TOTAL_GAMES_PLAYED = intPreferencesKey("total_games_played")
        val TOTAL_CORRECT_ANSWERS = intPreferencesKey("total_correct_answers")
        val TOTAL_WRONG_ANSWERS = intPreferencesKey("total_wrong_answers")
        val TOTAL_TIME_SPENT = longPreferencesKey("total_time_spent")
        val DAILY_XP_TODAY = intPreferencesKey("daily_xp_today")
        val LAST_XP_RESET_DATE = stringPreferencesKey("last_xp_reset_date")
    }

    val childName: Flow<String> = dataStore.data.map { it[Keys.CHILD_NAME] ?: "Kid" }
    val childAge: Flow<Int> = dataStore.data.map { it[Keys.CHILD_AGE] ?: 4 }
    val avatarIndex: Flow<Int> = dataStore.data.map { it[Keys.AVATAR_INDEX] ?: 0 }
    val parentPin: Flow<String> = dataStore.data.map { it[Keys.PARENT_PIN] ?: "1234" }
    val isPinSet: Flow<Boolean> = dataStore.data.map { it[Keys.IS_PIN_SET] ?: false }
    val totalXP: Flow<Int> = dataStore.data.map { it[Keys.TOTAL_XP] ?: 0 }
    val totalCoins: Flow<Int> = dataStore.data.map { it[Keys.TOTAL_COINS] ?: 0 }
    val currentStreak: Flow<Int> = dataStore.data.map { it[Keys.CURRENT_STREAK] ?: 0 }
    val longestStreak: Flow<Int> = dataStore.data.map { it[Keys.LONGEST_STREAK] ?: 0 }
    val lastActiveDate: Flow<String> = dataStore.data.map { it[Keys.LAST_ACTIVE_DATE] ?: "" }
    val darkMode: Flow<Boolean> = dataStore.data.map { it[Keys.DARK_MODE] ?: false }
    val soundEffects: Flow<Boolean> = dataStore.data.map { it[Keys.SOUND_EFFECTS] ?: true }
    val voiceInstructions: Flow<Boolean> = dataStore.data.map { it[Keys.VOICE_INSTRUCTIONS] ?: true }
    val hapticFeedback: Flow<Boolean> = dataStore.data.map { it[Keys.HAPTIC_FEEDBACK] ?: true }
    val totalGamesPlayed: Flow<Int> = dataStore.data.map { it[Keys.TOTAL_GAMES_PLAYED] ?: 0 }
    val totalCorrectAnswers: Flow<Int> = dataStore.data.map { it[Keys.TOTAL_CORRECT_ANSWERS] ?: 0 }
    val totalWrongAnswers: Flow<Int> = dataStore.data.map { it[Keys.TOTAL_WRONG_ANSWERS] ?: 0 }
    val totalTimeSpent: Flow<Long> = dataStore.data.map { it[Keys.TOTAL_TIME_SPENT] ?: 0L }

    suspend fun setChildName(name: String) {
        dataStore.edit { it[Keys.CHILD_NAME] = name }
    }

    suspend fun setChildAge(age: Int) {
        dataStore.edit { it[Keys.CHILD_AGE] = age }
    }

    suspend fun setAvatarIndex(index: Int) {
        dataStore.edit { it[Keys.AVATAR_INDEX] = index }
    }

    suspend fun setParentPin(pin: String) {
        dataStore.edit {
            it[Keys.PARENT_PIN] = pin
            it[Keys.IS_PIN_SET] = true
        }
    }

    suspend fun addXP(amount: Int) {
        dataStore.edit { it[Keys.TOTAL_XP] = (it[Keys.TOTAL_XP] ?: 0) + amount }
    }

    suspend fun addCoins(amount: Int) {
        dataStore.edit { it[Keys.TOTAL_COINS] = (it[Keys.TOTAL_COINS] ?: 0) + amount }
    }

    suspend fun spendCoins(amount: Int): Boolean {
        var success = false
        dataStore.edit {
            val current = it[Keys.TOTAL_COINS] ?: 0
            if (current >= amount) {
                it[Keys.TOTAL_COINS] = current - amount
                success = true
            }
        }
        return success
    }

    suspend fun incrementStreak() {
        dataStore.edit {
            val current = (it[Keys.CURRENT_STREAK] ?: 0) + 1
            it[Keys.CURRENT_STREAK] = current
            val longest = it[Keys.LONGEST_STREAK] ?: 0
            if (current > longest) {
                it[Keys.LONGEST_STREAK] = current
            }
        }
    }

    suspend fun resetStreak() {
        dataStore.edit { it[Keys.CURRENT_STREAK] = 0 }
    }

    suspend fun setLastActiveDate(date: String) {
        dataStore.edit { it[Keys.LAST_ACTIVE_DATE] = date }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { it[Keys.DARK_MODE] = enabled }
    }

    suspend fun setSoundEffects(enabled: Boolean) {
        dataStore.edit { it[Keys.SOUND_EFFECTS] = enabled }
    }

    suspend fun setVoiceInstructions(enabled: Boolean) {
        dataStore.edit { it[Keys.VOICE_INSTRUCTIONS] = enabled }
    }

    suspend fun setHapticFeedback(enabled: Boolean) {
        dataStore.edit { it[Keys.HAPTIC_FEEDBACK] = enabled }
    }

    suspend fun incrementGamesPlayed() {
        dataStore.edit { it[Keys.TOTAL_GAMES_PLAYED] = (it[Keys.TOTAL_GAMES_PLAYED] ?: 0) + 1 }
    }

    suspend fun recordAnswer(correct: Boolean) {
        dataStore.edit {
            if (correct) {
                it[Keys.TOTAL_CORRECT_ANSWERS] = (it[Keys.TOTAL_CORRECT_ANSWERS] ?: 0) + 1
            } else {
                it[Keys.TOTAL_WRONG_ANSWERS] = (it[Keys.TOTAL_WRONG_ANSWERS] ?: 0) + 1
            }
        }
    }

    suspend fun addTimeSpent(seconds: Long) {
        dataStore.edit { it[Keys.TOTAL_TIME_SPENT] = (it[Keys.TOTAL_TIME_SPENT] ?: 0L) + seconds }
    }

    suspend fun exportAllPreferences(): Map<String, Any> {
        var result = emptyMap<String, Any>()
        dataStore.edit {
            result = mapOf(
                "child_name" to (it[Keys.CHILD_NAME] ?: "Kid"),
                "child_age" to (it[Keys.CHILD_AGE] ?: 4),
                "avatar_index" to (it[Keys.AVATAR_INDEX] ?: 0),
                "total_xp" to (it[Keys.TOTAL_XP] ?: 0),
                "total_coins" to (it[Keys.TOTAL_COINS] ?: 0),
                "current_streak" to (it[Keys.CURRENT_STREAK] ?: 0),
                "longest_streak" to (it[Keys.LONGEST_STREAK] ?: 0),
                "total_games_played" to (it[Keys.TOTAL_GAMES_PLAYED] ?: 0),
                "total_correct_answers" to (it[Keys.TOTAL_CORRECT_ANSWERS] ?: 0),
                "total_wrong_answers" to (it[Keys.TOTAL_WRONG_ANSWERS] ?: 0),
                "total_time_spent" to (it[Keys.TOTAL_TIME_SPENT] ?: 0L)
            )
        }
        return result
    }

    suspend fun importPreferences(data: Map<String, Any>) {
        dataStore.edit {
            it[Keys.CHILD_NAME] = data["child_name"] as? String ?: "Kid"
            it[Keys.CHILD_AGE] = (data["child_age"] as? Int) ?: 4
            it[Keys.AVATAR_INDEX] = (data["avatar_index"] as? Int) ?: 0
            it[Keys.TOTAL_XP] = (data["total_xp"] as? Int) ?: 0
            it[Keys.TOTAL_COINS] = (data["total_coins"] as? Int) ?: 0
            it[Keys.CURRENT_STREAK] = (data["current_streak"] as? Int) ?: 0
            it[Keys.LONGEST_STREAK] = (data["longest_streak"] as? Int) ?: 0
            it[Keys.TOTAL_GAMES_PLAYED] = (data["total_games_played"] as? Int) ?: 0
            it[Keys.TOTAL_CORRECT_ANSWERS] = (data["total_correct_ANSWERS"] as? Int) ?: 0
            it[Keys.TOTAL_WRONG_ANSWERS] = (data["total_WRONG_ANSWERS"] as? Int) ?: 0
            it[Keys.TOTAL_TIME_SPENT] = (data["total_time_spent"] as? Long) ?: 0L
        }
    }
}