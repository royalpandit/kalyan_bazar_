package com.app.kalyanbazar.activity

import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivityGameRateBinding
import com.app.kalyanbazar.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityGameRate : BaseActivity<ActivityGameRateBinding>() {


    override fun getLayoutResId(): Int =R.layout.activity_game_rate

    override fun setupViews() {
        dataBinding.apply {

            toolbar.tvTitle.text="Game Rates"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
        }
        }

    override fun setupViewsOnResume() {
     }
}