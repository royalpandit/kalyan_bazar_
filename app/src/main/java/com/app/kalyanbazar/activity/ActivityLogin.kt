package com.app.kalyanbazar.activity


import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityLoginBinding
import com.app.kalyanbazar.model.request.RequestLogin
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityLogin : BaseActivity<ActivityLoginBinding>(){
    private val viewModel by viewModels<HomeViewModel>()


    override fun getLayoutResId(): Int =R.layout.activity_login

    override fun setupViews() {
        dataBinding.apply {
            tvregister.setOnClickListener {
                val intent = Intent(this@ActivityLogin, ActivityRegister::class.java)
                startActivity(intent)
            }
            login.setOnClickListener {
                if (editEmail.text.toString().isEmpty()) {
                    editEmail.requestFocus()
                    editEmail.error = "Enter Email"

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

                        MyApplication.tinyDB.putString(Constants.SharedPref.ACCESS_TOKEN, it.value.accessToken)

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