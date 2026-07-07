package com.smartkids.learning.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val achievementId: String,
    val title: String,
    val description: String,
    val iconName: String,
    val category: String,
    val requirementType: String,
    val requirementValue: Int,
    val currentValue: Int = 0,
    val isUnlocked: Boolean = false,
    val unlockedTimestamp: Long? = null,
    val xpReward: Int = 0,
    val coinReward: Int = 0
)