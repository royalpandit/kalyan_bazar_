package com.app.kalyanbazar.activity

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kalyanbazar.R
import com.app.kalyanbazar.adapter.AdapterBidHistory
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityBidHistoryBinding
import com.app.kalyanbazar.model.response.ResponseGetBid
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.Helper
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityBidHistory : BaseActivity<ActivityBidHistoryBinding>(),
    AdapterBidHistory.onClicklistUser {
    private val viewModel by viewModels<HomeViewModel>()
    var bidhistory: ArrayList<ResponseGetBid> = ArrayList()
    var strHistory: Int = 0
    var markeTYpe: String = ""
    override fun getLayoutResId(): Int = R.layout.activity_bid_history

    override fun setupViews() {
        strHistory = intent.getIntExtra(getString(R.string.history), 0)
        markeTYpe = intent.getStringExtra("marketType").toString()
Log.e("markeTYpe","markeTYpe==>>"+markeTYpe)
Log.e("strHistory","strHistory==>>"+strHistory)
        dataBinding.apply {

            //  toolbar.tvTitle.text="Bid History"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }

            fromDate.text = Helper.getCurrentDateYMD()
            toDate.text = Helper.getCurrentDateYMD()

            if (strHistory == 100) {
                markeTYpe=""
                //Log.e("1","norm")
                toolbar.tvTitle.text = "Win History"
              //  fromDate.isEnabled = false
             //   toDate.isEnabled = false
                getWinHIstory(fromDate.text.toString(), toDate.text.toString(),markeTYpe)
            } else if (strHistory == 200) {
                markeTYpe=""
               // Log.e("1","norms")
                toolbar.tvTitle.text = "Bid History"
                getBidHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
            }else if (strHistory == 300) {
                markeTYpe="STARLINE"
               // Log.e("1","normstar")
                toolbar.tvTitle.text = "Bid History"
                getBidHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
            }else if (strHistory == 400) {
                markeTYpe="STARLINE"
              //  Log.e("1","normstars")
                toolbar.tvTitle.text = "Win History"
              //  fromDate.isEnabled = false
             //   toDate.isEnabled = false
                getWinHIstory(fromDate.text.toString(), toDate.text.toString(),markeTYpe)
            } else {
                markeTYpe=""
              //  Log.e("1","normstarss")
                toolbar.tvTitle.text = "Win History"
             //   fromDate.isEnabled = false
             //   toDate.isEnabled = false
                getWinHIstory(fromDate.text.toString(), toDate.text.toString(),markeTYpe)
            }

          //  getBidHIstory(fromDate.text.toString(), toDate.text.toString())
            getUserList()
            // getNumberList()


            swipeRefLyt.setOnRefreshListener {
                swipeRefLyt.isRefreshing = false
                if (strHistory == 100) {
                    toolbar.tvTitle.text = "Win History"
                  //  fromDate.isEnabled = false
                  //  toDate.isEnabled = false
                    getWinHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
                    getUserList()
                } else if (strHistory == 200) {
                    toolbar.tvTitle.text = "Bid History"
                    getBidHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
                    getUserList()
                } else if (strHistory == 300) {
                    markeTYpe="STARLINE"
                    toolbar.tvTitle.text = "Bid History"
                    getBidHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
                }else if (strHistory == 400) {
                    markeTYpe="STARLINE"
                    toolbar.tvTitle.text = "Win History"
                   // fromDate.isEnabled = false
                   // toDate.isEnabled = false
                    getWinHIstory(fromDate.text.toString(), toDate.text.toString(),markeTYpe)
                } else {
                    toolbar.tvTitle.text = "Win History"
                   // fromDate.isEnabled = false
                   // toDate.isEnabled = false
                    getWinHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
                    getUserList()
                }
           //     getBidHIstory(fromDate.text.toString(), toDate.text.toString())
           //     getUserList()
            }

            submitbidhistory.setOnClickListener {
                if (strHistory == 100) {
                    toolbar.tvTitle.text = "Win History"
                 //   fromDate.isEnabled = false
                 //   toDate.isEnabled = false
                    getWinHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
                } else if (strHistory == 200) {
                    toolbar.tvTitle.text = "Bid History"
                    getBidHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
                } else if (strHistory == 300) {
                    markeTYpe="STARLINE"
                    toolbar.tvTitle.text = "Bid History"
                    getBidHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
                }else if (strHistory == 400) {
                    markeTYpe="STARLINE"
                    toolbar.tvTitle.text = "Win History"
                 //   fromDate.isEnabled = false
                //    toDate.isEnabled = false
                    getWinHIstory(fromDate.text.toString(), toDate.text.toString(),markeTYpe)
                }else {
                    toolbar.tvTitle.text = "Win History"
                 //   fromDate.isEnabled = false
                 //   toDate.isEnabled = false
                    getWinHIstory(fromDate.text.toString(), toDate.text.toString(), markeTYpe)
                }
              //  getBidHIstory(fromDate.text.toString(), toDate.text.toString())
            }


            fromDate.setOnClickListener {
                Helper.DatePickerDialogBoxAll(
                    this@ActivityBidHistory, this@ActivityBidHistory,
                    dataBinding.fromDate
                )
            }
            toDate.setOnClickListener {
                Helper.DatePickerDialogBoxAll(
                    this@ActivityBidHistory, this@ActivityBidHistory,
                    dataBinding.toDate
                )
            }

        }
    }
fun datecalanderOpen(){
    dataBinding.apply {

    }

}
    override fun setupViewsOnResume() {

    }

    private fun getBidHIstory(from: String, toDate: String, markeTYpe: String) {
        dataBinding.swipeRefLyt.isRefreshing = true
        viewModel.getBid.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        dataBinding.swipeRefLyt.isRefreshing = false
                        //bidhistory.clear()
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        bidhistory = ArrayList()
                        bidhistory = it.value.data
                        dataBinding.tvNotFound.visibility=View.GONE
                        dataBinding.recyclerView.visibility=View.VISIBLE

                        setHomeUserAdapter()
                    }else{
                        dataBinding.swipeRefLyt.isRefreshing = false
                        dataBinding.tvNotFound.visibility=View.VISIBLE
                        dataBinding.recyclerView.visibility=View.GONE
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityBidHistory,
                    retry = { getBidHIstory(from, toDate, this.markeTYpe) })
            }
        })
        viewModel.getBid(
            startDate = from,
            endDate = toDate,
            marketType = markeTYpe

        )
    }
    private fun getWinHIstory(from: String, toDate: String, markeTYpe: String) {
        dataBinding.swipeRefLyt.isRefreshing = true
        viewModel.getWin.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        dataBinding.swipeRefLyt.isRefreshing = false
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        bidhistory = ArrayList()
                        bidhistory = it.value.data
                        dataBinding.tvNotFound.visibility=View.GONE
                        dataBinding.recyclerView.visibility=View.VISIBLE

                        setHomeUserAdapter()
                    }else{
                        dataBinding.swipeRefLyt.isRefreshing = false
                        dataBinding.tvNotFound.visibility=View.VISIBLE
                        dataBinding.recyclerView.visibility=View.GONE
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityBidHistory,
                    retry = { getWinHIstory(from, toDate, this.markeTYpe) })
            }
        })
        viewModel.getWin(
            startDate = from,
            endDate = toDate,
            marketType = markeTYpe
        )
    }

    private fun setHomeUserAdapter() {
        val adapter = AdapterBidHistory(this, bidhistory, this,markeTYpe)
        dataBinding.recyclerView.setHasFixedSize(true)
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onItemClickUser(position: ResponseGetBid) {

    }
    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityBidHistory, it is Resource.Loading)
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
                    activity = this@ActivityBidHistory,
                    retry = { getUserList() })
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    //getNumberList
}