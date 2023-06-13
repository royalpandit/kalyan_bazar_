package com.app.kalyanbazar.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.app.kalyanbazar.R


abstract class BaseFragment <B : ViewDataBinding> : Fragment() {

    protected lateinit  var dataBinding: B

    override fun onDetach() {
        super.onDetach()

    }
    abstract fun setupViews()

    abstract fun setupViewsOnResume()

    @LayoutRes
    abstract fun getLayoutId(): Int



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)


        setupViews()
        return dataBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onResume() {
        super.onResume()
        setupViewsOnResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    fun isViewLive() = isAdded && view != null

    protected open fun initViews() {

    }

    protected fun openUrl(url: String) {
        try {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        } catch (e: ActivityNotFoundException) {
        }
    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    fun refer() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TITLE, R.string.app_name)
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Stylish Fonts express your LOVE with Stylish Fonts @ Whatsapp Facebook any chat app. I have been using it in a while, give it a try  : https://play.google.com/store/apps/details?id=${activity?.packageName}&h=en")
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    fun rateUs() {
        activity?.let {
            Toast.makeText(it, resources.getString(R.string.thanks_msg), Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW)
            // Try Google play
            intent.data = Uri
                    .parse("https://play.google.com/store/apps/details?id=${it.packageName}&h=en")
            startActivity(intent)
        }

    }

    fun SendEmail() {
        val send = Intent(Intent.ACTION_SENDTO)
        var pInfo: PackageInfo? = null
        try {
            pInfo =activity?. packageManager?.getPackageInfo(requireActivity().packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val Appversion = pInfo!!.versionName
        val androidOS = Build.VERSION.RELEASE
        val uriText = ("mailto:"
                + Uri.encode(resources.getString(R.string.mail))
                + "?subject="
                + resources.getString(R.string.email_subject) + "" + resources.getString(R.string.app_name) + "&body=" + "\n\n\n"
                + "App name : " + resources.getString(R.string.app_name) + "\n"
                + "Mobile model : " + "ApplicationUtils.deviceName()" + "\n"
                + "App version : " + Appversion + "\n"
                + "Android version : " + androidOS)
        val uri = Uri.parse(uriText)
        send.data = uri
        startActivity(Intent.createChooser(send, "Send mail..."))
    }

    fun moreApps() {
        val more = Intent(Intent.ACTION_VIEW)
        more.data = Uri.parse("market://search?q=pub:" + resources.getString(R.string.dev_name))
        startActivity(more)
    }



}