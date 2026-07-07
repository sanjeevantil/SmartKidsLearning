package com.smartkids.learning.domain.usecase

import com.smartkids.learning.domain.model.Achievement
import com.smartkids.learning.domain.model.UserProfile
import com.smartkids.learning.domain.repository.AchievementRepository
import com.smartkids.learning.domain.repository.LearningRepository
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckAchievementsUseCase @Inject constructor(
    private val achievementRepository: AchievementRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val learningRepository: LearningRepository
) {
    suspend operator fun invoke(): List<Achievement> {
        val profile = userPreferencesRepository.userProfile.first()
        val completedTopics = learningRepository.getCompletedTopicsCount().first()
        return achievementRepository.checkAndUnlockAchievements(
            totalXP = profile.totalXP,
            totalCoins = profile.totalCoins,
            streak = profile.currentStreak,
            gamesPlayed = profile.totalGamesPlayed,
            correctAnswers = profile.totalCorrectAnswers,
            completedTopics = completedTopics
        )
    }
}