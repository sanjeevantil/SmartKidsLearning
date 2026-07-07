package com.smartkids.learning.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF6B35),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFD6BF),
    onPrimaryContainer = Color(0xFF5C1A00),
    secondary = Color(0xFF00B4D8),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB8E8F5),
    onSecondaryContainer = Color(0xFF003E4E),
    tertiary = Color(0xFF7B2FF7),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE8D5FF),
    onTertiaryContainer = Color(0xFF2E0065),
    error = Color(0xFFEF476F),
    onError = Color.White,
    errorContainer = Color(0xFFFFD9DD),
    onErrorContainer = Color(0xFF69001C),
    background = Color(0xFFFFF8F0),
    onBackground = Color(0xFF2D3436),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF2D3436),
    surfaceVariant = Color(0xFFF5EDE6),
    onSurfaceVariant = Color(0xFF52443B),
    outline = Color(0xFF857368),
    outlineVariant = Color(0xFFD8C3B7),
    inverseSurface = Color(0xFF3B2F28),
    inverseOnSurface = Color(0xFFFBEFE7),
    inversePrimary = Color(0xFFFFB68C),
    surfaceTint = Color(0xFFFF6B35),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB68C),
    onPrimary = Color(0xFF7A2E00),
    primaryContainer = Color(0xFFAA4400),
    onPrimaryContainer = Color(0xFFFFD6BF),
    secondary = Color(0xFF6DD4EC),
    onSecondary = Color(0xFF003646),
    secondaryContainer = Color(0xFF004D62),
    onSecondaryContainer = Color(0xFFB8E8F5),
    tertiary = Color(0xFFD0A9FF),
    onTertiary = Color(0xFF4B009C),
    tertiaryContainer = Color(0xFF6200CB),
    onTertiaryContainer = Color(0xFFE8D5FF),
    error = Color(0xFFFF6B9D),
    onError = Color(0xFF69001C),
    errorContainer = Color(0xFF9B0031),
    onErrorContainer = Color(0xFFFFD9DD),
    background = Color(0xFF1A1A2E),
    onBackground = Color(0xFFEAEAEA),
    surface = Color(0xFF16213E),
    onSurface = Color(0xFFEAEAEA),
    surfaceVariant = Color(0xFF52443B),
    onSurfaceVariant = Color(0xFFD8C3B7),
    outline = Color(0xFFA08E83),
    outlineVariant = Color(0xFF52443B),
    inverseSurface = Color(0xFFFBEFE7),
    inverseOnSurface = Color(0xFF3B2F28),
    inversePrimary = Color(0xFFFF6B35),
    surfaceTint = Color(0xFFFFB68C),
)

@Composable
fun SmartKidsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}