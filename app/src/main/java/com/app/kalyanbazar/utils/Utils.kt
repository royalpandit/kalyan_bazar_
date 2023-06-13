package com.app.kalyanbazar.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import com.app.kalyanbazar.activity.ActivityLogin
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.utils.MyApplication.Companion.toast
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject




fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}
fun handleApiError(
    failure: Resource.Failure,
    view: View,
    activity: Activity? = null,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> snackbar(
            "Please check your internet connection", view,
            retry
        )
        failure.errorCode == 400 -> {
            activity?.let {
                Helper.hideKeyboard(it)
            }
            try {
                val jsonObject = JSONObject(failure.errorBody?.string().toString())
                val error = jsonObject.getString("message")
                snackbar(error, view)
            } catch (err: JSONException) {
                Log.d("Error", err.toString())
                val error = "Bad Request"
                snackbar(error, view)
            }
        }
        failure.errorCode == 500 -> {

            activity?.let {
                Helper.hideKeyboard(it)
            }
            try {
                val jsonObject = JSONObject(failure.errorBody?.string().toString())
                val error = jsonObject.getString("message")
                snackbar(error, view)

            } catch (err: JSONException) {
                Log.e("Error==>", err.toString())
                val error = "Data Not Found"
                snackbar(error, view)
            }

        }
        failure.errorCode == 401 -> {
            activity?.toast("Need to login again")
            MyApplication.tinyDB.clear()
            activity?.startNewActivity(ActivityLogin::class.java)
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            snackbar(error, view)
        }
    }
}

fun <A : Activity> Activity.startAActivity(activity: Class<A>) {
    Intent(this, activity).also {
        startActivity(it)
    }
}
fun snackbar(message: String, context: View, action: (() -> Unit)? = null) {
    var mess = message
    if (message.isNullOrEmpty()) mess = "Bad Request"
    val snackbar = Snackbar.make(context, mess, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}
