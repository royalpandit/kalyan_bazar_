package com.app.kalyanbazar.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityTransferBinding
import com.app.kalyanbazar.model.request.RequestTransfer
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityTransfer : BaseActivity<ActivityTransferBinding>(){
    private val viewModel by viewModels<HomeViewModel>()
    var pointValueMin: Int = 0
    var pointValueMax: Int = 0
    var WalletBalance: Int = 0
    var ErrorMessage: String = ""
    var isUserStatus = false
    override fun getLayoutResId(): Int = R.layout.activity_transfer

    override fun setupViews() {
        dataBinding.apply {
            getAppSetting()
         getUserList()
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            toolbar.tvTitle.text = "Transfer Fund"
            btnProceed.setOnClickListener {
                if (inputCoins.text.toString().isEmpty()){
                    inputCoins.requestFocus()
                    inputCoins.error = "Enter Digit"
                    ErrorMessage="Enter Digit"
                    successdialog(ErrorMessage)
                }  else if (inputCoins.text.toString().toInt() < pointValueMin) {
                    inputCoins.requestFocus()
                    inputCoins.error = "Minimum Points must be greater then " + pointValueMin.toString()
                    ErrorMessage = "Minimum Points must be greater then " + pointValueMin.toString()
                }else if (inputCoins.text.toString().toInt() > pointValueMax){
                    inputCoins.requestFocus()
                    inputCoins.error = "Maximum Points must be greater then " + pointValueMax.toString()
                    ErrorMessage="Maximum Points must be greater then " + pointValueMax.toString()
                    successdialog(ErrorMessage)
                } else if (inputCoins.text.toString().toInt() >= WalletBalance) {
                    inputCoins.error = "Transfer Amount is greater then wallet amount." + WalletBalance.toString()
                } else if (inputNumber.text.toString().isEmpty()){
                    inputNumber.requestFocus()
                    inputNumber.error = "Enter Mobile Number"
                    ErrorMessage="Enter Mobile Number"
                    successdialog(ErrorMessage)
                }else{
                    fundTransfer()
                }
            }
        }
     }

    override fun setupViewsOnResume() {
     }
    fun getAppSetting() {
        viewModel.getAppSetting.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {
                        pointValueMin=it.value.data[0].minTransfer!!.toInt()
                        pointValueMax=it.value.data[0].maxTransfer!!.toInt()
                        //   dataBinding.inTxtUpi.setText(it.value.data[0].upiAddress)
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

    fun fundTransfer() {
        viewModel.fundTransfer.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {
                        getUserList()
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
            viewModel.fundTransfer(
                RequestTransfer(
                    amount = inputCoins.text.toString(),
                    phoneNumber = inputNumber.text.toString()

                )

            )

        }

    }
    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityTransfer, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text=it.value.data.totalAmount.toString()
                        WalletBalance=it.value.data.totalAmount!!.toInt()
                      /*  if (it.value.data.transfer==true){
                            dataBinding.btnProceed.visibility=View.VISIBLE
                        }else{
                            dataBinding.btnProceed.visibility=View.GONE
                        }*/
                        isUserStatus = it.value.data.userStatus!!

                        if (isUserStatus.equals(false)){
                            MyApplication.tinyDB.clear()
                            val contactUs = Intent(this@ActivityTransfer, ActivityLogin::class.java)
                            startActivity(contactUs)
                            finish()
                        }
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityTransfer,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    private fun successdialog(ErrorMessage: String) {
        val btnconfirm: TextView
        val succesfull: TextView
        val placed: TextView

        val dialog = Dialog(this@ActivityTransfer)
        val li = this@ActivityTransfer.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.dialogue_succefull, null, false)
        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        v.background = resources.getDrawable(R.drawable.roundalert)
        dialog.setContentView(v)

        btnconfirm = dialog.findViewById(R.id.playagainbtn)
        placed = dialog.findViewById(R.id.placed)
        succesfull = dialog.findViewById(R.id.succesfull)
        placed.text=""
        btnconfirm.text="OK"
        succesfull.text=ErrorMessage
        btnconfirm.setOnClickListener {
            dialog.dismiss()
            //      finish()

        }
        dialog.show()

    }
}
