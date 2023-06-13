package com.app.kalyanbazar.data.repositry

import com.google.gson.annotations.SerializedName

class BaseModel<T>(
    @field:SerializedName("success") var success: Boolean,
    @field:SerializedName("status") var status: Boolean,
    @field:SerializedName("message") var message: String,
    @field:SerializedName("access_token") var accessToken: String,

    @field:SerializedName("path") var path: String,
   // @field:SerializedName("errors") var code: Int,
    @field:SerializedName("code") var code: Int,
    @field:SerializedName(
        "data"
    ) var data: T

)