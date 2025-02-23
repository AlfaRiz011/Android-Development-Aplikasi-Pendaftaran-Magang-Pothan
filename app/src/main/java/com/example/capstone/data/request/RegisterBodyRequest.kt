package com.example.capstone.data.request

import com.google.gson.annotations.SerializedName

data class RegisterBodyRequest(

    @field:SerializedName("nim")
    val nim: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("universitas")
    val universitas: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("no_telp")
    val noTelp: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("tanggal_lahir")
    val tanggalLahir: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,
)