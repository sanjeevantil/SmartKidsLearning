package com.smartkids.learning.ui.screens.rewards

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
import androidx.compose.ui.unit.sp
import com.smartkids.learning.ui.components.StatChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(viewModel: RewardsViewModel, onNavigateBack: () -> Unit) {
    val xp by viewModel.totalXP.collectAsState()
    val coins by viewModel.totalCoins.collectAsState()
    val streak by viewModel.currentStreak.collectAsState()
    val longest by viewModel.longestStreak.collectAsState()
    val games by viewModel.totalGames.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("My Rewards \uD83C\uDFC6") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF7B2FF7).copy(alpha = 0.1f)), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Total XP", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$xp", style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold, fontSize = 48.sp, color = Color(0xFF7B2FF7)))
                    Text("Level ${(xp / 100) + 1}", style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF7B2FF7)))
                    LinearProgressIndicator(progress = { (xp % 100).toFloat() / 100 }, modifier = Modifier.fillMaxWidth().height(10.dp), color = Color(0xFF7B2FF7), trackColor = Color(0xFF7B2FF7).copy(alpha = 0.15f))
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatChip(Icons.Default.MonetizationOn, "Coins", "$coins", Color(0xFFFFB800), Modifier.weight(1f))
                StatChip(Icons.Default.LocalFireDepartment, "Streak", "${streak}d", Color(0xFFFF6B35), Modifier.weight(1f))
            }
            StatChip(Icons.Default.EmojiEvents, "Longest Streak", "${longest} days", Color(0xFFFFD166), modifier = Modifier.fillMaxWidth())
            StatChip(Icons.Default.SportsEsports, "Games Played", "$games", Color(0xFF00B4D8), modifier = Modifier.fillMaxWidth())

            Text("Milestones", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            listOf(100 to "100 XP", 500 to "500 XP", 1000 to "1K XP", 5000 to "5K XP").forEach { (target, label) ->
                val done = xp >= target
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = if (done) Color(0xFF06D6A0).copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(if (done) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked, null, tint = if (done) Color(0xFF06D6A0) else Color.Gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(label, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium))
                    }
                }
            }
        }
    }
}