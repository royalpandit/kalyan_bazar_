package com.kalyan.kalyanbazzar.model.request

import com.google.gson.annotations.SerializedName

data class RequestForgotPassword(

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,
	@field:SerializedName("new_password")
	val newPassword: String? = null,
	@field:SerializedName("m_pin")
	val mPin: String? = null
)
