package com.kalyan.kalyanbazzar.model.request

import com.google.gson.annotations.SerializedName

data class RequestRegister(

	@field:SerializedName("user_status")
	val userStatus: Boolean? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("user_pin")
	val userPin: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
