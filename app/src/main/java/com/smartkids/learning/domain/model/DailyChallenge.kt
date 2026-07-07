package com.smartkids.learning.domain.model

data class DailyChallenge(
    val dateStamp: String,
    val challengeType: String,
    val topicId: String,
    val topicName: String,
    val difficulty: Int,
    val totalQuestions: Int,
    val isCompleted: Boolean = false,
    val score: Int = 0,
    val xpReward: Int = 0,
    val coinReward: Int = 0
)