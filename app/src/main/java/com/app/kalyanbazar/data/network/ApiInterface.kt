package com.app.kalyanbazar.data.network

import com.app.kalyanbazar.data.repositry.BaseModel
import com.app.kalyanbazar.model.request.*
import com.app.kalyanbazar.model.response.*
import retrofit2.http.*


interface ApiInterface {

    @POST("user/registration/")
    suspend fun RequestRegister(@Body model: RequestRegister): BaseModel<ArrayList<ResponseLoginItem>>
  //  suspend fun RequestRegister(@Body model: RequestRegister): BaseModel<*>


    @POST("user/login/")
    suspend fun RequestLogin(@Body model: RequestLogin): BaseModel<ArrayList<ResponseLoginItem>>


    @POST("forgot-password/")
    suspend fun ForgotPassword(@Body model:RequestForgotPassword): BaseModel<*>


   // suspend fun ForgotPassword(@Query("new_password") newPassword:String): BaseModel<*>

    @GET("user/profile/")
    suspend fun RequestProfile(): BaseModel<ResponseUserProfile>

    @GET("get-information")
    suspend fun getInformation(): BaseModel<ArrayList<ResponseGetInformation>>

    @GET("get-contact-us")
    suspend fun getContactUs(): BaseModel<ResponseContactUs>


    @GET("merchant")
    suspend fun getMerchant(): BaseModel<ResponseMerchantCode>


    @GET("get-url")
    suspend fun getHowToHelp(): BaseModel<ArrayList<ResponseHowtoHelp>>


    @GET("dashboard/list/")
    suspend fun RequestDashBoardList(@Query("market_type")marketType:String): BaseModel<ArrayList<ResponseDashBoardListItem>>

    @GET("dashboard/list/")
    suspend fun RequestDashBoardStarlineList(@Query("market_type")marketType:String): BaseModel<ArrayList<ResponseStarline>>


    @GET("dashboard/get-inner-market?")
    suspend fun getInDashboard(@Query("market_id") marketId: Int?): BaseModel<ArrayList<ResponseInDashBoard>>

    @GET("dashboard/get-image-slider")
    suspend fun getImageSlider(): BaseModel<ArrayList<ResponseImageSlider>>


    @POST("dashboard/create-bid/")
    suspend fun RequestCreateBid(@Body model: RequestCreateBid): BaseModel<*>


    @POST("friend-transfer")
    suspend fun fundTransfer(@Body model: RequestTransfer): BaseModel<*>

    @POST("create-user-upi/")
    suspend fun  CreateUserApi(@Body model: RequestCreateUserApi): BaseModel<*>

    @POST("user-bank-account-details-create/")
    suspend fun RequestUserBankAccountDetails(@Body model: RequestBankAccountDetails): BaseModel<*>

    @GET("user-bank-account-details-list/")
    suspend fun RequestUserBankAccountDetailsList(@Query("user_id") userID: Int?): BaseModel<ArrayList<ResponseBankDetailsItem>>

    @GET("get-user-upi/")
    suspend fun getUserUpi(@Query("user_id") userID: Int?): BaseModel<ArrayList<ResponseBankDetailsItem>>

    @POST("add-fund/")
    suspend fun AddFund(@Body model: RequestAddFund): BaseModel<*>

    @POST("add-withdrawl")
    suspend fun WithdrwalFund(@Body model: RequestWithdrwalFund): BaseModel<*>

    @GET("chart-pdf-get/")
    suspend fun ChartPdfGet(): BaseModel<ArrayList<ResponseChartPdfGetItem>>

    @GET("get-normal-game-rate")
    suspend fun getNormalGameRate(): BaseModel<ArrayList<ResponseGetNormalGameRate>>

    @GET("user-list?")
   suspend fun getUserList(@Query("user_id") userID: Int?): BaseModel<ResponseUserList>

    @POST("add-contact-us")
   suspend fun addContactUs(@Body model: RequestWithdrwalFund): BaseModel<*>

    @GET("get-user-fund?")
   suspend fun getUserFundList(@Query("user_id") userID: Int?): BaseModel<ArrayList<ResponseGetUserFund>>

    @GET("get-withdrawl-list?")
   suspend fun getWithdrwalList(@Query("user_id") userID: Int?,@Query("start_date") startDate: String?,@Query("end_date") endDate: String?): BaseModel<ArrayList<ResponseWithdrawalList>>

    @GET("admin-transfer-history?")
   suspend fun adminTransferHistory(@Query("start_date") startDate: String?,@Query("end_date") endDate: String?): BaseModel<ArrayList<ResponseWithdrawalList>>

    @GET("get-numbers-list?")
   suspend fun getNumberList(@Query("number_type") numberType: String?): BaseModel<ArrayList<ResponseGetNumberList>>

    @GET("get-numbers-list?")
   suspend fun getNumberListDouble(@Query("number_type") numberType: String?,@Query("call_from")callFrom:String?): BaseModel<ArrayList<String>>

    @GET("get-numbers-list?")
   //suspend fun getNumberListFull(@Query("number_type") numberType: String?,@Query("call_from")callFrom:String?): BaseModel<ResponseFullSangam>
   suspend fun getNumberListFull(@Query("number_type") numberType: String?,@Query("call_from")callFrom:String?): BaseModel<ResponseFullSangam>

    @GET("get-app-settings")
   // suspend fun getNumberList(@Body model: RequestNumberList): BaseModel<ArrayList<ResponseGetNumberList>>
  suspend fun getAppSetting(): BaseModel<ArrayList<ResponseGetAppSetting>>

    @GET("dashboard/get-bid/")
    suspend fun getBid(@Query("start_date") startDate: String?,@Query("end_date") endDate: String?,@Query("market_type") marketType: String?): BaseModel<ArrayList<ResponseGetBid>>

    @GET("dashboard/get-win/")
    suspend fun getWin(@Query("start_date") startDate: String?,@Query("end_date") endDate: String?,@Query("market_type") marketType: String?): BaseModel<ArrayList<ResponseGetBid>>

}