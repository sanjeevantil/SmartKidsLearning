package com.smartkids.learning.domain.repository

import com.smartkids.learning.domain.model.Achievement
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {

    fun getAllAchievements(): Flow<List<Achievement>>
    fun getUnlockedAchievements(): Flow<List<Achievement>>
    fun getUnlockedCount(): Flow<Int>

    suspend fun checkAndUnlockAchievements(
        totalXP: Int,
        totalCoins: Int,
        streak: Int,
        gamesPlayed: Int,
        correctAnswers: Int,
        completedTopics: Int
    ): List<Achievement>

    suspend fun seedAchievements()
}