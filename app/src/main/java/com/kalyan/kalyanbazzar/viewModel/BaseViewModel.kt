package com.kalyan.kalyanbazzar.viewModel

import androidx.lifecycle.ViewModel
import com.kalyan.kalyanbazzar.data.repositry.BaseRepository

abstract class BaseViewModel (
    private val repository: BaseRepository
) : ViewModel() {


}