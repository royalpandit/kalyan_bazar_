package com.kalyan.kalyanbazzar.model.request

import com.google.gson.annotations.SerializedName

data class RequestNumberList(

	@field:SerializedName("number_type")
	val numberType: String? = null
)
