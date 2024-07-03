package com.app.kalyanbazar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGetBid(

	@field:SerializedName("pana")
	val pana: String? = null,

	@field:SerializedName("market_name")
	val marketName: String? = null,

	@field:SerializedName("market_inside_name")
	val marketInsideName: String? = null,

	@field:SerializedName("market_inside_id_id")
	val marketInsideIdId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id_id")
	val userIdId: Int? = null,

	@field:SerializedName("pana_date")
	val panaDate: String? = null,

	@field:SerializedName("session")
	val session: Boolean? = null,

	@field:SerializedName("is_won")
	val isWon: Boolean? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("points")
	val points: Float? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
