package com.kalyan.kalyanbazzar.data.network

import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val code: Int?,
        val errorBody: ResponseBody? = null,
        val errorMessage: String? = null,
        var message : String = ""
    ) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}