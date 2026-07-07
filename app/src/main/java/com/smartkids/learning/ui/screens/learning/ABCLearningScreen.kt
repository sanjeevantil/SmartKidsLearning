package com.smartkids.learning.ui.screens.learning

import androidx.compose.animation.*
import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ABCLearningScreen(viewModel: ABCLearningViewModel, onNavigateBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val item = state.items[state.currentIndex]
    val colors = listOf(0xFFFF6B35, 0xFF00B4D8, 0xFF7B2FF7, 0xFF06D6A0, 0xFFFFD166, 0xFFFF6B9D)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ABC Learning") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background))
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("${state.currentIndex + 1} / ${state.items.size}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedContent(targetState = item.letter, transitionSpec = { slideInHorizontally() togetherWith slideOutHorizontally() }) { letter ->
                Box(modifier = Modifier.size(200.dp).clip(CircleShape).background(Color(colors[state.currentIndex % colors.size]).copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                    Text(letter, style = MaterialTheme.typography.displayLarge.copy(fontSize = 96.sp, fontWeight = FontWeight.Bold), color = Color(colors[state.currentIndex % colors.size]))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            AnimatedVisibility(visible = state.showWord, enter = fadeIn() + expandVertically(), exit = fadeOut() + shrinkVertically()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(item.word, style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${item.letter} is for ${item.word}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(onClick = { viewModel.prevLetter() }, enabled = state.currentIndex > 0, modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer)) {
                    Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
                Button(onClick = { viewModel.toggleWord() }, shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B35))) {
                    Text(if (state.showWord) "Hide" else "Show Word", style = MaterialTheme.typography.labelLarge)
                }
                IconButton(onClick = { viewModel.nextLetter() }, enabled = state.currentIndex < state.items.size - 1, modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer)) {
                    Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                state.items.forEachIndexed { index, _ ->
                    Surface(modifier = Modifier.size(8.dp).clip(CircleShape), color = if (index == state.currentIndex) Color(0xFFFF6B35) else Color.Gray.copy(alpha = 0.3f)) {}
                }
            }
        }
    }
}