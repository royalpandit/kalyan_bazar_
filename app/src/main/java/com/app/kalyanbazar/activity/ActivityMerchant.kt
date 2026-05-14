package com.app.kalyanbazar.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityMerchantBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityMerchant : BaseActivity<ActivityMerchantBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var isUserStatus = false
    override fun getLayoutResId(): Int =R.layout.activity_merchant

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text = "Add Point"
            toolbar.ivBack.setOnClickListener {
                //   onBackPressed()
                onBackPressedDispatcher.onBackPressed()
            }
            getMerchant()
            getUserList()
        }

    }


    override fun setupViewsOnResume() {
     }

    fun getMerchant() {
        viewModel.getMerchant.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        try {
                            Glide.with(this).load(it.value.data.image.toString()).into(dataBinding.ivMerchnatCode);

                            dataBinding.tvNumber.text=it.value.data.number.toString()
                        }catch (ex:Exception){

                        }
                        //   minFund=it.value.data[0].minDeposit!!.toInt()
                        //   maxFund=it.value.data[0].maxDeposit!!.toInt()

                    }
                }
                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this, retry = {

                    }
                )
                is Resource.Loading -> {}
            }
        })


        dataBinding.apply {
            viewModel.getMerchant(

            )

        }

    }
    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityMerchant, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {

                        dataBinding.toolbar.tvcois.text=it.value.data.totalAmount.toString()

                        isUserStatus = it.value.data.userStatus!!

                        if (isUserStatus.equals(false)){
                            MyApplication.tinyDB.clear()
                            val contactUs = Intent(this@ActivityMerchant, ActivityLogin::class.java)
                            startActivity(contactUs)
                            finish()
                        }
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityMerchant,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }
}
