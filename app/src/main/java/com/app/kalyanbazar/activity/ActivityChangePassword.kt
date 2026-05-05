package com.app.kalyanbazar.activity

import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivityChangePasswordBinding
import com.app.kalyanbazar.utils.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityChangePassword : BaseActivity<ActivityChangePasswordBinding>() {


    override fun getLayoutResId(): Int = R.layout.activity_change_password

    override fun setupViews() {
        dataBinding.apply {

            toolbar.tvTitle.text = "Change Password"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }


        }
    }

    fun GoChangePassBtn(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        if (TextUtils.isEmpty(dataBinding.inputNewPass.getText().toString())) {
            Snackbar.make(view, getString(R.string.please_enter_your_new_password), 2000).show()
            return
        }
        if (dataBinding.inputNewPass.getText().toString().length < 4) {
            Snackbar.make(view, getString(R.string.new_password_must_be_of_at_least_4_characters_length), 2000).show()
            return
        }
        if (TextUtils.isEmpty(dataBinding.inputConformPass.getText().toString())) {
            Snackbar.make(view, getString(R.string.please_enter_conform_password), 2000).show()
            return
        }
        if (dataBinding.inputConformPass.getText().toString()
                .trim { it <= ' ' } != dataBinding.inputNewPass.getText().toString()
                .trim { it <= ' ' }
        ) {
            Snackbar.make(view, getString(R.string.password_not_match), 2000).show()
            return
        }
    }

    fun passToggle(view: View?) {
        if (dataBinding.inputNewPass.getTransformationMethod().javaClass.getSimpleName() == "PasswordTransformationMethod") {
            dataBinding.inputNewPass.setTransformationMethod(SingleLineTransformationMethod())
            dataBinding.passToggle.setImageResource(R.drawable.passwordtoggle)
        } else {
            dataBinding.inputNewPass.setTransformationMethod(PasswordTransformationMethod())
            dataBinding.passToggle.setImageResource(R.drawable.passwordshow)
        }
        dataBinding.inputNewPass.setSelection(dataBinding.inputNewPass.getText()!!.length)
    }


    fun passToggleConf(view: View?) {
        if (dataBinding.inputConformPass.getTransformationMethod().javaClass.getSimpleName() == "PasswordTransformationMethod") {
            dataBinding.inputConformPass.setTransformationMethod(SingleLineTransformationMethod())
            dataBinding.passToggleEyeConf.setImageResource(R.drawable.passwordtoggle)
        } else {
            dataBinding.inputConformPass.setTransformationMethod(PasswordTransformationMethod())
            dataBinding.passToggleEyeConf.setImageResource(R.drawable.passwordshow)
        }
        dataBinding.inputConformPass.setSelection(dataBinding.inputConformPass.getText()!!.length)
    }

    override fun setupViewsOnResume() {
    }
}