package com.app.kalyanbazar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.data.repositry.BaseModel
import com.app.kalyanbazar.data.repositry.UserRepository
import com.app.kalyanbazar.model.request.RequestLogin
import com.app.kalyanbazar.model.request.RequestRegister
import com.app.kalyanbazar.model.response.ResponseLogin
import com.app.kalyanbazar.model.response.ResponseLoginItem
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


    private val _RequestProfile: MutableLiveData<Resource<BaseModel<ArrayList<ResponseLoginItem>>>> =
        MutableLiveData()
    val RequestProfile: LiveData<Resource<BaseModel<ArrayList<ResponseLoginItem>>>>
        get() = _RequestProfile

    fun RequestProfile() = viewModelScope.launch {
        _RequestProfile.value = Resource.Loading
        _RequestProfile.value = repository.RequestProfile()
    }



    private val _RequestDashBoardList: MutableLiveData<Resource<BaseModel<ArrayList<ResponseLoginItem>>>> =
        MutableLiveData()
    val RequestDashBoardList: LiveData<Resource<BaseModel<ArrayList<ResponseLoginItem>>>>
        get() = _RequestDashBoardList

    fun RequestDashBoardList() = viewModelScope.launch {
        _RequestDashBoardList.value = Resource.Loading
        _RequestDashBoardList.value = repository.RequestDashBoardList()
    }



}