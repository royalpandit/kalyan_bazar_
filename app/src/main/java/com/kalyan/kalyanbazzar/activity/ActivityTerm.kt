package com.kalyan.kalyanbazzar.activity

import android.text.Html
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivityTermBinding
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.Constants
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@AndroidEntryPoint
 class ActivityTerm : BaseActivity<ActivityTermBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var sebUrl: String = ""

    override fun getLayoutResId(): Int = R.layout.activity_term
    override fun setupViews() {

        dataBinding.apply {
            toolbar.ivWallet.visibility= View.GONE
            toolbar.tvcois.visibility= View.GONE
            toolbar.tvTitle.text = "Term & Condition"
            toolbar.ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }


            // getUserList()
            try {
                val htmlContent = loadHtmlFromAssets("privacy_policy_data.html")

                // Set the HTML content to the TextView

                tvData.text = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
            } catch (exception: IOException) {

            }
         /*   webview.webViewClient = WebViewClient()
            webview.settings.javaScriptEnabled = true
            webview.settings.javaScriptCanOpenWindowsAutomatically = true
            webview.settings.pluginState = WebSettings.PluginState.ON
            webview.settings.mediaPlaybackRequiresUserGesture = false
            webview.webChromeClient = WebChromeClient()*/
            // webview.loadUrl("https://www.youtube.com")
            // webview.loadUrl(WebUrl)
          //  webview.loadUrl("https://kalyanbazar.co.in/privacy-policy.html")
            //    WebUrl
        }
    }

    override fun setupViewsOnResume() {

    }
     private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityTerm, it is Resource.Loading)
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
                    activity = this@ActivityTerm,
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
