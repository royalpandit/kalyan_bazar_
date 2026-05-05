package com.app.kalyanbazar.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityWithdrawFundBinding
import com.app.kalyanbazar.model.request.RequestWithdrwalFund
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.MyApplication.Companion.toast
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ActivityWithdrawFund :  BaseActivity<ActivityWithdrawFundBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var minWithdrwal: Int = 0
    var maxWithdrwal: Int = 0
    var withdrwalOpenTime: String = ""
    var withdrwalCloseTime: String = ""
    var withdrwalUpi: String = ""
    var phoneNumber: String = ""
    var totalAmountPoint: Int = 0
    var currenttime: String = ""
     override fun getLayoutResId(): Int =R.layout.activity_withdraw_fund

    override fun setupViews() {
        val languages = resources.getStringArray(R.array.Languages)

        dataBinding.apply {
            toolbar.tvTitle.text = "Withdrawl Point"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            currenttime=SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            getAppSetting()
            getUserList()
            getInformation()
            getContactUs()

            tvunable.setOnClickListener {
                val contactUs = Intent(this@ActivityWithdrawFund, ActivityContactUs::class.java)
                startActivity(contactUs)
            }
            bank.setOnClickListener {
                val intent = Intent(this@ActivityWithdrawFund, ActivityBankDetails::class.java)
                startActivity(intent)
                //
            }
            rlphonepay.setOnClickListener {
                val intent = Intent(this@ActivityWithdrawFund, ActivityUpi::class.java)
                startActivity(intent)
            }
            rlgpay.setOnClickListener {
                val intent = Intent(this@ActivityWithdrawFund, ActivityUpi::class.java)
                startActivity(intent)
            }
            rlpaytm.setOnClickListener {
                val intent = Intent(this@ActivityWithdrawFund, ActivityUpi::class.java)
                startActivity(intent)
            }
            rlwhatsup.setOnClickListener {
                whatsAppBtn()
            }

            if (spinner != null) {
                val adapter = ArrayAdapter(
                    this@ActivityWithdrawFund,
                    android.R.layout.simple_spinner_item, languages
                )
                spinner.adapter = adapter

                spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {

               selectPayMethod.setText(languages[position])
                        Toast.makeText(
                            this@ActivityWithdrawFund,
                            "Selecetd Item" + " " +
                                    "" + languages[position], Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
                //
            }
            tvSubmit.setOnClickListener {
                if ( currenttime==withdrwalOpenTime||currenttime<withdrwalCloseTime){
                    toast("Withdrwal Time is Open :  $withdrwalOpenTime")

                    tvSubmit.isClickable=true
                    if (inputWithdrawPoints.text.toString().isEmpty()){
                        inputWithdrawPoints.requestFocus()
                        inputWithdrawPoints.error = "Enter Amount"
                    }else if (inputWithdrawPoints.text.toString().toInt() < minWithdrwal){
                        inputWithdrawPoints.requestFocus()
                        inputWithdrawPoints.error = "Minimum Points must be greater then " + minWithdrwal.toString()
                    }else if (inputWithdrawPoints.text.toString().toInt() > maxWithdrwal){
                        inputWithdrawPoints.requestFocus()
                        inputWithdrawPoints.error = "Maximum Points must be greater then " + maxWithdrwal.toString()
                    }

                    else{
                        if(inputWithdrawPoints.text.toString().toInt() <= totalAmountPoint)    {
                            WithdrawFund(inputWithdrawPoints.text.toString());
                        }else {
                            inputWithdrawPoints.error = "Withdrwal amount is greater thean wallet amount." + totalAmountPoint.toString()
                        }


                    }
                 }else{
                    tvSubmit.isClickable=false
                    toast("Withdrwal Time is Open :  $withdrwalOpenTime")

                }


               /* if (inputWithdrawPoints.text.toString().isEmpty()){
                    inputWithdrawPoints.requestFocus()
                    inputWithdrawPoints.error = "Enter Amount"
                }else if (inputWithdrawPoints.text.toString().toInt() < minWithdrwal){
                    inputWithdrawPoints.requestFocus()
                    inputWithdrawPoints.error = "Minimum Points must be greater then " + minWithdrwal.toString()
                }else if (inputWithdrawPoints.text.toString().toInt() > maxWithdrwal){
                    inputWithdrawPoints.requestFocus()
                    inputWithdrawPoints.error = "Maximum Points must be greater then " + maxWithdrwal.toString()
                }

                    else{
                    if(inputWithdrawPoints.text.toString().toInt() <= totalAmountPoint)    {
                        WithdrawFund(inputWithdrawPoints.text.toString());
                    }else {
                        inputWithdrawPoints.error = "Withdrwal amount is greater thean wallet amount." + totalAmountPoint.toString()
                    }


                }*/






            }



        }

    }

    //WithdrwalFund

    fun WithdrawFund(
        amount: String?
    ) {


        viewModel.WithdrwalFund.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        successdialog()
                        //finish()

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
            viewModel.WithdrwalFund(
                RequestWithdrwalFund(

                    amount = amount,
                    userId = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),
                    feedback = null


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
                        val setting = it.value.data[0]
                        withdrwalUpi = setting.upiAddress.orEmpty()
                        minWithdrwal = setting.minWithdrawl?.takeIf { it.isNotBlank() }?.toIntOrNull() ?: 0
                        maxWithdrwal = setting.maxWithdrawl?.takeIf { it.isNotBlank() }?.toIntOrNull() ?: 0
                        withdrwalOpenTime = setting.withdrawlOpenTime.orEmpty()
                        withdrwalCloseTime = setting.withdrawlCloseTime.orEmpty()
                      /*  withdrwalUpi=it.value.data[0].upiAddress.toString()
                        minWithdrwal=it.value.data[0].minWithdrawl!!.toInt()
                        maxWithdrwal=it.value.data[0].maxWithdrawl!!.toInt()
                        withdrwalOpenTime=it.value.data[0].withdrawlOpenTime.toString()
                        withdrwalCloseTime=it.value.data[0].withdrawlCloseTime.toString()*/

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
    override fun setupViewsOnResume() {
     }
    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityWithdrawFund, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text=it.value.data.totalAmount.toString()
                        totalAmountPoint=it.value.data.totalAmount!!.toInt()

                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityWithdrawFund,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    fun getInformation() {
        viewModel.getInformation.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {
                        try {
                            dataBinding.textviewcontent.text=it.value.data[0].information!!.withdrawlMessage!!.message

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
            viewModel.getInformation(

            )

        }

    }

    fun getContactUs() {
        viewModel.getContactUs.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        try {
                            phoneNumber=it.value.data.phoneNumber.toString()
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
            viewModel.getContactUs(

            )

        }

    }

    fun whatsAppBtn() {
        val url = "https://api.whatsapp.com/send?phone="+"+91"+ phoneNumber +"&text=" + URLEncoder.encode("", "UTF-8")
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun successdialog() {
         val btnconfirm: TextView
        val succesfull: TextView
        val placed: TextView
        val photo: ImageView
        val dialog = Dialog(this@ActivityWithdrawFund)
        val li = this@ActivityWithdrawFund.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.dialogue_succefull, null, false)
        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        v.background = resources.getDrawable(R.drawable.roundalert)
        dialog.setContentView(v)

        btnconfirm = dialog.findViewById(R.id.playagainbtn)
        placed = dialog.findViewById(R.id.placed)
        succesfull = dialog.findViewById(R.id.succesfull)
        photo = dialog.findViewById(R.id.photo)
        placed.text=""
        btnconfirm.text="OK"
        succesfull.text="Withdraw Points Request Placed Successfully"
        btnconfirm.setOnClickListener {
            dialog.dismiss()
            finish()

        }
        dialog.show()

    }
}
