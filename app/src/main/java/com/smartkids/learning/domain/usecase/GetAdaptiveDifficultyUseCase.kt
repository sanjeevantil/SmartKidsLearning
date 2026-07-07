package com.smartkids.learning.domain.usecase

import com.smartkids.learning.domain.model.LearningProgress
import com.smartkids.learning.domain.repository.LearningRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAdaptiveDifficultyUseCase @Inject constructor(
    private val repository: LearningRepository
) {
    suspend operator fun invoke(topicId: String, childAge: Int): Int {
        val progress = repository.getProgressByTopic(topicId).first()
        val baseDifficulty = when (childAge) {
            in 2..3 -> 1
            in 4..5 -> 2
            in 6..7 -> 3
            in 8..9 -> 4
            else -> 5
        }
        if (progress == null) return baseDifficulty
        val masteryBonus = if (progress.masteryLevel > 0.8f) 2
            else if (progress.masteryLevel > 0.6f) 1
            else if (progress.masteryLevel < 0.3f && progress.totalAttempts > 3) -1
            else 0
        val accuracyBonus = if (progress.accuracyPercentage > 0.9f && progress.totalAttempts > 5) 1
            else if (progress.accuracyPercentage < 0.5f && progress.totalAttempts > 5) -1
            else 0
        return (baseDifficulty + masteryBonus + accuracyBonus).coerceIn(1, 5)
    }
}