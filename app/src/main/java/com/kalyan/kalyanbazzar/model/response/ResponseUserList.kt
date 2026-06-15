package com.kalyan.kalyanbazzar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseUserList(

	@field:SerializedName("user_status")
	val userStatus: Boolean? = null,

	@field:SerializedName("user_pin")
	val userPin: Any? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("betting")
	val betting: Boolean? = null,

	@field:SerializedName("referral_count")
	val referralCount: Any? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("transfer")
	val transfer: Boolean? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("total_amount")
	val totalAmount: Int? = null,

	@field:SerializedName("referral_id")
	val referralId: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
