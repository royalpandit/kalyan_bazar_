package com.kalyan.kalyanbazzar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGetAppSetting(

	@field:SerializedName("min_transfer")
	val minTransfer: String? = null,

	@field:SerializedName("min_bid_amount")
	val minBidAmount: String? = null,

	@field:SerializedName("min_deposit")
	val minDeposit: String? = null,

	@field:SerializedName("min_withdrawl")
	val minWithdrawl: String? = null,

	@field:SerializedName("max_transfer")
	val maxTransfer: String? = null,

	@field:SerializedName("max_bid_amount")
	val maxBidAmount: String? = null,

	@field:SerializedName("welcome_bonus")
	val welcomeBonus: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("withdrawl_open_time")
	val withdrawlOpenTime: String? = null,

	@field:SerializedName("withdrawl_close_time")
	val withdrawlCloseTime: String? = null,

	@field:SerializedName("upi_address")
	val upiAddress: String? = null,

	@field:SerializedName("max_deposit")
	val maxDeposit: String? = null,

	@field:SerializedName("max_withdrawl")
	val maxWithdrawl: String? = null
)
