package com.smartkids.learning.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smartkids.learning.data.local.database.dao.AchievementDao
import com.smartkids.learning.data.local.database.dao.DailyChallengeDao
import com.smartkids.learning.data.local.database.dao.LearningProgressDao
import com.smartkids.learning.data.local.database.dao.MistakeTrackingDao
import com.smartkids.learning.data.local.database.dao.TopicDao
import com.smartkids.learning.data.local.database.entity.AchievementEntity
import com.smartkids.learning.data.local.database.entity.DailyChallengeEntity
import com.smartkids.learning.data.local.database.entity.LearningProgressEntity
import com.smartkids.learning.data.local.database.entity.MistakeTrackingEntity
import com.smartkids.learning.data.local.database.entity.TopicEntity

@Database(
    entities = [
        LearningProgressEntity::class,
        AchievementEntity::class,
        TopicEntity::class,
        MistakeTrackingEntity::class,
        DailyChallengeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun learningProgressDao(): LearningProgressDao
    abstract fun achievementDao(): AchievementDao
    abstract fun topicDao(): TopicDao
    abstract fun mistakeTrackingDao(): MistakeTrackingDao
    abstract fun dailyChallengeDao(): DailyChallengeDao
}