package com.app.kalyanbazar.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivityUpiBinding
import com.app.kalyanbazar.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityUpi : BaseActivity<ActivityUpiBinding>() {

    override fun getLayoutResId(): Int =R.layout.activity_upi

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text="Withdrwal UPI"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }

        }
     }

    override fun setupViewsOnResume() {
     }
}