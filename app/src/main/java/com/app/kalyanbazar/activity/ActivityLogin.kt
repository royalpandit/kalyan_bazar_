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
import com.app.kalyanbazar.databinding.ActivityLoginBinding
import com.app.kalyanbazar.model.request.RequestLogin
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.MyApplication.Companion.customTopToast
import com.app.kalyanbazar.utils.MyApplication.Companion.toast
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder

@AndroidEntryPoint
class ActivityLogin : BaseActivity<ActivityLoginBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var phoneNumber: String = ""
    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun setupViews() {
        dataBinding.apply {
            getContactUs()
            tvregister.setOnClickListener {
                val intent = Intent(this@ActivityLogin, ActivityRegister::class.java)
                startActivity(intent)
            }
            editEmail.requestFocus()
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editEmail, InputMethodManager.SHOW_IMPLICIT)
            rlwhatsup.setOnClickListener {
                whatsAppBtn()
            }
            login.setOnClickListener {
                if (editEmail.text.toString().isEmpty()) {
                    editEmail.requestFocus()
                    editEmail.error = "Enter Number"

                }else if (editEmail.text.toString().length != 10) {
                    editEmail.requestFocus()
                    editEmail.error = "Enter Number"
                } /*else if (!isValidEmail(editEmail.text.toString())) {
                    editEmail.requestFocus()
                    editEmail.error = "Please Enter Valid Email"
                } */else if (editPwd.text.toString().isEmpty()) {
                    editPwd.requestFocus()
                    editPwd.error = "Enter Password"
                } else {
                    login()
                }
            }

            tvforgotPassword.setOnClickListener {
                val intent = Intent(this@ActivityLogin, ActivityForgotPassword::class.java)
                startActivity(intent)
            }
            /*  login.setOnClickListener {
                  val intent = Intent(this@ActivityLogin, HomeDashboardActivity::class.java)
                  startActivity(intent)
              }*/
        }
    }

    override fun setupViewsOnResume() {

    }

    fun login() {
        viewModel.RequestLogin.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {
                        MyApplication.tinyDB.putString(
                            Constants.SharedPref.ACCESS_TOKEN,
                            it.value.accessToken
                        )
                        MyApplication.tinyDB.putInt(
                            Constants.SharedPref.OWNER_ID,
                            it.value.data[0].id!!
                        )
                        MyApplication.tinyDB.putString(
                            Constants.SharedPref.USERPIN,
                            it.value.data[0].userPin!!
                        )
//8285888485
                        Log.e("Token==>>>", it.value.accessToken)
                        val intent = Intent(this@ActivityLogin, SecurityPin::class.java)
                        /*  intent.putExtra("paymentmethod", "")
                          intent.putExtra("point", "")
                          intent.putExtra("note", "")*/
                        Log.e("Token==>>>", "phoneNumber==> "+phoneNumber)
                        intent.putExtra("phone", phoneNumber)
                        intent.putExtra("screen", "1")
                        startActivity(intent)
                        /*  val intent = Intent(this@ActivityLogin, HomeDashboardActivity::class.java)
                      startActivity(intent)*/
                        finish()

                    }else{
                        customTopToast(it.value.message)

                      //  toast(it.value.message)

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
            viewModel.RequestLogin(
                RequestLogin(
                    email =null ,
                    phoneNumber = editEmail.text.toString(),
                    password = editPwd.text.toString(),
                    loginType = "phone_number",
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
                            Log.e("","PhoneNUmber===>>>"+phoneNumber)
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
        val url = "https://api.whatsapp.com/send?phone="+"+91"+  phoneNumber +"&text=" + URLEncoder.encode("", "UTF-8")
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}
