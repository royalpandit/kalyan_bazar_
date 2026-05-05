package com.app.kalyanbazar.activity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityMerchantBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityMerchant : BaseActivity<ActivityMerchantBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    override fun getLayoutResId(): Int =R.layout.activity_merchant

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text = "Withdrawl Point"
            toolbar.ivBack.setOnClickListener {
                //   onBackPressed()
                onBackPressedDispatcher.onBackPressed()
            }
            getMerchant()
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
}
