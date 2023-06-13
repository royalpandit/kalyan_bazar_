package com.app.kalyanbazar.activity

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivitySplashBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.startAActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitySplash : BaseActivity<ActivitySplashBinding>() {


    override fun getLayoutResId(): Int =R.layout.activity_splash

    override fun setupViews() {
        postDelayed()
        /*Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }, 3000)*/
    }

    override fun setupViewsOnResume() {
     }


    private fun postDelayed() {
        Handler(Looper.getMainLooper()).postDelayed({
            Log.e("11>>","11")
            var toekn = MyApplication.tinyDB.getString(Constants.SharedPref.ACCESS_TOKEN, "")

            if (toekn != null){
                startAActivity(HomeDashboardActivity::class.java)
                finish()
            }else {
                startAActivity(ActivityLogin::class.java)
                //     startAActivityM("LoginActivity")
                finish()
            }

        },3000)


    }
}