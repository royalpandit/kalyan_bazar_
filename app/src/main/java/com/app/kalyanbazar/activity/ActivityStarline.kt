package com.app.kalyanbazar.activity


import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kalyanbazar.R
import com.app.kalyanbazar.adapter.AdapterHome
import com.app.kalyanbazar.adapter.AdapterStarline
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityStarlineBinding
import com.app.kalyanbazar.model.response.ResponseDashBoardListItem
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityStarline : BaseActivity<ActivityStarlineBinding>(), AdapterStarline.onClicklistBazar {
    private val viewModel by viewModels<HomeViewModel>()
    var arr: ArrayList<ResponseDashBoardListItem> = ArrayList()
    override fun getLayoutResId(): Int = R.layout.activity_starline

    override fun setupViews() {
        dataBinding.apply {

            toolbar.tvTitle.text = "Starline"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            getDashboardList()

            starlinebidhistory.setOnClickListener {
                //ActivityBidHistory
                startActivity(
                    Intent(
                        this@ActivityStarline,
                        ActivityBidHistory::class.java
                    ).putExtra(getString(R.string.history), 200)
                )

            }
            starlinewinhistory.setOnClickListener {
                startActivity(
                    Intent(
                        this@ActivityStarline,
                        ActivityBidHistory::class.java
                    ).putExtra(getString(R.string.history), 100)
                )

            }
        }
    }

    override fun setupViewsOnResume() {
    }

    private fun getDashboardList() {

        viewModel.RequestDashBoardList.observe(this, Observer {
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
            }
        })
        viewModel.RequestDashBoardList(
            marketType = "starline"
        )
    }

    private fun setHomeUserAdapter() {

        val adapter = AdapterStarline(this@ActivityStarline, arr, this)
        dataBinding.rvstarline.setHasFixedSize(true)
        dataBinding.rvstarline.adapter = adapter
        dataBinding.rvstarline.layoutManager = LinearLayoutManager(this@ActivityStarline)

    }

    override fun onItemClickBazar(position: ResponseDashBoardListItem, rlhead: RelativeLayout) {
        if (position.marketStatus!!.equals(true)) {
            Log.e("not==>>", "not")
            // ActivityIndashboard
            startActivity(
                Intent(this@ActivityStarline, ActivityIndashboard::class.java).putExtra(
                    Constants.marketID,
                    position.id
                ).putExtra(Constants.marketType, position.marketType)
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
}