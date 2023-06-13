package com.app.kalyanbazar.viewModel

import androidx.lifecycle.ViewModel
import com.app.kalyanbazar.data.repositry.BaseRepository

abstract class BaseViewModel (
    private val repository: BaseRepository
) : ViewModel() {


}