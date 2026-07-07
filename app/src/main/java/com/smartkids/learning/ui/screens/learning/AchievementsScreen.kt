package com.smartkids.learning.ui.screens.achievements

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.smartkids.learning.ui.components.LoadingIndicator
import com.smartkids.learning.ui.components.getIconForTopic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(viewModel: AchievementsViewModel, onNavigateBack: () -> Unit) {
    val achievements by viewModel.achievements.collectAsState()
    val unlockedCount by viewModel.unlockedCount.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text("Achievements") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        if (achievements.isEmpty()) { LoadingIndicator(modifier = Modifier.padding(padding)); return@Scaffold }
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Unlocked: $unlockedCount/${achievements.size}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = { if (achievements.isNotEmpty()) unlockedCount.toFloat() / achievements.size else 0f }, modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)), color = Color(0xFFFFD166), trackColor = Color(0xFFFFD166).copy(alpha = 0.15f))
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(achievements) { ach ->
                    val icon = getIconForTopic(ach.iconName)
                    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = if (ach.isUnlocked) Color(0xFFFFD166).copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(icon, null, tint = if (ach.isUnlocked) Color(0xFFFFD166) else Color.Gray, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(ach.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                                Text(ach.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                if (!ach.isUnlocked) {
                                    LinearProgressIndicator(progress = { ach.progressPercentage }, modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)), color = Color(0xFFFFD166))
                                } else {
                                    Text("Unlocked! +${ach.xpReward} XP, +${ach.coinReward} Coins", style = MaterialTheme.typography.labelSmall, color = Color(0xFFFF9F1C), fontWeight = FontWeight.Bold)
                                }
                            }
                            if (ach.isUnlocked) Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF06D6A0), modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }
        }
    }
}