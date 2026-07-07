package com.smartkids.learning.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mistake_tracking")
data class MistakeTrackingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val topicId: String,
    val questionText: String,
    val correctAnswer: String,
    val userAnswer: String,
    val timestamp: Long = System.currentTimeMillis(),
    val timesWrong: Int = 1,
    val timesCorrectAfterWrong: Int = 0,
    val isResolved: Boolean = false
)