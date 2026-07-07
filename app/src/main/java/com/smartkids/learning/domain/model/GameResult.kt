package com.smartkids.learning.domain.model

data class GameResult(
    val topicId: String,
    val gameType: String,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val timeSpentSeconds: Long,
    val difficultyLevel: Int,
    val xpEarned: Int,
    val coinsEarned: Int,
    val isNewBestScore: Boolean = false
)