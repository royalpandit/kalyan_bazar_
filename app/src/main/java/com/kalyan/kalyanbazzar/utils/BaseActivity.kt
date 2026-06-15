package com.kalyan.kalyanbazzar.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.kalyan.kalyanbazzar.BuildConfig
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.utils.MyApplication.Companion.toast
import java.util.Locale

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var dataBinding: B
    val RecordAudioRequestCode = 1
    private var speechRecognizer: SpeechRecognizer? = null

    override fun attachBaseContext(newBase: Context) {
        //langugae code
        super.attachBaseContext(
            LocaleHelper.wrap(
                newBase,
                MyApplication.tinyDB.getString(Constants.SharedPref.LANG, "not_selected") ?: "en"
            )
        )
    }

    @LayoutRes
    abstract fun getLayoutResId(): Int
    abstract fun setupViews()

    abstract fun setupViewsOnResume()

    override fun onResume() {
        super.onResume()
        setupViewsOnResume()
        if (BuildConfig.DEBUG) {
        } else {
            try {
                //  Log.e("STatus Debug","==>"+isLogacteEnabled())
                if (isLogacteEnabled() == 1) {
                    //1 = Usb Debugging on
                    showAlertLogcat("Please Disable Usb Debugging Mode", "Exit", true)
                } else {
                    //    showAlertLogcat("Debugging", "close Your App ", true)

                }
            } catch (ex: Exception) {
                toast(ex.toString())
                //    Log.e("STatus Debugas","==>"+ex.toString())
            }
        }

    }

    open fun getMyContext(): Context? {
        return LocaleHelper.wrap(
            this,
            MyApplication.tinyDB.getString(Constants.SharedPref.LANG, "not_selected") ?: "en"
        )
    }

    override fun getBaseContext(): Context {
        return getMyContext()!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //theme code
        /*        if (MyApplication.tinyDB.getBoolean(Constants.SharedPref.THEME, false))
                    setTheme(R.style.AppThemeDark)
                else
                    setTheme(R.style.AppTheme)*/

        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        setupStatusBar(this)
        applySystemBarInsetsToRoot(dataBinding.root)

        setupViews()
    }

    companion object {
        @JvmStatic
        fun setupStatusBar(activity: Activity) {
            val window: Window = activity.window
            val accentGold = ContextCompat.getColor(activity, R.color.red)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // Edge-to-edge on API 35+; insets are applied in applySystemBarInsetsToRoot.
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = accentGold
            window.navigationBarColor = accentGold
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
                isAppearanceLightNavigationBars = true
            }
        }

        @JvmStatic
        fun applySystemBarInsetsToRoot(rootView: View) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, windowInsets ->
                val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                val statusBars = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
                when (view) {
                    is DrawerLayout -> {
                        for (i in 0 until view.childCount) {
                            val child = view.getChildAt(i)
                            if (child is NavigationView) {
                                child.setPadding(
                                    systemBars.left,
                                    statusBars.top,
                                    systemBars.right,
                                    systemBars.bottom
                                )
                            } else {
                                applyContentInsets(child, systemBars, statusBars)
                            }
                        }
                    }

                    else -> applyContentInsets(view, systemBars, statusBars)
                }
                windowInsets
            }
            ViewCompat.requestApplyInsets(rootView)
        }

        /** Pushes toolbar below the status bar; screens without a toolbar get root top padding. */
        private fun applyContentInsets(
            view: View,
            systemBars: Insets,
            statusBars: Insets
        ) {
            val appBar = view.findViewById<View>(R.id.appbarLayout)
            val rlTool = view.findViewById<View>(R.id.rl_tool)

            when {
                appBar != null -> {
                    applyStatusBarInsetToToolbar(appBar, statusBars.top)
                    view.setPadding(systemBars.left, 0, systemBars.right, 0)
                }

                rlTool != null -> {
                    applyStatusBarInsetToToolbar(rlTool, statusBars.top)
                    view.setPadding(systemBars.left, 0, systemBars.right, 0)
                }

                else -> {
                    view.setPadding(
                        systemBars.left,
                        statusBars.top,
                        systemBars.right,
                        0
                    )
                }
            }
        }

        private fun findToolbarHost(root: View): View? {
            root.findViewById<View>(R.id.rl_tool)?.let { return it }
            root.findViewById<View>(R.id.appbarLayout)?.let { return it }
            return null
        }

        private fun applyStatusBarInsetToToolbar(toolbar: View, statusBarTop: Int) {
            if (toolbar.background == null) {
                toolbar.setBackgroundResource(R.drawable.button_gradient)
            }
            (toolbar as? ViewGroup)?.clipToPadding = false
            toolbar.setPadding(
                toolbar.paddingLeft,
                statusBarTop,
                toolbar.paddingRight,
                toolbar.paddingBottom
            )
            val layoutParams = toolbar.layoutParams
            if (layoutParams != null && layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                toolbar.layoutParams = layoutParams
            }
        }
    }

    private fun takeToPlaystore(packageName: String?) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            val url = "https://play.google.com/store/apps/details?id=$packageName"
            openUrl(url)
        }
    }

    private fun openUrl(url: String) {
        try {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No related activity found", Toast.LENGTH_SHORT).show()
        }
    }

    fun showAlert(
        message: String,
        button_title: String = getString(R.string.ok),
        isCancellable: Boolean = true,
        showCancel: Boolean = true,
        sucessBlock: () -> Unit
    ) {
        try {
            val builder1 = AlertDialog.Builder(this)
            builder1.setMessage(message)
            builder1.setTitle(getString(R.string.app_name))
            builder1.setCancelable(isCancellable)
            builder1.setPositiveButton(button_title) { dialog, id ->
                sucessBlock()
            }
            if (showCancel) {
                builder1.setNegativeButton(
                    getString(R.string.cancel)
                ) { dialog, id ->
                    dialog.cancel()
                }
            }
            val alert11 = builder1.create()
            alert11.show()
        } catch (e: Exception) {
        }
    }

    fun showAlertLogcat(
        message: String,
        button_title: String = getString(R.string.ok),
        isCancellable: Boolean = false,
    ) {
        try {
            val builder1 = AlertDialog.Builder(this)
            builder1.setMessage(message)
            builder1.setTitle(getString(R.string.app_name))
            builder1.setCancelable(isCancellable)

            builder1.setPositiveButton(button_title) { dialog, id ->
                finishAffinity();
                System.exit(0);
            }
            val alert11 = builder1.create()
            alert11.setCanceledOnTouchOutside(false);

            alert11.show()

            alert11.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (alert11.isShowing) {
                        //   alert11.dismiss()
                    }
                }
                true
            }
        } catch (e: Exception) {
        }
    }

    fun speechTotext(editText: EditText) {
        var x = 0
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission();
        } else {
            val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speechRecognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

            speechRecognizer!!.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(bundle: Bundle) {}
                override fun onBeginningOfSpeech() {
                    editText.setText("")
                    editText.setHint("Listening...")
                }

                override fun onRmsChanged(v: Float) {}
                override fun onBufferReceived(bytes: ByteArray) {}
                override fun onEndOfSpeech() {}
                override fun onError(i: Int) {}
                override fun onResults(bundle: Bundle) {
//                micButton.setImageResource(R.drawable.ic_mic_black_off)
                    val data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    editText.setText(data!![0])
                }

                override fun onPartialResults(bundle: Bundle) {}
                override fun onEvent(i: Int, bundle: Bundle) {}
            })

            speechRecognizer!!.startListening(speechRecognizerIntent)
        }

    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RecordAudioRequestCode
            )
        }

    }

    /*  fun getDeviceId():String{
          return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
      }
  */
    fun isLogacteEnabled(): Int {
        val devOptions = Settings.Secure.getInt(
            this.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            0
        )
        return devOptions
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer?.let { it.destroy() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(
                this,
                "Permission Granted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}