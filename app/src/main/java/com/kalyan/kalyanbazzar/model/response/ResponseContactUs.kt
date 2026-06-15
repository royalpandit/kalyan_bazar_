package com.kalyan.kalyanbazzar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseContactUs(

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null
)
