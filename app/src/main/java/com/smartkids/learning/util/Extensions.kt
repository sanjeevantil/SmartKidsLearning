package com.smartkids.learning.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch

fun Modifier.bounceClick(
    scaleDown: Float = 0.92f,
    onClick: () -> Unit
): Modifier = composed {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    this.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = {
            scope.launch {
                launch { scale.animateTo(scaleDown, spring(stiffness = Spring.StiffnessMediumHigh)) }
                onClick()
                launch { scale.animateTo(1f, spring(stiffness = Spring.StiffnessMediumLow)) }
            }
        }
    ).graphicsLayer {
        scaleX = scale.value
        scaleY = scale.value
    }
}

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
}

fun Int.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    return when {
        hours > 0 -> "${hours}h ${minutes}m"
        minutes > 0 -> "${minutes}m"
        else -> "${this}s"
    }
}

fun Float.toPercentage(): String = "${(this * 100).toInt()}%"