package com.smartkids.learning.data.repository

import com.smartkids.learning.data.local.database.dao.MistakeTrackingDao
import com.smartkids.learning.data.local.database.entity.MistakeTrackingEntity
import com.smartkids.learning.domain.model.MistakeRecord
import com.smartkids.learning.domain.repository.MistakeTrackingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MistakeTrackingRepositoryImpl @Inject constructor(
    private val dao: MistakeTrackingDao
) : MistakeTrackingRepository {

    override fun getUnresolvedMistakes(): Flow<List<MistakeRecord>> {
        return dao.getUnresolvedMistakes().map { list -> list.map { it.toDomain() } }
    }

    override fun getMistakesByTopic(topicId: String): Flow<List<MistakeRecord>> {
        return dao.getMistakesByTopic(topicId).map { list -> list.map { it.toDomain() } }
    }

    override fun getUnresolvedCount(): Flow<Int> {
        return dao.getUnresolvedMistakeCount()
    }

    override suspend fun recordMistake(
        topicId: String,
        questionText: String,
        correctAnswer: String,
        userAnswer: String
    ) {
        val existing = dao.findMistake(questionText, correctAnswer)
        if (existing != null) {
            dao.updateMistake(existing.copy(timesWrong = existing.timesWrong + 1, isResolved = false))
        } else {
            dao.insertMistake(
                MistakeTrackingEntity(
                    topicId = topicId,
                    questionText = questionText,
                    correctAnswer = correctAnswer,
                    userAnswer = userAnswer
                )
            )
        }
    }

    override suspend fun markAsCorrectAfterMistake(questionText: String, correctAnswer: String) {
        val existing = dao.findMistake(questionText, correctAnswer)
        if (existing != null) {
            val newCorrectCount = existing.timesCorrectAfterWrong + 1
            val resolved = newCorrectCount >= 3
            dao.updateMistake(
                existing.copy(
                    timesCorrectAfterWrong = newCorrectCount,
                    isResolved = resolved
                )
            )
        }
    }

    override suspend fun cleanupOldResolvedMistakes() {
        val threshold = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000)
        dao.deleteOldResolvedMistakes(threshold)
    }

    private fun MistakeTrackingEntity.toDomain() = MistakeRecord(
        id = id,
        topicId = topicId,
        questionText = questionText,
        correctAnswer = correctAnswer,
        userAnswer = userAnswer,
        timestamp = timestamp,
        timesWrong = timesWrong,
        timesCorrectAfterWrong = timesCorrectAfterWrong,
        isResolved = isResolved
    )
}