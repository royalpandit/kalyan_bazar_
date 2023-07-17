package com.app.kalyanbazar.activity


import android.app.Activity
import android.os.Build
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.app.kalyanbazar.R
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityChartBinding
import com.app.kalyanbazar.model.response.ResponseChartPdfGetItem
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityChart : BaseActivity<ActivityChartBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var arrChart: ArrayList<ResponseChartPdfGetItem> = ArrayList()
    override fun getLayoutResId(): Int =R.layout.activity_chart
var url:String=""
    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text = "Chart"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            getChart()

            /*dataBinding.webview.webViewClient = WebViewClient()

            // this will load the url of the website
            dataBinding.webview.loadUrl(url)

            // this will enable the javascript settings, it can also allow xss vulnerabilities
            dataBinding.webview.settings.javaScriptEnabled = true

            // if you want to enable zoom feature
            dataBinding.webview.settings.setSupportZoom(true)

            dataBinding.webview.settings.setJavaScriptEnabled(true)*/

            /*dataBinding.webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            dataBinding.webview.loadUrl(url)*/
        }
     }

    override fun setupViewsOnResume() {
     }



    fun getChart() {


        viewModel.ChartPdfGet.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        arrChart = ArrayList()
                        arrChart = it.value.data
                        url=arrChart[0].pdfUrl.toString()
                        Log.e("URL==>>>","$url")
                       // url(url)
                        dataBinding.webview.webViewClient = MyWebViewClient(this)
                        dataBinding.webview.loadUrl(url)
                        /* dataBinding.webview.webViewClient = WebViewClient()

                         // this will load the url of the website
                         dataBinding.webview.loadUrl(url)

                         // this will enable the javascript settings, it can also allow xss vulnerabilities
                         dataBinding.webview.settings.javaScriptEnabled = true

                         // if you want to enable zoom feature
                         dataBinding.webview.settings.setSupportZoom(true)

                         dataBinding.webview.settings.setJavaScriptEnabled(true)*/
                    }
                }
                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this, retry = {

                    }
                )
            }
        })


        dataBinding.apply {
            viewModel.ChartPdfGet(

            )

        }


    }


    fun url(url: String) {
        dataBinding.apply {
            dataBinding.webview.webViewClient = WebViewClient()
            //dataBinding.webview.setWebViewClient()
            dataBinding.webview.getSettings().setJavaScriptEnabled(true)
            dataBinding.webview.getSettings().setUseWideViewPort(true)
            dataBinding.webview.setWebChromeClient(WebChromeClient())
            dataBinding.webview.getSettings()
                .setUserAgentString("Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0")
            dataBinding.webview.getSettings().setGeolocationEnabled(true)
            dataBinding.webview.getSettings().setDomStorageEnabled(true)
            dataBinding.webview.getSettings().setDatabaseEnabled(true)
            dataBinding.webview.getSettings().setSupportMultipleWindows(true)
            //webView.getSettings().setAppCacheEnabled(true);
            //webView.getSettings().setAppCacheEnabled(true);
            dataBinding.webview.getSettings().setNeedInitialFocus(true)
            dataBinding.webview.getSettings().setLoadWithOverviewMode(true)
            dataBinding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)
            dataBinding.webview.setInitialScale(100)
            dataBinding.webview.loadUrl(url)
        }
    }


    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString();
            view?.loadUrl(url)
            Log.e("URL==>>>1","URL==>>>1")
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            Log.e("URL==>>>2","URL==>>>2")

            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            Log.e("URL==>>>3","URL==>>>3")
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }
}