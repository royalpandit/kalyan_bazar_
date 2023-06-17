package com.app.kalyanbazar.data.network

import com.app.kalyanbazar.data.repositry.BaseModel
import com.app.kalyanbazar.model.request.RequestLogin
import com.app.kalyanbazar.model.request.RequestRegister
 import com.app.kalyanbazar.model.response.ResponseDashBoardListItem
import com.app.kalyanbazar.model.response.ResponseInDashBoard
import com.app.kalyanbazar.model.response.ResponseLoginItem
import com.app.kalyanbazar.model.response.ResponseUserProfile
import retrofit2.http.*


interface ApiInterface {

    @POST("user/registration/")
    suspend fun RequestRegister(@Body model: RequestRegister): BaseModel<*>


    @POST("user/login/")
    suspend fun RequestLogin(@Body model: RequestLogin): BaseModel<ArrayList<ResponseLoginItem>>

    @GET("user/profile/")
    suspend fun RequestProfile(): BaseModel<ResponseUserProfile>


    @GET("dashboard/list/")
    suspend fun RequestDashBoardList(): BaseModel<ArrayList<ResponseDashBoardListItem>>


    @GET("dashboard/get-inner-market?")
    suspend fun getInDashboard(@Query("market_id") marketId: Int?): BaseModel<ArrayList<ResponseInDashBoard>>


}