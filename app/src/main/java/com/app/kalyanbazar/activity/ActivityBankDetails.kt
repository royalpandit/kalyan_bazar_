package com.app.kalyanbazar.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivityBankDetailsBinding
import com.app.kalyanbazar.utils.BaseActivity

class ActivityBankDetails : BaseActivity<ActivityBankDetailsBinding>() {


    override fun getLayoutResId(): Int =R.layout.activity_bank_details

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text="Bank Details"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }


        }
     }

    override fun setupViewsOnResume() {
     }
}