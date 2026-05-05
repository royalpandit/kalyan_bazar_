package com.app.kalyanbazar.model.response

 import com.google.gson.annotations.SerializedName

 data class ResponseMerchantCode(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("number")
	val number: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
