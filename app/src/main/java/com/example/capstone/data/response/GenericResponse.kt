package com.example.capstone.data.response

import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(

    @field:SerializedName("data")
    val data: T? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)