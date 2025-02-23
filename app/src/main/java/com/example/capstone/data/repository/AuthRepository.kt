package com.example.capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.model.Login
import com.example.capstone.data.model.Register
import com.example.capstone.data.model.User
import com.example.capstone.data.remote.ApiService
import com.example.capstone.data.request.LoginBodyRequest
import com.example.capstone.data.request.RegisterBodyRequest
import com.example.capstone.data.response.GenericResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(registerBodyRequest: RegisterBodyRequest): LiveData<GenericResponse<User>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<User>>()
        val client = apiService.register(registerBodyRequest)

        client.enqueue(object : Callback<GenericResponse<User>> {
            override fun onResponse(
                call: Call<GenericResponse<User>>,
                response: Response<GenericResponse<User>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
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


    fun login(loginBodyRequest: LoginBodyRequest): LiveData<GenericResponse<Login>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Login>>()
        val client = apiService.login(loginBodyRequest)

        client.enqueue(object : Callback<GenericResponse<Login>> {
            override fun onResponse(
                call: Call<GenericResponse<Login>>,
                response: Response<GenericResponse<Login>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()

                    CoroutineScope(Dispatchers.IO).launch {
                        response.body()?.data?.let {
                            pref.saveUser(it.user)

                            pref.saveTokenUser(it.token)
                            pref.setSession()

                            pref.saveRole(if (it.user?.email == "admin@gmail.com") "admin" else "user")
                        }
                    }
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Login>>, t: Throwable) {
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

    fun checkUser(register: LoginBodyRequest): LiveData<GenericResponse<Register>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Register>>()
        val client = apiService.checkUser(register)

        client.enqueue(object : Callback<GenericResponse<Register>> {
            override fun onResponse(
                call: Call<GenericResponse<Register>>,
                response: Response<GenericResponse<Register>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Register>>, t: Throwable) {
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
        private var instance: AuthRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, pref)
            }.also { instance = it }
    }
}