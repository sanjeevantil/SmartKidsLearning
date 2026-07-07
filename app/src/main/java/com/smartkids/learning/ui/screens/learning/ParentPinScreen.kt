package com.smartkids.learning.ui.screens.parent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class ParentPinViewModel @Inject constructor(
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {
    val parentPin = userPrefs.parentPin
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentPinScreen(
    onPinCorrect: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ParentPinViewModel = hiltViewModel()
) {
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    val correctPin by viewModel.parentPin.collectAsState(initial = "1234")

    LaunchedEffect(pin) {
        if (pin.length == 4) {
            kotlinx.coroutines.delay(300)
            if (pin == correctPin) {
                onPinCorrect()
            } else {
                error = true
                pin = ""
            }
        } else {
            error = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parent Zone") },
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
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Lock,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Enter Parent PIN",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "4-digit PIN required",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = pin,
                onValueChange = { if (it.length <= 4) pin = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter 4-digit PIN") },
                isError = error,
                supportingText = { if (error) Text("Incorrect PIN") else null },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                (1..4).forEach { i ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (pin.length >= i) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.size(48.dp)
                    ) {}
                }
            }
            if (error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Wrong PIN, try again", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}