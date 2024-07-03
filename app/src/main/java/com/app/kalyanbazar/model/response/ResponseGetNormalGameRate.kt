package com.app.kalyanbazar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGetNormalGameRate(

	@field:SerializedName("half_sangam_value_2")
	val halfSangamValue2: Int? = null,

	@field:SerializedName("double_pana_value_2")
	val doublePanaValue2: Int? = null,

	@field:SerializedName("half_sangam_value_1")
	val halfSangamValue1: Int? = null,

	@field:SerializedName("full_sangam_value_1")
	val fullSangamValue1: Int? = null,

	@field:SerializedName("double_pana_value_1")
	val doublePanaValue1: Int? = null,

	@field:SerializedName("single_pana_value_1")
	val singlePanaValue1: Int? = null,

	@field:SerializedName("single_digit_value_2")
	val singleDigitValue2: Int? = null,

	@field:SerializedName("single_digit_value_1")
	val singleDigitValue1: Int? = null,

	@field:SerializedName("game_type")
	val gameType: String? = null,

	@field:SerializedName("full_sangam_value_2")
	val fullSangamValue2: Int? = null,

	@field:SerializedName("jodi_digit_value_1")
	val jodiDigitValue1: Int? = null,

	@field:SerializedName("single_pana_value_2")
	val singlePanaValue2: Int? = null,

	@field:SerializedName("jodi_digit_value_2")
	val jodiDigitValue2: Int? = null,

	@field:SerializedName("triple_pana_value_2")
	val triplePanaValue2: Int? = null,

	@field:SerializedName("triple_pana_value_1")
	val triplePanaValue1: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
