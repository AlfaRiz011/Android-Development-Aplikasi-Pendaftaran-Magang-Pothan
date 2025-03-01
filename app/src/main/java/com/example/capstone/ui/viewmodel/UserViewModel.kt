package com.example.capstone.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.model.Document
import com.example.capstone.data.model.User
import com.example.capstone.data.repository.UserRepository
import com.example.capstone.data.response.GenericResponse
import okhttp3.MultipartBody

class UserViewModel(
    private val repository: UserRepository,
    private val pref : UserPreferences
) : ViewModel() {

    val preferences = pref
    var isLoading = repository.isLoading

    fun getUserById(userId: String): LiveData<GenericResponse<User>> {
        return repository.getUserById(userId)
    }

    fun applyJobUser(userId: String, jobId: String): LiveData<GenericResponse<User>> {
        return repository.applyJobUser(userId, jobId)
    }

    fun uploadDocUser(
        userId: String,
        jenisDokumen: String,
        file: MultipartBody.Part?
    ): LiveData<GenericResponse<Document>> {
        return repository.uploadDocUser(userId, jenisDokumen, file)
    }
}
