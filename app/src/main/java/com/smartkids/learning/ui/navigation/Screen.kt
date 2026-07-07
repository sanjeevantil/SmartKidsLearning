package com.smartkids.learning.ui.navigation

sealed class Screen(val route: String) {

    data object Home : Screen("home")
    data object TopicList : Screen("topics/{categoryId}") {
        fun createRoute(categoryId: String) = "topics/$categoryId"
        const val routeWithArg = "topics/{categoryId}"
    }
    data object Quiz : Screen("quiz/{topicId}/{difficulty}") {
        fun createRoute(topicId: String, difficulty: Int) = "quiz/$topicId/$difficulty"
        const val routeWithArgs = "quiz/{topicId}/{difficulty}"
    }
    data object GameSelection : Screen("games")
    data object MemoryGame : Screen("memory/{topicId}") {
        fun createRoute(topicId: String) = "memory/$topicId"
        const val routeWithArg = "memory/{topicId}"
    }
    data object BalloonPop : Screen("balloon/{topicId}") {
        fun createRoute(topicId: String) = "balloon/$topicId"
        const val routeWithArg = "balloon/{topicId}"
    }
    data object MatchingGame : Screen("matching/{topicId}") {
        fun createRoute(topicId: String) = "matching/$topicId"
        const val routeWithArg = "matching/{topicId}"
    }
    data object RapidQuiz : Screen("rapid/{topicId}") {
        fun createRoute(topicId: String) = "rapid/$topicId"
        const val routeWithArg = "rapid/{topicId}"
    }
    data object TimedChallenge : Screen("timed/{topicId}") {
        fun createRoute(topicId: String) = "timed/$topicId"
        const val routeWithArg = "timed/{topicId}"
    }
    data object ParentPin : Screen("parent_pin")
    data object ParentDashboard : Screen("parent_dashboard")
    data object Achievements : Screen("achievements")
    data object Rewards : Screen("rewards")
    data object ABCLearning : Screen("abc_learn")
    data object NumberLearning : Screen("number_learn")
    data object ShapeLearning : Screen("shape_learn")
    data object ColorLearning : Screen("color_learn")
    data object AnimalLearning : Screen("animal_learn")
    data object FlashCards : Screen("flashcards/{topicId}") {
        fun createRoute(topicId: String) = "flashcards/$topicId"
        const val routeWithArg = "flashcards/{topicId}"
    }
    data object Settings : Screen("settings")
    data object Profile : Screen("profile")

    data object DragDrop : Screen("dragdrop/{topicId}") {
        fun createRoute(topicId: String) = "dragdrop/$topicId"
        const val routeWithArg = "dragdrop/{topicId}"
    }
    data object Puzzle : Screen("puzzle")
    data object Maze : Screen("maze")
    data object FindObject : Screen("findobject/{topicId}") {
        fun createRoute(topicId: String) = "findobject/$topicId"
        const val routeWithArg = "findobject/{topicId}"
    }
    data object FruitCatch : Screen("fruitcatch")
    data object WordBuilder : Screen("wordbuilder/{topicId}") {
        fun createRoute(topicId: String) = "wordbuilder/$topicId"
        const val routeWithArg = "wordbuilder/{topicId}"
    }
    data object ColorMatch : Screen("colormatch")
    data object BubblePopGame : Screen("bubblepopgame")
    data object SpotDiff : Screen("spotdiff")
    data object Tracing : Screen("tracing")
}