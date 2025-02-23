package com.example.capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.model.Document
import com.example.capstone.data.model.User
import com.example.capstone.data.remote.ApiService
import com.example.capstone.data.response.GenericResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserById(userId: String): LiveData<GenericResponse<User>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<User>>()
        apiService.getUserById(userId).enqueue(object : Callback<GenericResponse<User>> {
            override fun onResponse(
                call: Call<GenericResponse<User>>,
                response: Response<GenericResponse<User>>
            ) {
                resultLiveData.value = if (response.isSuccessful) {
                    response.body()
                } else {
                    GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<User>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
        return resultLiveData
    }

    fun applyJobUser(userId: String, jobId: String): LiveData<GenericResponse<User>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<User>>()
        apiService.applyJobUser(userId, jobId).enqueue(object : Callback<GenericResponse<User>> {
            override fun onResponse(
                call: Call<GenericResponse<User>>,
                response: Response<GenericResponse<User>>
            ) {
                resultLiveData.value = if (response.isSuccessful) {
                    response.body()
                } else {
                    GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<User>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
        return resultLiveData
    }

    fun uploadDocUser(
        userId: String,
        file: MultipartBody.Part?
    ): LiveData<GenericResponse<Document>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Document>>()
        apiService.uploadDocUser(userId, file)
            .enqueue(object : Callback<GenericResponse<Document>> {
                override fun onResponse(
                    call: Call<GenericResponse<Document>>,
                    response: Response<GenericResponse<Document>>
                ) {
                    resultLiveData.value = if (response.isSuccessful) {
                        response.body()
                    } else {
                        GenericResponse(
                            message = response.code().toString(),
                            status = response.body()?.status,
                            data = null
                        )
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<GenericResponse<Document>>, t: Throwable) {
                    resultLiveData.value = GenericResponse(
                        message = "500",
                        status = "error",
                        data = null
                    )
                    _isLoading.value = false
                }
            })
        return resultLiveData
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref)
            }.also { instance = it }
    }
}