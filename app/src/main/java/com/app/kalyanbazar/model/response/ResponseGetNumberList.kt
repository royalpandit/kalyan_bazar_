package com.app.kalyanbazar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGetNumberList(

	@field:SerializedName("digit_type")
	val digitType: String? = null,

	@field:SerializedName("number")
	val number: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
