package com.kalyan.kalyanbazzar.activity

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivityContactUsBinding
import com.kalyan.kalyanbazzar.model.request.RequestWithdrwalFund
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityContactUs : BaseActivity<ActivityContactUsBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    override fun getLayoutResId(): Int =R.layout.activity_contact_us

    override fun setupViews() {


        dataBinding.apply {
            toolbar.tvTitle.text = "Contact Us"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            tvSubmit.setOnClickListener {
                if (inputQuery.text.toString().isEmpty()) {
                    inputQuery.requestFocus()
                    inputQuery.error = "Enter Your Query Here"
                } else {
                    AddContactUs(inputQuery.text.toString())
                }
            }
        }

     }

    override fun setupViewsOnResume() {
     }


    fun AddContactUs(inputQuery: String) {
        viewModel.addContactUs.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                       // toast("Your UPI ID Add Is Succefull")
                     //   finish()
                        successdialognew()
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
            viewModel.addContactUs(
                RequestWithdrwalFund(
                    feedback = inputQuery,
                    userId = null,
                    amount = null
                )
            )
        }

    }


    private fun successdialognew() {
        val btnconfirm: TextView
        val placed: TextView
        val succesfull: TextView
        val photo: ImageView
        val dialog = Dialog(this@ActivityContactUs)
        val li =
            this@ActivityContactUs.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.dialogue_succefull, null, false)
        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        v.background = resources.getDrawable(R.drawable.bg_radius)
        dialog.setContentView(v)

        btnconfirm = dialog.findViewById(R.id.playagainbtn)
        photo = dialog.findViewById(R.id.photo)
        succesfull = dialog.findViewById(R.id.succesfull)
        placed = dialog.findViewById(R.id.placed)
        photo.visibility = View.VISIBLE
        placed.visibility = View.GONE
        succesfull.visibility = View.VISIBLE

        btnconfirm.text = "OK"
        succesfull.text = "we will resolve your query as soon as possible."
        placed.text =  ""
        btnconfirm.setOnClickListener {
            dialog.dismiss()
           finish()
        }
        dialog.show()

    }

}

//addContactUs
