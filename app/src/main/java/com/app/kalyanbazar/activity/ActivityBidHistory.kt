package com.app.kalyanbazar.activity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kalyanbazar.R
import com.app.kalyanbazar.adapter.AdapterBidHistory
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityBidHistoryBinding
import com.app.kalyanbazar.model.response.ResponseGetBid
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Helper
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityBidHistory : BaseActivity<ActivityBidHistoryBinding>(),AdapterBidHistory.onClicklistUser {
    private val viewModel by viewModels<HomeViewModel>()
    var bidhistory: ArrayList<ResponseGetBid> = ArrayList()
    var strHistory: Int = 0
    override fun getLayoutResId(): Int = R.layout.activity_bid_history

    override fun setupViews() {
        strHistory = intent.getIntExtra(getString(R.string.history), 0)

        dataBinding.apply {
            if (strHistory==100){
                toolbar.tvTitle.text="Win History"
            }else if (strHistory==200){
                toolbar.tvTitle.text="Bid History"
            }else{
                toolbar.tvTitle.text="Win History"
            }
          //  toolbar.tvTitle.text="Bid History"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }

            fromDate.text=Helper.getCurrentDateYMD()
            toDate.text=Helper.getCurrentDateYMD()
            getBidHIstory(fromDate.text.toString(),toDate.text.toString())

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
swipeRefLyt.setOnRefreshListener {
    getBidHIstory(fromDate.text.toString(), toDate.text.toString())

}

            submitbidhistory.setOnClickListener {
                getBidHIstory(fromDate.text.toString(), toDate.text.toString())

            }

        }
    }


    override fun setupViewsOnResume() {
     }

  /*  fun setAdapter(){
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this@ActivityBidHistory, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val users = ArrayList<User>()

        //adding some dummy data to the list
        users.add(User("Welcome Bnous", "2023-06-01 10:20:09","Milan Morning","10:20 AM","12:20 PM","220-47-223","Teen Bazar"))
        users.add(User("New Bonus", "2023-06-03 10:20:09","Rudrakh Morning","10:20 AM","12:20 PM","120-47-253","Char Bazar"))
        users.add(User("Joining Bonus", "2023-06-08 10:20:09","Kalyan Morning","10:20 AM","12:20 PM","820-47-223","Five Bazar"))
        users.add(User("Logout Bonus Also", "2023-06-12 10:20:09","Madhur Morning","10:20 AM","12:20 PM","720-47-423","Six Bazar"))

        //creating our adapter
        val adapter = AdapterBidHistory(this,users)

        //now adding the adapter to recyclerview
        dataBinding.recyclerView.adapter = adapter

    }
*/


    private fun getBidHIstory(from: String, toDate: String) {
dataBinding.swipeRefLyt.isRefreshing=true
        viewModel.getBid.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        dataBinding.swipeRefLyt.isRefreshing=false
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        bidhistory = ArrayList()
                        bidhistory = it.value.data

                        setHomeUserAdapter()
                    }
                }
                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityBidHistory,
                    retry = { getBidHIstory(from, toDate) })
            }
        })
        viewModel.getBid(
            startDate = from,
            endDate = toDate
        )
    }

    private fun setHomeUserAdapter() {

        val adapter = AdapterBidHistory(this, bidhistory, this)
        dataBinding.recyclerView.setHasFixedSize(true)
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onItemClickUser(position: ResponseGetBid) {

    }

}