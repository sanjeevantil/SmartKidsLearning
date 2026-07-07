package com.smartkids.learning.ui.screens.learning

import androidx.lifecycle.ViewModel
import com.smartkids.learning.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class FlashCardState(
    val frontText: String = "",
    val backText: String = "",
    val currentIndex: Int = 0,
    val total: Int = 0,
    val isFlipped: Boolean = false,
    val items: List<Pair<String, String>> = emptyList()
)

@HiltViewModel
class FlashCardViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(FlashCardState())
    val state: StateFlow<FlashCardState> = _state.asStateFlow()

    fun loadCards(topicId: String) {
        val pairs: List<Pair<String, String>> = when (topicId) {
            "abc_learning" -> LearningData.abcItems.map { it.letter to "Word: ${it.word}\n${it.letter} is for ${it.word}" }
            "animals" -> LearningData.animalItems.map { it.name to "Sound: ${it.sound}\nHabitat: ${it.habitat}\n${it.description}" }
            "fruits" -> LearningData.fruitItems.map { it.name to "Color: ${it.color}\nTaste: ${it.taste}" }
            "colors" -> LearningData.colorItems.map { it.name to "Hex: ${it.hexCode}\nExample: ${it.exampleObject}" }
            "shapes" -> LearningData.shapeItems.map { it.name to "Sides: ${if (it.sides == 0) "None (round)" else it.sides}\n${it.description}" }
            "numbers" -> LearningData.numberItems.map { it.number.toString() to "Word: ${it.word}" }
            "birds" -> LearningData.birdItems.map { it.name to "Sound: ${it.sound}\nCan fly: ${it.canFly}" }
            "vegetables" -> LearningData.vegetableItems.map { it.name to "Color: ${it.color}\nBenefit: ${it.benefit}" }
            "vehicles" -> LearningData.vehicleItems.map { it.name to "Wheels: ${it.wheels}\nUsage: ${it.usage}" }
            "flowers" -> LearningData.flowerItems.map { it.name to "Color: ${it.color}\nSeason: ${it.season}" }
            "body_parts" -> LearningData.bodyPartItems.map { it.name to "Count: ${it.count}\nFunction: ${it.function}" }
            "days" -> LearningData.dayItems.map { it.name to "Short: ${it.shortName}\nDay ${it.position} of the week" }
            "months" -> LearningData.monthItems.map { it.name to "Short: ${it.shortName}\nDays: ${it.days}\nSeason: ${it.season}" }
            "seasons" -> LearningData.seasonItems.map { it.name to "Weather: ${it.weather}\nMonths: ${it.months}\nActivities: ${it.activities}" }
            "weather" -> LearningData.weatherItems.map { it.name to "${it.description}\nWear: ${it.clothing}" }
            "opposites" -> LearningData.oppositeItems.map { it.word1 to "Opposite: ${it.word2}\nCategory: ${it.category}" }
            "good_habits" -> LearningData.goodHabitItems.map { it.habit to "How: ${it.description}\nBenefit: ${it.benefit}" }
            "safety" -> LearningData.safetyRules.map { it.rule to "Why: ${it.description}\nExample: ${it.example}" }
            "general_knowledge" -> LearningData.gkItems.map { it.question to "Answer: ${it.answer}\nCategory: ${it.category}" }
            "science_basics" -> ExtraLearningData.scienceItems.map { it.concept to "Explanation: ${it.explanation}\nExample: ${it.example}" }
            "solar_system" -> LearningData.planetItems.map { it.name to "Position: ${it.position} from Sun\n${it.description}\nFun Fact: ${it.funFact}" }
            "countries_flags" -> LearningData.countryItems.map { it.name to "Capital: ${it.capital}\nContinent: ${it.continent}\nFlag Colors: ${it.flagColors}" }
            "maps" -> ExtraLearningData.mapItems.map { it.name to "Definition: ${it.definition}\nExample: ${it.example}" }
            "time_learning" -> ExtraLearningData.timeLearningItems.map { it.time to "Spoken: ${it.spokenForm}\n${it.description}" }
            "calendar" -> ExtraLearningData.calendarItems.map { it.term to "Meaning: ${it.meaning}\nExample: ${it.example}" }
            "hindi_learning" -> ExtraLearningData.hindiVarnmala.map { it.varnam to "Sound: ${it.sound}\nExample: ${it.exampleWord}\nWords: ${it.exampleWords.joinToString(", ")}" }
            "rhymes" -> LearningData.rhymeItems.map { it.title to it.lines.joinToString("\n") }
            "stories" -> LearningData.storyItems.map { it.title to it.paragraphs.joinToString("\n\n") + "\n\n★ Moral: ${it.moral}" }
            "phonics" -> LearningData.phonicsItems.map { it.letter to "Sound: ${it.sound}\nWords: ${it.words.joinToString(", ")}" }
            "vocabulary" -> LearningData.vocabularyItems.map { it.word to "Meaning: ${it.meaning}\nExample: ${it.example}\nDifficulty: ${it.difficulty}/4" }
            "speaking" -> ExtraLearningData.speakingPracticeItems.map { it.sentence to "Instruction: ${it.instruction}\nExample: ${it.example}" }
            "listening" -> ExtraLearningData.listeningPracticeItems.map { it.sentence to "Question: ${it.question}\nKey word: ${it.keyWord}\nOptions: ${it.options.joinToString(", ")}" }
            "reading" -> ExtraLearningData.readingPracticeItems.map { it.sentence to "Key words: ${it.keyWords}\nDifficulty: ${it.difficulty}/4" }
            "writing" -> ExtraLearningData.writingPracticeItems.map { it.prompt to "Instruction: ${it.instruction}\nExample: ${it.example}" }
            "addition", "subtraction", "multiplication", "division",
            "counting", "alphabet_tracing" -> LearningData.animalItems.map { it.name to it.description }
            else -> LearningData.animalItems.map { it.name to it.description }
        }
        if (pairs.isNotEmpty()) {
            _state.value = FlashCardState(
                frontText = pairs.first().first,
                backText = pairs.first().second,
                total = pairs.size,
                items = pairs
            )
        }
    }

    fun flip() { _state.value = _state.value.copy(isFlipped = !_state.value.isFlipped) }

    fun next() {
        val s = _state.value
        if (s.currentIndex < s.total - 1) {
            val n = s.currentIndex + 1
            _state.value = s.copy(currentIndex = n, frontText = s.items[n].first, backText = s.items[n].second, isFlipped = false)
        }
    }

    fun prev() {
        val s = _state.value
        if (s.currentIndex > 0) {
            val n = s.currentIndex - 1
            _state.value = s.copy(currentIndex = n, frontText = s.items[n].first, backText = s.items[n].second, isFlipped = false)
        }
    }
}