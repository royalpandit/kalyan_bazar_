package com.kalyan.kalyanbazzar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGetUserFund(

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("user_id__first_name")
	val userIdFirstName: String? = null,
	@field:SerializedName("transaction_type")
	val transactionType: String? = null
)
