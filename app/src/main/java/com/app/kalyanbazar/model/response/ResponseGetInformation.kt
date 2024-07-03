package com.app.kalyanbazar.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGetInformation(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("is_shown")
	val isShown: Boolean? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("information")
	val information: Information? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class WithdrawlMessage(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class AppMaintanence(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class AddFundMessage(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Information(

	@field:SerializedName("add_app_link")
	val addAppLink: AddAppLink? = null,

	@field:SerializedName("add_fund_message")
	val addFundMessage: AddFundMessage? = null,

	@field:SerializedName("withdrawl_message")
	val withdrawlMessage: WithdrawlMessage? = null,

	@field:SerializedName("pop_up_message")
	val popUpMessage: PopUpMessage? = null,

	@field:SerializedName("app_maintanence")
	val appMaintanence: AppMaintanence? = null
)

data class PopUpMessage(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class AddAppLink(

	@field:SerializedName("app_link")
	val appLink: String? = null,

	@field:SerializedName("share_message")
	val shareMessage: String? = null
)
