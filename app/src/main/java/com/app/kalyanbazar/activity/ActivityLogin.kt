package com.app.kalyanbazar.activity


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityLoginBinding
import com.app.kalyanbazar.model.request.RequestLogin
import com.app.kalyanbazar.utils.*
import com.app.kalyanbazar.utils.Helper.Companion.isValidEmail
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class ActivityLogin : BaseActivity<ActivityLoginBinding>() {
    private val viewModel by viewModels<HomeViewModel>()


    override fun getLayoutResId(): Int = R.layout.activity_login




    override fun setupViews() {
        dataBinding.apply {
            tvregister.setOnClickListener {
                val intent = Intent(this@ActivityLogin, ActivityRegister::class.java)
                startActivity(intent)
            }
            editEmail.requestFocus()
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editEmail, InputMethodManager.SHOW_IMPLICIT)
            login.setOnClickListener {


                if (editEmail.text.toString().isEmpty()) {
                    editEmail.requestFocus()
                    editEmail.error = "Enter Email"

                } else if (!isValidEmail(editEmail.text.toString())) {
                    editEmail.requestFocus()
                    editEmail.error = "Please Enter Valid Email"
                } else if (editPwd.text.toString().isEmpty()) {
                    editPwd.requestFocus()
                    editPwd.error = "Enter Password"
                } else {
                    login()
                }


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

                        Log.e("Token==>>>", it.value.accessToken)
                        val intent = Intent(this@ActivityLogin, HomeDashboardActivity::class.java)
                        startActivity(intent)
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
            viewModel.RequestLogin(
                RequestLogin(
                    email = editEmail.text.toString(),
                    phoneNumber = null,
                    password = editPwd.text.toString(),
                    loginType = "email",


                    )
            )

        }


    }
}