package com.smartkids.learning.ui.screens.games

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.row.LazyRow
import androidx.compose.foundation.lazy.row.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartkids.learning.ui.components.*

// ==================== DRAG & DROP SCREEN ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DragDropScreen(
    viewModel: ExtraGamesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit,
    topicId: String
) {
    val state by viewModel.dragDrop.collectAsState()
    LaunchedEffect(topicId) { viewModel.startDragDrop(topicId) }
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(state.score, state.totalPairs, state.xpEarned, state.coinsEarned, false,
            { viewModel.resetDragDrop() }, onNavigateHome)
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Drag & Drop") },
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
                .padding(padding)
                .padding(12.dp)
        ) {
            Text(
                "Drag items to match! Score: ${state.score}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            LinearProgressIndicator(
                progress = {
                    if (state.totalPairs > 0) state.matchedCount.toFloat() / state.totalPairs else 0f
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = Color(0xFF06D6A0)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Items:", style = MaterialTheme.typography.labelMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(state.items) { index, item ->
                    val isDragged = state.draggedIndex == index
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                item.isMatched -> Color(0xFF06D6A0).copy(alpha = 0.2f)
                                isDragged -> Color(0xFFFF6B35).copy(alpha = 0.3f)
                                else -> MaterialTheme.colorScheme.surface
                            }
                        ),
                        modifier = Modifier
                            .graphicsLayer {
                                if (isDragged) translationY = -20f
                            }
                            .pointerInput(index) {
                                detectDragGestures(
                                    onDragStart = { viewModel.setDraggedIndex(index) },
                                    onDragEnd = { viewModel.setDraggedIndex(null) }
                                ) { }
                            }
                    ) {
                        Text(
                            item.text,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = if (item.isMatched) Color(0xFF06D6A0)
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Targets:", style = MaterialTheme.typography.labelMedium)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(state.targets) { index, target ->
                    var isHovered by remember { mutableStateOf(false) }
                    val isMatched = state.items.any { it.pairId == target && it.isMatched }
                    Card(
                        onClick = {
                            if (state.draggedIndex != null)
                                viewModel.dragDropMatch(state.draggedIndex!!, index)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                isMatched -> Color(0xFF06D6A0).copy(alpha = 0.2f)
                                isHovered && state.draggedIndex != null ->
                                    Color(0xFFFF6B35).copy(alpha = 0.15f)
                                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerInput(index) {
                                detectTapGestures(
                                    onPress = { isHovered = true },
                                    onPressRelease = { isHovered = false }
                                )
                            }
                    ) {
                        Text(
                            target,
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isMatched) Color(0xFF06D6A0)
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

// ==================== PUZZLE SCREEN ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleScreen(
    viewModel: ExtraGamesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.puzzle.collectAsState()
    LaunchedEffect(Unit) { viewModel.startPuzzle("puzzle") }
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.tiles.size - 1, state.xpEarned, state.coinsEarned, true,
            { viewModel.resetPuzzle() }, onNavigateHome
        )
        return
    }
    val colors = listOf(
        Color(0xFFFF6B35), Color(0xFF00B4D8), Color(0xFF7B2FF7), Color(0xFF06D6A0),
        Color(0xFFFFD166), Color(0xFFFF6B9D), Color(0xFFEF476F), Color(0xFFFF9F1C)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sliding Puzzle") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text("Moves: ${state.moves}", modifier = Modifier.padding(end = 16.dp))
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Arrange 1-8 in order!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(state.size),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.tiles.size) { index ->
                    val num = state.tiles[index]
                    Card(
                        onClick = { if (num != -1) viewModel.moveTile(index) },
                        shape = RoundedCornerShape(12.dp),
                        colors = if (num == -1) CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ) else CardDefaults.cardColors(
                            containerColor = colors[num % colors.size].copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.aspectRatio(1f)
                    ) {
                        if (num != -1) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    (num + 1).toString(),
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = colors[num % colors.size]
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==================== MAZE SCREEN ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MazeScreen(
    viewModel: ExtraGamesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.maze.collectAsState()
    LaunchedEffect(Unit) { viewModel.startMaze() }
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.moves, state.xpEarned, state.coinsEarned, true,
            { viewModel.resetMaze() }, onNavigateHome
        )
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Maze") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text("Moves: ${state.moves}", modifier = Modifier.padding(end = 16.dp))
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Reach the ⭐! Use buttons below",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = { viewModel.moveMazePlayer("up") },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(12.dp),
                        modifier = Modifier.size(60.dp)
                    ) { Icon(Icons.Default.ArrowUpward, contentDescription = "Up") }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { viewModel.moveMazePlayer("left") },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(12.dp),
                        modifier = Modifier.size(60.dp)
                    ) { Icon(Icons.Default.ArrowBack, contentDescription = "Left") }
                    Box(modifier = Modifier.size(60.dp))
                    Button(
                        onClick = { viewModel.moveMazePlayer("right") },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(12.dp),
                        modifier = Modifier.size(60.dp)
                    ) { Icon(Icons.Default.ArrowForward, contentDescription = "Right") }
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = { viewModel.moveMazePlayer("down") },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(12.dp),
                        modifier = Modifier.size(60.dp)
                    ) { Icon(Icons.Default.ArrowDownward, contentDescription = "Down") }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(state.gridSize),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(state.gridSize * state.gridSize) { index ->
                    val r = index / state.gridSize
                    val c = index % state.gridSize
                    val isWall = state.grid[r][c] == 1
                    val isPlayer = r to c == state.playerPos
                    val isGoal = r to c == state.goalPos
                    Card(
                        shape = RoundedCornerShape(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                isWall -> Color(0xFF2D3436)
                                isPlayer -> Color(0xFFFF6B35)
                                isGoal -> Color(0xFFFFD166)
                                else -> Color.White
                            }
                        ),
                        modifier = Modifier.aspectRatio(1f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                isPlayer -> Text("🧒", fontSize = 16.sp)
                                isGoal -> Text("⭐", fontSize = 16.sp)
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==================== FIND OBJECT SCREEN ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindObjectScreen(
    viewModel: ExtraGamesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit,
    topicId: String
) {
    val state by viewModel.findObj.collectAsState()
    LaunchedEffect(topicId) { viewModel.startFindObject(topicId) }
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.targetCount, state.xpEarned, state.coinsEarned, false,
            { viewModel.resetFindObject(topicId) }, onNavigateHome
        )
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find: ${state.targetEmoji}") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        "Found: ${state.foundCount}/${state.targetCount}",
                        modifier = Modifier.padding(end = 16.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B35)
                    )
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
                .padding(padding)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Find all ${state.targetEmoji}! Tap them!",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(state.gridSize),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.grid.size) { index ->
                    val isFound = state.foundPositions.contains(index)
                    Card(
                        onClick = { viewModel.tapFindObject(index) },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isFound) Color(0xFF06D6A0).copy(alpha = 0.3f)
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier.aspectRatio(1f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isFound) Icon(
                                Icons.Default.CheckCircle, null,
                                tint = Color(0xFF06D6A0),
                                modifier = Modifier.size(32.dp)
                            )
                            else Text(state.grid[index], fontSize = 28.sp)
                        }
                    }
                }
            }
        }
    }
}

