package com.smartkids.learning.domain.model

data class MistakeRecord(
    val id: Long = 0,
    val topicId: String,
    val questionText: String,
    val correctAnswer: String,
    val userAnswer: String,
    val timestamp: Long,
    val timesWrong: Int,
    val timesCorrectAfterWrong: Int,
    val isResolved: Boolean
)