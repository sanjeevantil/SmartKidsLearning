package com.smartkids.learning.domain.repository

import com.smartkids.learning.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {

    fun getAllTopics(): Flow<List<Topic>>
    fun getTopicsByCategory(categoryId: String): Flow<List<Topic>>
    suspend fun getTopicById(topicId: String): Topic?

    suspend fun seedTopics()
}