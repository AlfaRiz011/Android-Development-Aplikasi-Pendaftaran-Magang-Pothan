package com.example.capstone.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.model.Login
import com.example.capstone.data.model.Register
import com.example.capstone.data.model.User
import com.example.capstone.data.repository.AuthRepository
import com.example.capstone.data.request.LoginBodyRequest
import com.example.capstone.data.request.RegisterBodyRequest
import com.example.capstone.data.response.GenericResponse
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AuthViewModel(
    private val repository: AuthRepository,
    pref: UserPreferences
) : ViewModel() {

    var preferences = pref
    var isLoading = repository.isLoading

    fun register(registerData: RegisterBodyRequest): LiveData<GenericResponse<User>> {
        return repository.register(registerData)
    }

    fun login(loginData: LoginBodyRequest): LiveData<GenericResponse<Login>> {
        return repository.login(loginData)
    }

    fun checkUser(data: LoginBodyRequest): LiveData<GenericResponse<Register>> {
        return repository.checkUser(data)
    }
}