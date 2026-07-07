package com.smartkids.learning.domain.repository

import com.smartkids.learning.domain.model.MistakeRecord
import kotlinx.coroutines.flow.Flow

interface MistakeTrackingRepository {

    fun getUnresolvedMistakes(): Flow<List<MistakeRecord>>
    fun getMistakesByTopic(topicId: String): Flow<List<MistakeRecord>>
    fun getUnresolvedCount(): Flow<Int>

    suspend fun recordMistake(
        topicId: String,
        questionText: String,
        correctAnswer: String,
        userAnswer: String
    )

    suspend fun markAsCorrectAfterMistake(questionText: String, correctAnswer: String)
    suspend fun cleanupOldResolvedMistakes()
}