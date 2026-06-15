package com.kalyan.kalyanbazzar.model.request

 import com.google.gson.annotations.SerializedName

 data class RequestAddFund(

	@field:SerializedName("transaction_id")
	val transactionId: String? = null,

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("transaction_status")
	val transactionStatus: Boolean? = null,

	@field:SerializedName("component_id")
	val componentId: String? = null,

	@field:SerializedName("component_ref_id")
	val componentRefId: String? = null,

	@field:SerializedName("component_status")
	val componentStatus: Boolean? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("approval_number")
	val approvalNumber: String? = null,

	@field:SerializedName("transaction_referral_id")
	val transactionReferralId: String? = null
)