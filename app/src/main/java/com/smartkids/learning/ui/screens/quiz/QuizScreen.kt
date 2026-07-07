package com.smartkids.learning.ui.screens.quiz

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun QuizScreen(
    viewModel: QuizViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        LoadingIndicator()
        return
    }

    if (state.isComplete) {
        ResultOverlay(
            score = state.correctCount,
            total = state.questions.size,
            xpEarned = state.xpEarned,
            coinsEarned = state.coinsEarned,
            isBestScore = state.isNewBestScore,
            onPlayAgain = {
                viewModel.resetQuiz()
                viewModel.startQuiz(state.topicId, state.difficulty)
            },
            onGoHome = onNavigateHome
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.topicName, style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        if (state.questions.isNotEmpty()) {
            val currentQuestion = state.questions[state.currentIndex]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Progress
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "${state.currentIndex + 1}/${state.questions.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    LinearProgressIndicator(
                        progress = { (state.currentIndex + 1).toFloat() / state.questions.size },
                        modifier = Modifier.weight(1f).height(8.dp).clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFFFF6B35),
                        trackColor = Color(0xFFFF6B35).copy(alpha = 0.15f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Score: ${state.score}",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF7B2FF7)
                    )
                }

                // Question
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            currentQuestion.questionText,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Options
                currentQuestion.options.forEach { option ->
                    val isSelected = state.selectedAnswer == option
                    val isCorrectOption = option == currentQuestion.correctAnswer
                    val backgroundColor = when {
                        state.isAnswered && isCorrectOption -> Color(0xFF06D6A0).copy(alpha = 0.2f)
                        state.isAnswered && isSelected && !state.isCorrect -> Color(0xFFEF476F).copy(alpha = 0.2f)
                        isSelected -> Color(0xFFFF6B35).copy(alpha = 0.15f)
                        else -> MaterialTheme.colorScheme.surface
                    }
                    val borderColor = when {
                        state.isAnswered && isCorrectOption -> Color(0xFF06D6A0)
                        state.isAnswered && isSelected && !state.isCorrect -> Color(0xFFEF476F)
                        isSelected -> Color(0xFFFF6B35)
                        else -> Color.Gray.copy(alpha = 0.2f)
                    }

                    Card(
                        onClick = { if (!state.isAnswered) viewModel.selectAnswer(option) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = backgroundColor),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(borderColor.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    option.first().toString(),
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = borderColor
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                option,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                                modifier = Modifier.weight(1f)
                            )
                            if (state.isAnswered && isCorrectOption) {
                                Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF06D6A0), modifier = Modifier.size(24.dp))
                            } else if (state.isAnswered && isSelected && !state.isCorrect) {
                                Icon(Icons.Default.Cancel, null, tint = Color(0xFFEF476F), modifier = Modifier.size(24.dp))
                            }
                        }
                    }
                }

                // Feedback
                AnimatedVisibility(
                    visible = state.showFeedback,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    val feedbackColor = if (state.isCorrect) Color(0xFF06D6A0) else Color(0xFFEF476F)
                    val feedbackText = if (state.isCorrect) "Correct! Great job! \uD83C\uDF89" else "Oops! The answer is ${currentQuestion.correctAnswer}"
                    val feedbackIcon = if (state.isCorrect) Icons.Default.ThumbUp else Icons.Default.Refresh

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = feedbackColor.copy(alpha = 0.15f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(feedbackIcon, null, tint = feedbackColor, modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(feedbackText, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold), color = feedbackColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}