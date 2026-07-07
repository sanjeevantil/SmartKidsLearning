package com.smartkids.learning.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smartkids.learning.data.local.database.entity.LearningProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningProgressDao {

    @Query("SELECT * FROM learning_progress ORDER BY lastAccessedTimestamp DESC")
    fun getAllProgress(): Flow<List<LearningProgressEntity>>

    @Query("SELECT * FROM learning_progress WHERE topicId = :topicId")
    fun getProgressByTopic(topicId: String): Flow<LearningProgressEntity?>

    @Query("SELECT * FROM learning_progress WHERE categoryId = :categoryId ORDER BY sortOrder ASC")
    fun getProgressByCategory(categoryId: String): Flow<List<LearningProgressEntity>>

    @Query("SELECT * FROM learning_progress WHERE isCompleted = 0 ORDER BY lastAccessedTimestamp DESC LIMIT 1")
    fun getContinueLearningTopic(): Flow<LearningProgressEntity?>

    @Query("SELECT SUM(xpEarned) FROM learning_progress")
    fun getTotalXP(): Flow<Int?>

    @Query("SELECT COUNT(*) FROM learning_progress WHERE isCompleted = 1")
    fun getCompletedTopicsCount(): Flow<Int>

    @Query("SELECT AVG(averageScore) FROM learning_progress WHERE totalAttempts > 0")
    fun getAverageScore(): Flow<Float?>

    @Query("SELECT SUM(timeSpentSeconds) FROM learning_progress")
    fun getTotalTimeSpent(): Flow<Long?>

    @Query("SELECT * FROM learning_progress WHERE masteryLevel < 0.5 AND totalAttempts > 0 ORDER BY masteryLevel ASC LIMIT 5")
    fun getWeakTopics(): Flow<List<LearningProgressEntity>>

    @Query("SELECT * FROM learning_progress WHERE masteryLevel >= 0.8 ORDER BY masteryLevel DESC LIMIT 5")
    fun getStrongTopics(): Flow<List<LearningProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: LearningProgressEntity)

    @Update
    suspend fun updateProgress(progress: LearningProgressEntity)

    @Query("DELETE FROM learning_progress")
    suspend fun deleteAllProgress()
}