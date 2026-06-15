package com.kalyan.kalyanbazzar.model.response

 import com.google.gson.annotations.SerializedName

 data class ResponseHowtoHelp(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)
