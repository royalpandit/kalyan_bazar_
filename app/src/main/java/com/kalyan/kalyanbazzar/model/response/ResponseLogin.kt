package com.kalyan.kalyanbazzar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("ResponseLogin")
	val responseLogin: ArrayList<ResponseLoginItem> = ArrayList()
)

data class ResponseLoginItem(

	@field:SerializedName("user_status")
	val userStatus: Boolean? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("user_pin")
	val userPin: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
