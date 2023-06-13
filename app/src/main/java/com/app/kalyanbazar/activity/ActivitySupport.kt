package com.app.kalyanbazar.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivitySupportBinding
import com.app.kalyanbazar.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitySupport : BaseActivity<ActivitySupportBinding>() {


    override fun getLayoutResId(): Int =R.layout.activity_support
    override fun setupViews() {
        dataBinding.apply {

            toolbar.tvTitle.text="Support"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            llphone.setOnClickListener {
                setClipboard(this@ActivitySupport,dataBinding.phoneNum1.text.toString())
            }
            llwhatsup.setOnClickListener {
                whatsAppBtn()
            }

        }

     }


    override fun setupViewsOnResume() {
     }
    private fun setClipboard(ct: Context, text: String) {
        val clipboard = ct.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", "+918107116566")
        clipboard.setPrimaryClip(clip)
        Toast.makeText(ct, "Mobile Number Copied to Clipboard", Toast.LENGTH_SHORT).show()
    }

    fun whatsAppBtn() {
        val url = "https://api.whatsapp.com/send?phone=" + "+918107116566"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}