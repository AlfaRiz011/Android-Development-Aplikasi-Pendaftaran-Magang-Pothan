package com.example.capstone.data.model

import com.google.gson.annotations.SerializedName

data class JobApply(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("job_id")
	val jobId: Int? = null,

	@field:SerializedName("alasan_penolakan")
	val alasanPenolakan: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("User")
	val user: User? = null,

	@field:SerializedName("Lowongan")
	val lowongan: Lowongan? = null
)
