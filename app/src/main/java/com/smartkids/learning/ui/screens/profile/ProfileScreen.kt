package com.smartkids.learning.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel, onNavigateBack: () -> Unit) {
    val name by viewModel.childName.collectAsState()
    val age by viewModel.childAge.collectAsState()
    val avatar by viewModel.avatarIndex.collectAsState()
    val avatars = listOf("\uD83D\uDC76","\uD83E\uDDD1","\uD83D\uDC69","\uD83D\uDC68","\uD83D\uDC66","\uD83D\uDC67","\uD83E\uDDD2","\uD83E\uDDD3")
    var editName by remember { mutableStateOf(name) }
    var editAge by remember { mutableStateOf(age.toString()) }
    var isEditing by remember { mutableStateOf(false) }

    Scaffold(topBar = { TopAppBar(title = { Text("Profile") }, navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, null) } }, actions = { IconButton(onClick = { isEditing = !isEditing }) { Icon(if (isEditing) Icons.Default.Check else Icons.Default.Edit, null) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(120.dp).clip(CircleShape()).background(Color(0xFFFF6B35).copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                Text(avatars[avatar % avatars.size], fontSize = 56.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (isEditing) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    avatars.forEachIndexed { i, emoji ->
                        Card(onClick = { viewModel.setAvatar(i) }, shape = CircleShape, colors = CardDefaults.cardColors(containerColor = if (i == avatar) Color(0xFFFF6B35) else MaterialTheme.colorScheme.surfaceVariant)) {
                            Text(emoji, modifier = Modifier.padding(8.dp), fontSize = 20.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (isEditing) {
                OutlinedTextField(value = editName, onValueChange = { editName = it }, label = { Text("Name") }, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = editAge, onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) editAge = it }, label = { Text("Age") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.setName(editName); viewModel.setAge(editAge.toIntOrNull() ?: 4); isEditing = false }, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B35))) { Text("Save") }
            } else {
                Text(name, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
                Text("Age: $age", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}