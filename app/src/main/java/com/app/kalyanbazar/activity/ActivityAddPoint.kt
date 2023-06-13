package com.app.kalyanbazar.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivityAddPointBinding
import com.app.kalyanbazar.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAddPoint : BaseActivity<ActivityAddPointBinding>() {


    override fun getLayoutResId(): Int = R.layout.activity_add_point

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text="Add Point"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun setupViewsOnResume() {
    }
}