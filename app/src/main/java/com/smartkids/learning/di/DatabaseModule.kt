package com.smartkids.learning.di

import android.content.Context
import androidx.room.Room
import com.smartkids.learning.data.local.database.AppDatabase
import com.smartkids.learning.data.local.database.dao.AchievementDao
import com.smartkids.learning.data.local.database.dao.LearningProgressDao
import com.smartkids.learning.data.local.database.dao.TopicDao
import com.smartkids.learning.data.local.database.dao.MistakeTrackingDao
import com.smartkids.learning.data.local.database.dao.DailyChallengeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "smartkids_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLearningProgressDao(database: AppDatabase): LearningProgressDao {
        return database.learningProgressDao()
    }

    @Provides
    fun provideAchievementDao(database: AppDatabase): AchievementDao {
        return database.achievementDao()
    }

    @Provides
    fun provideTopicDao(database: AppDatabase): TopicDao {
        return database.topicDao()
    }

    @Provides
    fun provideMistakeTrackingDao(database: AppDatabase): MistakeTrackingDao {
        return database.mistakeTrackingDao()
    }

    @Provides
    fun provideDailyChallengeDao(database: AppDatabase): DailyChallengeDao {
        return database.dailyChallengeDao()
    }
}