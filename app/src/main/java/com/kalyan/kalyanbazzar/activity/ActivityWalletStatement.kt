package com.kalyan.kalyanbazzar.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.adapter.AdapterWalletStatement
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivityWalletStatementBinding
import com.kalyan.kalyanbazzar.model.response.ResponseGetUserFund
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.Constants
import com.kalyan.kalyanbazzar.utils.Helper
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityWalletStatement : BaseActivity<ActivityWalletStatementBinding>(),
    AdapterWalletStatement.onClicklistUser {
    private val viewModel by viewModels<HomeViewModel>()
    var bidhistory: ArrayList<ResponseGetUserFund> = ArrayList()
    override fun getLayoutResId(): Int = R.layout.activity_wallet_statement
    var transferType: String = ""
    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text = "Wallet Statement"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            observeMerchant()
            getUserFundHistory()
            getUserList()
            getAppSetting()
            withdrawAdd.setOnClickListener {
                val withdrawPoints =
                    Intent(this@ActivityWalletStatement, ActivityWithdrawFund::class.java)
                startActivity(withdrawPoints)
            }
            addpoints.setOnClickListener {
                addpoints.isEnabled = false   // 👈 disable click
                viewModel.getMerchant()
               /* val withdrawPoints =
                    Intent(this@ActivityWalletStatement, ActivityAddPoint::class.java)
                startActivity(withdrawPoints)*/
            }


            transferpoints.setOnClickListener {
                if (transferType.equals("1")) {
                    val withdrawPoints =
                        Intent(this@ActivityWalletStatement, ActivityTransfer::class.java)
                    startActivity(withdrawPoints)
                } else {
                    successdialog()
                }
            }






            swipeRefLyt.setOnRefreshListener {
                swipeRefLyt.isRefreshing = false
                getUserFundHistory()
            }
        }
    }

    override fun setupViewsOnResume() {
    }

    //getUserList
    private fun successdialog() {
        val btnconfirm: TextView
        val succesfull: TextView
        val placed: TextView
        val dialog = Dialog(this@ActivityWalletStatement)
        val li =
            this@ActivityWalletStatement.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.dialogue_succefull, null, false)
        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        v.background = resources.getDrawable(R.drawable.roundalert)
        dialog.setContentView(v)

        btnconfirm = dialog.findViewById(R.id.playagainbtn)
        placed = dialog.findViewById(R.id.placed)
        succesfull = dialog.findViewById(R.id.succesfull)
        placed.text = ""
        btnconfirm.text = "OK"
        succesfull.text = "You Are Not Able To Transfer Your Fund"
        btnconfirm.setOnClickListener {
            dialog.dismiss()
            //      finish()
        }
        dialog.show()

    }

    private fun getUserFundHistory() {
        dataBinding.swipeRefLyt.isRefreshing = true
        viewModel.getUserFundList.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        dataBinding.swipeRefLyt.isRefreshing = false
                        getUserList()
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        bidhistory = ArrayList()
                        bidhistory = it.value.data

                        setHomeUserAdapter()
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityWalletStatement,
                    retry = { getUserFundHistory() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserFundList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )
    }

    private fun setHomeUserAdapter() {
        val adapter = AdapterWalletStatement(this, bidhistory)
        dataBinding.recyclerViewWallet.setHasFixedSize(true)
        dataBinding.recyclerViewWallet.adapter = adapter
        dataBinding.recyclerViewWallet.layoutManager = LinearLayoutManager(this)

    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityWalletStatement, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text = it.value.data.totalAmount.toString()
                        dataBinding.ptsText.text = "Available : "+it.value.data.totalAmount.toString()
                        //    WalletBalance=it.value.data.totalAmount!!.toInt()
                        if (it.value.data.transfer == true) {
                            transferType = "1"
                            // dataBinding.btnProceed.visibility= View.VISIBLE
                        } else {
                            transferType = "0"
                            // dataBinding.btnProceed.visibility= View.GONE
                        }
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@ActivityWalletStatement,
                    retry = { getUserList() })
                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    fun getAppSetting() {
        viewModel.getAppSetting.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        dataBinding.apply {
                            withdOpentime.text =
                                "Withdraw Open time              : " + Helper.dateFormateampm(it.value.data[0].withdrawlOpenTime!!.toString())
                            withdClosetime.text =
                                "Withdraw Close time            : " + Helper.dateFormateampm(it.value.data[0].withdrawlCloseTime!!.toString())
                            minWithdCoins.text =
                                "Minimum withdrawal points     : " +it.value.data[0].minWithdrawl!!.toString()
                        }

                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this, retry = {
                    }
                )
                is Resource.Loading -> {}
            }
        })


        dataBinding.apply {
            viewModel.getAppSetting(
            )
        }

    }

    private fun observeMerchant() {

        viewModel.getMerchant.observe(this) { response ->

            MyApplication.ProgressBar(this, response is Resource.Loading)

            when (response) {

                is Resource.Success -> {

                    dataBinding.addpoints.isEnabled = true   // 👈 enable back

                    if (response.value.status) {

                        val merchantData = response.value.data

                        if (merchantData?.status == true) {
                            startActivity(Intent(this, ActivityMerchant::class.java))
                        } else {
                            startActivity(Intent(this, ActivityAddPoint::class.java))
                        }

                    }
                }

                is Resource.Failure -> {

                    dataBinding.addpoints.isEnabled = true   // 👈 important

                    handleApiError(response, dataBinding.root, this)
                }

                is Resource.Loading -> {}
            }
        }
    }
}
