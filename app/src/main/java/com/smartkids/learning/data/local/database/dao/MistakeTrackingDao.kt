package com.smartkids.learning.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smartkids.learning.data.local.database.entity.MistakeTrackingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MistakeTrackingDao {

    @Query("SELECT * FROM mistake_tracking WHERE isResolved = 0 ORDER BY timesWrong DESC, timestamp ASC")
    fun getUnresolvedMistakes(): Flow<List<MistakeTrackingEntity>>

    @Query("SELECT * FROM mistake_tracking WHERE topicId = :topicId AND isResolved = 0 ORDER BY timesWrong DESC")
    fun getMistakesByTopic(topicId: String): Flow<List<MistakeTrackingEntity>>

    @Query("SELECT * FROM mistake_tracking WHERE questionText = :questionText AND correctAnswer = :correctAnswer LIMIT 1")
    suspend fun findMistake(questionText: String, correctAnswer: String): MistakeTrackingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMistake(mistake: MistakeTrackingEntity)

    @Update
    suspend fun updateMistake(mistake: MistakeTrackingEntity)

    @Query("SELECT COUNT(*) FROM mistake_tracking WHERE isResolved = 0")
    fun getUnresolvedMistakeCount(): Flow<Int>

    @Query("DELETE FROM mistake_tracking WHERE isResolved = 1 AND timestamp < :threshold")
    suspend fun deleteOldResolvedMistakes(threshold: Long)
}