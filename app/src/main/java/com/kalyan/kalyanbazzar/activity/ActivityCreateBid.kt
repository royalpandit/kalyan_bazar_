package com.kalyan.kalyanbazzar.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.adapter.BitFormAdapter
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.ActivityCreateBidBinding
import com.kalyan.kalyanbazzar.model.request.RequestCreateBid
import com.kalyan.kalyanbazzar.model.response.BitformModel
import com.kalyan.kalyanbazzar.model.response.ResponseGetNumberList
import com.kalyan.kalyanbazzar.utils.BaseActivity
import com.kalyan.kalyanbazzar.utils.Constants
import com.kalyan.kalyanbazzar.utils.Helper
import com.kalyan.kalyanbazzar.utils.MyApplication
import com.kalyan.kalyanbazzar.utils.MyApplication.Companion.toast
import com.kalyan.kalyanbazzar.utils.handleApiError
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import com.google.gson.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.toString

@AndroidEntryPoint
class ActivityCreateBid : BaseActivity<ActivityCreateBidBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    var numberlist: ArrayList<ResponseGetNumberList> = ArrayList()
    var numberlistFull: ArrayList<String> = ArrayList()
    var closeAnk: ArrayList<String> = ArrayList()
    var openAnk: ArrayList<String> = ArrayList()
    var showOpen: String = ""
    var numberlistDouble: ArrayList<String> = ArrayList()
    var numberlistName: ArrayList<String> = ArrayList()
    var numberlistNameZ: ArrayList<String> = ArrayList()
    var selectedItem: String? = ""
    var marketId: Int = 0
    var catId: Int = 0
    var marketName: String = ""
    var marketType: String = ""
    var openTime: String = ""
    var closeTime: String = ""
    var sessionName: String = ""
    var userId: Int? = null
    var sessionButton = false
    var pointValueMin: Int = 0
    var pointValueMax: Int = 0
    var totalAmountPoint: Int = 0
    var phoneNumber: String = ""
    val jsonParser = JsonParser()
    var ja = JSONArray()
    lateinit var bitFormAdapter: BitFormAdapter
    var valueOfText = ""
    var bitFormlist: ArrayList<BitformModel> = ArrayList()

    override fun getLayoutResId(): Int = R.layout.activity_create_bid

    override fun setupViews() {
        bitFormAdapter = BitFormAdapter(this, bitFormlist)
        dataBinding.rvadd.setHasFixedSize(true)
        dataBinding.rvadd.adapter = bitFormAdapter
        dataBinding.rvadd.layoutManager = LinearLayoutManager(this)

        userId = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        marketId = intent.getIntExtra(Constants.MARKET_ID, 0)
        catId = intent.getIntExtra(Constants.CAT_ID, 0)
        marketName = intent.getStringExtra(Constants.Category_Name).toString()
        marketType = intent.getStringExtra(Constants.marketType).toString()
        openTime = intent.getStringExtra(Constants.openTime).toString()
        closeTime = intent.getStringExtra(Constants.closeTime).toString()

        Log.e("OPEN_TIME", "OPEN_TIMEBID" + openTime)
        Log.e("CLOSE_TIME", "CLOSE_TIMEBID" + closeTime)
        showOpen = intent.getStringExtra(Constants.SHOW_OPEN).toString()
        Log.e("showOpen", "showOpen==>>" + showOpen)
        Log.e("showOpen", "showOpen==>>showOpen:" + showOpen)
        Log.e("marketName", "marketName==>>" + marketName)
        Log.e("marketName", "marketTypewe==>>" + marketType)
        getContactUs()
        if (marketName.equals("JODI DIGIT")) {

            sessionButton = false

        } else if (marketName.equals("HALF SANGAM")) {

            sessionButton = false

        } else if (marketName.equals("FULL SANGAM")) {

            sessionButton = false

        } else {

            val currentTime =
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

            Log.e("CURRENT_TIME", currentTime)
            Log.e("OPEN_TIME", openTime)

            if (currentTime >= openTime) {

                // ONLY CLOSE SESSION

                dataBinding.close.isChecked = true
                dataBinding.close.isActivated = true

                dataBinding.open.isChecked = false
                dataBinding.open.isActivated = false

                dataBinding.open.isEnabled = false

                sessionButton = false

            } else {

                // BOTH OPEN/CLOSE AVAILABLE

                dataBinding.open.isChecked = true
                dataBinding.open.isActivated = true

                dataBinding.close.isChecked = false
                dataBinding.close.isActivated = false

                dataBinding.open.isEnabled = true

                sessionButton = true
            }
        }
   /*     if (marketName.equals("JODI DIGIT")) {
            sessionButton = false
        } else if (marketName.equals("HALF SANGAM")) {
            sessionButton = false
        } else if (marketName.equals("FULL SANGAM")) {
            sessionButton = false
        } else {
            if (showOpen.equals("Yes")) {
                dataBinding.close.isChecked = true
                dataBinding.close.isActivated = true

                dataBinding.open.isChecked = false
                dataBinding.open.isActivated = false

                dataBinding.open.isEnabled = false

                sessionButton = false

            } else {
                //  dataBinding.llsession.visibility = View.GONE
                dataBinding.open.isChecked = true
                dataBinding.open.isActivated = true

                dataBinding.close.isChecked = false
                dataBinding.close.isActivated = false

                dataBinding.open.isEnabled = true

                sessionButton = true
            }
        }*/

        getAppSetting()
        if (marketType.equals("NORMAL")) {
            if (marketName.equals("SINGLE DIGIT")) {
                marketName = "SINGLE_DIGIT"
                dataBinding.toolbar.tvTitle.text = "Single Digit"

                dataBinding.llsession.visibility = View.VISIBLE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                getNumberList(marketName)

            } else if (marketName.equals("JODI DIGIT")) {
                marketName = "JODI_DIGIT"
                dataBinding.toolbar.tvTitle.text = "Jodi Digit"
                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                getNumberList(marketName)
            } else if (marketName.equals("SINGLE PANA")) {
                marketName = "SINGLE_PANA"
                dataBinding.toolbar.tvTitle.text = "Single Pana"
                dataBinding.llsession.visibility = View.VISIBLE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                dataBinding.inputdigits.hint = "Enter Pana"
                getNumberListDouble(marketName)
            } else if (marketName.equals("DOUBLE PANA")) {
                marketName = "DOUBLE_PANA"
                dataBinding.toolbar.tvTitle.text = "Double Pana"
                dataBinding.llsession.visibility = View.VISIBLE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                dataBinding.digText.text = "Pana"
                dataBinding.inputdigits.hint = "Enter Pana"
                getNumberListDouble(marketName)
            } else if (marketName.equals("TRIPLE PANA")) {
                marketName = "TRIPLE_PANA"
                dataBinding.toolbar.tvTitle.text = "Triple Pana"
                dataBinding.llsession.visibility = View.VISIBLE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                dataBinding.digText.text = "Pana"
                dataBinding.inputdigits.hint = "Enter Pana"

                getNumberList(marketName)
            } else if (marketName.equals("HALF SANGAM")) {
                marketName = "HALF_SANGAM"
                dataBinding.toolbar.tvTitle.text = "Half Sangam"
                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.VISIBLE
                dataBinding.digText.text = "Open Digit"
                dataBinding.panaText.text = "Close Pana"
                // getNumberListDouble(marketName)
                getNumberListFull(marketName)
            } else if (marketName.equals("FULL SANGAM")) {
                marketName = "FULL_SANGAM"
                dataBinding.toolbar.tvTitle.text = "Full Sangam"
                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.VISIBLE
                dataBinding.digText.text = "Open Pana"
                dataBinding.inputdigits.hint = "Enter Pana"
                dataBinding.panaText.text = "Close Pana"
                getNumberListFull(marketName)
            } else {
            }
        } else {
            if (marketName.equals("SINGLE DIGIT")) {
                marketName = "SINGLE_DIGIT"
                dataBinding.toolbar.tvTitle.text = "Single Digit"

                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                getNumberList(marketName)

            } else if (marketName.equals("JODI DIGIT")) {
                marketName = "JODI_DIGIT"
                dataBinding.toolbar.tvTitle.text = "Jodi Digit"
                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                getNumberList(marketName)
            } else if (marketName.equals("SINGLE PANA")) {
                marketName = "SINGLE_PANA"
                dataBinding.toolbar.tvTitle.text = "Single Pana"
                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                getNumberListDouble(marketName)
            } else if (marketName.equals("DOUBLE PANA")) {
                marketName = "DOUBLE_PANA"
                dataBinding.toolbar.tvTitle.text = "Double Pana"
                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                getNumberListDouble(marketName)
            } else if (marketName.equals("TRIPLE PANA")) {
                marketName = "TRIPLE_PANA"
                dataBinding.toolbar.tvTitle.text = "Triple Pana"
                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.GONE
                getNumberList(marketName)
            } else if (marketName.equals("HALF SANGAM")) {
                marketName = "HALF_SANGAM"
                dataBinding.toolbar.tvTitle.text = "Half Sangam"
                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.VISIBLE
                dataBinding.digText.text = "Open Digit"
                dataBinding.panaText.text = "Close Digit"
                // getNumberListDouble(marketName)
                getNumberListFull(marketName)
            } else if (marketName.equals("FULL SANGAM")) {
                marketName = "FULL_SANGAM"
                dataBinding.toolbar.tvTitle.text = "Full Sangam"
                dataBinding.llsession.visibility = View.GONE
                dataBinding.lldigit.visibility = View.VISIBLE
                dataBinding.llPana.visibility = View.VISIBLE
                dataBinding.digText.text = "Open Digit"
                dataBinding.panaText.text = "Close Digit"
                getNumberListFull(marketName)
            } else {
            }
        }

        fun onClickRadioButton(view: View) {}
        // getNumberList(marketName)
        getUserList()
        dataBinding.apply {
            //toolbar.tvTitle.text = marketName
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            chooseDate.text = Helper.getCurrentDateYMD()
            rlwhatsup.setOnClickListener {
                whatsAppBtn()
            }
            btnProceed.setOnClickListener {
                hideKeyboard()
                if (inputdigits.text.toString().isEmpty()) {
                    inputdigits.requestFocus()
                    inputdigits.error = "Enter Digit"
                } else if (inputCoins.text.toString().isEmpty()) {
                    inputCoins.requestFocus()
                    inputCoins.error = "Enter Amount"
                } else if (inputCoins.text.toString().toInt() < 10) {
                    inputCoins.requestFocus()
                    inputCoins.error = "Minimum bid amount should be 10"
                }
                else if (inputCoins.text.toString().toInt() % 10 != 0) {
                    inputCoins.requestFocus()
                    inputCoins.error = "Bid amount must be in multiples of 10 (10, 20, 30, 40...)"
                }else if (inputCoins.text.toString().toInt() < pointValueMin) {
                    inputCoins.requestFocus()
                    inputCoins.error =
                        "Minimum Points must be greater then " + pointValueMin.toString()
                } else if (inputCoins.text.toString().toInt() > pointValueMax) {
                    inputCoins.requestFocus()
                    inputCoins.error =
                        "Maximum Points must be greater then " + pointValueMax.toString()
                } else if (inputCoins.text.toString().toInt() >= totalAmountPoint) {
                    inputCoins.error =
                        "Bid Amount is greater then wallet amount." + totalAmountPoint.toString()

                } else {
                    var hasValue: Boolean = false
                    Log.e("==>", "marketName==>" + marketName)
                    Log.e("==>", "inputdigitsSingle==>" + inputdigits.text.toString())
                    Log.e("==>", "numberlistName==>" + numberlistName.toString())
                    if (marketName.equals("SINGLE_DIGIT")) {
                        Log.e("==>", "numberlistName==>" + numberlistName.toString())

                        hasValue = numberlistName.contains(inputdigits.text.toString())

                    } else if (marketName.equals("JODI_DIGIT")) {
                        hasValue = numberlistName.contains(inputdigits.text.toString())

                    } else if (marketName.equals("SINGLE_PANA")) {
                        hasValue = numberlistDouble.contains(inputdigits.text.toString())

                    } else if (marketName.equals("DOUBLE_PANA")) {
                        hasValue = numberlistDouble.contains(inputdigits.text.toString())

                    } else if (marketName.equals("TRIPLE_PANA")) {
                        hasValue = numberlistName.contains(inputdigits.text.toString())

                    } else if (marketName.equals("HALF_SANGAM")) {
                        hasValue =
                            openAnk.contains(inputdigits.text.toString()) && closeAnk.contains(
                                inputpana.text.toString()
                            )

                    } else if (marketName.equals("FULL_SANGAM")) {
                        Log.e("==>", "inputdigits==Sync>" + inputdigits.text.toString())
                        Log.e("==>", "inputpana==>" + inputpana.text.toString())
                        hasValue =
                            openAnk.contains(inputdigits.text.toString()) && closeAnk.contains(
                                inputpana.text.toString()
                            )

                    } else {
                    }

                    if (hasValue) {
                        show(
                            inputdigits.text.toString(),
                            inputCoins.text.toString(),
                            inputpana.text.toString()
                        )
                    } else {
                        toast("Please select proper value.")
                    }
                    // createBid(marketId)
                }
            }



            inputdigits.setOnFocusChangeListener { view, b -> if (b) inputdigits.showDropDown() }
        }
    }

    override fun setupViewsOnResume() {
    }

    //RequestCreateBid
    fun createBid(marketId: Int, valueToSend: String) {
        viewModel.RequestCreateBid.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        // finish()
                        //     bitFormlist.clear()
                        bitFormlist.clear()
                        bitFormAdapter = BitFormAdapter(this@ActivityCreateBid, bitFormlist)
                        dataBinding.rvadd.adapter = bitFormAdapter
                        dataBinding.apply {
                            inputdigits.setText("")
                            inputCoins.setText("")
                            inputpana.setText("")

                            rlinfo.visibility = View.GONE
                            btnProceeds.visibility = View.GONE
                        }

                        successdialog()

                    } else {
                        toast(it.value.message)
                        //  successdialogElse(it.value.message)
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
            //  var valueOfText = ""
            ja = JSONArray()

            for (m in 0 until bitFormlist.size) {
                val item = bitFormlist.get(m)
                val jo = JSONObject()
                ja.put(m, jo.put("session", item.session))
                ja.put(m, jo.put("pana", item.pana))
                ja.put(m, jo.put("points", item.points))
                ja.put(m, jo.put("status", true))
                ja.put(m, jo.put("user_id", item.user_Id))
                ja.put(m, jo.put("market_inside_id", item.marketInsideId))

            }


            if (TextUtils.isEmpty(inputpana.text.toString())) {
                valueOfText = inputdigits.text.toString()
            } else {
                valueOfText = inputdigits.text.toString() + " - " + inputpana.text.toString()
            }

            viewModel.RequestCreateBid(
                RequestCreateBid(
                    dataToSend = jsonParser.parse(ja.toString())
                    /* pana = valueOfText,
                     marketInsideId = catId,
                     // marketInsideId = marketId,
                     userId = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1),
                     //  userId = userId,
                     panaDate = chooseDate.text.toString(),
                     session = sessionButton,
                    // points = inputCoins.text.toString().toInt(),
                     points = valueToSend.toInt(),
                     status = true*/
                )
            )
        }

    }

    fun getAppSetting() {
        viewModel.getAppSetting.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {
                        pointValueMin = it.value.data[0].minBidAmount!!.toInt()
                        pointValueMax = it.value.data[0].maxBidAmount!!.toInt()
                        //   dataBinding.inTxtUpi.setText(it.value.data[0].upiAddress)
                        //     minWithdrwal=it.value.data[0].minWithdrawl!!.toInt()
                        //     maxWithdrwal=it.value.data[0].maxWithdrawl!!.toInt()

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
            viewModel.getAppSetting(
            )
        }

    }

    fun onClickRadioButton(view: View) {
        if (view is RadioButton) {
            when (view.id) {
                R.id.open ->
                    if (view.isChecked) {
                        //    sessionButton = false
                        sessionButton = true
                    }

                R.id.close ->
                    if (view.isChecked) {
                        sessionButton = false
                        // sessionButton = true

                    }
            }

        }
    }

    private fun getNumberList(marketName: String) {
        viewModel.getNumberList.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.code == 200) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        numberlist.clear()
                        numberlistName.clear()
                        numberlist = it.value.data

                        for (a in numberlist) {
                            a.number?.let { it1 -> numberlistName.add(it1) }
                        }
                        val adapter1 = ArrayAdapter(
                            this@ActivityCreateBid, // Context
                            android.R.layout.simple_list_item_single_choice, // Layout
                            numberlistName// Array
                        )
                        // Auto complete threshold
                        // The minimum number of characters to type to show the drop down
                        dataBinding.inputdigits.threshold = 0
                        // Set the AutoCompleteTextView adapter
                        dataBinding.inputdigits.setAdapter(adapter1)
                        val adapter2 = ArrayAdapter(
                            this@ActivityCreateBid, // Context
                            android.R.layout.simple_list_item_single_choice, // Layout
                            numberlistName// Array
                        )
                        // Auto complete threshold
                        // The minimum number of characters to type to show the drop down
                        dataBinding.inputpana.threshold = 0
                        // Set the AutoCompleteTextView adapter
                        dataBinding.inputpana.setAdapter(adapter2)

                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this@ActivityCreateBid,
                    retry = { getNumberList(this.marketName) })

                is Resource.Loading -> {

                }
            }
        })

        viewModel.getNumberList(
            numberType = marketName
        )
    }

    private fun getNumberListDouble(marketName: String) {
        viewModel.getNumberListDouble.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.code == 200) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        numberlistDouble = it.value.data
                        /*  for (a in numberlist) {
                              a.number?.let { it1 -> numberlistName.add(it1) }
                          }*/
                        val adapter1 = ArrayAdapter(
                            this@ActivityCreateBid, // Context
                            android.R.layout.simple_list_item_single_choice, // Layout
                            numberlistDouble// Array
                        )
                        // Auto complete threshold
                        // The minimum number of characters to type to show the drop down
                        dataBinding.inputdigits.threshold = 0
                        // Set the AutoCompleteTextView adapter
                        dataBinding.inputdigits.setAdapter(adapter1)
                        val adapter2 = ArrayAdapter(
                            this@ActivityCreateBid, // Context
                            android.R.layout.simple_list_item_single_choice, // Layout
                            numberlistDouble// Array
                        )
                        // Auto complete threshold
                        // The minimum number of characters to type to show the drop down
                        dataBinding.inputpana.threshold = 0
                        // Set the AutoCompleteTextView adapter
                        dataBinding.inputpana.setAdapter(adapter2)

                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this@ActivityCreateBid,
                    retry = { getNumberList(this.marketName) })

                is Resource.Loading -> {

                }
            }
        })

        viewModel.getNumberListDouble(
            numberType = marketName,
            callFrom = "app"
        )
    }

    private fun getNumberListFull(marketName: String) {
        viewModel.getNumberListFull.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.code == 200) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //  numberlistFull = it.value.data.
                        Log.e("Size==>", "ID==>" + it.value.message)
                        Log.e("Size==>", "data==>" + it.value.data.createdAt)
                        closeAnk = it.value.data.closeAnk
                        openAnk = it.value.data.openAnk
                        Log.e("Size==>", "SizeOpen==>" + openAnk)
                        Log.e("Size==>", "SizeClose==>" + closeAnk)
                        val adapter1 = ArrayAdapter(
                            this@ActivityCreateBid, // Context
                            android.R.layout.simple_list_item_single_choice, // Layout
                            openAnk// Array
                        )
                        // Auto complete threshold
                        // The minimum number of characters to type to show the drop down
                        dataBinding.inputdigits.threshold = 0
                        // Set the AutoCompleteTextView adapter
                        dataBinding.inputdigits.setAdapter(adapter1)
                        val adapter2 = ArrayAdapter(
                            this@ActivityCreateBid, // Context
                            android.R.layout.simple_list_item_single_choice, // Layout
                            closeAnk// Array
                        )
                        // Auto complete threshold
                        // The minimum number of characters to type to show the drop down
                        dataBinding.inputpana.threshold = 0
                        // Set the AutoCompleteTextView adapter
                        dataBinding.inputpana.setAdapter(adapter2)

                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this@ActivityCreateBid,
                    retry = { getNumberList(this.marketName) })

                is Resource.Loading -> {

                }
            }
        })

        viewModel.getNumberListFull(
            numberType = marketName,
            callFrom = "app"
        )
    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@ActivityCreateBid, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                        dataBinding.toolbar.tvcois.text = it.value.data.totalAmount.toString()
                        totalAmountPoint = it.value.data.totalAmount!!.toInt()
                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this@ActivityCreateBid,
                    retry = { getUserList() })

                is Resource.Loading -> {

                }
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    private fun bidPlaced() {
        val cancel: Button
        val confirm: Button
        val dialog = Dialog(this@ActivityCreateBid)
        val li =
            this@ActivityCreateBid.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.bid_placed_message, null, false)
        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        v.background = resources.getDrawable(R.drawable.bg_radius)
        dialog.setContentView(v)

        cancel = dialog.findViewById(R.id.cancel)
        confirm = dialog.findViewById(R.id.confirm)

        cancel.setOnClickListener {
            dialog.dismiss()
        }
        confirm.setOnClickListener {
            Log.e("=>", "===>" + dataBinding.inputCoins.text.toString())
            val valueToSend = dataBinding.inputCoins.text.toString()
            createBid(marketId, valueToSend)
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun successdialog() {
        val btnconfirm: TextView
        val dialogX = Dialog(this@ActivityCreateBid)
        val li =
            this@ActivityCreateBid.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.dialogue_succefull, null, false)
        val window = dialogX.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        v.background = resources.getDrawable(R.drawable.bg_radius)
        dialogX.setContentView(v)

        btnconfirm = dialogX.findViewById(R.id.playagainbtn)

        btnconfirm.setOnClickListener {
            dialogX.dismiss()
            getUserList()
            finish()
        }
        dialogX.show()

    }

    private fun successdialogElse(message: String) {
        val btnconfirm: TextView
        val succesfull: TextView
        val placed: TextView
        val photo: ImageView
        val dialog = Dialog(this@ActivityCreateBid)
        val li =
            this@ActivityCreateBid.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.dialogue_succefull, null, false)
        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        v.background = resources.getDrawable(R.drawable.bg_radius)
        dialog.setContentView(v)

        btnconfirm = dialog.findViewById(R.id.playagainbtn)
        succesfull = dialog.findViewById(R.id.succesfull)
        placed = dialog.findViewById(R.id.placed)
        photo = dialog.findViewById(R.id.photo)
        succesfull.visibility = View.GONE
        photo.visibility = View.GONE
        placed.text = message
        btnconfirm.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()

    }

    fun show(inputdigit: String, inputCoiins: String, inputPana: String) {
        dataBinding.apply {
            rlinfo.visibility = View.GONE
            btnProceeds.visibility = View.VISIBLE
            tvdigitopen.text = inputdigit
            tvpoint.text = inputCoiins
            tvdigitclose.text = inputPana
            /*
               val user_Id: Int,
    val marketInsideId: Int,
    var panaDate: String,
    var session: Boolean,
    var pana: String,
    var points: String,
    val status: Boolean
             */
            if (TextUtils.isEmpty(inputpana.text.toString())) {
                valueOfText = inputdigits.text.toString()

                bitFormlist.add(
                    BitformModel(
                        MyApplication.tinyDB.getInt(
                            Constants.SharedPref.OWNER_ID,
                            -1
                        ),
                        catId,
                        chooseDate.text.toString(),
                        sessionButton,
                        valueOfText,
                        inputCoiins,
                        true
                    )
                )

            } else {
                valueOfText = inputdigits.text.toString() + " - " + inputpana.text.toString()
                bitFormlist.add(
                    BitformModel(
                        MyApplication.tinyDB.getInt(
                            Constants.SharedPref.OWNER_ID,
                            -1
                        ),
                        catId,
                        chooseDate.text.toString(),
                        sessionButton,
                        valueOfText,
                        inputCoiins,
                        true
                    )
                )

            }
            inputdigits.text.clear()
            inputCoins.text.clear()
            inputpana.text.clear()

            bitFormAdapter = BitFormAdapter(this@ActivityCreateBid, bitFormlist)
            dataBinding.rvadd.adapter = bitFormAdapter



            if (inputPana.isEmpty()) {
                rlcosep.visibility = View.GONE
            } else {
                rlcosep.visibility = View.VISIBLE
            }

            ivclose.setOnClickListener {
                rlinfo.visibility = View.GONE
                inputCoins.setText("")
                inputdigits.setText("")
                inputpana.setText("")
                btnProceeds.visibility = View.GONE
            }


            btnProceeds.setOnClickListener {
                //createBid(marketId)
                bidPlaced()
                /* if (bitFormlist.size==0){

                 }else{
                     bidPlaced()
                 }*/
            }
            /*  if (inputdigits.text.toString().isEmpty()){
                  inputdigits.requestFocus()
                  inputdigits.error = "Enter Digit"
              }else if (inputCoins.text.toString().isEmpty()){
                  inputCoins.requestFocus()
                  inputCoins.error = "Enter Amount"
              }else if (inputCoins.text.toString().toInt() < pointValueMin){
                  inputCoins.requestFocus()
                  inputCoins.error = "Minimum Points must be greater then " + pointValueMin.toString()
              }else if (inputCoins.text.toString().toInt() > pointValueMax){
                  inputCoins.requestFocus()
                  inputCoins.error = "Maximum Points must be greater then " + pointValueMax.toString()
              }else{

                  createBid(marketId)
              }*/
        }
    }

    fun getContactUs() {
        viewModel.getContactUs.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        try {
                            phoneNumber = it.value.data.phoneNumber.toString()
                        } catch (ex: Exception) {
                        }
                        //   minFund=it.value.data[0].minDeposit!!.toInt()
                        //   maxFund=it.value.data[0].maxDeposit!!.toInt()

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
            viewModel.getContactUs(
            )
        }

    }

    fun whatsAppBtn() {
        val url =
            "https://api.whatsapp.com/send?phone=" + "+91" + phoneNumber + "&text=" + URLEncoder.encode(
                "",
                "UTF-8"
            )
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}