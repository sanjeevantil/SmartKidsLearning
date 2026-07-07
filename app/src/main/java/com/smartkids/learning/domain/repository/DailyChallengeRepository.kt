package com.smartkids.learning.domain.repository

import com.smartkids.learning.domain.model.DailyChallenge
import kotlinx.coroutines.flow.Flow

interface DailyChallengeRepository {

    fun getTodayChallenge(): Flow<DailyChallenge?>
    fun getRecentChallenges(): Flow<List<DailyChallenge>>
    fun getCompletedCount(): Flow<Int>

    suspend fun generateTodayChallenge()
    suspend fun completeChallenge(dateStamp: String, score: Int, total: Int)
}