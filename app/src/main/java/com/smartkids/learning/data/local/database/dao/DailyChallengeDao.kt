package com.smartkids.learning.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smartkids.learning.data.local.database.entity.DailyChallengeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyChallengeDao {

    @Query("SELECT * FROM daily_challenges WHERE dateStamp = :dateStamp LIMIT 1")
    fun getChallengeForDate(dateStamp: String): Flow<DailyChallengeEntity?>

    @Query("SELECT * FROM daily_challenges ORDER BY dateStamp DESC LIMIT 7")
    fun getRecentChallenges(): Flow<List<DailyChallengeEntity>>

    @Query("SELECT COUNT(*) FROM daily_challenges WHERE isCompleted = 1")
    fun getCompletedChallengeCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenge(challenge: DailyChallengeEntity)

    @Query("UPDATE daily_challenges SET isCompleted = :completed, score = :score, xpEarned = :xp, coinEarned = :coins, completedTimestamp = :timestamp WHERE dateStamp = :dateStamp")
    suspend fun completeChallenge(
        dateStamp: String,
        completed: Boolean,
        score: Int,
        xp: Int,
        coins: Int,
        timestamp: Long
    )
}