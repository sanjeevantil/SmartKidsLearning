package com.smartkids.learning.data.repository

import com.smartkids.learning.data.local.database.dao.DailyChallengeDao
import com.smartkids.learning.data.local.database.entity.DailyChallengeEntity
import com.smartkids.learning.domain.model.DailyChallenge
import com.smartkids.learning.domain.repository.DailyChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyChallengeRepositoryImpl @Inject constructor(
    private val dao: DailyChallengeDao
) : DailyChallengeRepository {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val random = Random()

    private val challengeTopics = listOf(
        "abc_learning" to "ABC Learning",
        "numbers" to "Numbers",
        "shapes" to "Shapes",
        "colors" to "Colors",
        "animals" to "Animals",
        "fruits" to "Fruits",
        "addition" to "Addition",
        "counting" to "Counting"
    )

    private val challengeTypes = listOf("quiz", "matching", "memory", "balloon_pop")

    override fun getTodayChallenge(): Flow<DailyChallenge?> {
        val today = dateFormat.format(Date())
        return dao.getChallengeForDate(today).map { it?.toDomain() }
    }

    override fun getRecentChallenges(): Flow<List<DailyChallenge>> {
        return dao.getRecentChallenges().map { list -> list.map { it.toDomain() } }
    }

    override fun getCompletedCount(): Flow<Int> {
        return dao.getCompletedChallengeCount()
    }

    override suspend fun generateTodayChallenge() {
        val today = dateFormat.format(Date())
        val existing = dao.getChallengeForDate(today)
        if (existing != null) return

        val (topicId, topicName) = challengeTopics[random.nextInt(challengeTopics.size)]
        val challengeType = challengeTypes[random.nextInt(challengeTypes.size)]
        val difficulty = random.nextInt(3) + 1
        val totalQuestions = 5 + difficulty * 2
        val xpReward = totalQuestions * 15
        val coinReward = totalQuestions * 8

        dao.insertChallenge(
            DailyChallengeEntity(
                dateStamp = today,
                challengeType = challengeType,
                topicId = topicId,
                difficulty = difficulty,
                questions = "",
                xpEarned = xpReward,
                coinEarned = coinReward
            )
        )
    }

    override suspend fun completeChallenge(dateStamp: String, score: Int, total: Int) {
        val now = System.currentTimeMillis()
        dao.completeChallenge(
            dateStamp = dateStamp,
            completed = true,
            score = score,
            xp = score * 15,
            coins = score * 8,
            timestamp = now
        )
    }

    private fun DailyChallengeEntity.toDomain() = DailyChallenge(
        challengeId = dateStamp + "_" + topicId,
        date = dateFormat.parse(dateStamp)?.time ?: 0L,
        title = challengeTopics.find { it.first == topicId }?.second ?: topicId,
        description = "Complete $challengeType challenge",
        type = challengeType,
        requirementCount = 5 + difficulty * 2,
        currentProgress = score,
        isCompleted = isCompleted,
        xpReward = xpEarned,
        coinReward = coinEarned,
        categoryId = null
    )
}