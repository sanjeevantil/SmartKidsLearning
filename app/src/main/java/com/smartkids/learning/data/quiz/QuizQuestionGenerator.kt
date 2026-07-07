package com.smartkids.learning.data.quiz

import com.smartkids.learning.data.model.*
import com.smartkids.learning.domain.model.QuestionType
import com.smartkids.learning.domain.model.QuizQuestion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizQuestionGenerator @Inject constructor() {

    fun generateABCQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.abcItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.abcItems
                .filter { it.letter != item.letter }
                .shuffled()
                .take(3)
                .map { it.letter }
            QuizQuestion(
                id = "abc_${item.letter}",
                topicId = "abc_learning",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which letter comes ${getLetterPosition(item.letter)}?",
                correctAnswer = item.letter,
                options = (listOf(item.letter) + wrongOptions).shuffled(),
                audioText = "${item.letter} for ${item.word}",
                difficulty = difficulty
            )
        }
    }

    fun generateNumberQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        val maxNum = when (difficulty) {
            1 -> 10
            2 -> 20
            3 -> 50
            else -> 100
        }
        val items = LearningData.numberItems.filter { it.number <= maxNum }
        return items.shuffled().take(count).map { item ->
            val wrongOptions = items
                .filter { it.number != item.number }
                .shuffled()
                .take(3)
                .map { it.number.toString() }
            QuizQuestion(
                id = "num_${item.number}",
                topicId = "numbers",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "What number is this: ${item.word}?",
                correctAnswer = item.number.toString(),
                options = (listOf(item.number.toString()) + wrongOptions).shuffled(),
                audioText = item.word,
                difficulty = difficulty
            )
        }
    }

    fun generateCountingQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        val maxCount = when (difficulty) {
            1 -> 5
            2 -> 10
            else -> 20
        }
        return (1..maxCount).shuffled().take(count).map { num ->
            val wrongOptions = (1..maxCount)
                .filter { it != num }
                .shuffled()
                .take(3)
                .map { it.toString() }
            QuizQuestion(
                id = "count_$num",
                topicId = "counting",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Count $num ${LearningData.fruitItems.random().name}s. How many are there?",
                correctAnswer = num.toString(),
                options = (listOf(num.toString()) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateAdditionQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        val maxNum = when (difficulty) {
            1 -> 5
            2 -> 10
            3 -> 20
            else -> 50
        }
        val questions = mutableListOf<QuizQuestion>()
        var id = 0
        while (questions.size < count) {
            val a = (1..maxNum).random()
            val b = (1..maxNum).random()
            val answer = a + b
            val wrongOptions = listOf(answer - 1, answer + 1, answer + 2)
                .filter { it != answer && it > 0 }
                .shuffled()
                .take(3)
                .map { it.toString() }
            if (wrongOptions.size == 3) {
                questions.add(
                    QuizQuestion(
                        id = "add_${id++}",
                        topicId = "addition",
                        questionType = QuestionType.MULTIPLE_CHOICE,
                        questionText = "What is $a + $b?",
                        correctAnswer = answer.toString(),
                        options = (listOf(answer.toString()) + wrongOptions).shuffled(),
                        difficulty = difficulty
                    )
                )
            }
        }
        return questions
    }

    fun generateSubtractionQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        val maxNum = when (difficulty) {
            1 -> 5
            2 -> 10
            3 -> 20
            else -> 50
        }
        val questions = mutableListOf<QuizQuestion>()
        var id = 0
        while (questions.size < count) {
            val a = (2..maxNum).random()
            val b = (1..a).random()
            val answer = a - b
            val wrongOptions = listOf(answer + 1, answer - 1, answer + 2)
                .filter { it != answer && it >= 0 }
                .shuffled()
                .take(3)
                .map { it.toString() }
            if (wrongOptions.size == 3) {
                questions.add(
                    QuizQuestion(
                        id = "sub_${id++}",
                        topicId = "subtraction",
                        questionType = QuestionType.MULTIPLE_CHOICE,
                        questionText = "What is $a - $b?",
                        correctAnswer = answer.toString(),
                        options = (listOf(answer.toString()) + wrongOptions).shuffled(),
                        difficulty = difficulty
                    )
                )
            }
        }
        return questions
    }

    fun generateMultiplicationQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        val maxTable = when (difficulty) {
            1 -> 5
            2 -> 9
            else -> 12
        }
        val questions = mutableListOf<QuizQuestion>()
        var id = 0
        while (questions.size < count) {
            val a = (2..maxTable).random()
            val b = (1..maxTable).random()
            val answer = a * b
            val wrongOptions = listOf(answer + a, answer - a, answer + b)
                .filter { it != answer && it > 0 }
                .shuffled()
                .take(3)
                .map { it.toString() }
            if (wrongOptions.size == 3) {
                questions.add(
                    QuizQuestion(
                        id = "mul_${id++}",
                        topicId = "multiplication",
                        questionType = QuestionType.MULTIPLE_CHOICE,
                        questionText = "What is $a × $b?",
                        correctAnswer = answer.toString(),
                        options = (listOf(answer.toString()) + wrongOptions).shuffled(),
                        difficulty = difficulty
                    )
                )
            }
        }
        return questions
    }

    fun generateDivisionQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        val maxNum = when (difficulty) {
            1 -> 20
            2 -> 50
            else -> 100
        }
        val questions = mutableListOf<QuizQuestion>()
        var id = 0
        while (questions.size < count) {
            val b = (2..10).random()
            val answer = (1..(maxNum / b)).random()
            val a = b * answer
            val wrongOptions = listOf(answer + 1, answer - 1, answer + 2)
                .filter { it != answer && it > 0 }
                .shuffled()
                .take(3)
                .map { it.toString() }
            if (wrongOptions.size == 3) {
                questions.add(
                    QuizQuestion(
                        id = "div_${id++}",
                        topicId = "division",
                        questionType = QuestionType.MULTIPLE_CHOICE,
                        questionText = "What is $a ÷ $b?",
                        correctAnswer = answer.toString(),
                        options = (listOf(answer.toString()) + wrongOptions).shuffled(),
                        difficulty = difficulty
                    )
                )
            }
        }
        return questions
    }

    fun generateShapeQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.shapeItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.shapeItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "shape_${item.name}",
                topicId = "shapes",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which shape has ${if (item.sides == 0) "no sides" else "${item.sides} sides"}? ${item.description}",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateColorQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.colorItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.colorItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "color_${item.name}",
                topicId = "colors",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "What color is a ${item.exampleObject}?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateAnimalQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.animalItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.animalItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            val questionText = when (difficulty) {
                1 -> "Which animal says '${item.sound}'?"
                2 -> "Which animal lives in the ${item.habitat} and says '${item.sound}'?"
                else -> "${item.description}. Which animal is this?"
            }
            QuizQuestion(
                id = "animal_${item.name}",
                topicId = "animals",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = questionText,
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                audioText = "The ${item.name} says ${item.sound}",
                difficulty = difficulty
            )
        }
    }

    fun generateFruitQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.fruitItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.fruitItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "fruit_${item.name}",
                topicId = "fruits",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which fruit is ${item.color} and ${item.taste}?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateVegetableQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.vegetableItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.vegetableItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "veg_${item.name}",
                topicId = "vegetables",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which vegetable is ${item.color} and ${item.benefit}?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateBirdQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.birdItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.birdItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "bird_${item.name}",
                topicId = "birds",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which bird says '${item.sound}' and ${if (item.canFly) "can fly" else "cannot fly"}?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateVehicleQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.vehicleItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.vehicleItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "vehicle_${item.name}",
                topicId = "vehicles",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which vehicle is used for ${item.usage}?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateFlowerQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.flowerItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.flowerItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "flower_${item.name}",
                topicId = "flowers",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which flower is ${item.color} and blooms in ${item.season}?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateBodyPartQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.bodyPartItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.bodyPartItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "body_${item.name}",
                topicId = "body_parts",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which body part is used for ${item.function} and we have ${item.count} of them?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateDayQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.dayItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.dayItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "day_${item.name}",
                topicId = "days",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which day is day number ${item.position} of the week?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateMonthQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.monthItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.monthItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "month_${item.name}",
                topicId = "months",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which month has ${item.days} days and comes in ${item.season}?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateSeasonQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.seasonItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.seasonItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "season_${item.name}",
                topicId = "seasons",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which season has ${item.weather} weather and months: ${item.months}?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateWeatherQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.weatherItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.weatherItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            QuizQuestion(
                id = "weather_${item.name}",
                topicId = "weather",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "When it's ${item.name} (${item.description}), you should wear ${item.clothing}. What's the weather?",
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateOppositeQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.oppositeItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.oppositeItems
                .filter { it.word2 != item.word2 }
                .shuffled()
                .take(3)
                .map { it.word2 }
            QuizQuestion(
                id = "opp_${item.word1}",
                topicId = "opposites",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "What is the opposite of '${item.word1}'?",
                correctAnswer = item.word2,
                options = (listOf(item.word2) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateGoodHabitQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.goodHabitItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.goodHabitItems
                .filter { it.habit != item.habit }
                .shuffled()
                .take(3)
                .map { it.habit }
            QuizQuestion(
                id = "habit_${item.habit.replace(" ", "_")}",
                topicId = "good_habits",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which habit ${item.description}?",
                correctAnswer = item.habit,
                options = (listOf(item.habit) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateSafetyQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.safetyRules.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.safetyRules
                .filter { it.rule != item.rule }
                .shuffled()
                .take(3)
                .map { it.rule }
            QuizQuestion(
                id = "safety_${item.rule.replace(" ", "_")}",
                topicId = "safety",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = "Which safety rule: ${item.description}?",
                correctAnswer = item.rule,
                options = (listOf(item.rule) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateGKQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.gkItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.gkItems
                .filter { it.answer != item.answer }
                .shuffled()
                .take(3)
                .map { it.answer }
            QuizQuestion(
                id = "gk_${item.question.hashCode()}",
                topicId = "general_knowledge",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = item.question,
                correctAnswer = item.answer,
                options = (listOf(item.answer) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generatePlanetQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.planetItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.planetItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            val qText = when (difficulty) {
                1 -> "Which planet is at position ${item.position} from the Sun?"
                2 -> "Which planet is described as: ${item.description}?"
                else -> "Fun fact: ${item.funFact}. Which planet?"
            }
            QuizQuestion(
                id = "planet_${item.name}",
                topicId = "solar_system",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = qText,
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateCountryQuestions(count: Int, difficulty: Int): List<QuizQuestion> {
        return LearningData.countryItems.shuffled().take(count).map { item ->
            val wrongOptions = LearningData.countryItems
                .filter { it.name != item.name }
                .shuffled()
                .take(3)
                .map { it.name }
            val qText = when (difficulty) {
                1 -> "What is the capital of ${item.name}?"
                2 -> "Which country's capital is ${item.capital} and is in ${item.continent}?"
                else -> "Which country has flag colors: ${item.flagColors}?"
            }
            QuizQuestion(
                id = "country_${item.name}",
                topicId = "countries_flags",
                questionType = QuestionType.MULTIPLE_CHOICE,
                questionText = qText,
                correctAnswer = item.name,
                options = (listOf(item.name) + wrongOptions).shuffled(),
                difficulty = difficulty
            )
        }
    }

    fun generateQuestionsForTopic(topicId: String, count: Int, difficulty: Int): List<QuizQuestion> {
        return when (topicId) {
            "abc_learning" -> generateABCQuestions(count, difficulty)
            "numbers" -> generateNumberQuestions(count, difficulty)
            "counting" -> generateCountingQuestions(count, difficulty)
            "addition" -> generateAdditionQuestions(count, difficulty)
            "subtraction" -> generateSubtractionQuestions(count, difficulty)
            "multiplication" -> generateMultiplicationQuestions(count, difficulty)
            "division" -> generateDivisionQuestions(count, difficulty)
            "shapes" -> generateShapeQuestions(count, difficulty)
            "colors" -> generateColorQuestions(count, difficulty)
            "animals" -> generateAnimalQuestions(count, difficulty)
            "fruits" -> generateFruitQuestions(count, difficulty)
            "vegetables" -> generateVegetableQuestions(count, difficulty)
            "birds" -> generateBirdQuestions(count, difficulty)
            "vehicles" -> generateVehicleQuestions(count, difficulty)
            "flowers" -> generateFlowerQuestions(count, difficulty)
            "body_parts" -> generateBodyPartQuestions(count, difficulty)
            "days" -> generateDayQuestions(count, difficulty)
            "months" -> generateMonthQuestions(count, difficulty)
            "seasons" -> generateSeasonQuestions(count, difficulty)
            "weather" -> generateWeatherQuestions(count, difficulty)
            "opposites" -> generateOppositeQuestions(count, difficulty)
            "good_habits" -> generateGoodHabitQuestions(count, difficulty)
            "safety" -> generateSafetyQuestions(count, difficulty)
            "general_knowledge" -> generateGKQuestions(count, difficulty)
            "solar_system" -> generatePlanetQuestions(count, difficulty)
            "countries_flags" -> generateCountryQuestions(count, difficulty)
            "alphabet_tracing", "hindi_learning", "phonics", "vocabulary",
            "speaking", "listening", "reading", "writing",
            "science_basics", "maps", "time_learning", "calendar",
            "rhymes", "stories" -> generateGKQuestions(count, difficulty)
            else -> generateGKQuestions(count, difficulty)
        }
    }

    private fun getLetterPosition(letter: String): String {
        val index = letter.first() - 'A' + 1
        return when (index) {
            1 -> "first in the alphabet"
            2 -> "second in the alphabet"
            3 -> "third in the alphabet"
            in 4..10 -> "at position $index"
            else -> "at position $index"
        }
    }
}