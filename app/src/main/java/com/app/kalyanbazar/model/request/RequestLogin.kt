package com.app.kalyanbazar.model.request

import com.google.gson.annotations.SerializedName

data class RequestLogin(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("login_type")
	val loginType: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
