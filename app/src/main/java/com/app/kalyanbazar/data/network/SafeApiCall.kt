package com.app.kalyanbazar.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

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
}