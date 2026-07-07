package com.smartkids.learning.data.repository

import com.smartkids.learning.data.local.database.dao.LearningProgressDao
import com.smartkids.learning.data.local.database.entity.LearningProgressEntity
import com.smartkids.learning.data.local.datastore.UserPreferencesDataStore
import com.smartkids.learning.domain.model.LearningProgress
import com.smartkids.learning.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningRepositoryImpl @Inject constructor(
    private val dao: LearningProgressDao,
    private val dataStore: UserPreferencesDataStore
) : LearningRepository {

    override fun getAllProgress(): Flow<List<LearningProgress>> {
        return dao.getAllProgress().map { list -> list.map { it.toDomain() } }
    }

    override fun getProgressByTopic(topicId: String): Flow<LearningProgress?> {
        return dao.getProgressByTopic(topicId).map { it?.toDomain() }
    }

    override fun getProgressByCategory(categoryId: String): Flow<List<LearningProgress>> {
        return dao.getProgressByCategory(categoryId).map { list -> list.map { it.toDomain() } }
    }

    override fun getContinueLearningTopic(): Flow<LearningProgress?> {
        return dao.getContinueLearningTopic().map { it?.toDomain() }
    }

    override fun getTotalXP(): Flow<Int> {
        return dataStore.totalXP
    }

    override fun getCompletedTopicsCount(): Flow<Int> {
        return dao.getCompletedTopicsCount()
    }

    override fun getAverageScore(): Flow<Float?> {
        return dao.getAverageScore()
    }

    override fun getTotalTimeSpent(): Flow<Long?> {
        return dao.getTotalTimeSpent()
    }

    override fun getWeakTopics(): Flow<List<LearningProgress>> {
        return dao.getWeakTopics().map { list -> list.map { it.toDomain() } }
    }

    override fun getStrongTopics(): Flow<List<LearningProgress>> {
        return dao.getStrongTopics().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun updateProgressAfterSession(
        topicId: String,
        topicName: String,
        categoryId: String,
        correct: Int,
        wrong: Int,
        timeSpent: Long,
        difficulty: Int
    ): LearningProgress {
        val existing = dao.getProgressByTopic(topicId)
        val updated = if (existing != null) {
            val newCorrect = existing.correctAnswers + correct
            val newWrong = existing.wrongAnswers + wrong
            val newTotal = existing.totalAttempts + correct + wrong
            val newCompleted = (existing.completedItems + correct).coerceAtMost(existing.totalItems)
            val newBest = maxOf(existing.bestScore, correct)
            val newAvg = if (newTotal > 0) newCorrect.toFloat() / newTotal else 0f
            val newTime = existing.timeSpentSeconds + timeSpent
            val mastery = calculateMastery(newAvg, newCompleted, existing.totalItems)
            val xp = correct * 10 * difficulty
            val isComplete = newCompleted >= existing.totalItems && existing.totalItems > 0

            existing.copy(
                correctAnswers = newCorrect,
                wrongAnswers = newWrong,
                totalAttempts = newTotal,
                completedItems = newCompleted,
                bestScore = newBest,
                averageScore = newAvg,
                timeSpentSeconds = newTime,
                lastAccessedTimestamp = System.currentTimeMillis(),
                difficultyLevel = difficulty,
                isCompleted = isComplete,
                masteryLevel = mastery,
                xpEarned = existing.xpEarned + xp
            )
        } else {
            val total = correct + wrong
            val avg = if (total > 0) correct.toFloat() / total else 0f
            LearningProgressEntity(
                topicId = topicId,
                topicName = topicName,
                categoryId = categoryId,
                totalItems = correct + wrong,
                completedItems = correct,
                correctAnswers = correct,
                wrongAnswers = wrong,
                totalAttempts = total,
                bestScore = correct,
                averageScore = avg,
                timeSpentSeconds = timeSpent,
                difficultyLevel = difficulty,
                isUnlocked = true,
                isCompleted = total == correct && total > 0,
                masteryLevel = avg,
                xpEarned = correct * 10 * difficulty
            )
        }
        dao.upsertProgress(updated)
        dataStore.addXP(correct * 10 * difficulty)
        dataStore.addCoins(correct * 5)
        return updated.toDomain()
    }

    override suspend fun initializeProgress(
        topicId: String,
        topicName: String,
        categoryId: String,
        totalItems: Int
    ) {
        val existing = dao.getProgressByTopic(topicId)
        if (existing == null) {
            dao.upsertProgress(
                LearningProgressEntity(
                    topicId = topicId,
                    topicName = topicName,
                    categoryId = categoryId,
                    totalItems = totalItems,
                    isUnlocked = true
                )
            )
        }
    }

    override suspend fun deleteAllProgress() {
        dao.deleteAllProgress()
    }

    private fun calculateMastery(avgScore: Float, completed: Int, total: Int): Float {
        val scoreWeight = avgScore * 0.6f
        val completionWeight = if (total > 0) (completed.toFloat() / total) * 0.4f else 0f
        return (scoreWeight + completionWeight).coerceIn(0f, 1f)
    }

    private fun LearningProgressEntity.toDomain() = LearningProgress(
        topicId = topicId,
        topicName = topicName,
        categoryId = categoryId,
        totalItems = totalItems,
        completedItems = completedItems,
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        totalAttempts = totalAttempts,
        bestScore = bestScore,
        averageScore = averageScore,
        timeSpentSeconds = timeSpentSeconds,
        lastAccessedTimestamp = lastAccessedTimestamp,
        difficultyLevel = difficultyLevel,
        isUnlocked = isUnlocked,
        isCompleted = isCompleted,
        masteryLevel = masteryLevel,
        xpEarned = xpEarned
    )
}