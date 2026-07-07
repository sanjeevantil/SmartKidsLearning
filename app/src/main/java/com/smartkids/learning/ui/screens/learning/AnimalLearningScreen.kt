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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalLearningScreen(viewModel: AnimalLearningViewModel, onNavigateBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val item = state.items[state.currentIndex]
    val emojis = mapOf("Lion" to "\uD83E\uDD81","Elephant" to "\uD83D\uDC18","Dog" to "\uD83D\uDC36","Cat" to "\uD83D\uDC31","Cow" to "\uD83D\uDC2E","Horse" to "\uD83D\uDC34","Rabbit" to "\uD83D\uDC30","Monkey" to "\uD83D\uDC35","Tiger" to "\uD83D\uDC2F","Bear" to "\uD83D\uDC3B","Deer" to "\uD83E\uDD8C","Fox" to "\uD83E\uDD8A","Giraffe" to "\uD83E\uDD92","Zebra" to "\uD83E\uDD93","Penguin" to "\uD83D\uDC27","Dolphin" to "\uD83D\uDC2C","Frog" to "\uD83D\uDC38","Snake" to "\uD83D\uDC0D","Parrot" to "\uD83D\uDC9C","Turtle" to "\uD83D\uDC22")
    Scaffold(topBar = { TopAppBar(title = { Text("Animals") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("${state.currentIndex + 1}/${state.items.size}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(12.dp))
            AnimatedContent(targetState = item.name) {
                Box(modifier = Modifier.size(180.dp).clip(CircleShape).background(Color(0xFFFF9F1C).copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                    Text(emojis[item.name] ?: "\uD83D\uDC3E", fontSize = 72.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(item.name, style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold, color = Color(0xFFFF9F1C)))
            Text("Sound: ${item.sound}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            AnimatedVisibility(visible = state.showDetails) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(item.description, style = MaterialTheme.typography.bodyMedium)
                    Text("Habitat: ${item.habitat}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(onClick = { viewModel.prev() }, enabled = state.currentIndex > 0, modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(28.dp)) }
                Button(onClick = { viewModel.toggleDetails() }, shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9F1C))) { Text(if (state.showDetails) "Hide" else "Details") }
                IconButton(onClick = { viewModel.next() }, enabled = state.currentIndex < state.items.size - 1, modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(28.dp)) }
            }
        }
    }
}