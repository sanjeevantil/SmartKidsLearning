package com.smartkids.learning.data.model

data class ABCItem(
    val letter: String,
    val word: String,
    val phonetic: String = "",
    fun ABCItem.exampleSentence(): String = "A is for $word"
)

data class NumberItem(
    val number: Int,
    val word: String,
    val countableObject: String = "star"
)

data class ShapeItem(
    val name: String,
    val sides: Int,
    val description: String
)

data class ColorItem(
    val name: String,
    val hexCode: String,
    val exampleObject: String
)

data class AnimalItem(
    val name: String,
    val sound: String,
    val description: String,
    val habitat: String
)

data class FruitItem(
    val name: String,
    val color: String,
    val taste: String
)

data class VegetableItem(
    val name: String,
    val color: String,
    val benefit: String
)

data class BirdItem(
    val name: String,
    val sound: String,
    val canFly: Boolean
)

data class VehicleItem(
    val name: String,
    val wheels: Int,
    val usage: String
)

data class FlowerItem(
    val name: String,
    val color: String,
    val season: String
)

data class BodyPartItem(
    val name: String,
    val count: Int,
    val function: String
)

data class DayItem(
    val name: String,
    val shortName: String,
    val position: Int
)

data class MonthItem(
    val name: String,
    val shortName: String,
    val days: Int,
    val season: String
)

data class SeasonItem(
    val name: String,
    val months: String,
    val weather: String,
    val activities: String
)

data class WeatherItem(
    val name: String,
    val description: String,
    val clothing: String
)

data class OppositeItem(
    val word1: String,
    val word2: String,
    val category: String
)

data class GoodHabitItem(
    val habit: String,
    val description: String,
    val benefit: String
)

data class SafetyRuleItem(
    val rule: String,
    val description: String,
    val example: String
)

data class GKItem(
    val question: String,
    val answer: String,
    val category: String
)

data class ScienceItem(
    val concept: String,
    val explanation: String,
    val example: String
)

data class PlanetItem(
    val name: String,
    val position: Int,
    val description: String,
    val funFact: String
)

data class CountryItem(
    val name: String,
    val capital: String,
    val continent: String,
    val flagColors: String
)

data class RhymeItem(
    val title: String,
    val lines: List<String>
)

data class StoryItem(
    val title: String,
    val paragraphs: List<String>,
    val moral: String
)

data class PhonicsItem(
    val letter: String,
    val sound: String,
    val words: List<String>
)

data class VocabularyItem(
    val word: String,
    val meaning: String,
    val example: String,
    val difficulty: Int
)