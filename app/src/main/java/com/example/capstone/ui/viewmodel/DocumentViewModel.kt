package com.example.capstone.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.model.Document
import com.example.capstone.data.repository.DocumentRepository
import com.example.capstone.data.response.GenericResponse

class DocumentViewModel(
    private val repository: DocumentRepository,
    private val pref: UserPreferences
) : ViewModel() {

    val preferences = pref
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getUserDocuments(userId: String): LiveData<GenericResponse<List<Document>>> {
        return repository.getUserDocuments(userId)
    }

    fun getAllDocument(): LiveData<GenericResponse<List<Document>>> {
        return repository.getAllDocuments()
    }

    fun getVerifiedDocument(dokumId: String): LiveData<GenericResponse<Document>> {
        return repository.getVerifiedDocument(dokumId)
    }

    fun verifyDocument(dokumId: String): LiveData<GenericResponse<Document>> {
        return repository.verifyDocument(dokumId)
    }

    fun rejectDocument(dokumId: String): LiveData<GenericResponse<Document>> {
        return repository.rejectDocument(dokumId)
    }
}
