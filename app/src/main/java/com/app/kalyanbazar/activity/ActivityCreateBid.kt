package com.app.kalyanbazar.activity

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityCreateBidBinding
import com.app.kalyanbazar.model.request.RequestCreateBid
import com.app.kalyanbazar.utils.*
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityCreateBid : BaseActivity<ActivityCreateBidBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var marketId: Int = 0
    var catId: Int = 0
    var marketName: String = ""
    var userId: Int? = null

    override fun getLayoutResId(): Int = R.layout.activity_create_bid

    override fun setupViews() {
        userId = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        marketId = intent.getIntExtra(Constants.MARKET_ID, 0)
        catId = intent.getIntExtra(Constants.CAT_ID, 0)
        marketName = intent.getStringExtra(Constants.Category_Name).toString()

        dataBinding.apply {

            toolbar.tvTitle.text = marketName
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            chooseDate.text = Helper.getCurrentDateYMD()

            btnProceed.setOnClickListener {
                createBid(marketId)
            }
        }
    }

    override fun setupViewsOnResume() {
    }

    //RequestCreateBid

    fun createBid(marketId: Int) {


        viewModel.RequestCreateBid.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {

                        finish()

                    }
                }
                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this, retry = {

                    }
                )
            }
        })


        dataBinding.apply {
            viewModel.RequestCreateBid(
                RequestCreateBid(
                    pana = inputD.text.toString(),
                    marketInsideId = marketId,
                    // userId = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),
                    userId = userId,
                    panaDate = chooseDate.text.toString(),
                    session = false,
                    points = inputCoins.text.toString().toInt(),
                    status = true


                )
            )

        }


    }
}