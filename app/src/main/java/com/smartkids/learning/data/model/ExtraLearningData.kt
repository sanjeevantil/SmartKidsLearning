package com.smartkids.learning.data.model

object ExtraLearningData {

    val scienceItems: List<ScienceItem> = listOf(
        ScienceItem("Plants need sunlight", "Plants use sunlight to make their food through a process called photosynthesis", "A plant kept in a dark room will grow weak and die"),
        ScienceItem("Water boils at 100°C", "When water reaches 100 degrees Celsius, it turns into steam", "When you boil water for tea, you see steam rising"),
        ScienceItem("Gravity pulls things down", "Gravity is a force that pulls everything towards the Earth", "When you throw a ball up, it comes back down"),
        ScienceItem("The Sun gives us light", "The Sun is a star that provides heat and light to Earth", "We feel warm in sunlight but cold in shade"),
        ScienceItem("Ice is frozen water", "When water gets very cold (below 0°C), it becomes ice", "Ice cubes in your drink are frozen water"),
        ScienceItem("Air is everywhere", "Air is all around us even though we cannot see it", "You can feel air when the wind blows"),
        ScienceItem("Magnets attract iron", "Magnets pull things made of iron or steel towards them", "A magnet can pick up paper clips"),
        ScienceItem("Shadow needs light", "A shadow forms when an object blocks light", "Your shadow is longest in the morning and evening"),
        ScienceItem("Sound travels through air", "Sound needs a medium like air to travel", "You hear thunder after seeing lightning because sound is slower"),
        ScienceItem("Living things grow", "All living things like plants and animals grow over time", "A baby grows into a child, then an adult"),
        ScienceItem("Earth rotates on its axis", "Earth spins around an imaginary line called its axis", "This rotation causes day and night"),
        ScienceItem("Moon reflects sunlight", "The Moon does not have its own light, it reflects the Sun's light", "We see different shapes of the Moon because of its position"),
        ScienceItem("Rain comes from clouds", "Water evaporates from oceans, forms clouds, and falls as rain", "The water cycle keeps repeating"),
        ScienceItem("Heart pumps blood", "The heart is a muscle that pumps blood through our body", "You can feel your heartbeat by placing hand on chest"),
        ScienceItem("Bones support our body", "Our skeleton made of bones gives our body shape and support", "An adult has 206 bones")
    )

    val timeLearningItems: List<TimeLearningItem> = listOf(
        TimeLearningItem("1:00", "One o'clock", "The short hand points to 1, long hand points to 12"),
        TimeLearningItem("2:00", "Two o'clock", "The short hand points to 2, long hand points to 12"),
        TimeLearningItem("3:00", "Three o'clock", "The short hand points to 3, long hand points to 12"),
        TimeLearningItem("4:00", "Four o'clock", "The short hand points to 4, long hand points to 12"),
        TimeLearningItem("5:00", "Five o'clock", "The short hand points to 5, long hand points to 12"),
        TimeLearningItem("6:00", "Six o'clock", "The short hand points to 6, long hand points to 12"),
        TimeLearningItem("7:00", "Seven o'clock", "The short hand points to 7, long hand points to 12"),
        TimeLearningItem("8:00", "Eight o'clock", "The short hand points to 8, long hand points to 12"),
        TimeLearningItem("9:00", "Nine o'clock", "The short hand points to 9, long hand points to 12"),
        TimeLearningItem("10:00", "Ten o'clock", "The short hand points to 10, long hand points to 12"),
        TimeLearningItem("11:00", "Eleven o'clock", "The short hand points to 11, long hand points to 12"),
        TimeLearningItem("12:00", "Twelve o'clock", "Both hands point to 12 - this is noon or midnight"),
        TimeLearningItem("Half past 1", "1:30", "The short hand is between 1 and 2, long hand points to 6"),
        TimeLearningItem("Half past 2", "2:30", "The short hand is between 2 and 3, long hand points to 6"),
        TimeLearningItem("Quarter past 3", "3:15", "The short hand is slightly past 3, long hand points to 3")
    )

    val calendarItems: List<CalendarLearningItem> = listOf(
        CalendarLearningItem("Today", "The current day we are in", "Look at a calendar to find today's date"),
        CalendarLearningItem("Yesterday", "The day before today", "If today is Monday, yesterday was Sunday"),
        CalendarLearningItem("Tomorrow", "The day after today", "If today is Monday, tomorrow is Tuesday"),
        CalendarLearningItem("Week", "7 days make a week", "Monday to Sunday is one week"),
        CalendarLearningItem("Month", "About 30 days make a month", "January, February, March are months"),
        CalendarLearningItem("Year", "12 months make a year", "A year has 365 days (366 in a leap year)"),
        CalendarLearningItem("Date", "The specific day number", "Today's date could be 15th January"),
        CalendarLearningItem("Leap Year", "A year with 366 days", "February has 29 days in a leap year"),
        CalendarLearningItem("Weekend", "Saturday and Sunday", "Most people rest on weekends"),
        CalendarLearningItem("Weekday", "Monday to Friday", "School days are weekdays")
    )

