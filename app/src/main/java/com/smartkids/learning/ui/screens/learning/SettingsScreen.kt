package com.smartkids.learning.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartkids.learning.ui.components.LoadingIndicator
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel, onNavigateBack: () -> Unit) {
    val darkMode by viewModel.darkMode.collectAsState()
    val sound by viewModel.soundEffects.collectAsState()
    val voice by viewModel.voiceInstructions.collectAsState()
    val haptic by viewModel.hapticFeedback.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Settings") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            SettingsSwitch("Dark Mode", "Switch between light and dark theme", darkMode, viewModel::setDarkMode, Icons.Default.DarkMode)
            HorizontalDivider()
            SettingsSwitch("Sound Effects", "Play sounds for actions", sound, viewModel::setSoundEffects, Icons.Default.VolumeUp)
            HorizontalDivider()
            SettingsSwitch("Voice Instructions", "Read questions aloud", voice, viewModel::setVoiceInstructions, Icons.Default.RecordVoiceOver)
            HorizontalDivider()
            SettingsSwitch("Haptic Feedback", "Vibrate on interactions", haptic, viewModel::setHapticFeedback, Icons.Default.Vibration)
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Smart Kids Learning v1.0.0", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
fun SettingsSwitch(title: String, subtitle: String, checked: Boolean, onChecked: (Boolean) -> Unit, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onChecked)
    }
}