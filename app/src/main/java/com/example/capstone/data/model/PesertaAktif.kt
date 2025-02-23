package com.example.capstone.data.model

import com.google.gson.annotations.SerializedName

data class PesertaAktif(

	@field:SerializedName("User")
	val user: User? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("job_id")
	val jobId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("Lowongan")
	val lowongan: Lowongan? = null
)