    val mapItems: List<MapItem> = listOf(
        MapItem("Map", "A drawing that shows places", "A world map shows all countries"),
        MapItem("Continent", "A large landmass on Earth", "There are 7 continents"),
        MapItem("Ocean", "A very large body of salt water", "There are 5 oceans on Earth"),
        MapItem("Country", "A land with its own government", "India is a country in Asia"),
        MapItem("City", "A large town with many buildings", "Mumbai, Delhi are cities"),
        MapItem("River", "A long flow of water", "The Ganga is a river in India"),
        MapItem("Mountain", "Very high land with a peak", "Mount Everest is the tallest mountain"),
        MapItem("Desert", "A dry, sandy area with little rain", "The Sahara is the largest hot desert"),
        MapItem("Island", "Land surrounded by water", "Sri Lanka is an island"),
        MapItem("Forest", "A large area with many trees", "The Amazon is the largest rainforest"),
        MapItem("North Pole", "The topmost point of Earth", "Santa Claus is said to live here"),
        MapItem("South Pole", "The bottommost point of Earth", "Antarctica is near the South Pole"),
        MapItem("Equator", "An imaginary line around the middle of Earth", "Places near the equator are hot"),
        MapItem("Compass", "A tool that shows directions", "A compass needle always points North"),
        MapItem("Globe", "A round model of the Earth", "A globe shows all continents and oceans")
    )

    val hindiVarnmala: List<HindiItem> = listOf(
        HindiItem("अ", "a", "अमरबेल", listOf("अनार", "अनंद", "अपना")),
        HindiItem("आ", "aa", "आम", listOf("आसमान", "आंख", "आदमी")),
        HindiItem("इ", "i", "इमली", listOf("इमारत", "इंसान", "इज्जत")),
        HindiItem("ई", "ee", "ईख", listOf("ईमानदार", "ईद", "ईंट")),
        HindiItem("उ", "u", "उल्लू", listOf("उंगली", "उत्सव", "उपकार")),
        HindiItem("ऊ", "oo", "ऊँट", listOf("ऊन", "ऊँचाई", "ऊधर")),
        HindiItem("ए", "e", "एक", listOf("एहसास", "एकता", "एड़ी")),
        HindiItem("ऐ", "ai", "ऐरावत", listOf("ऐतिहासिक", "ऐनक", "ऐप्स")),
        HindiItem("ओ", "o", "ओखली", listOf("ओखल", "ओझल", "ओलंपिक")),
        HindiItem("औ", "au", "औरत", listOf("औषधि", "और", "औजार")),
        HindiItem("अं", "an", "अंगूर", listOf("अंडा", "अंतरिक्ष", "अंक")),
        HindiItem("अः", "ah", "अःवान", listOf("अःतकाल", "अःम", "अःवानी"))
    )

    val speakingPracticeItems: List<SpeakingItem> = listOf(
        SpeakingItem("Hello, my name is...", "Introduce yourself", "Hello, my name is Riya."),
        SpeakingItem("How are you?", "Ask about someone's health", "How are you today?"),
        SpeakingItem("I am fine, thank you", "Respond to 'how are you'", "I am fine, thank you!"),
        SpeakingItem("What is your name?", "Ask someone's name", "What is your name?"),
        SpeakingItem("My favorite color is...", "Talk about preferences", "My favorite color is blue."),
        SpeakingItem("I like to eat...", "Talk about food likes", "I like to eat apples."),
        SpeakingItem("Can I have water, please?", "Ask for something politely", "Can I have water, please?"),
        SpeakingItem("Thank you very much", "Show gratitude", "Thank you very much for helping me."),
        SpeakingItem("I am sorry", "Apologize", "I am sorry for being late."),
        SpeakingItem("Good morning", "Morning greeting", "Good morning, teacher!"),
        SpeakingItem("Good night", "Night greeting", "Good night,妈妈!"),
        SpeakingItem("See you tomorrow", "Say goodbye", "See you tomorrow at school!"),
        SpeakingItem("I don't understand", "Express confusion", "I don't understand this question."),
        SpeakingItem("Can you help me?", "Ask for help", "Can you help me with this?"),
        SpeakingItem("I love my family", "Express feelings", "I love my family very much.")
    )

