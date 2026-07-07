package com.smartkids.learning.ui.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun RapidQuizScreen(
    viewModel: RapidQuizViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(state.score, state.questions.size, state.xpEarned, state.coinsEarned, false,
            onPlayAgain = { viewModel.resetGame() }, onGoHome = onNavigateHome)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rapid Quiz! \u26A1", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text("Score: ${state.score}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Color(0xFF7B2FF7))
                Spacer(modifier = Modifier.weight(1f))
                Surface(shape = RoundedCornerShape(12.dp), color = if (state.timeLeft <= 10) Color(0xFFEF476F) else Color(0xFFFF6B35)) {
                    Text(" \u23F1 ${state.timeLeft}s ", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.White), modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
                }
            }
            LinearProgressIndicator(
                progress = { if (state.questions.isNotEmpty()) state.currentIndex.toFloat() / state.questions.size else 0f },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = Color(0xFFFF6B35), trackColor = Color(0xFFFF6B35).copy(alpha = 0.15f)
            )
            if (state.currentIndex < state.questions.size) {
                val q = state.questions[state.currentIndex]
                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)), modifier = Modifier.fillMaxWidth()) {
                    Text(q.questionText, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold), textAlign = TextAlign.Center, modifier = Modifier.padding(20.dp).fillMaxWidth())
                }
                q.options.forEach { option ->
                    Card(onClick = { viewModel.answer(option) }, shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), modifier = Modifier.fillMaxWidth()) {
                        Text(option, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium), modifier = Modifier.padding(14.dp).fillMaxWidth(), textAlign = TextAlign.Center)
                    }
                }
                if (state.showFeedback) {
                    Text(
                        if (state.lastCorrect) "\u2705 Correct!" else "\u274C Wrong!",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (state.lastCorrect) Color(0xFF06D6A0) else Color(0xFFEF476F),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}