package com.example.capstone.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.repository.DocumentRepository
import com.example.capstone.di.Injection
import com.example.capstone.ui.viewmodel.DocumentViewModel

class DocumentViewModelFactory(
    private val repository: DocumentRepository,
    private val pref: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DocumentViewModel::class.java)) {
            return DocumentViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: DocumentViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): DocumentViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: DocumentViewModelFactory(
                    Injection.provideDocumentRepository(context),
                    Injection.providePreferences(context)
                ).also { instance = it }
            }
        }
    }
}