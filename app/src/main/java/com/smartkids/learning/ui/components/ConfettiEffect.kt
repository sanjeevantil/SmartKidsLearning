package com.smartkids.learning.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ConfettiEffect(
    show: Boolean,
    durationMillis: Long = 3000,
    modifier: Modifier = Modifier
) {
    if (!show) return

    val particles = remember {
        List(60) {
            ConfettiParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat() * -1f,
                color = listOf(
                    Color(0xFFFF6B35), Color(0xFF00B4D8), Color(0xFF7B2FF7),
                    Color(0xFF06D6A0), Color(0xFFFFD166), Color(0xFFFF6B9D),
                    Color(0xFFEF476F), Color(0xFFFF9F1C)
                ).random(),
                size = Random.nextInt(6, 14).toFloat(),
                speedX = Random.nextFloat() * 2f - 1f,
                speedY = Random.nextFloat() * 3f + 2f,
                rotation = Random.nextFloat() * 360f,
                rotationSpeed = Random.nextFloat() * 10f - 5f,
                shape = Random.nextInt(0, 3)
            )
        }
    }

    val anim = remember { Animatable(0f) }

    LaunchedEffect(show) {
        anim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis.toInt(), easing = LinearOutSlowInEasing)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                drawContent()
                val progress = anim.value
                particles.forEach { p ->
                    val x = (p.x + p.speedX * progress * 0.3f) * size.width
                    val y = (p.y + p.speedY * progress) * size.height
                    if (y in 0f..size.height && x in 0f..size.width) {
                        val alpha = if (progress > 0.7f) 1f - ((progress - 0.7f) / 0.3f) else 1f
                        val rotation = (p.rotation + p.rotationSpeed * progress * 360) % 360f
                        drawContext.canvas.save()
                        drawContext.canvas.translate(x, y)
                        drawContext.canvas.rotate(rotation)
                        val scaleX = if (p.shape == 1) 0.5f else 1f
                        val scaleY = if (p.shape == 2) 0.5f else 1f
                        drawContext.canvas.scale(scaleX, scaleY)
                        drawRect(
                            color = p.color.copy(alpha = alpha.coerceIn(0f, 1f)),
                            size = Size(p.size, p.size)
                        )
                        drawContext.canvas.restore()
                    }
                }
            }
    )
}

private data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val color: Color,
    val size: Float,
    val speedX: Float,
    val speedY: Float,
    val rotation: Float,
    val rotationSpeed: Float,
    val shape: Int
)