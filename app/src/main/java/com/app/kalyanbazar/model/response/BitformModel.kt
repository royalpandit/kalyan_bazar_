package com.app.kalyanbazar.model.response

import java.io.Serializable

data class BitformModel(
    val user_Id: Int,
    val marketInsideId: Int,
    var panaDate: String,
    var session: Boolean ?= null,
    var pana: String,
    var points: String,
    val status: Boolean
) : Serializable