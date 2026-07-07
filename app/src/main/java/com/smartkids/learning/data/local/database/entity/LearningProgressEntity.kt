package com.smartkids.learning.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learning_progress")
data class LearningProgressEntity(
    @PrimaryKey val topicId: String,
    val topicName: String,
    val categoryId: String,
    val totalItems: Int = 0,
    val completedItems: Int = 0,
    val correctAnswers: Int = 0,
    val wrongAnswers: Int = 0,
    val totalAttempts: Int = 0,
    val bestScore: Int = 0,
    val averageScore: Float = 0f,
    val timeSpentSeconds: Long = 0L,
    val lastAccessedTimestamp: Long = System.currentTimeMillis(),
    val difficultyLevel: Int = 1,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val masteryLevel: Float = 0f,
    val xpEarned: Int = 0
)