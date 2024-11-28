package com.example.samparcel.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samparcel.app.models.User
import com.samparcel.app.repositories.UserRepository
import com.samparcel.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userProfileState = MutableStateFlow<Resource<User>>(Resource.Idle())
    val userProfileState: StateFlow<Resource<User>> = _userProfileState

    private val _profileUpdateState = MutableStateFlow<Resource<User>>(Resource.Idle())
    val profileUpdateState: StateFlow<Resource<User>> = _profileUpdateState

    fun fetchUserProfile(userId: String) {
        viewModelScope.launch {
            userRepository.getUserProfile(userId).collect { result ->
                _userProfileState.value = result
            }
        }
    }

    fun updateUserProfile(user: User) {
        viewModelScope.launch {
            userRepository.updateUserProfile(user).collect { result ->
                _profileUpdateState.value = result
            }
        }
    }
}