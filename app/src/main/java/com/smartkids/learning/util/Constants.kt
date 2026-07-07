package com.smartkids.learning.util

object Constants {
    const val XP_PER_CORRECT_ANSWER = 10
    const val COINS_PER_CORRECT_ANSWER = 5
    const val XP_DIFFICULTY_MULTIPLIER = 1.5f
    const val STREAK_BONUS_XP = 20
    const val DAILY_CHALLENGE_BONUS_XP = 50
    const val LEVEL_XP_REQUIRED = 100
    const val MASTERY_THRESHOLD_HIGH = 0.8f
    const val MASTERY_THRESHOLD_LOW = 0.3f
    const val MISTAKE_RESOLUTION_CORRECT_COUNT = 3
    const val MIN_QUESTIONS_PER_SESSION = 5
    const val MAX_QUESTIONS_PER_SESSION = 20

    object Categories {
        const val LANGUAGE = "Language"
        const val MATH = "Math"
        const val WORLD = "World"
        const val TIME = "Time"
        const val LIFE_SKILLS = "Life Skills"
        const val KNOWLEDGE = "Knowledge"
        const val PRACTICE = "Practice"
    }

    object GameTypes {
        const val QUIZ = "quiz"
        const val MEMORY = "memory"
        const val MATCHING = "matching"
        const val BALLOON_POP = "balloon_pop"
        const val DRAG_DROP = "drag_drop"
        const val FLASH_CARDS = "flash_cards"
        const val PUZZLE = "puzzle"
        const val WORD_BUILDER = "word_builder"
        const val RAPID_QUIZ = "rapid_quiz"
        const val TIMED_CHALLENGE = "timed_challenge"
        const val BUBBLE_POP = "bubble_pop"
        const val MAZE = "maze"
        const val COLOR_MATCH = "color_match"
        const val FRUIT_CATCH = "fruit_catch"
        const val SPOT_DIFFERENCE = "spot_difference"
        const val FIND_OBJECT = "find_object"
    }
}