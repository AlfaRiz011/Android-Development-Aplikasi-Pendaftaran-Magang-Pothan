package com.example.capstone.data.remote

import com.example.capstone.data.model.Document
import com.example.capstone.data.model.JobApply
import com.example.capstone.data.model.Login
import com.example.capstone.data.model.Lowongan
import com.example.capstone.data.model.PesertaAktif
import com.example.capstone.data.model.Register
import com.example.capstone.data.model.User
import com.example.capstone.data.request.JobBodyRequest
import com.example.capstone.data.request.LoginBodyRequest
import com.example.capstone.data.request.RegisterBodyRequest
import com.example.capstone.data.response.GenericResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //AUTH
    @POST("auth/check")
    fun checkUser(
        @Body checkBodyRequest: LoginBodyRequest
    ): Call<GenericResponse<Register>>

    @POST("auth/register")
    fun register(
        @Body checkBodyRequest: RegisterBodyRequest
    ): Call<GenericResponse<User>>

    @POST("auth/login/")
    fun login(
        @Body loginRequest: LoginBodyRequest
    ): Call<GenericResponse<Login>>

    //USER
    @GET("user/{userId}")
    fun getUserById(
        @Path("userId") userId: String
    ): Call<GenericResponse<User>>

    @POST("user/apply/{userId}")
    fun applyJobUser(
        @Path("userId") userId: String,
        @Query("jobId") jobId: String
    ): Call<GenericResponse<User>>

    @Multipart
    @POST("user/upload/{userId}")
    fun uploadDocUser(
        @Path("userId") userId: String,
        @Query("jenis_dokumen") jenisDokumen: String,
        @Part file: MultipartBody.Part?
    ): Call<GenericResponse<Document>>

    // JOB
    @GET("job/")
    fun getAllJobs(): Call<GenericResponse<List<Lowongan>>>

    @GET("job/all/request")
    fun getAllRequested(): Call<GenericResponse<List<JobApply>>>

    @GET("job/{jobId}")
    fun getJobById(
        @Path("jobId") jobId: String
    ): Call<GenericResponse<Lowongan>>

    @GET("job/regis/{userId}")
    fun getRegisteredJobs(
        @Path("userId") userId: String
    ): Call<GenericResponse<List<JobApply>>>

    @GET("job/admin")
    fun getAllJobsAdmin(): Call<GenericResponse<List<PesertaAktif>>>

    @GET("job/regis/verif/{regisId}")
    fun getVerifiedRegistration(
        @Path("regisId") regisId: String
    ): Call<GenericResponse<JobApply>>

    @POST("job/make")
    fun createJob(
        @Body jobRequest: JobBodyRequest
    ): Call<GenericResponse<Lowongan>>

    @PUT("job/verif/regis/{regisId}")
    fun verifyJobRegistration(
        @Path("regisId") regisId: String
    ): Call<GenericResponse<JobApply>>

    @PUT("job/tolak/regis/{regisId}")
    fun rejectJobRegistration(
        @Path("regisId") regisId: String
    ): Call<GenericResponse<JobApply>>

    //DOCUMENT
    @GET("document/{userId}")
    fun getUserDocuments(
        @Path("userId") userId: String
    ): Call<GenericResponse<List<Document>>>

    @GET("document/")
    fun getAllDocument(): Call<GenericResponse<List<Document>>>

    @GET("document/dokum/{dokumId}")
    fun getVerifiedDocument(
        @Path("dokumId") dokumId: String
    ): Call<GenericResponse<Document>>

    @PUT("document/verif/dokum/{dokumId}")
    fun verifyDocument(
        @Path("dokumId") dokumId: String
    ): Call<GenericResponse<Document>>

    @PUT("document/tolak/dokum/{dokumId}")
    fun rejectDocument(
        @Path("dokumId") dokumId: String
    ): Call<GenericResponse<Document>>
}