package com.smartkids.learning.ui.screens.games

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSelectionScreen(
    viewModel: GameSelectionViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToMemoryGame: (String) -> Unit,
    onNavigateToBalloonPop: (String) -> Unit,
    onNavigateToMatching: (String) -> Unit,
    onNavigateToRapidQuiz: (String) -> Unit,
    onNavigateToTimedChallenge: (String) -> Unit,
    navController: NavController
) {
    val games by viewModel.games.collectAsState()
    var selectedGame by remember { mutableStateOf<GameType?>(null) }
    var showTopicPicker by remember { mutableStateOf(false) }

    if (showTopicPicker && selectedGame != null) {
        AlertDialog(
            onDismissRequest = { showTopicPicker = false },
            title = { Text("Choose Topic for ${selectedGame!!.name}") },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(selectedGame!!.topicOptions) { topic ->
                        Card(
                            onClick = {
                                showTopicPicker = false
                                when (selectedGame!!.id) {
                                    "memory" -> onNavigateToMemoryGame(topic)
                                    "balloon" -> onNavigateToBalloonPop(topic)
                                    "matching" -> onNavigateToMatching(topic)
                                    "rapid" -> onNavigateToRapidQuiz(topic)
                                    "timed" -> onNavigateToTimedChallenge(topic)
                                    "dragdrop" -> navController.navigate("dragdrop/$topic")
                                    "puzzle" -> navController.navigate("puzzle")
                                    "maze" -> navController.navigate("maze")
                                    "findobject" -> navController.navigate("findobject/$topic")
                                    "fruitcatch" -> navController.navigate("fruitcatch")
                                    "wordbuilder" -> navController.navigate("wordbuilder/$topic")
                                    "colormatch" -> navController.navigate("colormatch")
                                    "bubblepopgame" -> navController.navigate("bubblepopgame")
                                    "spotdiff" -> navController.navigate("spotdiff")
                                    "tracing" -> navController.navigate("tracing")
                                    else -> onNavigateToMemoryGame(topic)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                topic.replace("_", " ").replaceFirstChar { it.uppercase() },
                                modifier = Modifier.padding(14.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTopicPicker = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Games 🎮",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(games) { game ->
                val color = Color(game.color)
                Card(
                    onClick = { selectedGame = game; showTopicPicker = true },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(6.dp, RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = color.copy(alpha = 0.08f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = color.copy(alpha = 0.15f),
                            modifier = Modifier.size(60.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    when (game.icon) {
                                        "memory" -> Icons.Default.GridOn
                                        "balloon" -> Icons.Default.Circle
                                        "match" -> Icons.Default.Link
                                        "rapid" -> Icons.Default.Bolt
                                        "timed" -> Icons.Default.Timer
                                        "drag_drop" -> Icons.Default.SwapVert
                                        "puzzle" -> Icons.Default.Extension
                                        "maze" -> Icons.Default.GridView
                                        "find_object" -> Icons.Default.Search
                                        "fruit_catch" -> Icons.Default.SportsBasketball
                                        "word_builder" -> Icons.Default.TextFields
                                        "color_match" -> Icons.Default.Palette
                                        "bubble_pop" -> Icons.Default.Circle
                                        "spot_difference" -> Icons.Default.Difference
                                        "draw" -> Icons.Default.Draw
                                        else -> Icons.Default.Gamepad
                                    },
                                    contentDescription = null,
                                    tint = color,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                game.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = color
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                game.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Icon(
                            Icons.Default.ChevronRight,
                            null,
                            tint = color.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}