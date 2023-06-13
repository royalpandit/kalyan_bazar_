package com.app.kalyanbazar.data.repositry

import com.app.kalyanbazar.data.network.ApiInterface
import com.app.kalyanbazar.data.network.SafeApiCall


abstract class BaseRepository(private val api: ApiInterface) : SafeApiCall {

//    suspend fun logout() = safeApiCall {
//        api.logout()
//    }
}
