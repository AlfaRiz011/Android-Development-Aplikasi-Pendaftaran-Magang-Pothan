package com.example.capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.data.local.UserPreferences 
import com.example.capstone.data.model.JobApply
import com.example.capstone.data.model.Lowongan
import com.example.capstone.data.model.PesertaAktif 
import com.example.capstone.data.remote.ApiService
import com.example.capstone.data.request.JobBodyRequest
import com.example.capstone.data.response.GenericResponse 
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllJobs(): LiveData<GenericResponse<List<Lowongan>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Lowongan>>>()
        apiService.getAllJobs().enqueue(object : Callback<GenericResponse<List<Lowongan>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Lowongan>>>,
                response: Response<GenericResponse<List<Lowongan>>>
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

            override fun onFailure(call: Call<GenericResponse<List<Lowongan>>>, t: Throwable) {
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

    fun getAllRequested(): LiveData<GenericResponse<List<JobApply>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<JobApply>>>()
        apiService.getAllRequested().enqueue(object : Callback<GenericResponse<List<JobApply>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<JobApply>>>,
                response: Response<GenericResponse<List<JobApply>>>
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

            override fun onFailure(call: Call<GenericResponse<List<JobApply>>>, t: Throwable) {
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

    fun getJobById(jobId: String): LiveData<GenericResponse<Lowongan>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Lowongan>>()
        apiService.getJobById(jobId).enqueue(object : Callback<GenericResponse<Lowongan>> {
            override fun onResponse(
                call: Call<GenericResponse<Lowongan>>,
                response: Response<GenericResponse<Lowongan>>
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

            override fun onFailure(call: Call<GenericResponse<Lowongan>>, t: Throwable) {
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

    fun getRegisteredJobs(userId: String): LiveData<GenericResponse<List<JobApply>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<JobApply>>>()
        apiService.getRegisteredJobs(userId)
            .enqueue(object : Callback<GenericResponse<List<JobApply>>> {
                override fun onResponse(
                    call: Call<GenericResponse<List<JobApply>>>,
                    response: Response<GenericResponse<List<JobApply>>>
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

                override fun onFailure(call: Call<GenericResponse<List<JobApply>>>, t: Throwable) {
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

    fun getAllJobsAdmin(): LiveData<GenericResponse<List<PesertaAktif>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<PesertaAktif>>>()
        apiService.getAllJobsAdmin()
            .enqueue(object : Callback<GenericResponse<List<PesertaAktif>>> {
                override fun onResponse(
                    call: Call<GenericResponse<List<PesertaAktif>>>,
                    response: Response<GenericResponse<List<PesertaAktif>>>
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

                override fun onFailure(
                    call: Call<GenericResponse<List<PesertaAktif>>>,
                    t: Throwable
                ) {
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

    fun getVerifiedRegistration(regisId: String): LiveData<GenericResponse<JobApply>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<JobApply>>()
        apiService.getVerifiedRegistration(regisId)
            .enqueue(object : Callback<GenericResponse<JobApply>> {
                override fun onResponse(
                    call: Call<GenericResponse<JobApply>>,
                    response: Response<GenericResponse<JobApply>>
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

                override fun onFailure(call: Call<GenericResponse<JobApply>>, t: Throwable) {
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

    fun createJob(jobRequest: JobBodyRequest): LiveData<GenericResponse<Lowongan>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Lowongan>>()
        apiService.createJob(jobRequest).enqueue(object : Callback<GenericResponse<Lowongan>> {
            override fun onResponse(
                call: Call<GenericResponse<Lowongan>>,
                response: Response<GenericResponse<Lowongan>>
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

            override fun onFailure(call: Call<GenericResponse<Lowongan>>, t: Throwable) {
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

    fun verifyJobRegistration(regisId: String): LiveData<GenericResponse<JobApply>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<JobApply>>()
        apiService.verifyJobRegistration(regisId)
            .enqueue(object : Callback<GenericResponse<JobApply>> {
                override fun onResponse(
                    call: Call<GenericResponse<JobApply>>,
                    response: Response<GenericResponse<JobApply>>
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

                override fun onFailure(call: Call<GenericResponse<JobApply>>, t: Throwable) {
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

    fun rejectJobRegistration(regisId: String): LiveData<GenericResponse<JobApply>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<JobApply>>()
        apiService.rejectJobRegistration(regisId)
            .enqueue(object : Callback<GenericResponse<JobApply>> {
                override fun onResponse(
                    call: Call<GenericResponse<JobApply>>,
                    response: Response<GenericResponse<JobApply>>
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

                override fun onFailure(call: Call<GenericResponse<JobApply>>, t: Throwable) {
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
        private var instance: JobRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): JobRepository =
            instance ?: synchronized(this) {
                instance ?: JobRepository(apiService, pref)
            }.also { instance = it }
    }
}