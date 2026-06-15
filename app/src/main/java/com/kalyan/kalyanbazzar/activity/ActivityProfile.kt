package com.kalyan.kalyanbazzar.activity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivityProfileBinding
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.Constants
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityProfile : BaseActivity<ActivityProfileBinding>() {
    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutResId(): Int =R.layout.activity_profile

    override fun setupViews() {

        dataBinding.apply {
            toolbar.tvTitle.text="Profile"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            tvname.text= MyApplication.tinyDB.getString(Constants.SharedPref.USER_NAME,"")
            tvemail.text= MyApplication.tinyDB.getString(Constants.SharedPref.EMAIL,"")
            tvmobile.text= MyApplication.tinyDB.getString(Constants.SharedPref.MOBILE,"")
            getUserList()
        }
     }

    override fun setupViewsOnResume() {
     }
    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityProfile, it is Resource.Loading)
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
                    activity = this@ActivityProfile,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }
}
