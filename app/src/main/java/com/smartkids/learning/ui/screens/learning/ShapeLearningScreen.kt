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
fun ShapeLearningScreen(viewModel: ShapeLearningViewModel, onNavigateBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val item = state.items[state.currentIndex]
    Scaffold(topBar = { TopAppBar(title = { Text("Shapes") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            AnimatedContent(targetState = item.name) {
                Box(modifier = Modifier.size(180.dp).clip(CircleShape).background(Color(0xFF7B2FF7).copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Category, contentDescription = item.name, tint = Color(0xFF7B2FF7), modifier = Modifier.size(80.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(item.name, style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold, color = Color(0xFF7B2FF7)))
            AnimatedVisibility(visible = state.showInfo) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Sides: ${if (item.sides == 0) "None (round)" else item.sides}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(item.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(onClick = { viewModel.prev() }, enabled = state.currentIndex > 0, modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(28.dp)) }
                Button(onClick = { viewModel.toggleInfo() }, shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B2FF7))) { Text(if (state.showInfo) "Hide" else "Info") }
                IconButton(onClick = { viewModel.next() }, enabled = state.currentIndex < state.items.size - 1, modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(28.dp)) }
            }
        }
    }
}