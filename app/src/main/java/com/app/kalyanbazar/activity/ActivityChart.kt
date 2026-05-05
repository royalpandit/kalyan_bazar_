package com.app.kalyanbazar.activity


import android.app.Activity
import android.net.http.SslError
import android.os.Build
import android.util.Log
import android.view.View
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
import com.app.kalyanbazar.utils.Constants
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
var urls:String=""
var marketName:String=""
var marketId:String=""
    override fun setupViews() {


          marketName = intent.getStringExtra("market_name").toString()
        marketId = intent.getStringExtra("market_id").toString()
Log.e("Tranfer","MarketName==>>$marketName")
Log.e("Tranfer","MarketID==>>$marketId")
        dataBinding.apply {

            if (marketName.equals("starline")){

                urls="https://kalyanbazar.co.in/chart-starline.html"
            //    urls="https://kalyanbazar.co.in/chart.html?market_id="+marketId+"&market_name="+"chart-starline"

            }else{
                urls="https://kalyanbazar.co.in/chart.html?market_id="+marketId+"&market_name="+marketName
            }


        //    urls="https://stackoverflow.com/questions/45940861/android-8-cleartext-http-traffic-not-permitted"
            toolbar.tvTitle.text = "Chart"

            toolbar.tvcois.visibility=View.GONE
            toolbar.ivWallet.visibility=View.GONE
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }

          //  getChart()
          //  getUserList()
            dataBinding.webview.webViewClient = WebViewClient()

            // this will load the url of the website
            dataBinding.webview.loadUrl(urls)

            // this will enable the javascript settings, it can also allow xss vulnerabilities
            dataBinding.webview.settings.domStorageEnabled = true    // this
            dataBinding.webview.settings.javaScriptCanOpenWindowsAutomatically = true  //and this


            // if you want to enable zoom feature
            dataBinding.webview.settings.setSupportZoom(true)

            dataBinding.webview.settings.javaScriptEnabled = true


            dataBinding.webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    view?.loadUrl(urls)
                    return true
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    if (handler != null) {
                        handler.proceed()
                    }
                    super.onReceivedSslError(view, handler, error)
                }
            }
            dataBinding.webview.loadUrl(urls)

            Log.e("url","url==>>>::"+urls)
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
                is Resource.Loading -> {

                }
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
            dataBinding.webview.settings.javaScriptEnabled = true
            dataBinding.webview.settings.useWideViewPort = true
            dataBinding.webview.webChromeClient = WebChromeClient()
            dataBinding.webview.settings.userAgentString = "Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0"
            dataBinding.webview.settings.setGeolocationEnabled(true)
            dataBinding.webview.settings.domStorageEnabled = true
            dataBinding.webview.settings.databaseEnabled = true
            dataBinding.webview.settings.setSupportMultipleWindows(true)
            //webView.getSettings().setAppCacheEnabled(true);
            //webView.getSettings().setAppCacheEnabled(true);
            dataBinding.webview.settings.setNeedInitialFocus(true)
            dataBinding.webview.settings.loadWithOverviewMode = true
            dataBinding.webview.settings.javaScriptCanOpenWindowsAutomatically = true
            dataBinding.webview.setInitialScale(100)
            dataBinding.webview.loadUrl(url)
        }
    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityChart, it is Resource.Loading)
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
                    activity = this@ActivityChart,
                    retry = { getUserList() })

                is Resource.Loading -> {

                }
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString()
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
