package com.app.kalyanbazar.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kalyanbazar.R
import com.app.kalyanbazar.adapter.AdapterTransferHistory
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityTransferHistoryListBinding
import com.app.kalyanbazar.model.response.ResponseWithdrawalList
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.Helper
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityTransferHIstoryList : BaseActivity<ActivityTransferHistoryListBinding>(),
    AdapterTransferHistory.onClicklistUser {
    private val viewModel by viewModels<HomeViewModel>()
    var isUserStatus = false
    var bidhistory: ArrayList<ResponseWithdrawalList> = ArrayList()

    override fun getLayoutResId(): Int = R.layout.activity_transfer_history_list

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text = "Transfer History"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            fromDate.text = Helper.getCurrentDateYMD()
            toDate.text = Helper.getCurrentDateYMD()
            getTaansferHistory(fromDate.text.toString(), toDate.text.toString())
            getUserList()

            swipeRefLyt.setOnRefreshListener {
                swipeRefLyt.isRefreshing = false
                getTaansferHistory(fromDate.text.toString(), toDate.text.toString())
                getUserList()
            }

            fromDate.setOnClickListener {
                Helper.DatePickerDialogBoxAll(
                    this@ActivityTransferHIstoryList, this@ActivityTransferHIstoryList,
                    dataBinding.fromDate
                )
            }
            toDate.setOnClickListener {
                Helper.DatePickerDialogBoxAll(
                    this@ActivityTransferHIstoryList, this@ActivityTransferHIstoryList,
                    dataBinding.toDate
                )
            }

            submitbidhistory.setOnClickListener {
                getTaansferHistory(fromDate.text.toString(), toDate.text.toString())
                //  getBidHIstory(fromDate.text.toString(), toDate.text.toString())
            }
        }
    }

    override fun setupViewsOnResume() {
    }

    private fun getTaansferHistory(fromdate: String, todate: String) {
        dataBinding.swipeRefLyt.isRefreshing = true
        viewModel.adminTransferHistory.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        dataBinding.swipeRefLyt.isRefreshing = false
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        bidhistory = ArrayList()
                        bidhistory = it.value.data

                        setHomeUserAdapter()
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityTransferHIstoryList,
                    retry = {
                    })
                is Resource.Loading -> {}
            }
        })
        viewModel.adminTransferHistory(
         //   userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),
            startDate = fromdate,
            endDate = todate
        )
    }

    private fun setHomeUserAdapter() {
        val adapter = AdapterTransferHistory(this, bidhistory)
        dataBinding.recyclerView.setHasFixedSize(true)
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityTransferHIstoryList, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text = it.value.data.totalAmount.toString()

                        isUserStatus = it.value.data.userStatus!!

                        if (isUserStatus.equals(false)) {
                            MyApplication.tinyDB.clear()
                            val contactUs =
                                Intent(this@ActivityTransferHIstoryList, ActivityLogin::class.java)
                            startActivity(contactUs)
                            finish()
                        }
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityTransferHIstoryList,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }
}
