package com.kalyan.kalyanbazzar.activity


import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivityHelpBinding
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.Constants
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityHelp : BaseActivity<ActivityHelpBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var WebUrl: String = ""

    override fun getLayoutResId(): Int = R.layout.activity_help
    override fun setupViews() {

        dataBinding.apply {
            toolbar.ivWallet.visibility= View.GONE
            toolbar.tvcois.visibility= View.GONE
            toolbar.tvTitle.text = "How to help"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }


            getHowToHelp()
            getUserList()

            webview.webViewClient = WebViewClient()
            webview.settings.javaScriptEnabled = true
            webview.settings.javaScriptCanOpenWindowsAutomatically = true
            webview.settings.pluginState = WebSettings.PluginState.ON
            webview.settings.mediaPlaybackRequiresUserGesture = false
            webview.webChromeClient = WebChromeClient()
           // webview.loadUrl("https://www.youtube.com")
           // webview.loadUrl(WebUrl)
            webview.loadUrl("https://www.youtube.com/embed/HpbZsFakJVI")
        //    WebUrl
        }
    }

    override fun setupViewsOnResume() {

     }
    fun getHowToHelp() {
        viewModel.getHowToHelp.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        WebUrl=it.value.data[0].url.toString()
                       dataBinding.webview.setWebViewClient(WebViewClient())
                        dataBinding.webview.loadUrl(WebUrl)

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
            viewModel.getHowToHelp(

            )

        }

    }
    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityHelp, it is Resource.Loading)
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
                    activity = this@ActivityHelp,
                    retry = { getUserList() })

                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }
}
