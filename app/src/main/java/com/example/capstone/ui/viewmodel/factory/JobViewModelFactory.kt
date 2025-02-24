package com.example.capstone.ui.viewmodel.factory

import com.example.capstone.ui.viewmodel.JobViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.repository.JobRepository
import com.example.capstone.di.Injection

class JobViewModelFactory(
    private val repository: JobRepository,
    private val pref: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobViewModel ::class.java)) {
            return JobViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: JobViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): JobViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: JobViewModelFactory(
                    Injection.provideJobRepository(context),
                    Injection.providePreferences(context)
                ).also { instance = it }
            }
        }
    }
}