package com.app.kalyanbazar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseBankDetails(

	@field:SerializedName("ResponseBankDetails")
	val responseBankDetails: ArrayList<ResponseBankDetailsItem> = ArrayList()
)

data class ResponseBankDetailsItem(

	@field:SerializedName("ifsc_code")
	val ifscCode: String? = null,

	@field:SerializedName("account_number")
	val accountNumber: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id_id")
	val userIdId: Int? = null,

	@field:SerializedName("upi_id")
	val upi_id: String? = null,

	@field:SerializedName("account_holder_name")
	val accountHolderName: String? = null,

	@field:SerializedName("bank_name")
	val bankName: String? = null,

	@field:SerializedName("bank_address")
	val bankAddress: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
