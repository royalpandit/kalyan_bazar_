package com.app.kalyanbazar.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityAddPointBinding
import com.app.kalyanbazar.model.request.RequestAddFund
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.Helper
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.MyApplication.Companion.toast
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import dev.shreyaspatil.easyupipayment.model.TransactionStatus
/*import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import dev.shreyaspatil.easyupipayment.model.TransactionStatus*/
import java.net.URLEncoder
import java.util.Locale

@AndroidEntryPoint
class ActivityAddPoint : BaseActivity<ActivityAddPointBinding>(), PaymentStatusListener {

    private val viewModel by viewModels<HomeViewModel>()
   private lateinit var easyUpiPayment: EasyUpiPayment
    var phoneNumber: String = ""
    var amonut: Int = 0
    var GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
    var PAYTM_PAY_PACKAGE_NAME = "net.one97.paytm"
    var PHONE_PAY_PACKAGE_NAME = "com.phonepe.app"
    var UPI_PAY_PACKAGE_NAME = "in.org.npci.upiapp"
    var transactionIdd = "TID" + System.currentTimeMillis()
    var transactionRefIdd = transactionIdd + "_" + System.currentTimeMillis()
    var minFund: Int = 0
    var maxFund: Int = 0
    var addUpi: String = ""
    var MerchantUpiAddress: String = ""
    override fun getLayoutResId(): Int = R.layout.activity_add_point
    internal val UPI_PAYMENT = 0
    override fun setupViews() {
        dataBinding.apply {
            getUserList()
            getAppSetting()
            getInformation()
            getContactUs()
            toolbar.tvTitle.text = "Add Point"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            rlwhatsup.setOnClickListener {
                whatsAppBtn()
            }
            tvunable.setOnClickListener {
                val contactUs = Intent(this@ActivityAddPoint, ActivityContactUs::class.java)
                startActivity(contactUs)
            }
            tvaddpoints.setOnClickListener {
                val UPI =
                    "upi://pay?pa=7289056741@paytm&pn=Ankit%20Yadav&am=1&tn=IT6327466272&tr=IT6327466272"
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse(UPI)
                val chooser = Intent.createChooser(intent, "UPI Transfer With")
                startActivityForResult(chooser, UPI_PAYMENT, null)

                //phonepe://pay?pa=7289056741@paytm&pn=Ankit%20Yadav&am=1&tn=IT6327466272&tr=IT6327466272
            }
            tvaddpoint.setOnClickListener {
                submitCoins()
            }
            amount500.setOnClickListener {
                inputCoins.setText("500")
            }
            amount1000.setOnClickListener {
                inputCoins.setText("1000")
                // submitCoins()
            }
            amount2000.setOnClickListener {
                inputCoins.setText("2000")
                // submitCoins()
            }
            amount5000.setOnClickListener {
                inputCoins.setText("5000")
                //  submitCoins()
            }
            amount10000.setOnClickListener {
                inputCoins.setText("10000")
                //  submitCoins()
            }
        }
    }