    val listeningPracticeItems: List<ListeningItem> = listOf(
        ListeningItem("The cat is on the mat", "cat", "Where is the cat?", listOf("On the mat", "Under the table", "In the box", "On the chair")),
        ListeningItem("There are five apples", "five", "How many apples are there?", listOf("Five", "Three", "Seven", "Ten")),
        ListeningItem("The sky is blue", "blue", "What color is the sky?", listOf("Blue", "Red", "Green", "Yellow")),
        ListeningItem("Dogs can bark", "bark", "What sound do dogs make?", listOf("Bark", "Meow", "Moo", "Roar")),
        ListeningItem("Water is wet", "wet", "How does water feel?", listOf("Wet", "Dry", "Hot", "Cold")),
        ListeningItem("The sun rises in the east", "east", "Where does the sun rise?", listOf("East", "West", "North", "South")),
        ListeningItem("A triangle has three sides", "three", "How many sides does a triangle have?", listOf("Three", "Four", "Five", "Two")),
        ListeningItem("Bananas are yellow", "yellow", "What color are bananas?", listOf("Yellow", "Red", "Green", "Orange")),
        ListeningItem("Elephants are big", "big", "What size are elephants?", listOf("Big", "Small", "Tiny", "Medium")),
        ListeningItem("We breathe oxygen", "oxygen", "What gas do we breathe?", listOf("Oxygen", "Nitrogen", "Carbon dioxide", "Hydrogen"))
    )

    val readingPracticeItems: List<ReadingItem> = listOf(
        ReadingItem("The cat sat on the mat.", "cat, sat, mat", 1),
        ReadingItem("I see a big red dog.", "big, red, dog", 1),
        ReadingItem("The sun is very hot.", "sun, hot", 1),
        ReadingItem("She has two blue pens.", "two, blue, pens", 1),
        ReadingItem("We eat rice and dal.", "eat, rice, dal", 1),
        ReadingItem("The bird can fly high in the sky.", "bird, fly, sky", 2),
        ReadingItem("My mother makes tasty food.", "mother, tasty, food", 2),
        ReadingItem("Children play in the park.", "children, play, park", 2),
        ReadingItem("The flowers are beautiful and colorful.", "flowers, beautiful, colorful", 2),
        ReadingItem("We should drink water every day.", "drink, water, every day", 2),
        ReadingItem("The library has many interesting books to read.", "library, interesting, books", 3),
        ReadingItem("Plants need sunlight, water, and soil to grow properly.", "plants, sunlight, grow", 3),
        ReadingItem("The solar system has eight planets that orbit around the sun.", "solar system, planets, orbit", 3),
        ReadingItem("Dolphins are very intelligent animals that live in the ocean.", "dolphins, intelligent, ocean", 3),
        ReadingItem("Reading books every day improves your vocabulary and knowledge.", "reading, vocabulary, knowledge", 4)
    )

    val writingPracticeItems: List<WritingItem> = listOf(
        WritingItem("My name is ___.", "Fill in your name", "My name is Riya."),
        WritingItem("I am ___ years old.", "Fill in your age", "I am 6 years old."),
        WritingItem("My favorite color is ___.", "Fill in your favorite color", "My favorite color is blue."),
        WritingItem("I like to eat ___.", "Fill in your favorite food", "I like to eat pizza."),
        WritingItem("My best friend is ___.", "Fill in your friend's name", "My best friend is Arjun."),
        WritingItem("I live in ___.", "Fill in your city", "I live in Mumbai."),
        WritingItem("My school name is ___.", "Fill in your school name", "My school name is DPS."),
        WritingItem("I have ___ brothers and ___ sisters.", "Fill in numbers", "I have 1 brother and 0 sisters."),
        WritingItem("My favorite animal is the ___ because ___.", "Fill in animal and reason", "My favorite animal is the dog because it is loyal."),
        WritingItem("When I grow up, I want to be a ___.", "Fill in your dream job", "When I grow up, I want to be a doctor."),
        WritingItem("The best thing about school is ___.", "Write what you like about school", "The best thing about school is playing with friends."),
        WritingItem("If I could fly, I would ___.", "Use your imagination", "If I could fly, I would visit the moon."),
        WritingItem("My mother is special because ___.", "Write about your mother", "My mother is special because she takes care of me."),
        WritingItem("On weekends, I like to ___.", "Write about weekend activities", "On weekends, I like to play cricket."),
        WritingItem("A healthy habit I have is ___.", "Write about a good habit", "A healthy habit I have is brushing my teeth twice a day.")
    )
}

data class TimeLearningItem(val time: String, val spokenForm: String, val description: String)
data class CalendarLearningItem(val term: String, val meaning: String, val example: String)
data class MapItem(val name: String, val definition: String, val example: String)
data class HindiItem(varnam: String, val sound: String, val exampleWord: String, val exampleWords: List<String>)
data class SpeakingItem(val sentence: String, val instruction: String, val example: String)
data class ListeningItem(val sentence: String, val keyWord: String, val question: String, val options: List<String>)
data class ReadingItem(val sentence: String, val keyWords: String, val difficulty: Int)
data class WritingItem(val prompt: String, val instruction: String, val example: String)