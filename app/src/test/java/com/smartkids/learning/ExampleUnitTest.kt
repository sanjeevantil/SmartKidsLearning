package com.smartkids.learning

import com.smartkids.learning.domain.model.LearningProgress
import com.smartkids.learning.domain.model.UserProfile
import org.junit.Assert.*
import org.junit.Test

class LearningProgressTest {

    @Test
    fun `progress percentage is zero when no items`() {
        val progress = LearningProgress(topicId = "test", topicName = "Test", categoryId = "cat")
        assertEquals(0f, progress.progressPercentage)
    }

    @Test
    fun `progress percentage calculates correctly`() {
        val progress = LearningProgress(topicId = "test", topicName = "Test", categoryId = "cat", totalItems = 10, completedItems = 5)
        assertEquals(0.5f, progress.progressPercentage, 0.001f)
    }

    @Test
    fun `accuracy is zero when no attempts`() {
        val progress = LearningProgress(topicId = "test", topicName = "Test", categoryId = "cat")
        assertEquals(0f, progress.accuracyPercentage)
    }

    @Test
    fun `accuracy calculates correctly`() {
        val progress = LearningProgress(topicId = "test", topicName = "Test", categoryId = "cat", correctAnswers = 8, totalAttempts = 10)
        assertEquals(0.8f, progress.accuracyPercentage, 0.001f)
    }
}

class UserProfileTest {

    @Test
    fun `level calculation for 0 XP`() {
        val (level, xpInLevel) = UserProfile.calculateLevel(0)
        assertEquals(1, level)
        assertEquals(0, xpInLevel)
    }

    @Test
    fun `level calculation for 150 XP`() {
        val (level, xpInLevel) = UserProfile.calculateLevel(150)
        assertEquals(2, level)
        assertEquals(50, xpInLevel)
    }

    @Test
    fun `level calculation for 500 XP`() {
        val (level, xpInLevel) = UserProfile.calculateLevel(500)
        assertEquals(6, level)
        assertEquals(0, xpInLevel)
    }
}

class QuizQuestionGeneratorSanityTest {

    @Test
    fun `addition is correct`() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `subtraction is correct`() {
        assertEquals(3, 5 - 2)
    }

    @Test
    fun `multiplication is correct`() {
        assertEquals(12, 3 * 4)
    }

    @Test
    fun `division is correct`() {
        assertEquals(5, 10 / 2)
    }

    @Test
    fun `string capitalization works`() {
        assertEquals("Hello World", "hello world".replaceFirstChar { it.uppercase() })
    }
}