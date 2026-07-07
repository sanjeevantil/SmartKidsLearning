package com.smartkids.learning.ui.screens.games

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartkids.learning.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalloonPopScreen(
    viewModel: BalloonPopViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.totalRounds, state.xpEarned, state.coinsEarned, false,
            onPlayAgain = { viewModel.resetGame() }, onGoHome = onNavigateHome
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Balloon Pop!", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                actions = {
                    Text(
                        "Round ${state.round}/${state.totalRounds}",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Pop the '${state.targetLabel}' balloon!",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                "Score: ${state.score}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF7B2FF7),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Proper grid layout instead of overlapping offsets
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                val half = (state.balloons.size + 1) / 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.balloons.take(half).forEach { balloon ->
                        Button(
                            onClick = { viewModel.popBalloon(balloon) },
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(balloon.color)),
                            contentPadding = PaddingValues(4.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                balloon.label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                maxLines = 2
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.balloons.drop(half).forEach { balloon ->
                        Button(
                            onClick = { viewModel.popBalloon(balloon) },
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(balloon.color)),
                            contentPadding = PaddingValues(4.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                balloon.label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                maxLines = 2
                            )
                        }
                    }
                }
            }

            if (state.showFeedback) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (state.roundCorrect > 0) Color(0xFF06D6A0).copy(alpha = 0.2f)
                    else Color(0xFFEF476F).copy(alpha = 0.2f),
                    modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth()
                ) {
                    Text(
                        if (state.roundCorrect > 0) "Correct! 🎉" else "Missed! It was '${state.targetLabel}'",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        color = if (state.roundCorrect > 0) Color(0xFF06D6A0) else Color(0xFFEF476F),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}