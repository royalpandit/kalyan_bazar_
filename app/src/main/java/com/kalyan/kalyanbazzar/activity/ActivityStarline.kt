package com.kalyan.kalyanbazzar.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.adapter.AdapterStarline
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivityStarlineBinding
import com.kalyan.kalyanbazzar.model.response.ResponseStarline
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.Constants
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityStarline : BaseActivity<ActivityStarlineBinding>(), AdapterStarline.onClicklistBazar {
    private val viewModel by viewModels<HomeViewModel>()
    var arr: ArrayList<ResponseStarline> = ArrayList()
    var showAll: String = ""
    override fun getLayoutResId(): Int = R.layout.activity_starline

    override fun setupViews() {
        showAll = intent.getStringExtra(Constants.showALl).toString()
        Log.e("showOpen", "showOpen==>>showAll:" + showAll)
        dataBinding.apply {
            toolbar.tvTitle.text = "Starline"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }

            getUserList()
            starlinebidhistory.setOnClickListener {
                //ActivityBidHistory
                startActivity(
                    Intent(
                        this@ActivityStarline,
                        ActivityBidHistory::class.java
                    ).putExtra(getString(R.string.history), 300).putExtra("marketType", "Starline")
                )
            }
            chart.setOnClickListener {
                val chartTable = Intent(this@ActivityStarline, ActivityChart::class.java).putExtra("market_name","starline")

               startActivity(chartTable)
            }
            starlinewinhistory.setOnClickListener {
                startActivity(
                    Intent(
                        this@ActivityStarline,
                        ActivityBidHistory::class.java
                    ).putExtra(getString(R.string.history), 400).putExtra("marketType", "Starline")
                )
            }
        }
    }

    override fun setupViewsOnResume() {
        getUserList()
    }

    private fun getDashboardList() {
        viewModel.RequestDashBoardStarlineList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityStarline, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        arr = ArrayList()
                        arr = it.value.data

                        setHomeUserAdapter()
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityStarline,
                    retry = { getDashboardList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.RequestDashBoardStarlineList(
            marketType = "starline"
        )
    }

    private fun setHomeUserAdapter() {
        /*

                val adapter = AdapterStarline(this@ActivityStarline, arr, this)
                dataBinding.rvstarline.setHasFixedSize(true)
                dataBinding.rvstarline.adapter = adapter
                dataBinding.rvstarline.layoutManager = LinearLayoutManager(this@ActivityStarline)
        */
        val adapter = AdapterStarline(this, arr, this)
        dataBinding.rvstarline.layoutManager = GridLayoutManager(this, 2)
        dataBinding.rvstarline.adapter = adapter

    }

    override fun onItemClickBazar(position: ResponseStarline, rlhead: RelativeLayout) {
        if (position.marketStatus!!.equals(true)) {
            Log.e("not==>>", "not")
            // ActivityIndashboard
            startActivity(
                Intent(this@ActivityStarline, ActivityIndashboard::class.java).putExtra(
                    Constants.marketID,
                    position.id
                ).putExtra(Constants.marketType, position.marketType)
                    .putExtra(Constants.showALl, "starline")
            )

        } else {
            Log.e("notvibrate==>>", "not")

            ObjectAnimator.ofFloat(
                rlhead,
                "translationX",
                0f,
                25f,
                -25f,
                25f,
                -25f,
                15f,
                -15f,
                6f,
                -6f,
                0f
            ).setDuration(700).start()
            vibrate()
        }
    }

    fun vibrate() {
        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this@ActivityStarline.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            this@ActivityStarline.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vib.vibrate(500)
        }
    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityStarline, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text = it.value.data.totalAmount.toString()

                        getDashboardList()
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityStarline,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }
}
