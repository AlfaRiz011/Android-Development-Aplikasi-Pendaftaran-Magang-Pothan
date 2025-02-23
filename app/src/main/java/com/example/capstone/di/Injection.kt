package com.example.capstone.di

import android.content.Context
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.local.dataStore
import com.example.capstone.data.remote.ApiConfig
import com.example.capstone.data.repository.AuthRepository
import com.example.capstone.data.repository.UserRepository
import com.example.capstone.data.repository.JobRepository
import com.example.capstone.data.repository.DocumentRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object  Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return AuthRepository.getInstance(apiService, pref)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return UserRepository.getInstance(apiService, pref)
    }

    fun provideJobRepository(context: Context): JobRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return JobRepository.getInstance(apiService, pref)
    }

    fun provideDocumentRepository(context: Context): DocumentRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return DocumentRepository.getInstance(apiService, pref)
    }

    fun providePreferences(context: Context): UserPreferences {
        val pref = UserPreferences.getInstance(context.dataStore)
        return pref
    }
}