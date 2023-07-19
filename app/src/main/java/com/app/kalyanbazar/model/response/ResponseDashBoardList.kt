package com.app.kalyanbazar.model.response

import com.google.gson.annotations.SerializedName



data class ResponseDashBoardListItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("market_closing_time")
	val marketClosingTime: String? = null,

	@field:SerializedName("market_opening_time")
	val marketOpeningTime: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("market_code")
	val marketCode: String? = null,
	@field:SerializedName("market_type")
	val marketType: String? = null,

	@field:SerializedName("market_status")
	val marketStatus: Boolean? = null,

	@field:SerializedName("market_name")
	val marketName: String? = null
)
