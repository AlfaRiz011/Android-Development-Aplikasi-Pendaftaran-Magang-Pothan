package com.example.capstone.data.model

import com.google.gson.annotations.SerializedName

data class Document(

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("jenis_dokumen")
	val jenisDokumen: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("uploaded_at")
	val uploadedAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)
