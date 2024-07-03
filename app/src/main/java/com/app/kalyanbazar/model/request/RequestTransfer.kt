package com.app.kalyanbazar.model.request

import com.google.gson.annotations.SerializedName

data class RequestTransfer(

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null
)
