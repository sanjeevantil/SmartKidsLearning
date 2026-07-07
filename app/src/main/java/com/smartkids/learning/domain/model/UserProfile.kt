package com.smartkids.learning.domain.model

data class UserProfile(
    val childName: String = "Kid",
    val childAge: Int = 4,
    val avatarIndex: Int = 0,
    val totalXP: Int = 0,
    val totalCoins: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val level: Int = 1,
    val xpToNextLevel: Int = 100,
    val xpInCurrentLevel: Int = 0,
    val totalGamesPlayed: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val totalWrongAnswers: Int = 0,
    val totalTimeSpentSeconds: Long = 0L,
    val completedTopics: Int = 0,
    val averageScore: Float = 0f
) {
    companion object {
        fun calculateLevel(totalXP: Int): Pair<Int, Int> {
            val level = (totalXP / 100) + 1
            val xpInLevel = totalXP % 100
            return Pair(level, xpInLevel)
        }
    }
}