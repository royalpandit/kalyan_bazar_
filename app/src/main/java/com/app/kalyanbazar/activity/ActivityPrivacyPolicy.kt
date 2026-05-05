package com.app.kalyanbazar.activity

import android.text.Html
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityPrivacyPolicyBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@AndroidEntryPoint
class ActivityPrivacyPolicy : BaseActivity<ActivityPrivacyPolicyBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var sebUrl: String = ""


    override fun getLayoutResId(): Int = R.layout.activity_privacy_policy
    override fun setupViews() {

        dataBinding.apply {
toolbar.ivWallet.visibility=View.GONE
toolbar.tvcois.visibility=View.GONE
            toolbar.tvTitle.text = "Privacy Policy"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }

            try {
                val htmlContent = loadHtmlFromAssets("privacy_policy_data.html")

                // Set the HTML content to the TextView

                tvData.text = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
            } catch (exception: IOException) {

            }

           // tvData.text = getResources().getString(R.string.privacy_policy_data_new);
            //tvData.setText(Html.fromHtml(getResources().getString(R.string.my_html)));
            //getHowToHelp()
          //  getUserList()

          /*  webview.webViewClient = WebViewClient()
            webview.settings.javaScriptEnabled = true
            webview.settings.javaScriptCanOpenWindowsAutomatically = true
            webview.settings.pluginState = WebSettings.PluginState.ON
            webview.settings.mediaPlaybackRequiresUserGesture = false
            webview.webChromeClient = WebChromeClient()*/
            // webview.loadUrl("https://www.youtube.com")
            // webview.loadUrl(WebUrl)
            //webview.loadUrl("https://kalyanbazar.co.in/privacy-policy.html")
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
                       /* WebUrl=it.value.data[0].url.toString()
                        dataBinding.webview.setWebViewClient(WebViewClient())
                        dataBinding.webview.loadUrl(WebUrl)*/

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
            MyApplication.ProgressBar(this@ActivityPrivacyPolicy, it is Resource.Loading)
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
                    activity = this@ActivityPrivacyPolicy,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    private fun loadHtmlFromAssets(fileName: String): String {
        val stringBuilder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(assets.open(fileName)))

        reader.use {
            var line = it.readLine()
            while (line != null) {
                stringBuilder.append(line)
                line = it.readLine()
            }
        }

        return stringBuilder.toString()
    }
}
