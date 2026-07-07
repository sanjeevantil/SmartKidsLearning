package com.smartkids.learning.ui.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun MemoryGameScreen(
    viewModel: MemoryGameViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(state.score, state.cards.size / 2, state.xpEarned, state.coinsEarned, false,
            onPlayAgain = { viewModel.resetGame() }, onGoHome = onNavigateHome)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memory Game", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } },
                actions = { Text("Moves: ${state.moves}", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(end = 16.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { if (state.cards.isNotEmpty()) state.matchedPairs.size.toFloat() / (state.cards.size / 2) else 0f },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF7B2FF7), trackColor = Color(0xFF7B2FF7).copy(alpha = 0.15f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("Match the pairs!", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(12.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(state.cards) { index, card ->
                    val isFlipped = state.flippedCards.contains(index)
                    val isMatched = state.matchedPairs.contains(card.pairId)
                    val bgColor = when {
                        isMatched -> Color(0xFF06D6A0).copy(alpha = 0.2f)
                        isFlipped -> Color(0xFFFF6B35).copy(alpha = 0.15f)
                        else -> Color(0xFF7B2FF7).copy(alpha = 0.1f)
                    }
                    Card(
                        onClick = { viewModel.flipCard(index) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = bgColor),
                        modifier = Modifier.aspectRatio(0.85f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isFlipped || isMatched) {
                                Text(
                                    card.label,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                                    textAlign = TextAlign.Center,
                                    color = if (isMatched) Color(0xFF06D6A0) else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(4.dp)
                                )
                            } else {
                                Icon(Icons.Default.HelpOutline, null, tint = Color(0xFF7B2FF7).copy(alpha = 0.5f), modifier = Modifier.size(28.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}