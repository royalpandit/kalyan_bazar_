package com.app.kalyanbazar.activity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityGameRateBinding
import com.app.kalyanbazar.model.response.ResponseGetNormalGameRate
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityGameRate : BaseActivity<ActivityGameRateBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var bidhistory: ArrayList<ResponseGetNormalGameRate> = ArrayList()

    override fun getLayoutResId(): Int = R.layout.activity_game_rate

    override fun setupViews() {
        dataBinding.apply {
            getNormalGameRate()
            getUserList()
            toolbar.tvTitle.text = "Game Rates"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            swipeRefLyt.setOnRefreshListener {
                getNormalGameRate()
                getUserList()
            }
        }

    }

    override fun setupViewsOnResume() {
    }
    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityGameRate, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text=it.value.data.totalAmount.toString()
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityGameRate,
                    retry = { getUserList() })
                is Resource.Loading -> {

                }
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }
    private fun getNormalGameRate() {
        dataBinding.swipeRefLyt.isRefreshing = true
        viewModel.getNormalGameRate.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        dataBinding.swipeRefLyt.isRefreshing = false

                        bidhistory = ArrayList()
                        bidhistory = it.value.data

                        dataBinding.apply {
                            singleDV.text = it.value.data[0].singleDigitValue1.toString() + " KA " + it.value.data[0].singleDigitValue2.toString()
                            jodiDValue.text =
                                it.value.data[0].jodiDigitValue1.toString() + " KA " + it.value.data[0].jodiDigitValue2.toString()
                            singlePV.text =
                                it.value.data[0].singlePanaValue1.toString() + " KA " + it.value.data[0].singlePanaValue2.toString()
                            doubleDV.text =
                                it.value.data[0].doublePanaValue1.toString() + " KA " + it.value.data[0].doublePanaValue2.toString()
                            tripleDV.text =
                                it.value.data[0].triplePanaValue1.toString() + " KA " + it.value.data[0].triplePanaValue2.toString()
                            halfSV.text =
                                it.value.data[0].halfSangamValue1.toString() + " KA " + it.value.data[0].halfSangamValue2.toString()
                            fullSV.text =
                                it.value.data[0].fullSangamValue1.toString() + " KA " + it.value.data[0].fullSangamValue2.toString()
                        }

                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityGameRate,
                    retry = { getNormalGameRate() })
                is Resource.Loading -> {

                }
            }
        })
        viewModel.getNormalGameRate()
    }
}
