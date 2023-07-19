package com.app.kalyanbazar.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivityProfileBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityProfile : BaseActivity<ActivityProfileBinding>() {


    override fun getLayoutResId(): Int =R.layout.activity_profile

    override fun setupViews() {

        dataBinding.apply {
            toolbar.tvTitle.text="Profile"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            tvname.text= MyApplication.tinyDB.getString(Constants.SharedPref.USER_NAME,"")
            tvemail.text= MyApplication.tinyDB.getString(Constants.SharedPref.EMAIL,"")
            tvmobile.text= MyApplication.tinyDB.getString(Constants.SharedPref.MOBILE,"")
        }
     }

    override fun setupViewsOnResume() {
     }
}