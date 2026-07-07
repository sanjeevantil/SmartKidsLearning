package com.smartkids.learning

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.smartkids.learning.domain.repository.AchievementRepository
import com.smartkids.learning.domain.repository.TopicRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SmartKidsApp : Application() {

    @Inject lateinit var topicRepository: TopicRepository
    @Inject lateinit var achievementRepository: AchievementRepository

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        seedData()
    }

    private fun seedData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                topicRepository.seedTopics()
                achievementRepository.seedAchievements()
            } catch (_: Exception) {
                // Already seeded or error — safe to ignore
            }
        }
    }

    private fun createNotificationChannels() {
        val channel = NotificationChannel(
            CHANNEL_DAILY_REMINDER,
            "Daily Learning Reminder",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Reminds your child to practice daily"
        }
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_DAILY_REMINDER = "daily_reminder"
        const val DEFAULT_PARENT_PIN = "1234"
    }
}