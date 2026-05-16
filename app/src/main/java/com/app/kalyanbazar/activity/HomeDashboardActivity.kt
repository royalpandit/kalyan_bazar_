package com.app.kalyanbazar.activity

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kalyanbazar.BuildConfig
import com.app.kalyanbazar.R
import com.app.kalyanbazar.adapter.AdapterHome
import com.app.kalyanbazar.adapter.AdapterImageSlider
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityHomeDashboardBinding
import com.app.kalyanbazar.model.response.ResponseDashBoardListItem
import com.app.kalyanbazar.model.response.ResponseImageSlider
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class HomeDashboardActivity : BaseActivity<ActivityHomeDashboardBinding>(),
    AdapterHome.onClicklistBazar {
    private val viewModel by viewModels<HomeViewModel>()
    var arr: ArrayList<ResponseDashBoardListItem> = ArrayList()
    var arrImageSlider: ArrayList<ResponseImageSlider> = ArrayList()
    private var currentPage = 0
    private var NUM_PAGES = 0
    var fragment: Fragment? = null
    var refrelID: String = ""
    var totalAmount: String = ""
    lateinit var toggle: ActionBarDrawerToggle
    var isUserStatus = false
    var isBetting = false
    var popUpMessage: String = ""
    var maintenece: String = ""
    lateinit var menu: Menu
    lateinit var tvcoisNew: TextView
    var phoneNumber: String = ""
    var currenttime: String = ""
    override fun getLayoutResId(): Int = R.layout.activity_home_dashboard

    override fun setupViews() {
        dataBinding.apply {
            //   getUserList()
            setSupportActionBar(dataBinding.toolbar)
            val toggle = ActionBarDrawerToggle(
                this@HomeDashboardActivity,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            //  tvcoisNew = findViewById(R.id.tvcois)
            dataBinding.drawerLayout.addDrawerListener(toggle)
            // toggle.syncState()
            dataBinding.navigationView.itemIconTintList = null
            dataBinding.tvcois.setOnClickListener {
                val intent = Intent(this@HomeDashboardActivity, ActivityWalletStatement::class.java)
                startActivity(intent)
            }
            dataBinding.ivwallet.setOnClickListener {
                val intent = Intent(this@HomeDashboardActivity, ActivityWalletStatement::class.java)
                startActivity(intent)
            }
            menu = navigationView.menu
            menu.findItem(R.id.termcondition).isVisible = false
            menu.findItem(R.id.privacy).isVisible = false
            confToolbar()
            observeMerchant()
            observeDashboardList()
            onCLick()
            getUserList()
           // profile()
            getContactUs()
           // getImageSlider()
            getInformation()
            dataBinding.swipeRefLyt.setOnRefreshListener {

                dataBinding.swipeRefLyt.isRefreshing = false

                getUserList()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

    }

    fun onCLick() {
        dataBinding.apply {
            btnStartQuiz.setOnClickListener {

                val intent = Intent(
                    this@HomeDashboardActivity,
                    TrainingActivity::class.java
                )

                startActivity(intent)
            }

            whatsappItem.setOnClickListener {
                openPopup(popUpMessage)
            }

            rlwhatsups.setOnClickListener {
                whatsAppBtn()
            }

            rlcalls.setOnClickListener {
                callBtn()
            }

            addpointsItem.setOnClickListener {
                addpointsItem.isEnabled = false
                viewModel.getMerchant()
            }

            withdrawItem.setOnClickListener {
                val withdrawPoints =
                    Intent(this@HomeDashboardActivity, ActivityWithdrawFund::class.java)
                startActivity(withdrawPoints)
            }

            starlineItem.setOnClickListener {
                val intent = Intent(this@HomeDashboardActivity, ActivityStarline::class.java)
                intent.putExtra(Constants.showALl, "starline")
                startActivity(intent)
            }

            playstoreItem.setOnClickListener {
                val url =
                    "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }
    }

    fun openPopup(popUpMessage: String) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Kalyan Bazar")
        //set message for alert dialog
        //builder.setMessage("अगर आपको पॉइंट्स डालने या ऐड करने मैं किसी भी प्रकार की तकलीफ हो रही है तो आप हमारे नंबर पर व्हाट्सप्प (XXXXXXXXX) कर सकते है या फिर कॉल (XXXXXXXXX) कर के हेल्प सुविधा का उपयोग कर सकते है।")
        builder.setMessage(popUpMessage)
        builder.setIcon(R.drawable.kalyanbazar_logo)
        //performing positive action
        builder.setPositiveButton("Ok") { dialogInterface, which ->
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun setupViewsOnResume() {
        // getUserList()
      //  profile()
        getContactUs()
     //   getImageSlider()
        getInformation()
        /*dataBinding.swipeRefLyt.setOnRefreshListener {
            dataBinding.swipeRefLyt.isRefreshing = false
            // getDashboardList()
            getUserList()
        }*/

    }

    private fun confToolbar() {
        dataBinding.toolbar.setNavigationOnClickListener(View.OnClickListener { v: View? ->
            dataBinding.drawerLayout.openDrawer(
                GravityCompat.START
            )
        })
        dataBinding.navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home -> {
                    dataBinding.drawerLayout.closeDrawers()
                }

                R.id.seeFullProfile -> {
                    val profile = Intent(this@HomeDashboardActivity, ActivityProfile::class.java)
                    startActivity(profile)
                }

                R.id.addFunds -> {
                    viewModel.getMerchant()
                    // val addfund = Intent(this@HomeDashboardActivity, ActivityAddPoint::class.java)
                    //startActivity(addfund)
                }

                R.id.withdrawPoints -> {
                    val withdrawPoints =
                        Intent(this@HomeDashboardActivity, ActivityWithdrawFund::class.java)
                    startActivity(withdrawPoints)
                }

                R.id.transferfund -> {
                    val withdrawPoints =
                        Intent(this@HomeDashboardActivity, ActivityTransfer::class.java)
                    startActivity(withdrawPoints)
                }

                R.id.walletStatement -> {
                    val intent =
                        Intent(this@HomeDashboardActivity, ActivityWalletStatement::class.java)
                    startActivity(intent)
                }

                R.id.winHistory -> {
                    val winHistory =
                        Intent(this@HomeDashboardActivity, ActivityBidHistory::class.java)
                    winHistory.putExtra(getString(R.string.history), 100)
                    startActivity(winHistory)
                }

                R.id.bidHistory -> {
                    val bidHistory =
                        Intent(this@HomeDashboardActivity, ActivityBidHistory::class.java)
                    bidHistory.putExtra(getString(R.string.history), 200)
                    startActivity(bidHistory)
                }

                R.id.game_withdrwallist -> {
                    val bidHistory =
                        Intent(this@HomeDashboardActivity, ActivityWithdrwalList::class.java)
                    startActivity(bidHistory)
                }

                R.id.game_transferhistory -> {
                    val bidHistory =
                        Intent(this@HomeDashboardActivity, ActivityTransferHIstoryList::class.java)
                    startActivity(bidHistory)
                }

                R.id.game_values -> {
                    val gameRates = Intent(this@HomeDashboardActivity, ActivityGameRate::class.java)
                    gameRates.putExtra(getString(R.string.history), 1)
                    startActivity(gameRates)
                }

                R.id.how_to_learn -> {
                    val howToPlay =
                        Intent(this@HomeDashboardActivity, ActivityHelp::class.java)
                    startActivity(howToPlay)
                    /*    val howToPlay = Intent(this@HomeDashboardActivity, easyTwo::class.java)
                        howToPlay.putExtra(getString(R.string.f1bmain_activity), 2)
                        startActivity(howToPlay)*/
                }

                R.id.termcondition -> {
                    val termcondition =
                        Intent(this@HomeDashboardActivity, ActivityTerm::class.java)
                    startActivity(termcondition)
                    /*    val howToPlay = Intent(this@HomeDashboardActivity, easyTwo::class.java)
                        howToPlay.putExtra(getString(R.string.f1bmain_activity), 2)
                        startActivity(howToPlay)*/
                }

                R.id.privacy -> {
                    val privacyPolicy =
                        Intent(this@HomeDashboardActivity, ActivityPrivacyPolicy::class.java)
                    startActivity(privacyPolicy)
                    /*    val howToPlay = Intent(this@HomeDashboardActivity, easyTwo::class.java)
                        howToPlay.putExtra(getString(R.string.f1bmain_activity), 2)
                        startActivity(howToPlay)*/
                }

                R.id.contactUs -> {
                    // val contactUs = Intent(this@HomeDashboardActivity, ActivitySupport::class.java)
                    val contactUs =
                        Intent(this@HomeDashboardActivity, ActivitySupport::class.java)
                    startActivity(contactUs)
                    /*    val howToPlay = Intent(this@HomeDashboardActivity, easyTwo::class.java)
                        howToPlay.putExtra(getString(R.string.f1bmain_activity), 2)
                        startActivity(howToPlay)*/
                }

                R.id.rateUs -> {
                    val url =
                        "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }

                R.id.shareApp -> {
                    try {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                        var shareMessage = "\nLet me recommend you this application\n\n"
                        shareMessage =
                            """
                        ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                        
                        
                        """.trimIndent()
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    } catch (e: Exception) {
                        //e.toString();
                    }
                }

                R.id.logout -> {
                    logout()
                }
            }
            true
        })
    }


    private fun logout() {
        MyApplication.tinyDB.clear()
        val contactUs = Intent(this@HomeDashboardActivity, ActivityLogin::class.java)
        startActivity(contactUs)
        finish()
    }
    private fun observeDashboardList() {

        viewModel.RequestDashBoardList.observe(this) {

            MyApplication.ProgressBar(this@HomeDashboardActivity, it is Resource.Loading)

            when (it) {

                is Resource.Success -> {

                    if (it.value.status) {

                        arr = ArrayList()

                        arr = it.value.data.filter {
                            it.active == true
                        } as ArrayList<ResponseDashBoardListItem>

                        setHomeUserAdapter()
                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this@HomeDashboardActivity,
                    retry = { getDashboardList() }
                )

                is Resource.Loading -> {}
            }
        }
    }
    fun getDashboardList() {

        viewModel.RequestDashBoardList(
            marketType = "normal"
        )

    }

    private fun setHomeUserAdapter() {
        val adapter = AdapterHome(this, arr, this, isBetting)
        dataBinding.rvHome.setHasFixedSize(true)
        dataBinding.rvHome.adapter = adapter
        dataBinding.rvHome.layoutManager = LinearLayoutManager(this)

    }
    override fun onItemClickBazar(position: ResponseDashBoardListItem, rlhead: RelativeLayout) {

        if (position.marketStatus == true) {

            if (!isBetting) {
                return
            }

            val status = checkTimeStatus(
                position.marketOpeningTime!!,
                position.marketClosingTime!!
            )

            when (status) {

                // Before Open Time
                1 -> {
                    startActivity(
                        Intent(this, ActivityIndashboard::class.java)
                            .putExtra(Constants.marketID, position.id)
                            .putExtra(Constants.marketType, position.marketType)
                            .putExtra(Constants.openTime, position.marketOpeningTime)
                            .putExtra(Constants.closeTime, position.marketClosingTime)
                            .putExtra(Constants.showALl, "no")
                    )
                }

                // Market Running
                2 -> {
                    startActivity(
                        Intent(this, ActivityIndashboard::class.java)
                            .putExtra(Constants.marketID, position.id)
                            .putExtra(Constants.marketType, position.marketType)
                            .putExtra(Constants.openTime, position.marketOpeningTime)
                            .putExtra(Constants.closeTime, position.marketClosingTime)
                            .putExtra(Constants.showALl, "yes")
                    )
                }

                // Market Closed
                3 -> {
                    openPopup("Today's time is over for this market.")
                }
            }

        } else {

            ObjectAnimator.ofFloat(
                rlhead,
                "translationX",
                0f,
                25f,
                -25f,
                25f,
                -25f,
                15f,
                -15f,
                6f,
                -6f,
                0f
            ).setDuration(700).start()

            vibrate()
        }
    }
/*
    override fun onItemClickBazar(position: ResponseDashBoardListItem, rlhead: RelativeLayout) {
        if (position.marketStatus!!.equals(true)) {
            Log.e("not==>>", "not")
             startActivity(
                Intent(this@HomeDashboardActivity, ActivityIndashboard::class.java).putExtra(
                    Constants.marketID,
                    position.id
                ).putExtra(Constants.marketType, position.marketType)
                    .putExtra(Constants.openTime, position.marketOpeningTime)
                    .putExtra(Constants.closeTime, position.marketClosingTime)
            )

        } else {
            Log.e("notvibrate==>>", "not")

            ObjectAnimator.ofFloat(
                rlhead,
                "translationX",
                0f,
                25f,
                -25f,
                25f,
                -25f,
                15f,
                -15f,
                6f,
                -6f,
                0f
            ).setDuration(700).start()
            vibrate()
        }
    }
*/

    fun checkTimeStatus(startTime: String, endTime: String): Int {

        val dateFormat = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
        val currentTime = dateFormat.format(java.util.Date())

        return try {

            val startDate = dateFormat.parse(startTime)
            val endDate = dateFormat.parse(endTime)
            val currentTimeDate = dateFormat.parse(currentTime)

            when {
                currentTimeDate.before(startDate) -> 1
                currentTimeDate.after(endDate) -> 3
                else -> 2
            }

        } catch (e: Exception) {
            -1
        }
    }
    fun vibrate() {
        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this@HomeDashboardActivity.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            this@HomeDashboardActivity.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vib.vibrate(500)
        }
    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@HomeDashboardActivity, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {

                         val headerView = dataBinding.navigationView.getHeaderView(0)
                        val tvUserName = headerView.findViewById<TextView>(R.id.tvusername)
                        val tvPhone = headerView.findViewById<TextView>(R.id.tvphone)
                        tvUserName.text = it.value.data.firstName.toString()
                        tvPhone.text = it.value.data.phoneNumber.toString()
                        dataBinding.tvcois.text = it.value.data.totalAmount.toString()
                        isUserStatus = it.value.data.userStatus!!
                        isBetting = it.value.data.betting!!
                        getImageSlider()
                        if (isBetting == false) {
                            dataBinding.rvHome.visibility = View.GONE
                            dataBinding.btnStartQuiz.visibility = View.VISIBLE
                            dataBinding.cvgif.visibility = View.VISIBLE
                            Glide.with(this)
                                .asGif()
                                .load(R.drawable.dumm)
                                .into(dataBinding.ivGif)

                            dataBinding.aaa.visibility = View.GONE
                            dataBinding.tvcois.visibility = View.GONE
                            dataBinding.ivwallet.visibility = View.GONE
                            dataBinding.tvAnnouncement.visibility = View.GONE
                            menu.findItem(R.id.walletStatement).isVisible = false
                            menu.findItem(R.id.game_withdrwallist).isVisible = false
                            menu.findItem(R.id.game_transferhistory).isVisible = false
                            menu.findItem(R.id.bidHistory).isVisible = false
                            menu.findItem(R.id.winHistory).isVisible = false
                            menu.findItem(R.id.game_values).isVisible = false
                            menu.findItem(R.id.how_to_learn).isVisible = false
                            menu.findItem(R.id.shareApp).isVisible = false
                            menu.findItem(R.id.rateUs).isVisible = false



                        } else {
                            dataBinding.rvHome.visibility = View.VISIBLE
                            dataBinding.btnStartQuiz.visibility = View.GONE
                            dataBinding.cvgif.visibility = View.GONE
                            dataBinding.tvAnnouncement.visibility = View.VISIBLE
                            dataBinding.aaa.visibility = View.VISIBLE
                            dataBinding.tvcois.visibility = View.VISIBLE
                            dataBinding.ivwallet.visibility = View.VISIBLE
                            menu.findItem(R.id.walletStatement).isVisible = true
                            menu.findItem(R.id.game_withdrwallist).isVisible = true
                            menu.findItem(R.id.game_transferhistory).isVisible = true
                            menu.findItem(R.id.bidHistory).isVisible = true
                            menu.findItem(R.id.winHistory).isVisible = true
                            menu.findItem(R.id.game_values).isVisible = true
                            menu.findItem(R.id.how_to_learn).isVisible = true
                            menu.findItem(R.id.shareApp).isVisible = true
                            menu.findItem(R.id.rateUs).isVisible = true


                        }
                        if (isUserStatus.equals(false)) {
                            MyApplication.tinyDB.clear()
                            val contactUs =
                                Intent(this@HomeDashboardActivity, ActivityLogin::class.java)
                            startActivity(contactUs)
                            finish()
                        }
                         getDashboardList()
                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this@HomeDashboardActivity,
                    retry = { getUserList() })

                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    fun getImageSlider() {
        viewModel.getImageSlider.observe(this, Observer {
            MyApplication.ProgressBar(this@HomeDashboardActivity, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        /* arrImageSlider = ArrayList()
                         arrImageSlider = it.value.data
                         init()*/
                        arrImageSlider = ArrayList()

                        if (isBetting == false) {
                            val staticBanner1 = ResponseImageSlider(
                                imageUrl = "https://static.vecteezy.com/system/resources/thumbnails/067/618/488/small/quiz-word-on-yellow-speech-bubble-free-vector.jpg"
                            )
                            val staticBanner2 = ResponseImageSlider(
                                imageUrl = "https://i.pinimg.com/736x/67/68/01/676801edfff1985c7e93af9ee88adb9a.jpg"
                            )

                            arrImageSlider.add(staticBanner1)
                            arrImageSlider.add(staticBanner2)

                        } else {
                            arrImageSlider = it.value.data
                        }

                        init()
                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this@HomeDashboardActivity,
                    retry = { getImageSlider() })

                is Resource.Loading -> {}
            }
        })
        viewModel.getImageSlider(
        )

    }

    private fun init() {
        dataBinding.viewPager.adapter =
            AdapterImageSlider(this@HomeDashboardActivity, arrImageSlider)

        dataBinding.indicator.setViewPager(dataBinding.viewPager)
        //NUM_PAGES = arrImageSlider.size
        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == arrImageSlider.size) {
                currentPage = 0
            }
            dataBinding.viewPager.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

    }

    fun getInformation() {
        viewModel.getInformation.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.e("Success==>>>qq", "suceess")

                    if (it.value.status) {
                        try {
                            dataBinding.tvAnnouncement.text =
                                it.value.data[0].information!!.popUpMessage!!.message
                            dataBinding.tvAnnouncement.isSelected = true
                            popUpMessage = it.value.data[0].information!!.popUpMessage!!.message!!
                            maintenece =
                                it.value.data[0].information!!.appMaintanence!!.status.toString()
                            if (maintenece.equals("true")) {
                                openPopup(popUpMessage)
                            }
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

                is Resource.Loading -> {}
            }
        })


        dataBinding.apply {
            viewModel.getInformation(
            )
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

                is Resource.Loading -> {}
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

    fun callBtn() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

    private fun observeMerchant() {
        viewModel.getMerchant.observe(this) { response ->
            MyApplication.ProgressBar(this, response is Resource.Loading)

            when (response) {
                is Resource.Success -> {
                    dataBinding.addpointsItem.isEnabled = true
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
                    handleApiError(response, dataBinding.root, this)
                }

                is Resource.Loading -> {}
            }
        }
    }
}
