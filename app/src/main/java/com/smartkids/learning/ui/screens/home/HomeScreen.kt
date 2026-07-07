package com.smartkids.learning.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartkids.learning.domain.model.Topic
import com.smartkids.learning.domain.model.UserProfile
import com.smartkids.learning.ui.components.*
import com.smartkids.learning.util.HapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToTopicList: (String) -> Unit,
    onNavigateToGameSelection: () -> Unit,
    onNavigateToRewards: () -> Unit,
    onNavigateToParentPin: () -> Unit,
    onNavigateToAchievements: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToQuiz: (String, Int) -> Unit,
    onNavigateToABC: () -> Unit,
    onNavigateToNumbers: () -> Unit,
    onNavigateToShapes: () -> Unit,
    onNavigateToColors: () -> Unit,
    onNavigateToAnimals: () -> Unit,
    onNavigateToFlashCards: (String) -> Unit,
    onNavigateToTracing: () -> Unit,    // <-- ADD THIS LINE
    haptic: HapticFeedback = hiltViewModel()
) {
    val profile by viewModel.userProfile.collectAsState()
    val challenge by viewModel.todayChallenge.collectAsState()
    val featuredTopics by viewModel.featuredTopics.collectAsState()
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Hi ${profile.childName}! 👋",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    IconButton(onClick = { haptic.lightTap(); onNavigateToSettings() }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                    IconButton(onClick = { haptic.mediumTap(); onNavigateToParentPin() }) {
                        Icon(Icons.Default.Lock, contentDescription = "Parent Zone", tint = Color.Gray)
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            XPBar(currentXP = profile.totalXP, level = profile.level)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatChip(Icons.Default.MonetizationOn, "Coins", "${profile.totalCoins}", Color(0xFFFFB800), Modifier.weight(1f))
                StatChip(Icons.Default.LocalFireDepartment, "Streak", "${profile.currentStreak}d", Color(0xFFFF6B35), Modifier.weight(1f))
                StatChip(Icons.Default.SportsEsports, "Games", "${profile.totalGamesPlayed}", Color(0xFF00B4D8), Modifier.weight(1f))
            }

            Text("Quick Learn", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                item { QuickLearnButton("ABC", Color(0xFFFF6B35), Icons.Default.FontDownload) { haptic.lightTap(); onNavigateToABC() } }
                item { QuickLearnButton("123", Color(0xFF00B4D8), Icons.Default.Pin) { haptic.lightTap(); onNavigateToNumbers() } }
                item { QuickLearnButton("Shapes", Color(0xFF7B2FF7), Icons.Default.Category) { haptic.lightTap(); onNavigateToShapes() } }
                item { QuickLearnButton("Colors", Color(0xFF06D6A0), Icons.Default.Palette) { haptic.lightTap(); onNavigateToColors() } }
                item { QuickLearnButton("Animals", Color(0xFFFF9F1C), Icons.Default.Pets) { haptic.lightTap(); onNavigateToAnimals() } }
            }

            challenge?.let { ch ->
                DailyChallengeCard(
                    challenge = ch,
                    isCompleted = ch.isCompleted,
                    onStart = { haptic.success(); onNavigateToQuiz(ch.topicId, ch.difficulty) }
                )
            }

            MascotBubble(text = getMotivationalMessage(profile.currentStreak))

            Text("Explore Topics", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { (id, name) ->
                    CategoryChip(name, id) { haptic.lightTap(); onNavigateToTopicList(id) }
                }
            }

            Text("Popular Topics", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(featuredTopics) { topic ->
                    TopicCard(
                        topic = topic,
                        onClick = {
                            haptic.lightTap()
                            when (topic.topicId) {
                                "abc_learning" -> onNavigateToABC()
                                "numbers" -> onNavigateToNumbers()
                                "shapes" -> onNavigateToShapes()
                                "colors" -> onNavigateToColors()
                                "animals" -> onNavigateToAnimals()
                                else -> onNavigateToQuiz(topic.topicId, 1)
                            }
                        }
                    )
                }
            }
        }

        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarItem(Icons.Default.Home, "Home", true) {}
                BottomBarItem(Icons.Default.MenuBook, "Topics", false) { haptic.lightTap(); onNavigateToTopicList("Language") }
                BottomBarItem(Icons.Default.SportsEsports, "Games", false) { haptic.lightTap(); onNavigateToGameSelection() }
                BottomBarItem(Icons.Default.EmojiEvents, "Rewards", false) { haptic.lightTap(); onNavigateToRewards() }
                BottomBarItem(Icons.Default.Person, "Profile", false) { haptic.lightTap(); onNavigateToProfile() }
            }
        }
    }
}

@Composable
fun QuickLearnButton(label: String, color: Color, icon: ImageVector, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f)),
        modifier = Modifier.size(80.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = color, fontSize = 11.sp)
        }
    }
}

@Composable
fun DailyChallengeCard(challenge: com.smartkids.learning.domain.model.DailyChallenge, isCompleted: Boolean, onStart: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(listOf(Color(0xFFFF6B35), Color(0xFFFF9F1C))),
                    RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Bolt, null, tint = Color.White, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Daily Challenge", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.White))
                    if (isCompleted) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.CheckCircle, null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text("${challenge.topicName} - ${challenge.challengeType.replace("_", " ").replaceFirstChar { it.uppercase() }}", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.9f))
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Reward: ${challenge.xpReward} XP + ${challenge.coinReward} Coins", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.weight(1f))
                    if (!isCompleted) {
                        Button(
                            onClick = onStart,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text("Start", color = Color(0xFFFF6B35), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(name: String, categoryId: String, onClick: () -> Unit) {
    val colors = mapOf(
        "Language" to Color(0xFFFF6B35), "Math" to Color(0xFF00B4D8),
        "World" to Color(0xFF06D6A0), "Time" to Color(0xFFFF6B9D),
        "Life Skills" to Color(0xFFEF476F), "Knowledge" to Color(0xFFFF9F1C),
        "Practice" to Color(0xFF7B2FF7)
    )
    val color = colors[categoryId] ?: Color(0xFFFF6B35)
    FilterChip(
        selected = false,
        onClick = onClick,
        label = { Text(name, fontWeight = FontWeight.Medium, fontSize = 13.sp) },
        shape = RoundedCornerShape(12.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = color,
            containerColor = color.copy(alpha = 0.1f),
            selectedLabelColor = Color.White
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = false,
            borderColor = color.copy(alpha = 0.3f),
            selectedBorderColor = color
        )
    )
}

@Composable
fun BottomBarItem(icon: ImageVector, label: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(40.dp)) {
            Icon(
                icon, contentDescription = label,
                tint = if (selected) Color(0xFFFF6B35) else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) Color(0xFFFF6B35) else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp
        )
    }
}

fun getMotivationalMessage(streak: Int): String = when {
    streak >= 30 -> "You're on fire! $streak days! 🔥"
    streak >= 7 -> "Amazing! $streak day streak! ⭐"
    streak >= 3 -> "Great $streak day streak! Keep going! 👏"
    streak > 0 -> "$streak day streak! Let's learn today!"
    else -> "Let's start learning today! 📚"
}