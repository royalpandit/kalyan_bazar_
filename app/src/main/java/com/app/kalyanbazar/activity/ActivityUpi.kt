package com.app.kalyanbazar.activity

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityUpiBinding
import com.app.kalyanbazar.model.request.RequestCreateUserApi
import com.app.kalyanbazar.model.response.ResponseBankDetailsItem
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.MyApplication.Companion.toast
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityUpi : BaseActivity<ActivityUpiBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var bankDetails: ArrayList<ResponseBankDetailsItem> = ArrayList()
    override fun getLayoutResId(): Int = R.layout.activity_upi

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text = "Withdrwal UPI"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
          //  getAppSetting()
            AddBankAccountDetails()
            getUserList()

            tvsubmit.setOnClickListener {
                if (inTxtUpi.text.toString().isEmpty()) {
                    inTxtUpi.requestFocus()
                    inTxtUpi.error = "Enter Amount"
                } else {
                    CreateUpI(inTxtUpi.text.toString())
                }
            }
        }
    }

    //CreateUserApi
    override fun setupViewsOnResume() {
    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityUpi, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text = it.value.data.totalAmount.toString()
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityUpi,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    fun CreateUpI(upiID: String) {
        viewModel.CreateUserApi.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        toast("Your UPI ID Add Is Succefull")
                        finish()

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
            viewModel.CreateUserApi(
                RequestCreateUserApi(
                    user = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),
                    upiId = upiID,
                    status = true
                )
            )
        }

    }

    fun getAppSetting() {
        viewModel.getAppSetting.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {
                        if (it.value.data[0].upiAddress.equals("")){
                            dataBinding.tvUpiID.text="UPI is not attached please add UPI Address"

                        }else{
                            dataBinding.tvUpiID.text="UPI : "+it.value.data[0].upiAddress

                        }


                        //     minWithdrwal=it.value.data[0].minWithdrawl!!.toInt()
                        //     maxWithdrwal=it.value.data[0].maxWithdrawl!!.toInt()

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
            viewModel.getAppSetting(
            )
        }

    }
    fun AddBankAccountDetails() {
        viewModel.getUserUpi.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        bankDetails  = ArrayList()
                        bankDetails=it.value.data
                        dataBinding.apply {
                             inTxtUpi.setText(bankDetails[0].upi_id)
                            if (bankDetails[0].upi_id.equals("")){
                                dataBinding.tvUpiID.text="UPI is not attached please add UPI Address"

                            }else{
                                dataBinding.tvUpiID.text="UPI : "+bankDetails[0].upi_id

                            }
                        }

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
            viewModel.getUserUpi(

                userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),


                )
        }

    }
}
