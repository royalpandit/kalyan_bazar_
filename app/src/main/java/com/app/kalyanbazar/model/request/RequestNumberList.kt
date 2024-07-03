package com.app.kalyanbazar.model.request

import com.google.gson.annotations.SerializedName

data class RequestNumberList(

	@field:SerializedName("number_type")
	val numberType: String? = null
)
