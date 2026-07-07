package com.smartkids.learning.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_challenges")
data class DailyChallengeEntity(
    @PrimaryKey val dateStamp: String,
    val challengeType: String,
    val topicId: String,
    val difficulty: Int = 1,
    val questions: String,
    val isCompleted: Boolean = false,
    val score: Int = 0,
    val xpEarned: Int = 0,
    val coinEarned: Int = 0,
    val completedTimestamp: Long? = null
)