// ==================== FRUIT CATCH SCREEN ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitCatchScreen(
    viewModel: ExtraGamesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.fruitCatch.collectAsState()
    LaunchedEffect(Unit) { viewModel.startFruitCatch() }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.caughtCount, state.xpEarned, state.coinsEarned, false,
            { viewModel.resetFruitCatch() }, onNavigateHome
        )
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fruit Catch!") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.stopFruitCatch()
                        onNavigateBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        "❤️ ${state.lives}  🍎 ${state.score}",
                        modifier = Modifier.padding(end = 12.dp),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .pointerInput(Unit) {
                    detectTapGestures { viewModel.catchFruit() }
                }
        ) {
            state.fallingItems.forEach { item ->
                Text(
                    item.emoji,
                    fontSize = 36.sp,
                    modifier = Modifier.offset(
                        x = (item.x * 320).dp,
                        y = (item.y * 550).dp
                    )
                )
            }
            Text(
                "🧺",
                fontSize = 42.sp,
                modifier = Modifier
                    .offset(x = (state.basketX * 320 - 20).dp, y = 500.dp)
                    .align(Alignment.BottomCenter)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        viewModel.moveBasket(
                            (state.basketX - 0.15f).coerceIn(0.05f, 0.85f)
                        )
                    },
                    shape = RoundedCornerShape(12.dp)
                ) { Icon(Icons.Default.ArrowBack, contentDescription = "Left") }
                Button(
                    onClick = { viewModel.catchFruit() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B35))
                ) { Text("Catch!", fontWeight = FontWeight.Bold) }
                Button(
                    onClick = {
                        viewModel.moveBasket(
                            (state.basketX + 0.15f).coerceIn(0.05f, 0.85f)
                        )
                    },
                    shape = RoundedCornerShape(12.dp)
                ) { Icon(Icons.Default.ArrowForward, contentDescription = "Right") }
            }
        }
    }
}

