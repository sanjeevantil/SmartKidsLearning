package com.smartkids.learning.domain.model

data class QuizQuestion(
    val id: String,
    val topicId: String,
    val questionType: QuestionType,
    val questionText: String,
    val correctAnswer: String,
    val options: List<String>,
    val imagePath: String? = null,
    val audioText: String? = null,
    val difficulty: Int = 1,
    val hint: String? = null
)

enum class QuestionType {
    MULTIPLE_CHOICE,
    TRUE_FALSE,
    FILL_IN_THE_BLANK,
    IMAGE_BASED,
    AUDIO_BASED,
    DRAG_DROP,
    MATCHING,
    SORTING
}