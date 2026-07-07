package com.smartkids.learning.di

import com.smartkids.learning.data.local.database.dao.AchievementDao
import com.smartkids.learning.data.local.database.dao.DailyChallengeDao
import com.smartkids.learning.data.local.database.dao.LearningProgressDao
import com.smartkids.learning.data.local.database.dao.MistakeTrackingDao
import com.smartkids.learning.data.local.database.dao.TopicDao
import com.smartkids.learning.data.local.datastore.UserPreferencesDataStore
import com.smartkids.learning.data.repository.AchievementRepositoryImpl
import com.smartkids.learning.data.repository.DailyChallengeRepositoryImpl
import com.smartkids.learning.data.repository.LearningRepositoryImpl
import com.smartkids.learning.data.repository.MistakeTrackingRepositoryImpl
import com.smartkids.learning.data.repository.TopicRepositoryImpl
import com.smartkids.learning.data.repository.UserPreferencesRepositoryImpl
import com.smartkids.learning.domain.repository.AchievementRepository
import com.smartkids.learning.domain.repository.DailyChallengeRepository
import com.smartkids.learning.domain.repository.LearningRepository
import com.smartkids.learning.domain.repository.MistakeTrackingRepository
import com.smartkids.learning.domain.repository.TopicRepository
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLearningRepository(
        dao: LearningProgressDao,
        dataStore: UserPreferencesDataStore
    ): LearningRepository {
        return LearningRepositoryImpl(dao, dataStore)
    }

    @Provides
    @Singleton
    fun provideAchievementRepository(
        dao: AchievementDao
    ): AchievementRepository {
        return AchievementRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideTopicRepository(
        dao: TopicDao
    ): TopicRepository {
        return TopicRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideMistakeTrackingRepository(
        dao: MistakeTrackingDao
    ): MistakeTrackingRepository {
        return MistakeTrackingRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideDailyChallengeRepository(
        dao: DailyChallengeDao
    ): DailyChallengeRepository {
        return DailyChallengeRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        dataStore: UserPreferencesDataStore
    ): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(dataStore)
    }
}