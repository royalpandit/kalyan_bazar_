package com.app.kalyanbazar.data.repositry

import com.app.kalyanbazar.data.network.ApiInterface
import com.app.kalyanbazar.model.request.RequestBankAccountDetails
import com.app.kalyanbazar.model.request.RequestCreateBid
import com.app.kalyanbazar.model.request.RequestLogin
import com.app.kalyanbazar.model.request.RequestRegister
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiInterface,
) : BaseRepository(api) {


    suspend fun RequestRegister(data: RequestRegister) =
        safeApiCall { api.RequestRegister(data) }

    suspend fun RequestLogin(data: RequestLogin) =
        safeApiCall { api.RequestLogin(data) }

    suspend fun RequestProfile() =
        safeApiCall { api.RequestProfile() }

    suspend fun getImageSlider() =
        safeApiCall { api.getImageSlider() }

    suspend fun RequestDashBoardList(marketType:String) =
        safeApiCall { api.RequestDashBoardList(marketType) }

    suspend fun getInDashboard(marketId: Int?) =
        safeApiCall { api.getInDashboard(marketId) }


    suspend fun RequestCreateBid(data: RequestCreateBid) =
        safeApiCall { api.RequestCreateBid(data) }


    suspend fun RequestUserBankAccountDetails(data: RequestBankAccountDetails) =
        safeApiCall { api.RequestUserBankAccountDetails(data) }

    suspend fun ChartPdfGet() =
        safeApiCall { api.ChartPdfGet( ) }


    suspend fun getBid(startDate: String?,endDate: String?) =
        safeApiCall { api.getBid(startDate,endDate) }


}