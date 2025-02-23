package com.example.capstone.data.model

import com.google.gson.annotations.SerializedName

data class Login (

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("user")
    val user: User? = null,

)