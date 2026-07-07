package com.smartkids.learning.ui.screens.parent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.smartkids.learning.ui.components.StatChip
import com.smartkids.learning.util.formatTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentDashboardScreen(viewModel: ParentDashboardViewModel, onNavigateBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    if (state.isLoading) { com.smartkids.learning.ui.components.LoadingIndicator(); return }

    Scaffold(topBar = { TopAppBar(title = { Text("Parent Dashboard") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Child: ${state.profile.childName}, Age: ${state.profile.childAge}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatChip(Icons.Default.Star, "Total XP", "${state.profile.totalXP}", Color(0xFF7B2FF7), Modifier.weight(1f))
                StatChip(Icons.Default.MonetizationOn, "Coins", "${state.profile.totalCoins}", Color(0xFFFFB800), Modifier.weight(1f))
                StatChip(Icons.Default.LocalFireDepartment, "Streak", "${state.profile.currentStreak}d", Color(0xFFFF6B35), Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatChip(Icons.Default.CheckCircle, "Topics Done", "${state.completedTopics}", Color(0xFF06D6A0), Modifier.weight(1f))
                StatChip(Icons.Default.SportsEsports, "Games", "${state.profile.totalGamesPlayed}", Color(0xFF00B4D8), Modifier.weight(1f))
                StatChip(Icons.Default.Schedule, "Time", state.totalTime.formatTime(), Color(0xFFFF6B9D), Modifier.weight(1f))
            }
            Text("Average Score: ${(state.averageScore * 100).toInt()}%", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
            LinearProgressIndicator(progress = { state.averageScore }, modifier = Modifier.fillMaxWidth().height(8.dp), color = Color(0xFF06D6A0), trackColor = Color(0xFF06D6A0).copy(alpha = 0.15f))

            if (state.weakTopics.isNotEmpty()) {
                Text("Topics to Improve", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFEF476F)))
                state.weakTopics.forEach { t ->
                    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFEF476F).copy(alpha = 0.1f)), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingDown, null, tint = Color(0xFFEF476F))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column { Text(t.topicName, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)); Text("Mastery: ${(t.masteryLevel * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = Color(0xFFEF476F)) }
                        }
                    }
                }
            }
            if (state.strongTopics.isNotEmpty()) {
                Text("Strong Topics", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF06D6A0)))
                state.strongTopics.forEach { t ->
                    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF06D6A0).copy(alpha = 0.1f)), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingUp, null, tint = Color(0xFF06D6A0))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column { Text(t.topicName, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)); Text("Mastery: ${(t.masteryLevel * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = Color(0xFF06D6A0)) }
                        }
                    }
                }
            }
        }
    }
}