package com.smartkids.learning.domain.model

data class Topic(
    val topicId: String,
    val name: String,
    val categoryId: String,
    val categoryName: String,
    val description: String,
    val iconName: String,
    val primaryColor: Long,
    val secondaryColor: Long,
    val minAge: Int = 2,
    val maxAge: Int = 10,
    val difficulty: Int = 1,
    val totalLessons: Int = 0,
    val isPremium: Boolean = false,
    val progress: LearningProgress? = null
)