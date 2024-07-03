package com.app.kalyanbazar.model.request

import com.google.gson.annotations.SerializedName

data class RequestCreateUserApi(

	@field:SerializedName("user")
	val user: Int? = null,

	@field:SerializedName("upi_id")
	val upiId: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
