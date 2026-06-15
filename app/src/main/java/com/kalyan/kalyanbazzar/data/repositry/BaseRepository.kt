package com.kalyan.kalyanbazzar.data.repositry

import com.kalyan.kalyanbazzar.data.network.ApiInterface
import com.kalyan.kalyanbazzar.data.network.SafeApiCall


abstract class BaseRepository(private val api: ApiInterface) : SafeApiCall {

//    suspend fun logout() = safeApiCall {
//        api.logout()
//    }
}
