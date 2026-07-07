package com.smartkids.learning.ui.screens.learning

import androidx.compose.animation.*
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
import androidx.compose.foundation.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardScreen(viewModel: FlashCardViewModel, onNavigateBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text("Flash Cards (${state.currentIndex + 1}/${state.total})") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Card(
                onClick = { viewModel.flip() },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth().height(350.dp),
                colors = CardDefaults.cardColors(containerColor = if (state.isFlipped) Color(0xFF7B2FF7).copy(alpha = 0.1f) else Color(0xFFFF6B35).copy(alpha = 0.1f)),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                    AnimatedContent(targetState = state.isFlipped) { flipped ->
                        if (flipped) {
                            Text(state.backText, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium), textAlign = TextAlign.Center, color = Color(0xFF7B2FF7))
                        } else {
                            Text(state.frontText, style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold), textAlign = TextAlign.Center, color = Color(0xFFFF6B35))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(if (state.isFlipped) "Tap to see front" else "Tap to see back", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                IconButton(onClick = { viewModel.prev() }, enabled = state.currentIndex > 0, modifier = Modifier.size(60.dp).clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(28.dp)) }
                IconButton(onClick = { viewModel.flip() }, modifier = Modifier.size(60.dp).clip(RoundedCornerShape(16.dp)).background(Color(0xFFFF6B35).copy(alpha = 0.15f))) { Icon(Icons.Default.Flip, null, tint = Color(0xFFFF6B35), modifier = Modifier.size(28.dp)) }
                IconButton(onClick = { viewModel.next() }, enabled = state.currentIndex < state.total - 1, modifier = Modifier.size(60.dp).clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(28.dp)) }
            }
        }
    }
}