package com.kalyan.kalyanbazzar.data.repositry

import com.kalyan.kalyanbazzar.data.network.ApiInterface
import com.kalyan.kalyanbazzar.model.request.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiInterface,
) : BaseRepository(api) {


    suspend fun RequestRegister(data: RequestRegister) =
        safeApiCall { api.RequestRegister(data) }

    suspend fun RequestLogin(data: RequestLogin) =
        safeApiCall { api.RequestLogin(data) }

    suspend fun ForgotPassword(data: RequestForgotPassword) =
        safeApiCall { api.ForgotPassword(data) }

    suspend fun RequestProfile() =
        safeApiCall { api.RequestProfile() }

    suspend fun getInformation() =
        safeApiCall { api.getInformation() }

    suspend fun getContactUs() =
        safeApiCall { api.getContactUs() }

    suspend fun getMerchant() =
        safeApiCall { api.getMerchant() }

    suspend fun getHowToHelp() =
        safeApiCall { api.getHowToHelp() }

    suspend fun getImageSlider() =
        safeApiCall { api.getImageSlider() }

    suspend fun RequestDashBoardList(marketType:String) =
        safeApiCall { api.RequestDashBoardList(marketType) }

    suspend fun RequestDashBoardStarlineList(marketType:String) =
        safeApiCall { api.RequestDashBoardStarlineList(marketType) }

    suspend fun getInDashboard(marketId: Int?) =
        safeApiCall { api.getInDashboard(marketId) }


    suspend fun getNumberList(numberType: String?) =
        safeApiCall { api.getNumberList(numberType) }


    suspend fun getNumberListDouble(numberType: String?,callFrom: String?) =
        safeApiCall { api.getNumberListDouble(numberType,callFrom) }


    suspend fun getNumberListFull(numberType: String?,callFrom: String?) =
        safeApiCall { api.getNumberListFull(numberType,callFrom) }


    suspend fun getAppSetting() =
        safeApiCall { api.getAppSetting() }


    suspend fun getUserList(userID: Int?) =
        safeApiCall { api.getUserList(userID) }

    suspend fun getUserFundList(userID: Int?) =
        safeApiCall { api.getUserFundList(userID) }


    suspend fun getWithdrwalList(userID: Int?,startDate: String?,endDate: String?) =
        safeApiCall { api.getWithdrwalList(userID,startDate,endDate) }

    suspend fun adminTransferHistory(startDate: String?,endDate: String?) =
        safeApiCall { api.adminTransferHistory(startDate,endDate) }


    suspend fun RequestCreateBid(data: RequestCreateBid) =
        safeApiCall { api.RequestCreateBid(data) }

    suspend fun fundTransfer(data: RequestTransfer) =
        safeApiCall { api.fundTransfer(data) }

    suspend fun CreateUserApi(data: RequestCreateUserApi) =
        safeApiCall { api.CreateUserApi(data) }


    suspend fun RequestUserBankAccountDetails(data: RequestBankAccountDetails) =
        safeApiCall { api.RequestUserBankAccountDetails(data) }

    suspend fun RequestUserBankAccountDetailsList(userID: Int?) =
        safeApiCall { api.RequestUserBankAccountDetailsList(userID) }

    suspend fun getUserUpi(userID: Int?) =
        safeApiCall { api.getUserUpi(userID) }

    suspend fun AddFund(data: RequestAddFund) =
        safeApiCall { api.AddFund(data) }

    suspend fun WithdrwalFund(data: RequestWithdrwalFund) =
        safeApiCall { api.WithdrwalFund(data) }

    suspend fun addContactUs(data: RequestWithdrwalFund) =
        safeApiCall { api.addContactUs(data) }

    suspend fun ChartPdfGet() =
        safeApiCall { api.ChartPdfGet( ) }
    suspend fun getNormalGameRate() =
        safeApiCall { api.getNormalGameRate( ) }


    suspend fun getBid(startDate: String?,endDate: String?,marketType: String?) =
        safeApiCall { api.getBid(startDate,endDate,marketType) }

    suspend fun getWin(startDate: String?,endDate: String?,marketType: String?) =
        safeApiCall { api.getWin(startDate,endDate,marketType) }


}