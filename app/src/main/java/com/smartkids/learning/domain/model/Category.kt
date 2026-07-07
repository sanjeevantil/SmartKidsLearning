package com.smartkids.learning.domain.model

data class Category(
    val categoryId: String,
    val name: String,
    val description: String,
    val iconName: String,
    val color: Long,
    val topics: List<Topic> = emptyList()
)