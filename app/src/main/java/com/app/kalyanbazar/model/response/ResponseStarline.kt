package com.app.kalyanbazar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseStarline(

	@field:SerializedName("market_closing_time")
	val marketClosingTime: Any? = null,

	@field:SerializedName("market_opening_time")
	val marketOpeningTime: String? = null,

	@field:SerializedName("close_pana_result")
	val closePanaResult: String? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("market_code")
	val marketCode: String? = null,

	@field:SerializedName("market_name")
	val marketName: String? = null,

	@field:SerializedName("opening_status")
	val openingStatus: Boolean? = null,

	@field:SerializedName("open_pana_result")
	val openPanaResult: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("market_off_day")
	val marketOffDay: MarketOffDay? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("market_status")
	val marketStatus: Boolean? = null,

	@field:SerializedName("market_type")
	val marketType: String? = null
)

data class MarketOffDay(
	val any: Any? = null
)