// ==================== WORD BUILDER SCREEN ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordBuilderScreen(
    viewModel: ExtraGamesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit,
    topicId: String
) {
    val state by viewModel.wordBuilder.collectAsState()
    LaunchedEffect(topicId) { viewModel.startWordBuilder(topicId) }
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.totalRounds, state.xpEarned, state.coinsEarned, false,
            { viewModel.resetWordBuilder(topicId) }, onNavigateHome
        )
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Word Builder R${state.round + 1}/${state.totalRounds}") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        "Score: ${state.score}",
                        modifier = Modifier.padding(end = 16.dp),
                        color = Color(0xFF7B2FF7),
                        fontWeight = FontWeight.Bold
                    )
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
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Build: ${state.targetWord.uppercase()}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7B2FF7)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        if (state.builtWord.isEmpty()) "..."
                        else state.builtWord.uppercase(),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (state.builtWord == state.targetWord) Color(0xFF06D6A0)
                            else MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
            if (state.showResult) {
                Text(
                    if (state.lastCorrect) "✅ Correct!"
                    else "❌ Wrong! It was ${state.targetWord}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (state.lastCorrect) Color(0xFF06D6A0) else Color(0xFFEF476F)
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Tap letters:", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                state.availableLetters.forEachIndexed { index, charItem ->
                    Card(
                        onClick = { viewModel.tapWordBuilderChar(index) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (charItem.used) Color.Gray.copy(alpha = 0.2f)
                            else Color(0xFFFF6B35).copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                charItem.char.uppercase(),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (charItem.used) Color.Gray else Color(0xFFFF6B35)
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { viewModel.resetWordBuilderChar() },
                shape = RoundedCornerShape(12.dp)
            ) { Text("Undo Last") }
        }
    }
}

