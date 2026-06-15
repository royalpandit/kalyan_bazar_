package com.kalyan.kalyanbazzar.model.request

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class RequestCreateBid(

	@field:SerializedName("dataToSend")
	//val submitRequests: List<SubmitRequestsItem?>? = null
	val dataToSend: JsonElement,
/*
 	@field:SerializedName("pana")
	val pana: String? = null,

	@field:SerializedName("market_inside_id")
	val marketInsideId: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("pana_date")
	val panaDate: String? = null,

	@field:SerializedName("session")
	val session: Boolean? = null,

	@field:SerializedName("is_won")
	val isWon: Boolean? = null,

	@field:SerializedName("points")
	val points: Int? = null,

	@field:SerializedName("status")
	val status: Boolean? = null*/
)
