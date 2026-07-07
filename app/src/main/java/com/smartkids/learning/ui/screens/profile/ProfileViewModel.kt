package com.smartkids.learning.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartkids.learning.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userPrefs: UserPreferencesRepository) : ViewModel() {
    val childName: StateFlow<String> = userPrefs.childName.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Kid")
    val childAge: StateFlow<Int> = userPrefs.childAge.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 4)
    val avatarIndex: StateFlow<Int> = userPrefs.avatarIndex.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    fun setName(n: String) { viewModelScope.launch { userPrefs.setChildName(n) } }
    fun setAge(a: Int) { viewModelScope.launch { userPrefs.setChildAge(a) } }
    fun setAvatar(i: Int) { viewModelScope.launch { userPrefs.setAvatarIndex(i) } }
}