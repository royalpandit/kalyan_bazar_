package com.app.kalyanbazar.utils

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context

import androidx.multidex.MultiDex
 import android.view.Window
 import android.widget.Toast
import com.app.kalyanbazar.R
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


        fun Context.toast(message: CharSequence) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


    }

}