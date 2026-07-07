package com.smartkids.learning.ui.screens.learning

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.foundation.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberLearningScreen(viewModel: NumberLearningViewModel, onNavigateBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val item = state.items[state.currentIndex]
    Scaffold(topBar = { TopAppBar(title = { Text("Numbers") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("${state.currentIndex + 1}/${state.items.size}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedContent(targetState = item.number) { num ->
                Box(modifier = Modifier.size(200.dp).clip(CircleShape).background(Color(0xFF00B4D8).copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                    Text(num.toString(), style = MaterialTheme.typography.displayLarge.copy(fontSize = 80.sp, fontWeight = FontWeight.Bold), color = Color(0xFF00B4D8))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(item.word, style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))
            AnimatedVisibility(visible = state.showCount) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("\u2B50".repeat(item.number.coerceAtMost(20)), fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(onClick = { viewModel.prev() }, enabled = state.currentIndex > 0, modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(28.dp)) }
                Button(onClick = { viewModel.toggleCount() }, shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B4D8))) { Text(if (state.showCount) "Hide" else "Count") }
                IconButton(onClick = { viewModel.next() }, enabled = state.currentIndex < state.items.size - 1, modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(28.dp)) }
            }
        }
    }
}