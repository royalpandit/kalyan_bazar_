package com.app.kalyanbazar.activity

import android.content.Intent
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivityWithdrawFundBinding
import com.app.kalyanbazar.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityWithdrawFund :  BaseActivity<ActivityWithdrawFundBinding>() {

    override fun getLayoutResId(): Int =R.layout.activity_withdraw_fund

    override fun setupViews() {

        dataBinding.apply {
            toolbar.tvTitle.text="Withdrwal Point"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }

            bank.setOnClickListener {
                val intent = Intent(this@ActivityWithdrawFund, ActivityBankDetails::class.java)
                startActivity(intent)
               //
            }
            rlphonepay.setOnClickListener {
                val intent = Intent(this@ActivityWithdrawFund, ActivityUpi::class.java)
                startActivity(intent)
            }
            rlgpay.setOnClickListener {
                val intent = Intent(this@ActivityWithdrawFund, ActivityUpi::class.java)
                startActivity(intent)
            }
            rlpaytm.setOnClickListener {
                val intent = Intent(this@ActivityWithdrawFund, ActivityUpi::class.java)
                startActivity(intent)
            }


            //
        }




     }

    override fun setupViewsOnResume() {
     }
}