    override fun setupViewsOnResume() {
    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityAddPoint, it is Resource.Loading)
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
                    activity = this@ActivityAddPoint,
                    retry = { getUserList() })

                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    fun submitCoins() {
        Helper.hideKeyboard(this@ActivityAddPoint)
        val mString: String = dataBinding.inputCoins.text.toString()
        if (mString.length > 0) {
            amonut = mString.toInt()
        }
        if (TextUtils.isEmpty(mString)) {
            toast("Please Enter Amount")
            return
        }
        //   if (uhkf < Integer.parseInt(fifthAttemptThree.getMaxMinObject(this, fifthAttemptThree.AI))) {
        if (amonut < minFund) {
            toast("Minimum Amount must be greater then " + " " + minFund)
            return
        }
        if (amonut > maxFund) {
            toast("Maximum Amount must be less then" + " " + maxFund)
            return
        }

        Log.e(">>>>>>>>>>>Submitedtion", "$transactionRefIdd")
        //TID1689419163129
        //Code UNcomment When App Is Live
         pay()

    }

   override fun onTransactionCancelled() {
        Log.e("===>>>Submited", "Cancelled by user")
        // Payment Cancelled by User
        toast("Cancelled by user")
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        if (transactionDetails.transactionStatus == TransactionStatus.SUCCESS) {
            toast("SUCESSS")
            onTransactionSuccess(transactionDetails)
        } else if (transactionDetails.transactionStatus == TransactionStatus.FAILURE) {
            toast("FAILED")
            Log.e("===>>>Submited", "FAILED")
            onTransactionFailed()
        } else if (transactionDetails.transactionStatus == TransactionStatus.SUBMITTED) {
            toast("SUBMITED")
            Log.e("===>>>Submited", "SUBMITED")
            onTransactionSubmitted()

        } else {
            toast("SUCESSS")
            Log.e("===>>>Submited", "SUCESSS")

            onTransactionSuccess(transactionDetails)
        }

    }

    private fun onTransactionFailed() {
        // Payment Failed
        Log.e("===>>>Submited", "Failed")

        toast("Failed")
    }

    private fun onTransactionSubmitted() {
        // Payment Pending
        Log.e("===>>>Submited", "Pending | Submitted")
        toast("Pending | Submitted")
    }

    private fun onTransactionSuccess(td: TransactionDetails) {
        AddFund(
            td.amount,
            td.transactionId,
            td.transactionRefId,
            td.transactionStatus,
            td.transactionId,
            td.transactionRefId,
            td.approvalRefNo,
            td.transactionStatus
        )
        // Payment Success
        toast("Success")
        toast(
            "Success" + td.amount + td.transactionId + td.transactionStatus + td.component1()
                    + td.component2() + td.component3() + td.component3() + td.component4() + td.component5() + td.component6()
        )

        Log.e("===>>>Tag", "" + td.amount + td.transactionId + td.transactionStatus)
        Log.e(" getAmount ", "" + td.amount)
        Log.e(" getTransactionId ", "" + td.transactionId)
        Log.e(" getTransactionStatus ", "" + td.transactionStatus)
        Log.e(" getTransactionRefId ", "" + td.transactionRefId)
        Log.e(" component1 ", "" + td.component1())
        Log.e(" component2 ", "" + td.component2())
        Log.e(" component3 ", "" + td.component3())
        Log.e(" component4 ", "" + td.component4())
        Log.e(" component5 ", "" + td.component5())
        Log.e(" component6 ", "" + td.component6())
        Log.e(" getApprovalRefNo ", "" + td.approvalRefNo)
        Log.e(" getResponseCode ", "" + td.responseCode)
        //    addCoinMethod(this, td.amount, "paid with upi")
    }

    private fun pay() {
        easyUpiPayment = EasyUpiPayment(this@ActivityAddPoint) {
            paymentApp = paymentApp
         //   payeeVpa = "7289056741@paytm"
          //  payeeVpa = "HSbimopad.ym529805-02SOb0000013683@sbi"
            payeeVpa = MerchantUpiAddress
            //payeeVpa = "7289056741@paytm"
            // payeeVpa = "obie9166@ybl"
            //    payeeName = "7289056741@paytm"
            payeeName = "Kalyan"
            transactionId = transactionIdd
            transactionRefId = transactionRefIdd
            payeeMerchantCode = ""
            description = R.string.app_name.toString()
            amount = dataBinding.inputCoins.text.toString() + ".00"
        }
//SubmitedTransaction: TID1689415379839
        try {
            easyUpiPayment.setPaymentStatusListener(this)



            easyUpiPayment.startPayment()

        } catch (e: Exception) {
            e.printStackTrace()
            toast("Error: ${e.message}")
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            UPI_PAYMENT -> if (Activity.RESULT_OK == resultCode || resultCode == 11) {
                if (data != null) {
                    val trxt = data.getStringExtra("response")
                    Log.e(">>>>>>>>>UPI", "onActivityResult: $trxt")
                    val dataList = ArrayList<String>()
                    dataList.add(trxt.toString())
                    //   upiPaymentDataOperation(dataList)
                    upiPaymentDataOperations(dataList)
                } else {
                    Log.e(">>>>>>>>UPI", "onActivityResult: " + "Return data is null")
                    val dataList = ArrayList<String>()
                    dataList.add("nothing")
                    //upiPaymentDataOperation(dataList)
                    upiPaymentDataOperations(dataList)
                }
            } else {
                Log.e(
                    ">>>>>>>>UPI",
                    "onActivityResult: " + "Return data is null"
                ) //when user simply back without payment
                val dataList = ArrayList<String>()
                dataList.add("nothing")
                //   upiPaymentDataOperation(dataList)
                upiPaymentDataOperations(dataList)
            }
        }
    }


    private fun upiPaymentDataOperations(data: ArrayList<String>) {
        if (isConnectionAvailable(this@ActivityAddPoint)) {
            if (data.isEmpty()) {
                Toast.makeText(this, "Payment Cancelled by User", Toast.LENGTH_SHORT).show()
            } else {
                var str: String? = data[0]
                Log.d("UPIPAY", "upiPaymentDataOperation: " + str!!)
                var paymentCancel = ""
                if (str == null) str = "discard"
                var status = ""
                var approvalRefNo = ""
                val response =
                    str.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (i in response.indices) {
                    val equalStr = response[i].split("=".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                    if (equalStr.size >= 2) {
                        if (equalStr[0].lowercase(Locale.ROOT) == "Status".lowercase(Locale.ROOT)) {
                            status = equalStr[1].lowercase(Locale.ROOT)
                        } else if (equalStr[0].equals(
                                "ApprovalRefNo",
                                ignoreCase = true
                            ) || equalStr[0].lowercase(
                                Locale.ROOT
                            ) == "txnRef".lowercase(Locale.ROOT)
                        ) {
                            approvalRefNo = equalStr[1]
                        }
                    } else {
                        paymentCancel = "Payment cancelled by user."
                    }
                }

                if (status == "success") {
                    //Code to handle successful transaction here.
                    // apiaddmoneyviaupi(transactionId!!)
                    // apigetdepositlist()
                    Toast.makeText(
                        this@ActivityAddPoint,
                        "Transaction successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("UPI", "responseStr: $approvalRefNo")
                } else if ("Payment cancelled by user." == paymentCancel) {
                    Toast.makeText(
                        this@ActivityAddPoint,
                        "Payment cancelled by user.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ActivityAddPoint,
                        "Transaction failed.Please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                this@ActivityAddPoint,
                "Internet connection is not available. Please check and try again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        fun isConnectionAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val netInfo = connectivityManager.activeNetworkInfo
                if (netInfo != null && netInfo.isConnected
                    && netInfo.isConnectedOrConnecting
                    && netInfo.isAvailable
                ) {
                    return true
                }
            }
            return false
        }
    }





  fun AddFund(
        amount: String?,
        transactionId: String?,
        transactionRefId: String?,
        transactionStatus: TransactionStatus,
        componentId: String?,
        componentRefId: String?,
        approvalRefNo: String?,
        componentStatus: TransactionStatus
    ) {
        viewModel.AddFund.observe(this, Observer {
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
            viewModel.AddFund(
                RequestAddFund(
                    amount = amount,
                    transactionId = transactionId,
                    transactionReferralId = transactionRefId,
                    transactionStatus = transactionStatus.equals(true),
                    componentId = componentId,
                    componentRefId = componentRefId,
                    approvalNumber = approvalRefNo,
                    componentStatus = componentStatus.equals(true),
                    userId = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),
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
                        minFund = it.value.data[0].minDeposit!!.toInt()
                        maxFund = it.value.data[0].maxDeposit!!.toInt()
                        MerchantUpiAddress = it.value.data[0].upiAddress.toString()

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

    fun getInformation() {
        viewModel.getInformation.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {
                        try {
                            dataBinding.textviewcontent.text =
                                it.value.data[0].information!!.addFundMessage!!.message

                        } catch (ex: Exception) {
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

    fun whatsAppBtn() {
        val url =
            "https://api.whatsapp.com/send?phone=" + "+91" + phoneNumber + "&text=" + URLEncoder.encode(
                "",
                "UTF-8"
            )
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun getContactUs() {
        viewModel.getContactUs.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        try {
                            phoneNumber = it.value.data.phoneNumber.toString()
                        } catch (ex: Exception) {
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
}
