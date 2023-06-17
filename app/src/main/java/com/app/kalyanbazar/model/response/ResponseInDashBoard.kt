package com.app.kalyanbazar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseInDashBoard(

	@field:SerializedName("game_status")
	val gameStatus: Boolean? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("is_insider")
	val isInsider: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("market_id_id")
	val marketIdId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
