package com.smartkids.learning.domain.repository

import com.smartkids.learning.domain.model.LearningProgress
import kotlinx.coroutines.flow.Flow

interface LearningRepository {

    fun getAllProgress(): Flow<List<LearningProgress>>
    fun getProgressByTopic(topicId: String): Flow<LearningProgress?>
    fun getProgressByCategory(categoryId: String): Flow<List<LearningProgress>>
    fun getContinueLearningTopic(): Flow<LearningProgress?>
    fun getTotalXP(): Flow<Int>
    fun getCompletedTopicsCount(): Flow<Int>
    fun getAverageScore(): Flow<Float?>
    fun getTotalTimeSpent(): Flow<Long?>
    fun getWeakTopics(): Flow<List<LearningProgress>>
    fun getStrongTopics(): Flow<List<LearningProgress>>

    suspend fun updateProgressAfterSession(
        topicId: String,
        topicName: String,
        categoryId: String,
        correct: Int,
        wrong: Int,
        timeSpent: Long,
        difficulty: Int
    ): LearningProgress

    suspend fun initializeProgress(
        topicId: String,
        topicName: String,
        categoryId: String,
        totalItems: Int
    )

    suspend fun deleteAllProgress()
}