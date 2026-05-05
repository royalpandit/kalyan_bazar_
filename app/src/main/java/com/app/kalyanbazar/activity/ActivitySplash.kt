package com.app.kalyanbazar.activity

 import android.content.Context
 import android.content.Intent
 import android.content.SharedPreferences
 import android.os.Handler
import android.os.Looper
 import android.text.TextUtils
 import android.util.Log
 import androidx.activity.viewModels
 import androidx.lifecycle.Observer
 import com.app.kalyanbazar.R
 import com.app.kalyanbazar.data.network.Resource
 import com.app.kalyanbazar.databinding.ActivitySplashBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
 import com.app.kalyanbazar.utils.handleApiError
 import com.app.kalyanbazar.utils.startAActivity
 import com.app.kalyanbazar.viewModel.HomeViewModel
 import com.google.firebase.messaging.FirebaseMessaging
 import dagger.hilt.android.AndroidEntryPoint
 import java.util.Timer
 import kotlin.concurrent.timerTask

@AndroidEntryPoint
class ActivitySplash : BaseActivity<ActivitySplashBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var token: String = ""
    lateinit var prefs: SharedPreferences
    var pinenter: String? = ""
    var phoneNumber: String = ""
    var userid: Int? = null
    override fun getLayoutResId(): Int =R.layout.activity_splash

    override fun setupViews() {
       // postDelayed()
       prefs = getSharedPreferences("MyPrf", Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor =
            this@ActivitySplash.getSharedPreferences("MyPrf", Context.MODE_PRIVATE).edit()
        editor.putString("countdown", "0")
        editor.apply()
        editor.commit()
        getContactUs()
        val prefs: SharedPreferences = getSharedPreferences("MyPrf", Context.MODE_PRIVATE)
     //   userid = prefs.getString("user_id", null)
        userid = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        pinenter = MyApplication.tinyDB.getString(Constants.SharedPref.USERPIN,"")
        Log.e("PinEnter","PinENter==>>>"+pinenter)
      //  pinenter = prefs.getString("pinenter", null)
        getToken()
        Timer().schedule(timerTask { checkFirstRun() }, 3000)

    }

    override fun setupViewsOnResume() {
       // checkFirstRun()
     }


    private fun postDelayed() {
        Handler(Looper.getMainLooper()).postDelayed({
             token = MyApplication.tinyDB.getString(Constants.SharedPref.ACCESS_TOKEN, "-1").toString()
            if (token == "-1"){
                 startAActivity(ActivityLogin::class.java)
                finish()
            }
            else if (token != null  && token != ""){
                 startAActivity(HomeDashboardActivity::class.java)
                finish()
            }else {
                 startAActivity(ActivityRegister::class.java)
                finish()
            }

        },1000)


    }




    fun checkFirstRun() {
        token = MyApplication.tinyDB.getString(Constants.SharedPref.ACCESS_TOKEN, "-1").toString()

        if (token != null && token != "") {
          //  if (pinenter.equals("1")) {
            if (pinenter != null && pinenter !="" ) {
                val intent = Intent(this@ActivitySplash, SecurityPin::class.java)
                intent.putExtra("paymentmethod", "")
                intent.putExtra("point", "")
                intent.putExtra("note", "")
                intent.putExtra("screen", "1")
                intent.putExtra("phone", phoneNumber)
                startActivity(intent)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                finish()
            } else {
                startActivity(Intent(this, HomeDashboardActivity::class.java))
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                finish()
            }

        } else {
            startActivity(Intent(this, ActivityRegister::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()

        }

    }

    fun getToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null && !TextUtils.isEmpty(task.result)) {
                        val token: String = task.result!!
                        MyApplication.tinyDB.putString(Constants.SharedPref.FIREBASE_TOKEN, token)
                        Log.e("Token==> ", "Token==s>" + token)
                    }
                }
            }
    }
    fun getContactUs() {
        viewModel.getContactUs.observe(this@ActivitySplash, Observer  {
            MyApplication.ProgressBar(this@ActivitySplash, it is Resource.Loading)
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
                    activity = this@ActivitySplash, retry = {

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
