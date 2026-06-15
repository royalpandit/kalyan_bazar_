package com.kalyan.kalyanbazzar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseWithdrawalList(

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("user_id__phone_number")
	val userIdPhoneNumber: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("is_rejected")
	val isRejected: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user_id__first_name")
	val userIdFirstName: String? = null
)
