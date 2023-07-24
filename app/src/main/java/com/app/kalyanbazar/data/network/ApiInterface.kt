package com.app.kalyanbazar.data.network

import com.app.kalyanbazar.data.repositry.BaseModel
import com.app.kalyanbazar.model.request.*
import com.app.kalyanbazar.model.response.*
import retrofit2.http.*


interface ApiInterface {

    @POST("user/registration/")
    suspend fun RequestRegister(@Body model: RequestRegister): BaseModel<*>


    @POST("user/login/")
    suspend fun RequestLogin(@Body model: RequestLogin): BaseModel<ArrayList<ResponseLoginItem>>

    @GET("user/profile/")
    suspend fun RequestProfile(): BaseModel<ResponseUserProfile>


    @GET("dashboard/list/")
    suspend fun RequestDashBoardList(@Query("market_type")marketType:String): BaseModel<ArrayList<ResponseDashBoardListItem>>


    @GET("dashboard/get-inner-market?")
    suspend fun getInDashboard(@Query("market_id") marketId: Int?): BaseModel<ArrayList<ResponseInDashBoard>>

    @GET("dashboard/get-image-slider")
    suspend fun getImageSlider(): BaseModel<ArrayList<ResponseImageSlider>>


    @POST("dashboard/create-bid/")
    suspend fun RequestCreateBid(@Body model: RequestCreateBid): BaseModel<*>

    @POST("user-bank-account-details-create/")
    suspend fun RequestUserBankAccountDetails(@Body model: RequestBankAccountDetails): BaseModel<*>

    @POST("add-fund/")
    suspend fun AddFund(@Body model: RequestAddFund): BaseModel<*>

    @GET("chart-pdf-get/")
    suspend fun ChartPdfGet(): BaseModel<ArrayList<ResponseChartPdfGetItem>>

    @GET(" dashboard/get-bid/")
    suspend fun getBid(@Query("start_date") startDate: String?,@Query("end_date") endDate: String?): BaseModel<ArrayList<ResponseGetBid>>

}