// ==================== COLOR MATCH SCREEN ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorMatchScreen(
    viewModel: ExtraGamesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.colorMatch.collectAsState()
    LaunchedEffect(Unit) { viewModel.startColorMatch() }
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.pairs.size, state.xpEarned, state.coinsEarned, false,
            { viewModel.resetColorMatch() }, onNavigateHome
        )
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Color Match") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        "Score: ${state.score}",
                        modifier = Modifier.padding(end = 16.dp),
                        color = Color(0xFF06D6A0),
                        fontWeight = FontWeight.Bold
                    )
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
                .padding(padding)
                .padding(12.dp)
        ) {
            Text(
                "Match colors to names!",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Colors:", style = MaterialTheme.typography.labelMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(state.pairs) { index, pair ->
                    val isSelected = state.selectedColor == index
                    Card(
                        onClick = { viewModel.selectColorMatchColor(index) },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color(pair.color)
                        ),
                        modifier = Modifier
                            .size(56.dp)
                            .then(
                                if (isSelected) Modifier.border(
                                    BorderStroke(3.dp, Color(0xFFFF6B35)),
                                    CircleShape
                                ) else Modifier
                            )
                    ) {}
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Names:", style = MaterialTheme.typography.labelMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(state.pairs) { index, pair ->
                    val isSelected = state.selectedName == index
                    val isMatched = pair.colorMatched && pair.nameMatched
                    Card(
                        onClick = { viewModel.selectColorMatchName(index) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                isMatched -> Color(0xFF06D6A0).copy(alpha = 0.2f)
                                isSelected -> Color(0xFFFF6B35).copy(alpha = 0.15f)
                                else -> MaterialTheme.colorScheme.surface
                            }
                        )
                    ) {
                        Text(
                            pair.name,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = if (isMatched) Color(0xFF06D6A0)
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

// ==================== BUBBLE POP GAME SCREEN ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubblePopGameScreen(
    viewModel: ExtraGamesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.bubblePop.collectAsState()
    LaunchedEffect(Unit) { viewModel.startBubblePop() }
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.totalRounds, state.xpEarned, state.coinsEarned, false,
            { viewModel.resetBubblePop() }, onNavigateHome
        )
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bubble Pop R${state.round}/${state.totalRounds}") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        "Score: ${state.score}",
                        modifier = Modifier.padding(end = 16.dp),
                        color = Color(0xFF7B2FF7),
                        fontWeight = FontWeight.Bold
                    )
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
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val targetHex = state.bubbles.firstOrNull()?.colorHex ?: 0xFFFF6B35
            Text(
                "Pop all '${state.targetColor}' bubbles!",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(targetHex)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                state.bubbles.forEach { bubble ->
                    Card(
                        onClick = {
                            viewModel.popBubble(state.bubbles.indexOf(bubble))
                        },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color(bubble.colorHex)
                        ),
                        modifier = Modifier.size(72.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                bubble.color.first().toString(),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
            if (state.showRoundResult) {
                Text(
                    if (state.roundCorrect) "✅ Correct!" else "❌ Missed!",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (state.roundCorrect) Color(0xFF06D6A0) else Color(0xFFEF476F)
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

// ==================== SPOT DIFFERENCE SCREEN ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotDiffScreen(
    viewModel: ExtraGamesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.spotDiff.collectAsState()
    LaunchedEffect(Unit) { viewModel.startSpotDiff() }
    if (state.isLoading) { LoadingIndicator(); return }
    if (state.isComplete) {
        ResultOverlay(
            state.score, state.totalRounds, state.xpEarned, state.coinsEarned, false,
            { viewModel.resetSpotDiff() }, onNavigateHome
        )
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spot Difference R${state.round}/${state.totalRounds}") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        "Score: ${state.score}",
                        modifier = Modifier.padding(end = 16.dp),
                        color = Color(0xFFEF476F),
                        fontWeight = FontWeight.Bold
                    )
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
                .padding(padding)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Tap the DIFFERENT emoji in the RIGHT grid!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Original", style = MaterialTheme.typography.labelSmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxWidth(0.42f).aspectRatio(1f),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(state.leftGrid.size) { i ->
                            Card(
                                modifier = Modifier.aspectRatio(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                        alpha = 0.3f
                                    )
                                )
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) { Text(state.leftGrid[i], fontSize = 28.sp) }
                            }
                        }
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Find diff!",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFEF476F),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxWidth(0.42f).aspectRatio(1f),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(state.rightGrid.size) { i ->
                            val isFound = i == state.diffPosition && state.found
                            Card(
                                onClick = { viewModel.tapSpotDiff("right", i) },
                                modifier = Modifier.aspectRatio(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isFound) Color(0xFF06D6A0).copy(
                                        alpha = 0.3f
                                    ) else MaterialTheme.colorScheme.surfaceVariant.copy(
                                        alpha = 0.3f
                                    )
                                )
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) { Text(state.rightGrid[i], fontSize = 28.sp) }
                            }
                        }
                    }
                }
            }
            if (state.showRoundResult) {
                Text(
                    if (state.roundCorrect) "✅ Found it!" else "❌ Try again!",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (state.roundCorrect) Color(0xFF06D6A0) else Color(0xFFEF476F)
                    ),
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}