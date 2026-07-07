package com.smartkids.learning.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartkids.learning.domain.model.Topic

@Composable
fun TopicCard(
    topic: Topic,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = Color(topic.primaryColor)
    val secondaryColor = Color(topic.secondaryColor)
    var pressed by remember { mutableStateOf(false) }
    val cardColor by animateColorAsState(
        targetValue = if (pressed) secondaryColor.copy(alpha = 0.3f) else Color.White,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "cardColor"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            pressed = true
            onClick()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getIconForTopic(topic.iconName),
                    contentDescription = topic.name,
                    tint = primaryColor,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = topic.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = topic.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                topic.progress?.let { progress ->
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progress.progressPercentage },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = primaryColor,
                        trackColor = primaryColor.copy(alpha = 0.15f),
                    )
                    Text(
                        text = "${(progress.progressPercentage * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor
                    )
                }
            }

            if (topic.isPremium) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Premium",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(primaryColor)
        )
    }
}

fun getIconForTopic(iconName: String): ImageVector {
    return when (iconName) {
        "alphabet" -> Icons.Default.FontDownload
        "draw" -> Icons.Default.Draw
        "language" -> Icons.Default.Translate
        "pin" -> Icons.Default.Pin
        "calculate" -> Icons.Default.Calculate
        "add_circle" -> Icons.Default.AddCircle
        "remove_circle" -> Icons.Default.RemoveCircle
        "close" -> Icons.Default.Close
        "crop_square" -> Icons.Default.CropSquare
        "category" -> Icons.Default.Category
        "palette" -> Icons.Default.Palette
        "apple" -> Icons.Default.Apple
        "eco" -> Icons.Default.Eco
        "pets" -> Icons.Default.Pets
        "flutter_dash" -> Icons.Default.FlutterDash
        "directions_car" -> Icons.Default.DirectionsCar
        "local_florist" -> Icons.Default.LocalFlorist
        "accessibility_new" -> Icons.Default.AccessibilityNew
        "calendar_today" -> Icons.Default.CalendarToday
        "date_range" -> Icons.Default.DateRange
        "wb_sunny" -> Icons.Default.WbSunny
        "cloud" -> Icons.Default.Cloud
        "swap_horiz" -> Icons.Default.SwapHoriz
        "volunteer_activism" -> Icons.Default.VolunteerActivism
        "shield" -> Icons.Default.Shield
        "lightbulb" -> Icons.Default.Lightbulb
        "science" -> Icons.Default.Science
        "public" -> Icons.Default.Public
        "flag" -> Icons.Default.Flag
        "map" -> Icons.Default.Map
        "schedule" -> Icons.Default.Schedule
        "event_note" -> Icons.Default.EventNote
        "music_note" -> Icons.Default.MusicNote
        "auto_stories" -> Icons.Default.AutoStories
        "record_voice_over" -> Icons.Default.RecordVoiceOver
        "menu_book" -> Icons.Default.MenuBook
        "mic" -> Icons.Default.Mic
        "headphones" -> Icons.Default.Headphones
        "chrome_reader_mode" -> Icons.Default.ChromeReaderMode
        "edit" -> Icons.Default.Edit
        "sports_esports" -> Icons.Default.SportsEsports
        "school" -> Icons.Default.School
        "book" -> Icons.Default.Book
        "star" -> Icons.Default.Star
        "workspace_premium" -> Icons.Default.WorkspacePremium
        "emoji_events" -> Icons.Default.EmojiEvents
        else -> Icons.Default.Circle
    }
}