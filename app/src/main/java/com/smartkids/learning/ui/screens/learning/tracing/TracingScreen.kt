package com.smartkids.learning.ui.screens.tracing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TracingScreen(viewModel: TracingViewModel, onNavigateBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val item = viewModel.letters[state.currentIndex]
    val path = remember { mutableStateOf(Path()) }
    val strokeColor = Color(0xFFFF6B35)
    val guideColor = Color(0xFFFF6B35).copy(alpha = 0.15f)
    val textMeasurer = rememberTextMeasurer()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trace: ${item.letter} for ${item.word}") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "${state.currentIndex + 1}/${viewModel.letters.size}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = { viewModel.toggleGuide() }) {
                    Text(if (state.showGuide) "Hide Guide" else "Show Guide")
                }
                TextButton(onClick = {
                    path.value = Path()
                    viewModel.markComplete()
                }) {
                    Text("Clear")
                }
            }
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(item.letter) {
                            detectDragGestures { change, _ ->
                                val pos = change.position
                                path.value = Path().apply {
                                    addPath(path.value)
                                    lineTo(pos.x, pos.y)
                                }
                                change.consume()
                            }
                        }
                ) {
                    val fontSize = size.minDimension * 0.7f
                    if (state.showGuide) {
                        val measured = textMeasurer.measure(
                            item.letter,
                            TextStyle(
                                fontSize = fontSize.sp,
                                fontWeight = FontWeight.Bold,
                                color = guideColor
                            )
                        )
                        drawText(
                            measured,
                            topLeft = Offset(
                                (size.width - measured.size.width) / 2f,
                                (size.height - measured.size.height) / 2f
                            )
                        )
                    }
                    drawPath(
                        path = path.value,
                        color = strokeColor,
                        style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
            if (state.isComplete) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF06D6A0).copy(alpha = 0.15f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Great tracing! ${item.letter} for ${item.word} ✨",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF06D6A0)
                        ),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(
                    onClick = { viewModel.prev(); path.value = Path() },
                    enabled = state.currentIndex > 0,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape())
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(28.dp))
                }
                IconButton(
                    onClick = { viewModel.next(); path.value = Path() },
                    enabled = state.currentIndex < viewModel.letters.size - 1,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape())
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(28.dp))
                }
            }
        }
    }
}