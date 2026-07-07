package com.smartkids.learning.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey val topicId: String,
    val name: String,
    val nameResKey: String,
    val categoryId: String,
    val categoryName: String,
    val description: String,
    val iconName: String,
    val primaryColor: Long,
    val secondaryColor: Long,
    val sortOrder: Int = 0,
    val minAge: Int = 2,
    val maxAge: Int = 10,
    val difficulty: Int = 1,
    val totalLessons: Int = 0,
    val isPremium: Boolean = false
)