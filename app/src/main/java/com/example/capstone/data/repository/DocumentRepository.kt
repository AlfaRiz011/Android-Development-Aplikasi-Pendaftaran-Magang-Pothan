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

class DocumentRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDocuments(userId: String): LiveData<GenericResponse<List<Document>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Document>>>()
        apiService.getUserDocuments(userId)
            .enqueue(object : Callback<GenericResponse<List<Document>>> {
                override fun onResponse(
                    call: Call<GenericResponse<List<Document>>>,
                    response: Response<GenericResponse<List<Document>>>
                ) {
                    resultLiveData.value = response.body() ?: GenericResponse(
                        message = response.code().toString(),
                        status = "error",
                        data = null
                    )
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<GenericResponse<List<Document>>>, t: Throwable) {
                    resultLiveData.value =
                        GenericResponse(message = "500", status = "error", data = null)
                    _isLoading.value = false
                }
            })
        return resultLiveData
    }

    fun getVerifiedDocument(dokumId: String): LiveData<GenericResponse<Document>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Document>>()
        apiService.getVerifiedDocument(dokumId)
            .enqueue(object : Callback<GenericResponse<Document>> {
                override fun onResponse(
                    call: Call<GenericResponse<Document>>,
                    response: Response<GenericResponse<Document>>
                ) {
                    resultLiveData.value = response.body() ?: GenericResponse(
                        message = response.code().toString(),
                        status = "error",
                        data = null
                    )
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<GenericResponse<Document>>, t: Throwable) {
                    resultLiveData.value =
                        GenericResponse(message = "500", status = "error", data = null)
                    _isLoading.value = false
                }
            })
        return resultLiveData
    }

    fun verifyDocument(dokumId: String): LiveData<GenericResponse<Document>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Document>>()
        apiService.verifyDocument(dokumId)
            .enqueue(object : Callback<GenericResponse<Document>> {
                override fun onResponse(
                    call: Call<GenericResponse<Document>>,
                    response: Response<GenericResponse<Document>>
                ) {
                    resultLiveData.value = response.body() ?: GenericResponse(
                        message = response.code().toString(),
                        status = "error",
                        data = null
                    )
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<GenericResponse<Document>>, t: Throwable) {
                    resultLiveData.value =
                        GenericResponse(message = "500", status = "error", data = null)
                    _isLoading.value = false
                }
            })
        return resultLiveData
    }

    fun rejectDocument(dokumId: String): LiveData<GenericResponse<Document>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Document>>()
        apiService.rejectDocument(dokumId).enqueue(object : Callback<GenericResponse<Document>> {
            override fun onResponse(
                call: Call<GenericResponse<Document>>,
                response: Response<GenericResponse<Document>>
            ) {
                resultLiveData.value = response.body() ?: GenericResponse(
                    message = response.code().toString(),
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Document>>, t: Throwable) {
                resultLiveData.value =
                    GenericResponse(message = "500", status = "error", data = null)
                _isLoading.value = false
            }
        })
        return resultLiveData
    }

    companion object {
        @Volatile
        private var instance: DocumentRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): DocumentRepository =
            instance ?: synchronized(this) {
                instance ?: DocumentRepository(apiService, pref)
            }.also { instance = it }
    }
}