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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorLearningScreen(viewModel: ColorLearningViewModel, onNavigateBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val item = state.items[state.currentIndex]
    val color = try { Color(android.graphics.Color.parseColor(item.hexCode)) } catch (_: Exception) { Color(0xFF06D6A0) }
    Scaffold(topBar = { TopAppBar(title = { Text("Colors") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            AnimatedContent(targetState = item.name) {
                Box(modifier = Modifier.size(200.dp).clip(CircleShape()).background(color.copy(alpha = 0.3f)), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(120.dp).clip(CircleShape()).background(color))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(item.name, style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold, color = color))
            AnimatedVisibility(visible = state.showExample) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Example: ${item.exampleObject}", style = MaterialTheme.typography.bodyLarge, color = color)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(onClick = { viewModel.prev() }, enabled = state.currentIndex > 0, modifier = Modifier.size(60.dp).clip(CircleShape()).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(28.dp)) }
                Button(onClick = { viewModel.toggleExample() }, shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = color)) { Text(if (state.showExample) "Hide" else "Example", color = Color.White) }
                IconButton(onClick = { viewModel.next() }, enabled = state.currentIndex < state.items.size - 1, modifier = Modifier.size(60.dp).clip(CircleShape()).background(MaterialTheme.colorScheme.primaryContainer)) { Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(28.dp)) }
            }
        }
    }
}