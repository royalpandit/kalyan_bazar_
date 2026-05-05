package com.app.kalyanbazar.activity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityProfileBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
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
