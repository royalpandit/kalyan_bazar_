package com.app.kalyanbazar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.data.repositry.BaseModel
import com.app.kalyanbazar.data.repositry.UserRepository
import com.app.kalyanbazar.model.request.RequestBankAccountDetails
import com.app.kalyanbazar.model.request.RequestCreateBid
import com.app.kalyanbazar.model.request.RequestLogin
import com.app.kalyanbazar.model.request.RequestRegister
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


    private val _RequestProfile: MutableLiveData<Resource<BaseModel<ResponseUserProfile>>> =
        MutableLiveData()
    val RequestProfile: LiveData<Resource<BaseModel<ResponseUserProfile>>>
        get() = _RequestProfile

    fun RequestProfile() = viewModelScope.launch {
        _RequestProfile.value = Resource.Loading
        _RequestProfile.value = repository.RequestProfile()
    }



    private val _RequestDashBoardList: MutableLiveData<Resource<BaseModel<ArrayList<ResponseDashBoardListItem>>>> =
        MutableLiveData()
    val RequestDashBoardList: LiveData<Resource<BaseModel<ArrayList<ResponseDashBoardListItem>>>>
        get() = _RequestDashBoardList

    fun RequestDashBoardList() = viewModelScope.launch {
        _RequestDashBoardList.value = Resource.Loading
        _RequestDashBoardList.value = repository.RequestDashBoardList()
    }



    private val _getInDashboard: MutableLiveData<Resource<BaseModel<ArrayList<ResponseInDashBoard>>>> =
        MutableLiveData()
    val getInDashboard: LiveData<Resource<BaseModel<ArrayList<ResponseInDashBoard>>>>
        get() = _getInDashboard

    fun getInDashboard(marketId: Int?) = viewModelScope.launch {
        _getInDashboard.value = Resource.Loading
        _getInDashboard.value = repository.getInDashboard(marketId)
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

    private val _RequestUserBankAccountDetails: MutableLiveData<Resource<BaseModel<*>>> =
        MutableLiveData()
    val RequestUserBankAccountDetails: LiveData<Resource<BaseModel<*>>>
        get() = _RequestUserBankAccountDetails

    fun RequestUserBankAccountDetails(data: RequestBankAccountDetails) = viewModelScope.launch {
        _RequestUserBankAccountDetails.value = Resource.Loading
        _RequestUserBankAccountDetails.value = repository.RequestUserBankAccountDetails(data)
    }

}