package com.kalyan.kalyanbazzar.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivitySupportBinding
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.Constants
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder

@AndroidEntryPoint
class ActivitySupport : BaseActivity<ActivitySupportBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var phoneNumber: String = ""
    override fun getLayoutResId(): Int =R.layout.activity_support
    override fun setupViews() {
        dataBinding.apply {
            getContactUs()
            toolbar.ivWallet.visibility=View.GONE
            toolbar.tvcois.visibility=View.GONE
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
            getUserList()

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

    fun getContactUs() {
        viewModel.getContactUs.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        try {
                            phoneNumber=it.value.data.phoneNumber.toString()
                        }catch (ex:Exception){

                        }
                        //   minFund=it.value.data[0].minDeposit!!.toInt()
                        //   maxFund=it.value.data[0].maxDeposit!!.toInt()

                    }
                }
                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this, retry = {

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
    fun whatsAppBtn() {
        val url = "https://api.whatsapp.com/send?phone="+"+91"+ phoneNumber +"&text=" + URLEncoder.encode("", "UTF-8")
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivitySupport, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text=it.value.data.totalAmount.toString()
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivitySupport,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }
}
