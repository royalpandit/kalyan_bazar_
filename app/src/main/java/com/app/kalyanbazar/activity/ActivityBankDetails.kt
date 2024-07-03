package com.app.kalyanbazar.activity

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityBankDetailsBinding
import com.app.kalyanbazar.model.request.RequestBankAccountDetails
import com.app.kalyanbazar.model.response.ResponseBankDetailsItem
import com.app.kalyanbazar.model.response.ResponseWithdrawalList
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.MyApplication.Companion.toast
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityBankDetails : BaseActivity<ActivityBankDetailsBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var bankDetails: ArrayList<ResponseBankDetailsItem> = ArrayList()
    var totalAmountPoint: Int = 0
    override fun getLayoutResId(): Int = R.layout.activity_bank_details

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text = "Bank Details"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            Log.e("token","TOken==>>>"+MyApplication.tinyDB.getString(Constants.SharedPref.ACCESS_TOKEN, ""))
            getUserList()
            AddBankAccountDetails()
            banksubmit.setOnClickListener {
                if (name.text.toString().isEmpty()) {
                    name.requestFocus()
                    name.error = "Enter Name"

                } else if (acNumber.text.toString().isEmpty()) {
                    acNumber.requestFocus()
                    acNumber.error = "Enter Account Number"
                } else if (ifscCode.text.toString().isEmpty()) {
                    ifscCode.requestFocus()
                    ifscCode.error = "Enter Ifsc Code"
                } else if (bankName.text.toString().isEmpty()) {
                    bankName.requestFocus()
                    bankName.error = "Enter Bank Name"
                } else if (bankAddress.text.toString().isEmpty()) {
                    bankAddress.requestFocus()
                    bankAddress.error = "Enter Bank Address"
                } else {
                    AddBankAccount()
                }
            }
        }
    }

    override fun setupViewsOnResume() {

    }

    fun AddBankAccount() {
        viewModel.RequestUserBankAccountDetails.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {
                        toast("Bank Account Detail Succefully Saved")
                        finish()

                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this, retry = {
                    }
                )
            }
        })


        dataBinding.apply {
            viewModel.RequestUserBankAccountDetails(
                RequestBankAccountDetails(
                    accountHolderName = name.text.toString(),
                    accountNumber = acNumber.text.toString(),
                    // userId = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),
                    userId = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),
                    ifscCode = ifscCode.text.toString(),
                    bankAddress = bankAddress.text.toString(),
                    bankName = bankName.text.toString(),
                    status = true
                )
            )
        }

    }
    fun AddBankAccountDetails() {
        viewModel.RequestUserBankAccountDetailsList.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        bankDetails  = ArrayList()
                        bankDetails=it.value.data
                        dataBinding.apply {
                             name.setText(bankDetails[0].accountHolderName)
                            acNumber.setText(bankDetails[0].accountNumber)
                            ifscCode.setText(bankDetails[0].ifscCode)
                            bankName.setText(bankDetails[0].bankName)
                            bankAddress.setText(bankDetails[0].bankAddress)
                         }

                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this, retry = {
                    }
                )
            }
        })


        dataBinding.apply {
            viewModel.RequestUserBankAccountDetailsList(

                userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),


            )
        }

    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityBankDetails, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text = it.value.data.totalAmount.toString()
                        totalAmountPoint = it.value.data.totalAmount!!.toInt()
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityBankDetails,
                    retry = { getUserList() })
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }
}