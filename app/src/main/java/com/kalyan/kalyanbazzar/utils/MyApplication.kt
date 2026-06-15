package com.kalyan.kalyanbazzar.utils

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import androidx.multidex.MultiDex
 import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.kalyan.kalyanbazzar.R
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
//            Stetho.initializeWithDefaults(this)
//        }
        val spPrivate = getSharedPreferences("private", MODE_PRIVATE)
        tinyDB = TinyDB(spPrivate)
        instance = this

      // Realm.init(this)
/*
        val configuration = RealmConfiguration.Builder()
            .name("todo.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)*/
        //Chucker.getLaunchIntent(this)


    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }



    companion object {
        lateinit var tinyDB: TinyDB
         var BASE_URLNEW="https://identity.marstea.in/"
         var BASE_URLNEW_Sign="https://project.marstea.in/"


        @get:Synchronized
        var instance: MyApplication? = null
            private set
        var dialog: Dialog? = null
        fun createLoaderView(context: Context) {

            dialog = Dialog(context)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
          //  dialog!!.window?.decorView?.setBackgroundResource(android.R.color.transparent)
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.loader_view_layout)
            dialog!!.show()
        }

        fun ProgressBar(context: Activity, flag: Boolean) {
            try {
                if (flag) {
                    dialog?.let {
                        if (it != null && it.isShowing) {
                            it.dismiss()
                        }
                    }
                    context?.let {
                        createLoaderView(it)
                    }

                } else {
                    dialog?.dismiss()
                }
            } catch (e: Exception) {

            }
        }

        fun ProgressBar(flag: Boolean) {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        }
        fun Context.customTopToast(message: String) {
            val inflater = LayoutInflater.from(this)
            val layout = inflater.inflate(R.layout.toast_top, null)
            layout.findViewById<TextView>(R.id.toast_text).text = message

            val toast = Toast(this)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
            toast.show()
        }
        fun Context.toast(message: CharSequence) {
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100) // 100 is the Y offset
            toast.show()
        }
        fun Context.toasts(message: CharSequence) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


    }

}