package com.kalyan.kalyanbazzar.model.request

import com.google.gson.annotations.SerializedName

data class RequestWithdrwalFund(

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("feedback")
	val feedback: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null
)
