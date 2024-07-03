package com.app.kalyanbazar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.data.repositry.BaseModel
import com.app.kalyanbazar.data.repositry.UserRepository
import com.app.kalyanbazar.model.request.*
import com.app.kalyanbazar.model.response.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val repository: UserRepository,
) : BaseViewModel(repository) {

    private val _RequestRegister: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val RequestRegister: LiveData<Resource<BaseModel<*>>>
        get() = _RequestRegister

    fun RequestRegister(data: RequestRegister) = viewModelScope.launch {
        _RequestRegister.value = Resource.Loading
        _RequestRegister.value = repository.RequestRegister(data)
    }


    private val _RequestLogin: MutableLiveData<Resource<BaseModel<ArrayList<ResponseLoginItem>>>> =
        MutableLiveData()
    val RequestLogin: LiveData<Resource<BaseModel<ArrayList<ResponseLoginItem>>>>
        get() = _RequestLogin

    fun RequestLogin(data: RequestLogin) = viewModelScope.launch {
        _RequestLogin.value = Resource.Loading
        _RequestLogin.value = repository.RequestLogin(data)
    }

    private val _ForgotPassword: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val ForgotPassword: LiveData<Resource<BaseModel<*>>>
        get() = _ForgotPassword

    fun ForgotPassword(data: RequestForgotPassword) = viewModelScope.launch {
        _ForgotPassword.value = Resource.Loading
        _ForgotPassword.value = repository.ForgotPassword(data)
    }


    private val _RequestProfile: MutableLiveData<Resource<BaseModel<ResponseUserProfile>>> =
        MutableLiveData()
    val RequestProfile: LiveData<Resource<BaseModel<ResponseUserProfile>>>
        get() = _RequestProfile

    fun RequestProfile() = viewModelScope.launch {
        _RequestProfile.value = Resource.Loading
        _RequestProfile.value = repository.RequestProfile()
    }




    private val _getInformation: MutableLiveData<Resource<BaseModel<ArrayList<ResponseGetInformation>>>> =
        MutableLiveData()
    val getInformation: LiveData<Resource<BaseModel<ArrayList<ResponseGetInformation>>>>
        get() = _getInformation

    fun getInformation() = viewModelScope.launch {
        _getInformation.value = Resource.Loading
        _getInformation.value = repository.getInformation()
    }


    private val _getContactUs: MutableLiveData<Resource<BaseModel<ResponseContactUs>>> =
        MutableLiveData()
    val getContactUs: LiveData<Resource<BaseModel<ResponseContactUs>>>
        get() = _getContactUs

    fun getContactUs() = viewModelScope.launch {
        _getContactUs.value = Resource.Loading
        _getContactUs.value = repository.getContactUs()
    }

    private val _getHowToHelp: MutableLiveData<Resource<BaseModel<ArrayList<ResponseHowtoHelp>>>> =
        MutableLiveData()
    val getHowToHelp: LiveData<Resource<BaseModel<ArrayList<ResponseHowtoHelp>>>>
        get() = _getHowToHelp

    fun getHowToHelp() = viewModelScope.launch {
        _getHowToHelp.value = Resource.Loading
        _getHowToHelp.value = repository.getHowToHelp()
    }



    private val _RequestDashBoardList: MutableLiveData<Resource<BaseModel<ArrayList<ResponseDashBoardListItem>>>> =
        MutableLiveData()
    val RequestDashBoardList: LiveData<Resource<BaseModel<ArrayList<ResponseDashBoardListItem>>>>
        get() = _RequestDashBoardList

    fun RequestDashBoardList(marketType:String) = viewModelScope.launch {
        _RequestDashBoardList.value = Resource.Loading
        _RequestDashBoardList.value = repository.RequestDashBoardList(marketType)
    }


    private val _RequestDashBoardStarlineList: MutableLiveData<Resource<BaseModel<ArrayList<ResponseStarline>>>> =
        MutableLiveData()
    val RequestDashBoardStarlineList: LiveData<Resource<BaseModel<ArrayList<ResponseStarline>>>>
        get() = _RequestDashBoardStarlineList

    fun RequestDashBoardStarlineList(marketType:String) = viewModelScope.launch {
        _RequestDashBoardStarlineList.value = Resource.Loading
        _RequestDashBoardStarlineList.value = repository.RequestDashBoardStarlineList(marketType)
    }



    private val _getInDashboard: MutableLiveData<Resource<BaseModel<ArrayList<ResponseInDashBoard>>>> =
        MutableLiveData()
    val getInDashboard: LiveData<Resource<BaseModel<ArrayList<ResponseInDashBoard>>>>
        get() = _getInDashboard

    fun getInDashboard(marketId: Int?) = viewModelScope.launch {
        _getInDashboard.value = Resource.Loading
        _getInDashboard.value = repository.getInDashboard(marketId)
    }

    private val _getNumberList: MutableLiveData<Resource<BaseModel<ArrayList<ResponseGetNumberList>>>> =
        MutableLiveData()
    val getNumberList: LiveData<Resource<BaseModel<ArrayList<ResponseGetNumberList>>>>
        get() = _getNumberList

    fun getNumberList(numberType: String?) = viewModelScope.launch {
        _getNumberList.value = Resource.Loading
        _getNumberList.value = repository.getNumberList(numberType)
    }

    private val _getNumberListDouble: MutableLiveData<Resource<BaseModel<ArrayList<String>>>> =
        MutableLiveData()
    val getNumberListDouble: LiveData<Resource<BaseModel<ArrayList<String>>>>
        get() = _getNumberListDouble

    fun getNumberListDouble(numberType: String?,callFrom: String?) = viewModelScope.launch {
        _getNumberListDouble.value = Resource.Loading
        _getNumberListDouble.value = repository.getNumberListDouble(numberType,callFrom)
    }

    private val _getNumberListFull: MutableLiveData<Resource<BaseModel<ResponseFullSangam>>> =
        MutableLiveData()
   // val getNumberListFull: LiveData<Resource<BaseModel<ArrayList<ResponseFullSangam>>>>
    val getNumberListFull: LiveData<Resource<BaseModel<ResponseFullSangam>>>
        get() = _getNumberListFull

    fun getNumberListFull(numberType: String?,callFrom: String?) = viewModelScope.launch {
        _getNumberListFull.value = Resource.Loading
        _getNumberListFull.value = repository.getNumberListFull(numberType,callFrom)
    }

    private val _getAppSetting: MutableLiveData<Resource<BaseModel<ArrayList<ResponseGetAppSetting>>>> =
        MutableLiveData()
    val getAppSetting: LiveData<Resource<BaseModel<ArrayList<ResponseGetAppSetting>>>>
        get() = _getAppSetting

    fun getAppSetting() = viewModelScope.launch {
        _getAppSetting.value = Resource.Loading
        _getAppSetting.value = repository.getAppSetting()
    }


    private val _getUserList: MutableLiveData<Resource<BaseModel <ResponseUserList>>> =
        MutableLiveData()
    val getUserList: LiveData<Resource<BaseModel <ResponseUserList>>>
        get() = _getUserList

    fun getUserList(userID: Int?) = viewModelScope.launch {
        _getUserList.value = Resource.Loading
        _getUserList.value = repository.getUserList(userID)
    }

    private val _getUserFundList: MutableLiveData<Resource<BaseModel<ArrayList<ResponseGetUserFund>>>> =
        MutableLiveData()
    val getUserFundList: LiveData<Resource<BaseModel<ArrayList<ResponseGetUserFund>>>>
        get() = _getUserFundList

    fun getUserFundList(userID: Int?) = viewModelScope.launch {
        _getUserFundList.value = Resource.Loading
        _getUserFundList.value = repository.getUserFundList(userID)
    }


    private val _getWithdrwalList: MutableLiveData<Resource<BaseModel<ArrayList<ResponseWithdrawalList>>>> =
        MutableLiveData()
    val getWithdrwalList: LiveData<Resource<BaseModel<ArrayList<ResponseWithdrawalList>>>>
        get() = _getWithdrwalList

    fun getWithdrwalList(userID: Int?,startDate: String?,endDate: String?) = viewModelScope.launch {
        _getWithdrwalList.value = Resource.Loading
        _getWithdrwalList.value = repository.getWithdrwalList(userID,startDate,endDate)
    }




    private val _adminTransferHistory: MutableLiveData<Resource<BaseModel<ArrayList<ResponseWithdrawalList>>>> =
        MutableLiveData()
    val adminTransferHistory: LiveData<Resource<BaseModel<ArrayList<ResponseWithdrawalList>>>>
        get() = _adminTransferHistory

    fun adminTransferHistory(startDate: String?,endDate: String?) = viewModelScope.launch {
        _adminTransferHistory.value = Resource.Loading
        _adminTransferHistory.value = repository.adminTransferHistory(startDate,endDate)
    }



    private val _getImageSlider: MutableLiveData<Resource<BaseModel<ArrayList<ResponseImageSlider>>>> =
        MutableLiveData()
    val getImageSlider: LiveData<Resource<BaseModel<ArrayList<ResponseImageSlider>>>>
        get() = _getImageSlider

    fun getImageSlider() = viewModelScope.launch {
        _getImageSlider.value = Resource.Loading
        _getImageSlider.value = repository.getImageSlider()
    }


    private val _RequestCreateBid: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val RequestCreateBid: LiveData<Resource<BaseModel<*>>>
        get() = _RequestCreateBid

    fun RequestCreateBid(data: RequestCreateBid) = viewModelScope.launch {
        _RequestCreateBid.value = Resource.Loading
        _RequestCreateBid.value = repository.RequestCreateBid(data)
    }


    private val _fundTransfer: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val fundTransfer: LiveData<Resource<BaseModel<*>>>
        get() = _fundTransfer

    fun fundTransfer(data: RequestTransfer) = viewModelScope.launch {
        _fundTransfer.value = Resource.Loading
        _fundTransfer.value = repository.fundTransfer(data)
    }


    private val _CreateUserApi: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val CreateUserApi: LiveData<Resource<BaseModel<*>>>
        get() = _CreateUserApi

    fun CreateUserApi(data: RequestCreateUserApi) = viewModelScope.launch {
        _CreateUserApi.value = Resource.Loading
        _CreateUserApi.value = repository.CreateUserApi(data)
    }

    private val _RequestUserBankAccountDetails: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val RequestUserBankAccountDetails: LiveData<Resource<BaseModel<*>>>
        get() = _RequestUserBankAccountDetails

    fun RequestUserBankAccountDetails(data: RequestBankAccountDetails) = viewModelScope.launch {
        _RequestUserBankAccountDetails.value = Resource.Loading
        _RequestUserBankAccountDetails.value = repository.RequestUserBankAccountDetails(data)
    }

    private val _RequestUserBankAccountDetailsList: MutableLiveData<Resource<BaseModel<ArrayList<ResponseBankDetailsItem>>>> =
        MutableLiveData()
    val RequestUserBankAccountDetailsList: LiveData<Resource<BaseModel<ArrayList<ResponseBankDetailsItem>>>>
        get() = _RequestUserBankAccountDetailsList

    fun RequestUserBankAccountDetailsList(userID: Int?) = viewModelScope.launch {
        _RequestUserBankAccountDetailsList.value = Resource.Loading
        _RequestUserBankAccountDetailsList.value = repository.RequestUserBankAccountDetailsList(userID)
    }
    private val _getUserUpi: MutableLiveData<Resource<BaseModel<ArrayList<ResponseBankDetailsItem>>>> =
        MutableLiveData()
    val getUserUpi: LiveData<Resource<BaseModel<ArrayList<ResponseBankDetailsItem>>>>
        get() = _getUserUpi

    fun getUserUpi(userID: Int?) = viewModelScope.launch {
        _getUserUpi.value = Resource.Loading
        _getUserUpi.value = repository.getUserUpi(userID)
    }

    private val _AddFund: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val AddFund: LiveData<Resource<BaseModel<*>>>
        get() = _AddFund

    fun AddFund(data: RequestAddFund) = viewModelScope.launch {
        _AddFund.value = Resource.Loading
        _AddFund.value = repository.AddFund(data)
    }

    private val _WithdrwalFund: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val WithdrwalFund: LiveData<Resource<BaseModel<*>>>
        get() = _WithdrwalFund

    fun WithdrwalFund(data: RequestWithdrwalFund) = viewModelScope.launch {
        _WithdrwalFund.value = Resource.Loading
        _WithdrwalFund.value = repository.WithdrwalFund(data)
    }

    private val _addContactUs: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val addContactUs: LiveData<Resource<BaseModel<*>>>
        get() = _addContactUs

    fun addContactUs(data: RequestWithdrwalFund) = viewModelScope.launch {
        _addContactUs.value = Resource.Loading
        _addContactUs.value = repository.addContactUs(data)
    }

    private val _ChartPdfGet: MutableLiveData<Resource<BaseModel<ArrayList<ResponseChartPdfGetItem>>>> =
        MutableLiveData()
    val ChartPdfGet: LiveData<Resource<BaseModel<ArrayList<ResponseChartPdfGetItem>>>>
        get() = _ChartPdfGet

    fun ChartPdfGet() = viewModelScope.launch {
        _ChartPdfGet.value = Resource.Loading
        _ChartPdfGet.value = repository.ChartPdfGet()
    }

    private val _getNormalGameRate: MutableLiveData<Resource<BaseModel<ArrayList<ResponseGetNormalGameRate>>>> =
        MutableLiveData()
    val getNormalGameRate: LiveData<Resource<BaseModel<ArrayList<ResponseGetNormalGameRate>>>>
        get() = _getNormalGameRate

    fun getNormalGameRate() = viewModelScope.launch {
        _getNormalGameRate.value = Resource.Loading
        _getNormalGameRate.value = repository.getNormalGameRate()
    }

    private val _getBid: MutableLiveData<Resource<BaseModel<ArrayList<ResponseGetBid>>>> =
        MutableLiveData()
    val getBid: LiveData<Resource<BaseModel<ArrayList<ResponseGetBid>>>>
        get() = _getBid

    fun getBid(startDate: String?,endDate: String?,marketType: String?) = viewModelScope.launch {
        _getBid.value = Resource.Loading
        _getBid.value = repository.getBid(startDate,endDate,marketType)
    }
    private val _getWin: MutableLiveData<Resource<BaseModel<ArrayList<ResponseGetBid>>>> =
        MutableLiveData()
    val getWin: LiveData<Resource<BaseModel<ArrayList<ResponseGetBid>>>>
        get() = _getWin

    fun getWin(startDate: String?,endDate: String?,marketType: String?) = viewModelScope.launch {
        _getWin.value = Resource.Loading
        _getWin.value = repository.getWin(startDate,endDate,marketType)
    }

}