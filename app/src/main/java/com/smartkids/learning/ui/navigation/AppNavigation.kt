package com.smartkids.learning.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smartkids.learning.ui.screens.home.HomeScreen
import com.smartkids.learning.ui.screens.home.HomeViewModel
import com.smartkids.learning.ui.screens.parent.ParentPinScreen
import com.smartkids.learning.ui.screens.parent.ParentDashboardScreen
import com.smartkids.learning.ui.screens.parent.ParentDashboardViewModel
import com.smartkids.learning.ui.screens.quiz.QuizScreen
import com.smartkids.learning.ui.screens.quiz.QuizViewModel
import com.smartkids.learning.ui.screens.topics.TopicListScreen
import com.smartkids.learning.ui.screens.topics.TopicListViewModel
import com.smartkids.learning.ui.screens.games.GameSelectionScreen
import com.smartkids.learning.ui.screens.games.GameSelectionViewModel
import com.smartkids.learning.ui.screens.games.MemoryGameScreen
import com.smartkids.learning.ui.screens.games.MemoryGameViewModel
import com.smartkids.learning.ui.screens.games.BalloonPopScreen
import com.smartkids.learning.ui.screens.games.BalloonPopViewModel
import com.smartkids.learning.ui.screens.achievements.AchievementsScreen
import com.smartkids.learning.ui.screens.achievements.AchievementsViewModel
import com.smartkids.learning.ui.screens.rewards.RewardsScreen
import com.smartkids.learning.ui.screens.rewards.RewardsViewModel
import com.smartkids.learning.ui.screens.learning.ABCLearningScreen
import com.smartkids.learning.ui.screens.learning.ABCLearningViewModel
import com.smartkids.learning.ui.screens.learning.NumberLearningScreen
import com.smartkids.learning.ui.screens.learning.NumberLearningViewModel
import com.smartkids.learning.ui.screens.learning.ShapeLearningScreen
import com.smartkids.learning.ui.screens.learning.ShapeLearningViewModel
import com.smartkids.learning.ui.screens.learning.ColorLearningScreen
import com.smartkids.learning.ui.screens.learning.ColorLearningViewModel
import com.smartkids.learning.ui.screens.learning.AnimalLearningScreen
import com.smartkids.learning.ui.screens.learning.AnimalLearningViewModel
import com.smartkids.learning.ui.screens.learning.FlashCardScreen
import com.smartkids.learning.ui.screens.learning.FlashCardViewModel
import com.smartkids.learning.ui.screens.settings.SettingsScreen
import com.smartkids.learning.ui.screens.settings.SettingsViewModel
import com.smartkids.learning.ui.screens.profile.ProfileScreen
import com.smartkids.learning.ui.screens.profile.ProfileViewModel
import com.smartkids.learning.ui.screens.games.MatchingGameScreen
import com.smartkids.learning.ui.screens.games.MatchingGameViewModel
import com.smartkids.learning.ui.screens.games.RapidQuizScreen
import com.smartkids.learning.ui.screens.games.RapidQuizViewModel
import com.smartkids.learning.ui.screens.games.TimedChallengeScreen
import com.smartkids.learning.ui.screens.games.TimedChallengeViewModel
import com.smartkids.learning.ui.screens.games.ExtraGamesViewModel
import com.smartkids.learning.ui.screens.games.DragDropScreen
import com.smartkids.learning.ui.screens.games.PuzzleScreen
import com.smartkids.learning.ui.screens.games.MazeScreen
import com.smartkids.learning.ui.screens.games.FindObjectScreen
import com.smartkids.learning.ui.screens.games.FruitCatchScreen
import com.smartkids.learning.ui.screens.games.WordBuilderScreen
import com.smartkids.learning.ui.screens.games.ColorMatchScreen
import com.smartkids.learning.ui.screens.games.BubblePopGameScreen
import com.smartkids.learning.ui.screens.games.SpotDiffScreen
import com.smartkids.learning.ui.screens.tracing.TracingScreen
import com.smartkids.learning.ui.screens.tracing.TracingViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onNavigateToTopicList = { navController.navigate(Screen.TopicList.createRoute(it)) },
                onNavigateToGameSelection = { navController.navigate(Screen.GameSelection.route) },
                onNavigateToRewards = { navController.navigate(Screen.Rewards.route) },
                onNavigateToParentPin = { navController.navigate(Screen.ParentPin.route) },
                onNavigateToAchievements = { navController.navigate(Screen.Achievements.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToQuiz = { topicId, diff ->
                    navController.navigate(Screen.Quiz.createRoute(topicId, diff))
                },
                onNavigateToABC = { navController.navigate(Screen.ABCLearning.route) },
                onNavigateToNumbers = { navController.navigate(Screen.NumberLearning.route) },
                onNavigateToShapes = { navController.navigate(Screen.ShapeLearning.route) },
                onNavigateToColors = { navController.navigate(Screen.ColorLearning.route) },
                onNavigateToAnimals = { navController.navigate(Screen.AnimalLearning.route) },
                onNavigateToFlashCards = { topicId ->
                    navController.navigate(Screen.FlashCards.createRoute(topicId))
                }
            )
        }

        composable(
            route = Screen.TopicList.routeWithArg,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: "Language"
            val viewModel: TopicListViewModel = hiltViewModel()
            LaunchedEffect(categoryId) { viewModel.loadTopics(categoryId) }
            TopicListScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToQuiz = { topicId, diff ->
                    navController.navigate(Screen.Quiz.createRoute(topicId, diff))
                },
                onNavigateToFlashCards = { topicId ->
                    navController.navigate(Screen.FlashCards.createRoute(topicId))
                },
                onNavigateToLearning = { topicId ->
                    when (topicId) {
                        "abc_learning" -> navController.navigate(Screen.ABCLearning.route)
                        "numbers" -> navController.navigate(Screen.NumberLearning.route)
                        "shapes" -> navController.navigate(Screen.ShapeLearning.route)
                        "colors" -> navController.navigate(Screen.ColorLearning.route)
                        "animals" -> navController.navigate(Screen.AnimalLearning.route)
                        else -> navController.navigate(Screen.Quiz.createRoute(topicId, 1))
                    }
                }
            )
        }

        composable(
            route = Screen.Quiz.routeWithArgs,
            arguments = listOf(
                navArgument("topicId") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.IntType; defaultValue = 1 }
            )
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "abc_learning"
            val difficulty = backStackEntry.arguments?.getInt("difficulty") ?: 1
            val viewModel: QuizViewModel = hiltViewModel()
            LaunchedEffect(topicId, difficulty) { viewModel.startQuiz(topicId, difficulty) }
            QuizScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(Screen.GameSelection.route) {
            val viewModel: GameSelectionViewModel = hiltViewModel()
            GameSelectionScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToMemoryGame = { navController.navigate(Screen.MemoryGame.createRoute(it)) },
                onNavigateToBalloonPop = { navController.navigate(Screen.BalloonPop.createRoute(it)) },
                onNavigateToMatching = { navController.navigate(Screen.MatchingGame.createRoute(it)) },
                onNavigateToRapidQuiz = { navController.navigate(Screen.RapidQuiz.createRoute(it)) },
                onNavigateToTimedChallenge = { navController.navigate(Screen.TimedChallenge.createRoute(it)) }
            )
        }

        composable(
            route = Screen.MemoryGame.routeWithArg,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "animals"
            val viewModel: MemoryGameViewModel = hiltViewModel()
            LaunchedEffect(topicId) { viewModel.startGame(topicId) }
            MemoryGameScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(
            route = Screen.BalloonPop.routeWithArg,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "numbers"
            val viewModel: BalloonPopViewModel = hiltViewModel()
            LaunchedEffect(topicId) { viewModel.startGame(topicId) }
            BalloonPopScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(
            route = Screen.MatchingGame.routeWithArg,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "colors"
            val viewModel: MatchingGameViewModel = hiltViewModel()
            LaunchedEffect(topicId) { viewModel.startGame(topicId) }
            MatchingGameScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(
            route = Screen.RapidQuiz.routeWithArg,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "general_knowledge"
            val viewModel: RapidQuizViewModel = hiltViewModel()
            LaunchedEffect(topicId) { viewModel.startGame(topicId) }
            RapidQuizScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(
            route = Screen.TimedChallenge.routeWithArg,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "addition"
            val viewModel: TimedChallengeViewModel = hiltViewModel()
            LaunchedEffect(topicId) { viewModel.startGame(topicId) }
            TimedChallengeScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(Screen.ParentPin.route) {
            ParentPinScreen(
                onPinCorrect = { navController.navigate(Screen.ParentDashboard.route) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ParentDashboard.route) {
            val viewModel: ParentDashboardViewModel = hiltViewModel()
            ParentDashboardScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Achievements.route) {
            val viewModel: AchievementsViewModel = hiltViewModel()
            AchievementsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Rewards.route) {
            val viewModel: RewardsViewModel = hiltViewModel()
            RewardsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ABCLearning.route) {
            val viewModel: ABCLearningViewModel = hiltViewModel()
            ABCLearningScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.NumberLearning.route) {
            val viewModel: NumberLearningViewModel = hiltViewModel()
            NumberLearningScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ShapeLearning.route) {
            val viewModel: ShapeLearningViewModel = hiltViewModel()
            ShapeLearningScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ColorLearning.route) {
            val viewModel: ColorLearningViewModel = hiltViewModel()
            ColorLearningScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AnimalLearning.route) {
            val viewModel: AnimalLearningViewModel = hiltViewModel()
            AnimalLearningScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.FlashCards.routeWithArg,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "animals"
            val viewModel: FlashCardViewModel = hiltViewModel()
            LaunchedEffect(topicId) { viewModel.loadCards(topicId) }
            FlashCardScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.DragDrop.routeWithArg,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "animals"
            val viewModel: ExtraGamesViewModel = hiltViewModel()
            DragDropScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } },
                topicId = topicId
            )
        }

        composable(Screen.Puzzle.route) {
            val viewModel: ExtraGamesViewModel = hiltViewModel()
            PuzzleScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(Screen.Maze.route) {
            val viewModel: ExtraGamesViewModel = hiltViewModel()
            MazeScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(
            route = Screen.FindObject.routeWithArg,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "animals"
            val viewModel: ExtraGamesViewModel = hiltViewModel()
            FindObjectScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } },
                topicId = topicId
            )
        }

        composable(Screen.FruitCatch.route) {
            val viewModel: ExtraGamesViewModel = hiltViewModel()
            FruitCatchScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(
            route = Screen.WordBuilder.routeWithArg,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "animals"
            val viewModel: ExtraGamesViewModel = hiltViewModel()
            WordBuilderScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } },
                topicId = topicId
            )
        }

        composable(Screen.ColorMatch.route) {
            val viewModel: ExtraGamesViewModel = hiltViewModel()
            ColorMatchScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(Screen.BubblePopGame.route) {
            val viewModel: ExtraGamesViewModel = hiltViewModel()
            BubblePopGameScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(Screen.SpotDiff.route) {
            val viewModel: ExtraGamesViewModel = hiltViewModel()
            SpotDiffScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(Screen.Tracing.route) {
            val viewModel: TracingViewModel = hiltViewModel()
            TracingScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}