package com.app.kalyanbazar.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivityAddPointBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Helper
import com.app.kalyanbazar.utils.MyApplication.Companion.toast
import dagger.hilt.android.AndroidEntryPoint
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import dev.shreyaspatil.easyupipayment.model.TransactionStatus


@AndroidEntryPoint
class ActivityAddPoint : BaseActivity<ActivityAddPointBinding>(),PaymentStatusListener {
    private lateinit var easyUpiPayment: EasyUpiPayment
    var amonut: Int = 0
    var GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
    var PAYTM_PAY_PACKAGE_NAME = "net.one97.paytm"
    var PHONE_PAY_PACKAGE_NAME = "com.phonepe.app"
    var UPI_PAY_PACKAGE_NAME = "in.org.npci.upiapp"

    var hgj = "TID" + System.currentTimeMillis()
     override fun getLayoutResId(): Int = R.layout.activity_add_point
    internal val UPI_PAYMENT = 0
    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text="Add Point"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
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


    fun submitCoins() {

        Helper.hideKeyboard(this@ActivityAddPoint)
        val mString: String = dataBinding.inputCoins.getText().toString()
        if (mString.length > 0) {
            amonut = mString.toInt()
        }
        if (TextUtils.isEmpty(mString)) {
            toast("Please Enter Amount")
            return
        }
        //   if (uhkf < Integer.parseInt(fifthAttemptThree.getMaxMinObject(this, fifthAttemptThree.AI))) {
        if (amonut < 1) {
            toast("Minimum Amount must be greater then " + " " + "500")
            return
        }
        if (amonut > 500) {
            toast("Maximum Amount must be less then" + " " + "500")
            return
        }

Log.e(">>>>>>>>>>>SubmitedTransaction","$hgj")
        //TID1689419163129
       // pay()
        payUsingUpi()
    }

