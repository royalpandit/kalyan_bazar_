package com.app.kalyanbazar.data.network

import com.app.kalyanbazar.data.repositry.BaseModel
import com.app.kalyanbazar.model.request.RequestLogin
import com.app.kalyanbazar.model.request.RequestRegister
 import com.app.kalyanbazar.model.response.ResponseLoginItem
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    @POST("user/registration/")
    suspend fun RequestRegister(@Body model: RequestRegister): BaseModel<*>


    @POST("user/login/")
    suspend fun RequestLogin(@Body model: RequestLogin): BaseModel<ArrayList<ResponseLoginItem>>

    @POST("user/profile/")
    suspend fun RequestProfile(): BaseModel<ArrayList<ResponseLoginItem>>


    @POST("dashboard/list/")
    suspend fun RequestDashBoardList(): BaseModel<ArrayList<ResponseLoginItem>>


}