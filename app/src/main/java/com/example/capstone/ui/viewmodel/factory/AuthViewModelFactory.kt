package com.example.capstone.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.repository.AuthRepository
import com.example.capstone.di.Injection
import com.example.capstone.ui.viewmodel.AuthViewModel

class AuthViewModelFactory(
    private val repository: AuthRepository,
    private val pref: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: AuthViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): AuthViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: AuthViewModelFactory(
                    Injection.provideAuthRepository(context),
                    Injection.providePreferences(context)
                ).also { instance = it }
            }
        }
    }
}