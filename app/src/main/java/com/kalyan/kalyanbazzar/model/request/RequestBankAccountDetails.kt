package com.kalyan.kalyanbazzar.model.request

import com.google.gson.annotations.SerializedName

data class RequestBankAccountDetails(

	@field:SerializedName("ifsc_code")
	val ifscCode: String? = null,

	@field:SerializedName("account_number")
	val accountNumber: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("account_holder_name")
	val accountHolderName: String? = null,

	@field:SerializedName("bank_name")
	val bankName: String? = null,

	@field:SerializedName("bank_address")
	val bankAddress: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
