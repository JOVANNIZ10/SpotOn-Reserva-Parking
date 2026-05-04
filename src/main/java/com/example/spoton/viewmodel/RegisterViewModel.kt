package com.example.spoton.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spoton.data.User
import com.example.spoton.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

class RegisterViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state: StateFlow<RegisterState> = _state

    fun register(
        nombre: String,
        usuario: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _state.value = RegisterState.Loading
            val user = User(
                nombre = nombre,
                usuario = usuario,
                email = email
            )
            repository.registerUser(email, password, user)
                .onSuccess { _state.value = RegisterState.Success }
                .onFailure { _state.value = RegisterState.Error(it.message ?: "Error desconocido") }
        }
    }
}
