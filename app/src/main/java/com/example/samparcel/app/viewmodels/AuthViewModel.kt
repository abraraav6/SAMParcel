package com.example.samparcel.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samparcel.app.models.User
import com.samparcel.app.repositories.AuthRepository
import com.samparcel.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow<Resource<User>>(Resource.Idle())
    val loginState: StateFlow<Resource<User>> = _loginState

    private val _registrationState = MutableStateFlow<Resource<User>>(Resource.Idle())
    val registrationState: StateFlow<Resource<User>> = _registrationState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collect { result ->
                _loginState.value = result
            }
        }
    }

    fun register(user: User, password: String) {
        viewModelScope.launch {
            authRepository.register(user, password).collect { result ->
                _registrationState.value = result
            }
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
