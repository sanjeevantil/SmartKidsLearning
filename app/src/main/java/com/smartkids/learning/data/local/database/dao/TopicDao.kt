package com.smartkids.learning.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smartkids.learning.data.local.database.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    @Query("SELECT * FROM topics ORDER BY categoryId, sortOrder ASC")
    fun getAllTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE categoryId = :categoryId ORDER BY sortOrder ASC")
    fun getTopicsByCategory(categoryId: String): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE topicId = :topicId")
    suspend fun getTopicById(topicId: String): TopicEntity?

    @Query("SELECT DISTINCT categoryId FROM topics ORDER BY categoryId")
    fun getAllCategoryIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<TopicEntity>)

    @Query("DELETE FROM topics")
    suspend fun deleteAllTopics()
}