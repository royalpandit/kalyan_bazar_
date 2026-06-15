package com.kalyan.kalyanbazzar.model.response

 import com.google.gson.annotations.SerializedName

 data class ResponseFullSangam(

	 @field:SerializedName("digit_type")
	val digitType: String? = null,

	 @field:SerializedName("updated_at")
	val updatedAt: String? = null,

	 @field:SerializedName("close_ank")
	val closeAnk: ArrayList<String> = ArrayList(),

	 @field:SerializedName("open_ank")
	val openAnk: ArrayList<String> = ArrayList(),

	 @field:SerializedName("created_at")
	val createdAt: String? = null,

	 @field:SerializedName("id")
	val id: Int? = null
)
