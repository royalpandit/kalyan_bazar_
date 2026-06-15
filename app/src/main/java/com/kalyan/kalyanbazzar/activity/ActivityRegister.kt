package com.kalyan.kalyanbazzar.activity

import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivityRegisterBinding
import com.kalyan.kalyanbazzar.model.request.RequestRegister
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.Constants
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder

@AndroidEntryPoint
class ActivityRegister : BaseActivity<ActivityRegisterBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var phoneNumber: String = ""
    override fun getLayoutResId(): Int = R.layout.activity_register

    override fun setupViews() {
        dataBinding.apply {
            editFirst.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editFirst, InputMethodManager.SHOW_IMPLICIT)

            getContactUs()

            rlwhatsup.setOnClickListener {
                whatsAppBtn()
            }
            tvalreadylogin.setOnClickListener {
                val intent = Intent(this@ActivityRegister, ActivityLogin::class.java)
                startActivity(intent)
            }
            btnsignup.setOnClickListener {
                if (editFirst.text.toString().isEmpty()) {
                    editFirst.requestFocus()
                    editFirst.error = "Enter First Name"

                } /*else if (editEmail.text.toString().isEmpty()) {
                    editEmail.requestFocus()
                    editEmail.error = "Enter Email"
                } else if (!isValidEmail(editEmail.text.toString())) {
                    editEmail.requestFocus()
                    editEmail.error = "Please Enter Valid Email"
                }*/ /*else if (Helper.isMobileValid(editPhonenumber.text.toString())) {
                    editPhonenumber.requestFocus()
                    editPhonenumber.error = "Please Number Start With 9/8/7/6"
                }*/ else if (editPhonenumber.text.toString().isEmpty()) {
                    editPhonenumber.requestFocus()
                    editPhonenumber.error = "Enter Phone Number"
                } else if (editPhonenumber.text.toString().length != 10) {
                    editPhonenumber.requestFocus()
                    editPhonenumber.error = "Enter Phone Number"
                } else if (editPassword.text.toString().isEmpty()) {
                    editPassword.requestFocus()
                    editPassword.error = "Enter Password"
                } else if (editPin.text.toString().isEmpty()) {
                    editPin.requestFocus()
                    editPin.error = "Enter Last name"
                } else {
                    if (editPhonenumber.text.toString()
                            .startsWith("9") || editPhonenumber.text.toString()
                            .startsWith("8") || editPhonenumber.text.toString()
                            .startsWith("7") || editPhonenumber.text.toString().startsWith("6")
                    ) {
                        register()
                    } else {
                        editPhonenumber.requestFocus()
                        editPhonenumber.error = "Please Number Start With 9/8/7/6"
                    }

                }
            }
        }

    }

    override fun setupViewsOnResume() {
    }

    fun register() {
        viewModel.RequestRegister.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
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
                        val intent = Intent(this@ActivityRegister, HomeDashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                       /* val intent = Intent(this@ActivityRegister, SecurityPin::class.java)
                        intent.putExtra("phone", dataBinding.editPhonenumber.text.toString())
                        intent.putExtra("screen", "1")
                        startActivity(intent)
                        finish()*/
                    }
                    /*if (it.value.status) {
                        toast("Register Succefull")
                        MyApplication.tinyDB.putString(
                            Constants.SharedPref.ACCESS_TOKEN,
                            it.value.accessToken
                        )

                        Log.e("Token==>>>", it.value.accessToken)
                        val intent = Intent(this@ActivityRegister, ActivityLogin::class.java)
                        startActivity(intent)
                        finish()

                    }*/
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
            viewModel.RequestRegister(
                RequestRegister(
                    email = editEmail.text.toString(),
                    phoneNumber = editPhonenumber.text.toString(),
                    password = editPassword.text.toString(),
                    firstName = editFirst.text.toString(),
                    lastName = "",
                    userStatus = null,
                    userPin = editPin.text.toString()
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
}
