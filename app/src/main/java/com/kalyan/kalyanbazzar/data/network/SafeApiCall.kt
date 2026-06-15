package com.kalyan.kalyanbazzar.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
interface SafeApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Log.d("SafeApiCall", throwable.toString())

                when (throwable) {
                    is HttpException -> {
                        val code = throwable.code()
                        val errorBody = throwable.response()?.errorBody()

                        val errorMessage = try {
                            errorBody?.string()
                        } catch (e: Exception) {
                            null
                        }

                        Log.e("ERROR_BODY_STRING", errorMessage ?: "null")

                        Resource.Failure(
                            isNetworkError = false,
                            code = code,
                            errorBody = null, // ❗ can't reuse after .string() is read
                            errorMessage = errorMessage
                        )
                    }

                    else -> {
                        Resource.Failure(
                            isNetworkError = true,
                            code = null,
                            errorBody = null,
                            errorMessage = throwable.localizedMessage
                        )
                    }
                }
            }
        }
    }
}


/*
interface SafeApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            }
            catch (throwable: Throwable) {
                Log.d("re==", throwable.toString())
                when (throwable) {
                    is HttpException -> {
                        if(throwable.code() ==400) {

                            Resource.Failure(false, throwable.code(), throwable.response()?.errorBody(),
                                throwable.response()?.errorBody()?.string().toString())
                        }else if(throwable.code() ==401) {
                            Log.e("re==", throwable.toString())
                            Resource.Failure(false, throwable.code(), throwable.response()?.errorBody(),
                                throwable.response()?.errorBody()?.string().toString())
                        }else if(throwable.code() ==500) {
                            Log.e("re==", throwable.toString())
                            Resource.Failure(false, throwable.code(), throwable.response()?.errorBody(),
                                throwable.response()?.errorBody()?.string().toString())
                        }
                        else
                            Log.e("re==failure", throwable.toString())
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}*/
