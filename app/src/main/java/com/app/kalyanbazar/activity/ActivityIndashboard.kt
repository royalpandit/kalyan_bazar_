package com.app.kalyanbazar.activity


import android.content.Intent
import androidx.activity.viewModels
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

@AndroidEntryPoint
class ActivityIndashboard : BaseActivity<ActivityIndashboardBinding>(), AdapterInDashboard.onClicklistBazar {
    private val viewModel by viewModels<HomeViewModel>()
    var arrUser: ArrayList<ResponseInDashBoard> = ArrayList()
    var marketID: Int = 0
    override fun getLayoutResId(): Int =R.layout.activity_indashboard

    override fun setupViews() {
        marketID = intent.getIntExtra(Constants.marketID, 0)
        dataBinding.apply {
            toolbar.tvTitle.text="Game"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            getInDashboardList(marketID)
        }
     }

    override fun setupViewsOnResume() {

    }

    override fun onItemClickBazar(position: ResponseInDashBoard) {
//ActivityCreateBid
 startActivity(Intent(this@ActivityIndashboard, ActivityCreateBid::class.java)
     .putExtra(Constants.Category_Name,position.name).putExtra(Constants.MARKET_ID,position.marketIdId)
     .putExtra(Constants.CAT_ID,position.id))

    }
    private fun getInDashboardList(marketID: Int) {

        viewModel.getInDashboard.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityIndashboard, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {

                        arrUser = ArrayList()
                        arrUser = it.value.data
                        setHomeUserAdapter()
                    }else{
                        toast("BadRequest")
                    }
                }
                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this,
                    retry = { getInDashboardList(this.marketID) })
            }
        })
        viewModel.getInDashboard(
            marketId = marketID

        )
    }

    private fun setHomeUserAdapter() {
        val adapter = AdapterInDashboard(this, arrUser, this)
        dataBinding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        dataBinding.recyclerView.adapter = adapter

    }
}