    override fun onTransactionCancelled() {
        Log.e("===>>>Submited","Cancelled by user")
        // Payment Cancelled by User
        toast("Cancelled by user")
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {

if (transactionDetails.transactionStatus==TransactionStatus.SUCCESS){
    toast("SUCESSS")
    onTransactionSuccess(transactionDetails)
}else if (transactionDetails.transactionStatus==TransactionStatus.FAILURE){
    toast("FAILED")
    Log.e("===>>>Submited","FAILED")
    onTransactionFailed()
}else if (transactionDetails.transactionStatus==TransactionStatus.SUBMITTED){
    toast("SUBMITED")
    Log.e("===>>>Submited","SUBMITED")
    onTransactionSubmitted()
}else{
    toast("SUCESSS")
    Log.e("===>>>Submited","SUCESSS")

    onTransactionSuccess(transactionDetails)
}


     }
    private fun onTransactionFailed() {
        // Payment Failed
        Log.e("===>>>Submited","Failed")

        toast("Failed")
    }

    private fun onTransactionSubmitted() {
        // Payment Pending
        Log.e("===>>>Submited","Pending | Submitted")
        toast("Pending | Submitted")
    }

    override fun onDestroy() {
        super.onDestroy()
       // easyUpiPayment.removePaymentStatusListener()
    }
    private fun onTransactionSuccess(td: TransactionDetails) {
        // Payment Success
        toast("Success")
        toast("Success"+td.amount+td.transactionId+td.transactionStatus+td.component1()
                + td.component2()+ td.component3()+ td.component3()+ td.component4()+ td.component5()+ td.component6())

        Log.e("===>>>Tag",""+td.amount+td.transactionId+td.transactionStatus)
        Log.e(" getAmount ","" + td.amount)
        Log.e(" getTransactionId ",""  + td.transactionId)
        Log.e(" getTransactionStatus " ,"" + td.transactionStatus)
        Log.e(" getTransactionRefId ",""  + td.transactionRefId)
        Log.e(" component1 " ,"" + td.component1())
        Log.e(" component2 ",""  + td.component2())
        Log.e(" component3 " ,"" + td.component3())
        Log.e(" component4 ",""  + td.component4())
        Log.e(" component5 ",""  + td.component5())
        Log.e(" component6 " ,"" + td.component6())
        Log.e(" getApprovalRefNo " ,"" + td.approvalRefNo)
        Log.e(" getResponseCode " ,"" + td.responseCode)
    //    addCoinMethod(this, td.amount, "paid with upi")
    }
    private fun pay() {
      /*  val payeeVpa = "8502019579@ibl"
        val payeeName = "Riya Sharma"
        val transactionId = field_transaction_id.text.toString()
        val transactionRefId = field_transaction_ref_id.text.toString()
        val payeeMerchantCode = field_payee_merchant_code.text.toString()
        val description = field_description.text.toString()
        val amount = field_amount.text.toString()
        val paymentAppChoice = radioAppChoice*/

       /* val paymentApp = when (paymentAppChoice.checkedRadioButtonId) {
            R.id.app_default -> PaymentApp.ALL
            R.id.app_amazonpay -> PaymentApp.AMAZON_PAY
            R.id.app_bhim_upi -> PaymentApp.BHIM_UPI
            R.id.app_google_pay -> PaymentApp.GOOGLE_PAY
            R.id.app_phonepe -> PaymentApp.PHONE_PE
            R.id.app_paytm -> PaymentApp.PAYTM
            else -> throw IllegalStateException("Unexpected value: " + paymentAppChoice.id)
        }*/
//SubmitedTransaction: TID1689415379839
        try {
            // START PAYMENT INITIALIZATION
            easyUpiPayment = EasyUpiPayment(this@ActivityAddPoint) {
                this.paymentApp = paymentApp
           //   this.payeeVpa = "obie9166@ybl"
             // this.payeeVpa = "8107116566@paytm"
              this.payeeVpa = "7777777777@paytm"
             // this.payeeVpa = "8502019579@ibl"
             //   this.payeeVpa = "7289056741@paytm"
                //this.payeeVpa = payeeVpa
                this.payeeName = "Abhishek Punia"
                this.transactionId = hgj
                this.transactionRefId = hgj
                 this.payeeMerchantCode = "12345"
                this.description = R.string.app_name.toString()
                this.amount = dataBinding.inputCoins.getText().toString() + ".0"
            }
            // END INITIALIZATION

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(this)


            // Start payment / transaction
            easyUpiPayment.startPayment()

        } catch (e: Exception) {
            e.printStackTrace()
            toast("Error: ${e.message}")
        }
    }
    fun payUsingUpi() {

        val uri = Uri.parse("upi://pay").buildUpon()
      //  val uri = Uri.parse("net.one97.paytm").buildUpon()
            .appendQueryParameter("pa", "obie9166@ybl")
            .appendQueryParameter("pn", "Abhishek")
            .appendQueryParameter("tn", R.string.app_name.toString())
            .appendQueryParameter("am", dataBinding.inputCoins.getText().toString() + ".0")
            .appendQueryParameter("cu", "INR")
            .appendQueryParameter("transactionId", hgj)
            .appendQueryParameter("transactionRefId", hgj)
            .build()

//TID1689422369810
        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri

        // will always show a dialog to user to choose an app
        val chooser = Intent.createChooser(upiPayIntent, "Pay with")

        // check if intent resolves
         if (null != chooser.resolveActivity(packageManager)) {
            startActivityForResult(chooser, UPI_PAYMENT)
        } else {
            Toast.makeText(this@ActivityAddPoint, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show()
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
                    upiPaymentDataOperation(dataList)
                } else {
                    Log.e(">>>>>>>>UPI", "onActivityResult: " + "Return data is null")
                    val dataList = ArrayList<String>()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            } else {
                Log.e(">>>>>>>>UPI", "onActivityResult: " + "Return data is null") //when user simply back without payment
                val dataList = ArrayList<String>()
                dataList.add("nothing")
                upiPaymentDataOperation(dataList)
            }
        }
    }
    private fun upiPaymentDataOperation(data: ArrayList<String>) {
        if (isConnectionAvailable(this@ActivityAddPoint)) {
            var str: String? = data[0]
            Log.e(">>>>>>>>UPIPAY", "upiPaymentDataOperation: " + str!!)
            var paymentCancel = ""
            if (str == null) str = "discard"
            var status = ""
            var approvalRefNo = ""
            var logsd = ""
            val response = str.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in response.indices) {
                val equalStr = response[i].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                Log.e(">>>>>>>>equalStr==>>>","$equalStr")
                if (equalStr.size >= 2) {
                    if (equalStr[0].toLowerCase() == "Status".toLowerCase()) {
                        status = equalStr[1].toLowerCase()
                    } else if (equalStr[0].toLowerCase() == "ApprovalRefNo".toLowerCase() || equalStr[0].toLowerCase() == "txnRef".toLowerCase()) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }

            if (status == "success") {
                //Code to handle successful transaction here.
                Toast.makeText(this@ActivityAddPoint, "Transaction successful.", Toast.LENGTH_SHORT).show()
                Log.e(">>>>>>>>UPI", "responseStr: $approvalRefNo")
            } else if ("Payment cancelled by user." == paymentCancel) {
                Toast.makeText(this@ActivityAddPoint, "Payment cancelled by user.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@ActivityAddPoint, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@ActivityAddPoint, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {

        fun isConnectionAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val netInfo = connectivityManager.activeNetworkInfo
                if (netInfo != null && netInfo.isConnected
                    && netInfo.isConnectedOrConnecting
                    && netInfo.isAvailable) {
                    return true
                }
            }
            return false
        }
    }
}