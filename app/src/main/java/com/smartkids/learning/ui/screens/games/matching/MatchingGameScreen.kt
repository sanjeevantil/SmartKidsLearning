package com.smartkids.learning.ui.screens.games

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.smartkids.learning.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchingGameScreen(
    viewModel: MatchingGameViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.totalPairs, state.xpEarned, state.coinsEarned, false,
            onPlayAgain = { viewModel.resetGame() }, onGoHome = onNavigateHome
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Match Pairs", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                actions = {
                    Text(
                        "Score: ${state.score}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF7B2FF7),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(8.dp)) {
            LinearProgressIndicator(
                progress = {
                    if (state.totalPairs > 0) state.matchedCount.toFloat() / state.totalPairs else 0f
                },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = Color(0xFF06D6A0),
                trackColor = Color(0xFF06D6A0).copy(alpha = 0.15f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier.weight(1f).padding(end = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    itemsIndexed(state.leftItems) { index, item ->
                        val bgColor = when {
                            item.isMatched -> Color(0xFF06D6A0).copy(alpha = 0.2f)
                            item.isSelected -> Color(0xFFFF6B35).copy(alpha = 0.2f)
                            else -> MaterialTheme.colorScheme.surface
                        }
                        Card(
                            onClick = { viewModel.selectLeft(index) },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = bgColor),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                item.text,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier.weight(1f).padding(start = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    itemsIndexed(state.rightItems) { index, item ->
                        val bgColor = when {
                            item.isMatched -> Color(0xFF06D6A0).copy(alpha = 0.2f)
                            item.isSelected -> Color(0xFF00B4D8).copy(alpha = 0.2f)
                            else -> MaterialTheme.colorScheme.surface
                        }
                        Card(
                            onClick = { viewModel.selectRight(index) },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = bgColor),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                item.text,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center,
                                color = if (item.isMatched) Color(0xFF06D6A0) else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}