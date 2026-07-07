package com.smartkids.learning.data.repository

import com.smartkids.learning.data.local.database.dao.TopicDao
import com.smartkids.learning.data.local.database.entity.TopicEntity
import com.smartkids.learning.domain.model.Topic
import com.smartkids.learning.domain.repository.TopicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicRepositoryImpl @Inject constructor(
    private val dao: TopicDao
) : TopicRepository {

    override fun getAllTopics(): Flow<List<Topic>> {
        return dao.getAllTopics().map { list -> list.map { it.toDomain() } }
    }

    override fun getTopicsByCategory(categoryId: String): Flow<List<Topic>> {
        return dao.getTopicsByCategory(categoryId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getTopicById(topicId: String): Topic? {
        return dao.getTopicById(topicId)?.toDomain()
    }

    override suspend fun seedTopics() {
        val existing = dao.getAllTopics()
        if (existing.firstOrNull()?.isNotEmpty() == true) return
        dao.insertTopics(allTopics)
    }

    private val allTopics: List<TopicEntity>
        get() = listOf(
            TopicEntity("abc_learning", "ABC Learning", "abc_learning", "Language", "Language", "Learn English alphabets A-Z", "alphabet", 0xFFFF6B35, 0xFFFFD166, 2, 6, 1, 26),
            TopicEntity("alphabet_tracing", "Alphabet Tracing", "alphabet_tracing", "Language", "Language", "Trace letters with your finger", "draw", 0xFFFF6B35, 0xFFFFD166, 2, 6, 1, 26),
            TopicEntity("hindi_learning", "Hindi Learning", "hindi_learning", "Language", "Language", "Learn Hindi alphabets", "language", 0xFFFF6B35, 0xFFFFD166, 3, 10, 1, 26),
            TopicEntity("numbers", "Numbers", "numbers", "Math", "Math", "Learn numbers 0-100", "pin", 0xFF00B4D8, 0xFF90E0EF, 2, 6, 1, 20),
            TopicEntity("counting", "Counting", "counting", "Math", "Math", "Count objects from 1-20", "calculate", 0xFF00B4D8, 0xFF90E0EF, 2, 5, 1, 15),
            TopicEntity("addition", "Addition", "addition", "Math", "Math", "Learn addition with fun", "add_circle", 0xFF00B4D8, 0xFF90E0EF, 4, 7, 2, 20),
            TopicEntity("subtraction", "Subtraction", "subtraction", "Math", "Math", "Learn subtraction easily", "remove_circle", 0xFF00B4D8, 0xFF90E0EF, 4, 7, 2, 20),
            TopicEntity("multiplication", "Multiplication", "multiplication", "Math", "Math", "Multiplication tables made fun", "close", 0xFF00B4D8, 0xFF90E0EF, 6, 10, 3, 20),
            TopicEntity("division", "Division", "division", "Math", "Math", "Division made simple", "crop_square", 0xFF00B4D8, 0xFF90E0EF, 7, 10, 4, 20),
            TopicEntity("shapes", "Shapes", "shapes", "World", "World", "Learn basic shapes", "category", 0xFF7B2FF7, 0xFFC77DFF, 2, 6, 1, 12),
            TopicEntity("colors", "Colors", "colors", "World", "World", "Learn all colors", "palette", 0xFF7B2FF7, 0xFFC77DFF, 2, 4, 1, 12),
            TopicEntity("fruits", "Fruits", "fruits", "World", "World", "Discover delicious fruits", "apple", 0xFF06D6A0, 0xFFB7E4C7, 2, 6, 1, 15),
            TopicEntity("vegetables", "Vegetables", "vegetables", "World", "World", "Learn about vegetables", "eco", 0xFF06D6A0, 0xFFB7E4C7, 2, 6, 1, 15),
            TopicEntity("animals", "Animals", "animals", "World", "World", "Meet amazing animals", "pets", 0xFF06D6A0, 0xFFB7E4C7, 2, 8, 1, 20),
            TopicEntity("birds", "Birds", "birds", "World", "World", "Learn about birds", "flutter_dash", 0xFF06D6A0, 0xFFB7E4C7, 3, 8, 1, 15),
            TopicEntity("vehicles", "Vehicles", "vehicles", "World", "World", "Explore vehicles", "directions_car", 0xFF06D6A0, 0xFFB7E4C7, 2, 6, 1, 12),
            TopicEntity("flowers", "Flowers", "flowers", "World", "World", "Beautiful flowers", "local_florist", 0xFF06D6A0, 0xFFB7E4C7, 3, 8, 1, 12),
            TopicEntity("body_parts", "Body Parts", "body_parts", "World", "World", "Learn body parts", "accessibility_new", 0xFF06D6A0, 0xFFB7E4C7, 2, 6, 1, 15),
            TopicEntity("days", "Days", "days", "Time", "Time", "Days of the week", "calendar_today", 0xFFFF6B9D, 0xFFFFC2D4, 3, 6, 1, 7),
            TopicEntity("months", "Months", "months", "Time", "Time", "Months of the year", "date_range", 0xFFFF6B9D, 0xFFFFC2D4, 3, 6, 1, 12),
            TopicEntity("seasons", "Seasons", "seasons", "Time", "Time", "Four seasons", "wb_sunny", 0xFFFF6B9D, 0xFFFFC2D4, 3, 7, 1, 4),
            TopicEntity("weather", "Weather", "weather", "Time", "Time", "Different weather types", "cloud", 0xFFFF6B9D, 0xFFFFC2D4, 3, 7, 1, 8),
            TopicEntity("opposites", "Opposites", "opposites", "Language", "Language", "Learn opposites", "swap_horiz", 0xFFFF6B35, 0xFFFFD166, 3, 7, 2, 15),
            TopicEntity("good_habits", "Good Habits", "good_habits", "Life Skills", "Life Skills", "Build good habits", "volunteer_activism", 0xFFEF476F, 0xFFFFB3C6, 3, 8, 1, 10),
            TopicEntity("safety", "Safety", "safety", "Life Skills", "Life Skills", "Stay safe", "shield", 0xFFEF476F, 0xFFFFB3C6, 3, 8, 1, 10),
            TopicEntity("general_knowledge", "General Knowledge", "general_knowledge", "Knowledge", "Knowledge", "Fun facts and knowledge", "lightbulb", 0xFFFF9F1C, 0xFFFFE0A3, 4, 10, 2, 20),
            TopicEntity("science_basics", "Science Basics", "science_basics", "Knowledge", "Knowledge", "Simple science concepts", "science", 0xFFFF9F1C, 0xFFFFE0A3, 5, 10, 3, 15),
            TopicEntity("solar_system", "Solar System", "solar_system", "Knowledge", "Knowledge", "Explore our solar system", "public", 0xFFFF9F1C, 0xFFFFE0A3, 5, 10, 3, 8),
            TopicEntity("countries_flags", "Countries & Flags", "countries_flags", "Knowledge", "Knowledge", "Countries and their flags", "flag", 0xFFFF9F1C, 0xFFFFE0A3, 5, 10, 3, 20),
            TopicEntity("maps", "Maps", "maps", "Knowledge", "Knowledge", "Understand maps", "map", 0xFFFF9F1C, 0xFFFFE0A3, 6, 10, 3, 10),
            TopicEntity("time_learning", "Time Learning", "time_learning", "Time", "Time", "Learn to tell time", "schedule", 0xFFFF6B9D, 0xFFFFC2D4, 5, 8, 2, 10),
            TopicEntity("calendar", "Calendar", "calendar", "Time", "Time", "Understand the calendar", "event_note", 0xFFFF6B9D, 0xFFFFC2D4, 4, 8, 2, 8),
            TopicEntity("rhymes", "Rhymes", "rhymes", "Language", "Language", "Fun nursery rhymes", "music_note", 0xFFFF6B35, 0xFFFFD166, 2, 5, 1, 15),
            TopicEntity("stories", "Stories", "stories", "Language", "Language", "Interesting stories", "auto_stories", 0xFFFF6B35, 0xFFFFD166, 3, 7, 1, 10),
            TopicEntity("phonics", "Phonics", "phonics", "Language", "Language", "Learn letter sounds", "record_voice_over", 0xFFFF6B35, 0xFFFFD166, 3, 6, 2, 26),
            TopicEntity("vocabulary", "Vocabulary Builder", "vocabulary", "Language", "Language", "Build your vocabulary", "menu_book", 0xFFFF6B35, 0xFFFFD166, 4, 10, 2, 30),
            TopicEntity("speaking", "Speaking Practice", "speaking", "Practice", "Practice", "Practice speaking", "mic", 0xFF00B4D8, 0xFF90E0EF, 4, 10, 2, 20),
            TopicEntity("listening", "Listening Practice", "listening", "Practice", "Practice", "Practice listening", "headphones", 0xFF00B4D8, 0xFF90E0EF, 4, 10, 2, 20),
            TopicEntity("reading", "Reading Practice", "reading", "Practice", "Practice", "Practice reading", "chrome_reader_mode", 0xFF00B4D8, 0xFF90E0EF, 4, 10, 2, 20),
            TopicEntity("writing", "Writing Practice", "writing", "Practice", "Practice", "Practice writing", "edit", 0xFF00B4D8, 0xFF90E0EF, 4, 10, 2, 20)
        )

    private fun TopicEntity.toDomain() = Topic(
        topicId = topicId,
        name = name,
        categoryId = categoryId,
        categoryName = categoryName,
        description = description,
        iconName = iconName,
        primaryColor = primaryColor,
        secondaryColor = secondaryColor,
        minAge = minAge,
        maxAge = maxAge,
        difficulty = difficulty,
        totalLessons = totalLessons,
        isPremium = isPremium
    )
}