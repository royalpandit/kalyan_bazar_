package com.app.kalyanbazar.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityForgotPasswordBinding
import com.app.kalyanbazar.model.request.RequestForgotPassword
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder

@AndroidEntryPoint
class ActivityForgotPassword : BaseActivity<ActivityForgotPasswordBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var phoneNumber: String = ""
    override fun getLayoutResId(): Int =R.layout.activity_forgot_password

    override fun setupViews() {
        dataBinding.apply {
            getContactUs()
            editPwd.requestFocus()
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editPwd, InputMethodManager.SHOW_IMPLICIT)
            tvregister.setOnClickListener {
                finish()
            }
            rlwhatsup.setOnClickListener {
                whatsAppBtn()
            }
            login.setOnClickListener {
                if (editNumber.text.toString().isEmpty()) {
                    editNumber.requestFocus()
                    editNumber.error = "Enter Mobile Number"

                } else if (editPwd.text.toString().isEmpty()) {
                    editPwd.requestFocus()
                    editPwd.error = "Enter Password"
                }else if (editMpin.text.toString().isEmpty()) {
                    editMpin.requestFocus()
                    editMpin.error = "Enter Password"
                }  else {
                    forgotpassword()
                }
            }
        }
    }


    override fun setupViewsOnResume() {
     }
    fun forgotpassword() {


        viewModel.ForgotPassword.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {


                        Log.e("Token==>>>", it.value.accessToken)
                        val intent = Intent(this@ActivityForgotPassword, ActivityLogin::class.java)
                        /*  intent.putExtra("paymentmethod", "")
                          intent.putExtra("point", "")
                          intent.putExtra("note", "")*/
                        intent.putExtra("screen", "1")
                        startActivity(intent)
                        /*  val intent = Intent(this@ActivityLogin, HomeDashboardActivity::class.java)
                      startActivity(intent)*/
                        finish()

                    }
                }
                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this, retry = {

                    }
                )
                is Resource.Loading -> {

                }
            }
        })


        dataBinding.apply {
            viewModel.ForgotPassword(
                RequestForgotPassword(
                    phoneNumber=editNumber.text.toString(),
                    newPassword = editPwd.text.toString(),
                    mPin = editMpin.text.toString()
                )




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
                is Resource.Loading -> {

                }
            }
        })


        dataBinding.apply {
            viewModel.getContactUs(

            )

        }

    }
    fun whatsAppBtn() {
        val url = "https://api.whatsapp.com/send?phone="+ "+91"+ phoneNumber +"&text=" + URLEncoder.encode("", "UTF-8")
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}
