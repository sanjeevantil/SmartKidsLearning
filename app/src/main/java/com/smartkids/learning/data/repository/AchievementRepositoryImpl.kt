package com.smartkids.learning.data.repository

import com.smartkids.learning.data.local.database.dao.AchievementDao
import com.smartkids.learning.data.local.database.entity.AchievementEntity
import com.smartkids.learning.domain.model.Achievement
import com.smartkids.learning.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepositoryImpl @Inject constructor(
    private val dao: AchievementDao
) : AchievementRepository {

    override fun getAllAchievements(): Flow<List<Achievement>> {
        return dao.getAllAchievements().map { list -> list.map { it.toDomain() } }
    }

    override fun getUnlockedAchievements(): Flow<List<Achievement>> {
        return dao.getUnlockedAchievements().map { list -> list.map { it.toDomain() } }
    }

    override fun getUnlockedCount(): Flow<Int> {
        return dao.getUnlockedCount()
    }

    override suspend fun checkAndUnlockAchievements(
        totalXP: Int,
        totalCoins: Int,
        streak: Int,
        gamesPlayed: Int,
        correctAnswers: Int,
        completedTopics: Int
    ): List<Achievement> {
        val newlyUnlocked = mutableListOf<Achievement>()
        val allAchievements = getAllAchievementsDefinitions()

        for (def in allAchievements) {
            if (def.isUnlocked) continue
            val currentVal = when (def.requirementType) {
                "total_xp" -> totalXP
                "total_coins" -> totalCoins
                "streak" -> streak
                "games_played" -> gamesPlayed
                "correct_answers" -> correctAnswers
                "completed_topics" -> completedTopics
                else -> 0
            }
            if (currentVal >= def.requirementValue) {
                val unlocked = def.copy(
                    isUnlocked = true,
                    unlockedTimestamp = System.currentTimeMillis(),
                    currentValue = currentVal
                )
                dao.updateAchievement(unlocked)
                newlyUnlocked.add(unlocked.toDomain())
            } else {
                dao.updateAchievement(
                    def.copy(currentValue = currentVal)
                )
            }
        }
        return newlyUnlocked
    }

    override suspend fun seedAchievements() {
        val existing = dao.getAllAchievements()
        if (existing.isNotEmpty()) return
        dao.insertAchievements(getAllAchievementsDefinitions())
    }

    private fun getAllAchievementsDefinitions(): List<AchievementEntity> {
        return listOf(
            AchievementEntity("ach_first_lesson", "First Steps", "Complete your first lesson", "star", "learning", "completed_topics", 1, 0, false, null, 20, 10),
            AchievementEntity("ach_5_lessons", "Quick Learner", "Complete 5 topics", "book", "learning", "completed_topics", 5, 0, false, null, 50, 25),
            AchievementEntity("ach_10_lessons", "Knowledge Seeker", "Complete 10 topics", "school", "learning", "completed_topics", 10, 0, false, null, 100, 50),
            AchievementEntity("ach_25_lessons", "Scholar", "Complete 25 topics", "workspace_premium", "learning", "completed_topics", 25, 0, false, null, 200, 100),
            AchievementEntity("ach_50_lessons", "Mastermind", "Complete 50 topics", "emoji_events", "learning", "completed_topics", 50, 0, false, null, 500, 250),
            AchievementEntity("ach_100_xp", "XP Starter", "Earn 100 XP", "bolt", "xp", "total_xp", 100, 0, false, null, 15, 5),
            AchievementEntity("ach_500_xp", "XP Hunter", "Earn 500 XP", "local_fire_department", "xp", "total_xp", 500, 0, false, null, 75, 40),
            AchievementEntity("ach_1000_xp", "XP Master", "Earn 1000 XP", "whatshot", "xp", "total_xp", 1000, 0, false, null, 150, 80),
            AchievementEntity("ach_5000_xp", "XP Legend", "Earn 5000 XP", "military_tech", "xp", "total_xp", 5000, 0, false, null, 400, 200),
            AchievementEntity("ach_50_coins", "Coin Collector", "Earn 50 coins", "monetization_on", "coins", "total_coins", 50, 0, false, null, 20, 10),
            AchievementEntity("ach_200_coins", "Coin Hoarder", "Earn 200 coins", "savings", "coins", "total_coins", 200, 0, false, null, 60, 30),
            AchievementEntity("ach_1000_coins", "Coin Tycoon", "Earn 1000 coins", "account_balance_wallet", "coins", "total_coins", 1000, 0, false, null, 200, 100),
            AchievementEntity("ach_3_streak", "On Fire", "3-day streak", "local_fire_department", "streak", "streak", 3, 0, false, null, 30, 15),
            AchievementEntity("ach_7_streak", "Week Warrior", "7-day streak", "calendar_month", "streak", "streak", 7, 0, false, null, 75, 40),
            AchievementEntity("ach_30_streak", "Monthly Master", "30-day streak", "event", "streak", "streak", 30, 0, false, null, 300, 150),
            AchievementEntity("ach_5_games", "Game On", "Play 5 games", "sports_esports", "games", "games_played", 5, 0, false, null, 25, 10),
            AchievementEntity("ach_25_games", "Game Champion", "Play 25 games", "videogame_asset", "games", "games_played", 25, 0, false, null, 100, 50),
            AchievementEntity("ach_100_games", "Game Legend", "Play 100 games", "casino", "games", "games_played", 100, 0, false, null, 300, 150),
            AchievementEntity("ach_50_correct", "Getting Smarter", "50 correct answers", "thumb_up", "accuracy", "correct_answers", 50, 0, false, null, 30, 15),
            AchievementEntity("ach_200_correct", "Brain Power", "200 correct answers", "psychology", "accuracy", "correct_answers", 200, 0, false, null, 100, 50),
            AchievementEntity("ach_1000_correct", "Genius", "1000 correct answers", "auto_awesome", "accuracy", "correct_answers", 1000, 0, false, null, 500, 250)
        )
    }

    private fun AchievementEntity.toDomain() = Achievement(
        achievementId = achievementId,
        title = title,
        description = description,
        iconName = iconName,
        category = category,
        requirementType = requirementType,
        requirementValue = requirementValue,
        currentValue = currentValue,
        isUnlocked = isUnlocked,
        unlockedTimestamp = unlockedTimestamp,
        xpReward = xpReward,
        coinReward = coinReward
    )

    private fun Achievement.toEntity() = AchievementEntity(
        achievementId = achievementId,
        title = title,
        description = description,
        iconName = iconName,
        category = category,
        requirementType = requirementType,
        requirementValue = requirementValue,
        currentValue = currentValue,
        isUnlocked = isUnlocked,
        unlockedTimestamp = unlockedTimestamp,
        xpReward = xpReward,
        coinReward = coinReward
    )
}