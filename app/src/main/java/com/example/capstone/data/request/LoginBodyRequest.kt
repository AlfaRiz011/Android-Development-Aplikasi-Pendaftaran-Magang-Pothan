package com.example.capstone.data.request

import com.google.gson.annotations.SerializedName

data class LoginBodyRequest(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("email")
    val email: String? = null

)