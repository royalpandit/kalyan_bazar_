package com.app.kalyanbazar.activity

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.app.kalyanbazar.R
import com.app.kalyanbazar.adapter.AdapterInDashboard
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityIndashboardBinding
import com.app.kalyanbazar.model.response.ResponseInDashBoard
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.MyApplication.Companion.toast
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ActivityIndashboard : BaseActivity<ActivityIndashboardBinding>(),
    AdapterInDashboard.onClicklistBazar {
    private val viewModel by viewModels<HomeViewModel>()
    var arrUser: ArrayList<ResponseInDashBoard> = ArrayList()
    var arrUserDataToshow: ArrayList<ResponseInDashBoard> = ArrayList()
    var marketID: Int = 0
    var showAll: String = "yes"
    var showOpen: String = ""
    var currenttime: String = ""
    var marketType: String = ""
    var openTime: String = ""
    var closeTime: String = ""
    var endtime: String = ""
    var inBetween: Boolean = true
    override fun getLayoutResId(): Int = R.layout.activity_indashboard

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setupViews() {
        marketID = intent.getIntExtra(Constants.marketID, 0)
        showAll = intent.getStringExtra(Constants.showALl!!).toString()
        Log.e("showOpen", "showOpen==>>showAll:" + showAll)


        Log.e("Show", "Show==>>>" + showAll)

        marketType = intent.getStringExtra(Constants.marketType!!).toString()
        openTime = intent.getStringExtra(Constants.openTime).toString()
        closeTime = intent.getStringExtra(Constants.closeTime).toString()
        Log.e("OPEN_TIME", "OPEN_TIMEINDashBoard"+openTime)
        Log.e("CLOSE_TIME", "CLOSE_TIMEDashBoard"+closeTime)

        Log.e("Show", "marketType==>>>" + marketType)
        dataBinding.apply {
            toolbar.tvTitle.text = "Game"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            currenttime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            //Log.e("Currenttime","time--->>>"+currenttime)
            //checkTime("08:00:00","17:00:00",currenttime)
            //Log.e("inBetween","inBetween--->>>"+inBetween)
            /* if(showAll.equals("yes")){
                 inBetween = true
                 showOpen="Yes"
             }else {
                 inBetween = false
                 showOpen="No"
             }*/

            if (showAll.equals("yes")) {
                inBetween = true
                showOpen = "Yes"
            } else if (showAll.equals("no")) {
                inBetween = false
                showOpen = "No"
            } else {
                inBetween = false
                showOpen = "starline"
            }

            getInDashboardList(marketID)
            //  getUserList()
        }
    }

    override fun setupViewsOnResume() {
        getUserList()
        Log.e("Onresume====", "OnResume====>>")
    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityIndashboard, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text = it.value.data.totalAmount.toString()
                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this@ActivityIndashboard,
                    retry = { getUserList() })

                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    override fun onItemClickBazar(position: ResponseInDashBoard) {
//ActivityCreateBid
        startActivity(
            Intent(this@ActivityIndashboard, ActivityCreateBid::class.java)
                .putExtra(Constants.Category_Name, position.name)
                .putExtra(Constants.MARKET_ID, position.marketIdId)
                .putExtra(Constants.SHOW_OPEN, showOpen)
                .putExtra(Constants.CAT_ID, position.id)
                .putExtra(Constants.marketType, marketType)
                .putExtra(Constants.openTime, openTime)
                .putExtra(Constants.closeTime, closeTime)
        )

    }

    private fun getInDashboardList(marketID: Int) {
        viewModel.getInDashboard.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityIndashboard, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  getUserList()
                        arrUser = ArrayList()
                        arrUser = it.value.data

                        if (!inBetween) {
                            arrUserDataToshow = arrUser
                        } else {
                            for (a in arrUser) {
                                if (a.name.equals("FULL SANGAM") || a.name.equals("JODI DIGIT") || a.name.equals(
                                        "HALF SANGAM"
                                    )
                                ) {
                                } else {
                                    arrUserDataToshow.add(a)
                                }
                            }
                        }

                        setHomeUserAdapter()
                    } else {
                        toast("BadRequest")
                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this,
                    retry = { getInDashboardList(this.marketID) })

                is Resource.Loading -> {}
            }
        })
        viewModel.getInDashboard(
            marketId = marketID
        )
    }

    private fun setHomeUserAdapter() {
        val adapter = AdapterInDashboard(this, arrUserDataToshow, this)
        dataBinding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        dataBinding.recyclerView.adapter = adapter

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkTime(startTime: String, endTime: String, checkTime: String) {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US)
        val startLocalTime: LocalTime = LocalTime.parse(startTime, formatter)
        val endLocalTime: LocalTime = LocalTime.parse(endTime, formatter)
        val checkLocalTime: LocalTime = LocalTime.parse(checkTime, formatter)
        var isInBetween = false
        if (endLocalTime.isAfter(startLocalTime)) {
            if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
                isInBetween = true
            }
        } else if (checkLocalTime.isAfter(startLocalTime) || checkLocalTime.isBefore(endLocalTime)) {
            isInBetween = true
        }
        if (isInBetween) {
            println("Is in between!")
            inBetween = true
        } else {
            println("Is not in between!")
            inBetween = false
        }
    }
}
