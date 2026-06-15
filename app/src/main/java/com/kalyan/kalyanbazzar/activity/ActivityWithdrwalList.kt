package com.kalyan.kalyanbazzar.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.adapter.AdapterWithDrwalStatement
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivityWithdrwalListBinding
import com.kalyan.kalyanbazzar.model.response.ResponseWithdrawalList
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.Constants
import com.kalyan.kalyanbazzar.utils.Helper
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityWithdrwalList : BaseActivity<ActivityWithdrwalListBinding>(),
    AdapterWithDrwalStatement.onClicklistUser {
    private val viewModel by viewModels<HomeViewModel>()

    var isUserStatus = false

    var bidhistory: ArrayList<ResponseWithdrawalList> = ArrayList()


    override fun getLayoutResId(): Int =R.layout.activity_withdrwal_list

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text="Withdrawl Statement"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            fromDate.text = Helper.getCurrentDateYMD()
            toDate.text = Helper.getCurrentDateYMD()
            getWithdrwalHistory(fromDate.text.toString(), toDate.text.toString())
            getUserList()

            swipeRefLyt.setOnRefreshListener {
                swipeRefLyt.isRefreshing=false
                getWithdrwalHistory(fromDate.text.toString(), toDate.text.toString())
                getUserList()
            }

            fromDate.setOnClickListener {
                Helper.DatePickerDialogBoxAll(
                    this@ActivityWithdrwalList, this@ActivityWithdrwalList,
                    dataBinding.fromDate
                )
            }
            toDate.setOnClickListener {
                Helper.DatePickerDialogBoxAll(
                    this@ActivityWithdrwalList, this@ActivityWithdrwalList,
                    dataBinding.toDate
                )
            }

            submitbidhistory.setOnClickListener {
                getWithdrwalHistory(fromDate.text.toString(), toDate.text.toString())
                //  getBidHIstory(fromDate.text.toString(), toDate.text.toString())
            }
        }
     }

    override fun setupViewsOnResume() {
     }
    private fun getWithdrwalHistory(fromdate: String, todate: String) {
        dataBinding.swipeRefLyt.isRefreshing = true
        viewModel.getWithdrwalList.observe(this, Observer {
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
                    activity = this@ActivityWithdrwalList,
                    retry = {
                    })
                is Resource.Loading -> {}
            }
        })
        viewModel.getWithdrwalList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),
            startDate = fromdate,
            endDate = todate
        )
    }

    private fun setHomeUserAdapter() {
        val adapter = AdapterWithDrwalStatement(this, bidhistory)
        dataBinding.recyclerView.setHasFixedSize(true)
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityWithdrwalList, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text=it.value.data.totalAmount.toString()

                        isUserStatus = it.value.data.userStatus!!

                        if (isUserStatus.equals(false)){
                            MyApplication.tinyDB.clear()
                            val contactUs = Intent(this@ActivityWithdrwalList, ActivityLogin::class.java)
                            startActivity(contactUs)
                            finish()
                        }
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityWithdrwalList,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }
}
