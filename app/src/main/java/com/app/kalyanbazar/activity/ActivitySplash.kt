package com.app.kalyanbazar.activity

 import android.os.Handler
import android.os.Looper
 import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivitySplashBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.startAActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitySplash : BaseActivity<ActivitySplashBinding>() {
    var token: String = ""

    override fun getLayoutResId(): Int =R.layout.activity_splash

    override fun setupViews() {
        postDelayed()

    }

    override fun setupViewsOnResume() {
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
                 startAActivity(ActivityLogin::class.java)
                finish()
            }

        },1000)


    }
}