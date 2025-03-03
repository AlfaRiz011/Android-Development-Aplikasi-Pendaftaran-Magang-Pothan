package com.example.capstone.data.request

import com.google.gson.annotations.SerializedName

data class JobBodyRequest(

    @field:SerializedName("closed_at")
    val closedAt: String? = null,

    @field:SerializedName("jabatan")
    val jabatan: String? = null,

    @field:SerializedName("periode_magang")
    val periodeMagang: String? = null,

    @field:SerializedName("posisi")
    val posisi: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null

)