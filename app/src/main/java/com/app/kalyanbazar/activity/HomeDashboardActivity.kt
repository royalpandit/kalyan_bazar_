package com.app.kalyanbazar.activity

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kalyanbazar.BuildConfig
import com.app.kalyanbazar.R
import com.app.kalyanbazar.adapter.AdapterHome
import com.app.kalyanbazar.adapter.AdapterImageSlider
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.ActivityHomeDashboardBinding
import com.app.kalyanbazar.fragment.HomeFragment
import com.app.kalyanbazar.model.response.ResponseDashBoardListItem
import com.app.kalyanbazar.model.response.ResponseImageSlider
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication
import com.app.kalyanbazar.utils.MyApplication.Companion.toast
import com.app.kalyanbazar.utils.handleApiError
import com.app.kalyanbazar.viewModel.HomeViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.thread

@AndroidEntryPoint
class HomeDashboardActivity : BaseActivity<ActivityHomeDashboardBinding>() , AdapterHome.onClicklistBazar{
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
            menu = navigationView.getMenu()
            confToolbar()

            onCLick()
           getUserList()
            profile()
            getContactUs()
            getImageSlider()
            getInformation()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
           /* fragment = HomeFragment()
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment!!)
            ft.commit()*/
            /*  swipeRefLyt.setOnRefreshListener {
               swipeRefLyt.isRefreshing = false
Log.e("tag","Tag1")
               // getDashboardList()
               getUserList()
           }*/

        }

    }
    fun onCLick(){
        dataBinding.apply {
            whatsappItem.setOnClickListener {
                /*   val url = "https://api.whatsapp.com/send?phone=" + "+918107116566"
                   val i = Intent(Intent.ACTION_VIEW)
                   i.data = Uri.parse(url)
                   startActivity(i)*/
                openPopup(popUpMessage)
            }

            rlwhatsups.setOnClickListener {
                /*   val url = "https://api.whatsapp.com/send?phone=" + "+918107116566"
                   val i = Intent(Intent.ACTION_VIEW)
                   i.data = Uri.parse(url)
                   startActivity(i)*/
                whatsAppBtn()
            }

            rlcalls.setOnClickListener {
                /*   val url = "https://api.whatsapp.com/send?phone=" + "+918107116566"
                   val i = Intent(Intent.ACTION_VIEW)
                   i.data = Uri.parse(url)
                   startActivity(i)*/
                callBtn()
            }

            addpointsItem.setOnClickListener {
                val addfund = Intent(this@HomeDashboardActivity, ActivityAddPoint::class.java)
                startActivity(addfund)
            }

            withdrawItem.setOnClickListener {
                val withdrawPoints = Intent(this@HomeDashboardActivity, ActivityWithdrawFund::class.java)
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

        Log.e("DomeDOme===>>>>>>>>","DomeDOme===>>>>>>>>"+"==>>>>>DOMEDOME")
    }
    override fun setupViewsOnResume() {
        Log.e("DomeDOme===>>>>>>>>","DomeDOme===>>>>>>>>"+"==>>>>>DOMEDOMEnewnext")

        // getUserList()
        profile()
        getContactUs()
        getImageSlider()
        getInformation()
        dataBinding.swipeRefLyt.setOnRefreshListener {
            dataBinding.swipeRefLyt.isRefreshing = false
            Log.e("tag","Tag1")
            Log.e("DomeDOme===>>>>>>>>","DomeDOme===>>>>>>>>"+"==>>>>>DOMEDOMEnewMultiple==>>")

            // getDashboardList()
            getUserList()
        }
       /* totalAmount =   MyApplication.tinyDB.getString(Constants.TOTAL_AMO, "0.0").toString()
      //  Toast.makeText(this@HomeDashboardActivity,"APiHItHIt $totalAmount",Toast.LENGTH_LONG)

        dataBinding.tvcois.text = totalAmount
        //toast("Api HIt HIt$totalAmount")


        dataBinding.swipeRefLyt.setOnRefreshListener {
            dataBinding.swipeRefLyt.isRefreshing = false
          //  Log.e("tag","BoonBOOm")
            fragment = HomeFragment()
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment!!)
            ft.commit()
        }*/
    }


    private fun confToolbar() {
        // var fragment: Fragment? = null
      //  profile()
        dataBinding.toolbar.setNavigationOnClickListener(View.OnClickListener { v: View? ->
            dataBinding.drawerLayout.openDrawer(
                GravityCompat.START
            )
        })
        dataBinding.navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item: MenuItem ->

            when (item.itemId) {
                R.id.home -> {

                   // fragment = HomeFragment()
                    // val profile = Intent(this@HomeDashboardActivity, HomeActivity::class.java)
                    //startActivity(profile)
                    /*supportFragmentManager
                         .beginTransaction()
                         .replace(containerId, fragment, fragment::class.java.simpleName)
                         .commit() */
                    dataBinding.drawerLayout.closeDrawers()
                }

                R.id.seeFullProfile -> {
                    val profile = Intent(this@HomeDashboardActivity, ActivityProfile::class.java)
                    startActivity(profile)
                }

                R.id.addFunds -> {
                    val addfund = Intent(this@HomeDashboardActivity, ActivityAddPoint::class.java)
                    startActivity(addfund)
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

                R.id.contactUs -> {
                    // val contactUs = Intent(this@HomeDashboardActivity, ActivitySupport::class.java)
                    val contactUs =
                        Intent(this@HomeDashboardActivity, ActivityContactUs::class.java)
                    startActivity(contactUs)




                }

                R.id.shareWithFriends -> {
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    /*   sendIntent.putExtra(
                           Intent.EXTRA_TEXT,
                           """
                           Hello there !!

                           Check out this amazing and most trusted Kalyan Bazar

                           https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                           """.trimIndent()
                       )*/
                    sendIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        """ Hi! I'm inviting you to use Paytm. it's simple and secure way to make satta  """ + """""" +
                                """
                        or click my link below to get an exclsive welcome reward when you make your first UPI Payment via Add fund
                        Switch to india's highest rated  and most trusted Kalyan Bazar
                        
                        https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                        """.trimIndent() + "" + """use my refrel Code""" + "-" + refrelID
                    )
                    sendIntent.type = "text/plain"
                    startActivity(sendIntent)
                }

                R.id.rateApp -> {
                    val url =
                        "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }

                R.id.changePassword -> {
                    val contactUs =
                        Intent(this@HomeDashboardActivity, ActivityChangePassword::class.java)
                    startActivity(contactUs)
                    /*  val arrayStrings = arrayOf<String>(
                          fifthAttemptThree.getRegistrationObject(
                              this@sixthAttemptEleven,
                              fifthAttemptThree.AUN
                          ), fifthAttemptThree.getLoginInToken(this@sixthAttemptEleven)
                      )
                      val changePassword =
                          Intent(this@sixthAttemptEleven, easyFourteen::class.java)
                      changePassword.putExtra(getString(R.string.chaf1bngePassword), 1)
                      changePassword.putExtra(getString(R.string.pn), arrayStrings)
                      startActivity(changePassword)*/
                }

                R.id.logout -> {
                    // LogOutDialog()
                    MyApplication.tinyDB.clear()
                    val contactUs = Intent(this@HomeDashboardActivity, ActivityLogin::class.java)
                    startActivity(contactUs)
                    finish()

                    dataBinding.drawerLayout.closeDrawers()
                }
            }
            dataBinding.drawerLayout.closeDrawers()
            true
        })


        //replacing the fragment
        if (fragment != null) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment!!)
            ft.commit()
        }
    }

    override fun onBackPressed() {
        if (dataBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            dataBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun profile() {
        viewModel.RequestProfile.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        Log.e("Success==>>>tokenqq", "suceess")
                        // MyApplication.tinyDB.putString(Constants.SharedPref.ACCESS_TOKEN, it.value.accessToken)
                        val tvUserName = dataBinding.navigationView.getHeaderView(0)
                            .findViewById<TextView>(R.id.tvusername)
                        val tvemail = dataBinding.navigationView.getHeaderView(0)
                            .findViewById<TextView>(R.id.tvemail)
                        val tvPhone = dataBinding.navigationView.getHeaderView(0)
                            .findViewById<TextView>(R.id.tvphone)
                        tvUserName.text = it.value.data.firstName/*+" "+it.value.data.lastName*/
                        tvemail.text = it.value.data.email
                        tvPhone.text = it.value.data.phoneNumber

                        refrelID = it.value.data.referralId.toString()
                        MyApplication.tinyDB.putInt(
                            Constants.SharedPref.OWNER_ID,
                            it.value.data.id!!
                        )
                        MyApplication.tinyDB.putString(
                            Constants.SharedPref.MOBILE,
                            it.value.data.phoneNumber.toString()
                        )
                        MyApplication.tinyDB.putString(
                            Constants.SharedPref.EMAIL,
                            it.value.data.email.toString()
                        )
                        MyApplication.tinyDB.putString(
                            Constants.SharedPref.USER_NAME,
                            it.value.data.firstName.toString() + " " + it.value.data.lastName.toString()
                        )
                        // dataBinding.navigationView.getHeaderView(0).findViewById<MaterialTextView>(R.id.tvusername)
                        // dataBinding.navigationView.
                        //  dataBinding.navigationView.getHeaderView(0).findViewById<MaterialTextView>(R.id.tvemail)

                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this, retry = {
                    }
                )
            }
        })


        dataBinding.apply {
            viewModel.RequestProfile(
            )
        }

    }

    private fun getUserList() {
        viewModel.getUserList.observe(this, Observer {
            MyApplication.ProgressBar(this@HomeDashboardActivity, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        //   dataBinding.toolbar.setTitle(it.value.data.totalAmount.toString())
                       // = it.value.data.totalAmount.toString()

                       /* MyApplication.tinyDB.putString(Constants.TOTAL_AMO,it.value.data.totalAmount.toString())

                        totalAmount = MyApplication.tinyDB.getString(Constants.TOTAL_AMO, "0.0").toString()

                        dataBinding.tvcois.text = totalAmount*/
                        dataBinding.tvcois.text = it.value.data.totalAmount.toString()
                        Log.e("DomeDOme===>>>>>>>>","DomeDOme===>>>>>>>>"+"==>>>>>DOMEDOMEnewMultiple==>>Multiple")

                        getDashboardList(isBetting)
                        isUserStatus = it.value.data.userStatus!!
                        isBetting = it.value.data.betting!!
                        if (isUserStatus.equals(false)){
                            MyApplication.tinyDB.clear()
                            val contactUs = Intent(this@HomeDashboardActivity, ActivityLogin::class.java)
                            startActivity(contactUs)
                            finish()
                        }
                        if (isBetting.equals(false)){
                            dataBinding.apply {
                                tvcois.visibility=View.GONE
                                ivwallet.visibility=View.GONE
                                 menu.findItem(R.id.walletStatement).setVisible(false)
                                menu.findItem(R.id.game_withdrwallist).setVisible(false)
                                menu.findItem(R.id.game_transferhistory).setVisible(false)
                                menu.findItem(R.id.game_values).setVisible(false)
                                menu.findItem(R.id.winHistory).setVisible(false)
                                menu.findItem(R.id.bidHistory).setVisible(false)
                                aaa.visibility=View.GONE
                                withdrawItem.visibility = View.GONE
                                addpointsItem.visibility = View.GONE
                                starlineItem.visibility = View.GONE
                            }
                        }else{
                            dataBinding.apply {
                                tvcois.visibility=View.VISIBLE
                                ivwallet.visibility=View.VISIBLE
                                menu.findItem(R.id.walletStatement).setVisible(true)
                                menu.findItem(R.id.game_transferhistory).setVisible(true)
                                menu.findItem(R.id.game_withdrwallist).setVisible(true)
                                menu.findItem(R.id.game_values).setVisible(true)
                                menu.findItem(R.id.winHistory).setVisible(true)
                                menu.findItem(R.id.bidHistory).setVisible(true)
                                aaa.visibility=View.VISIBLE
                                withdrawItem.visibility = View.VISIBLE
                                addpointsItem.visibility = View.VISIBLE
                                starlineItem.visibility = View.VISIBLE
                                /* addpointsItem.visibility=View.VISIBLE
                                 starlineItem.visibility=View.VISIBLE
 */
                            }
                        }

                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this@HomeDashboardActivity,
                    retry = {
                        getUserList()
                    })
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

   fun updatecoin(values: String) {


       this@HomeDashboardActivity?.runOnUiThread {
           totalAmount =   MyApplication.tinyDB.getString(Constants.TOTAL_AMO, "0.0").toString()

           dataBinding.tvcois.text = totalAmount
//Log.e("Hello======","Hello======")

          // Toast.makeText(this@HomeDashboardActivity,"APiHItHIt $totalAmount",Toast.LENGTH_LONG)

       }


   }


    fun getInformation() {
        viewModel.getInformation.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        try {
                            popUpMessage =
                                it.value.data[0].information!!.popUpMessage!!.message.toString()
                            dataBinding.apply {
                                tvhelp.text=popUpMessage
                                tvhelp.isSelected=true
                            }
                            if (it.value.data[0].information!!.appMaintanence!!.status == true) {
                                maintenece =
                                    it.value.data[0].information!!.appMaintanence!!.message.toString()
                                successdialognew(maintenece)
                            } else {
                            }

                          /*  dataBinding.apply {
                                tvhelp.text=popUpMessage
                                tvhelp.isSelected=true
                            }*/
                        } catch (ex: Exception) {
                        }
                        //   minFund=it.value.data[0].minDeposit!!.toInt()
                        //   maxFund=it.value.data[0].maxDeposit!!.toInt()

                    }
                }

                is Resource.Failure -> handleApiError(
                    it,
                    dataBinding.root,
                    activity = this@HomeDashboardActivity, retry = {
                    }
                )
            }
        })


        dataBinding.apply {
            viewModel.getInformation(
            )
        }

    }

    private fun successdialognew(maintenece: String) {
        val btnconfirm: TextView
        val placed: TextView
        val succesfull: TextView
        val photo: ImageView
        val dialog = Dialog(this@HomeDashboardActivity)
        val li = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.dialogue_succefull, null, false)
        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        v.background = resources.getDrawable(R.drawable.bg_radius)
        dialog.setContentView(v)

        btnconfirm = dialog.findViewById(R.id.playagainbtn)
        photo = dialog.findViewById(R.id.photo)
        succesfull = dialog.findViewById(R.id.succesfull)
        photo.visibility = View.GONE
        succesfull.visibility = View.VISIBLE
        placed = dialog.findViewById(R.id.placed)
        btnconfirm.text = "OK"
        succesfull.text = "महत्वपूर्ण सूचना"
        placed.text = maintenece
        btnconfirm.setOnClickListener {
            dialog.dismiss()
            //  finish()
        }
        dialog.show()

    }


    private fun getDashboardList(isBetting: Boolean) {
        viewModel.RequestDashBoardList.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()

                        arr = ArrayList()
                        arr.clear()
                        var arrTemp = it.value.data

                        for(data in arrTemp){
                            if(data.active == true){
                                arr.add(data)
                            }

                        }
                        setHomeUserAdapter(isBetting)

                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this,
                    retry = { getDashboardList(this.isBetting) })
            }
        })
        viewModel.RequestDashBoardList(
            marketType = "normal"
        )
    }
    private fun setHomeUserAdapter(isBetting: Boolean) {
        val adapter = AdapterHome(this@HomeDashboardActivity, arr, this, isBetting)
        dataBinding.recRecyclerView.setHasFixedSize(true)
        dataBinding.recRecyclerView.adapter = adapter
        dataBinding.recRecyclerView.layoutManager = LinearLayoutManager(this)

    }
    private fun getImageSlider() {
        viewModel.getImageSlider.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        arrImageSlider = ArrayList()
                        arrImageSlider = it.value.data

                        setImageAdapter()
                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = this,
                    retry = { getImageSlider() })
            }
        })
        viewModel.getImageSlider(
        )
    }
    private fun setImageAdapter() {
        val adapter = AdapterImageSlider(this, arrImageSlider)
        dataBinding.viewpager.adapter = adapter
// Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            dataBinding.viewpager.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClickBazar(position: ResponseDashBoardListItem, rlhead: RelativeLayout) {
        if (position.marketStatus!!.equals(true)) {
            Log.e("not==>>", "not")
            // ActivityIndashboard
            if (!isBetting) {
                toast("You are Not Verified")
                return
            }

            currenttime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            Log.e("Currenttime", "time--->>>" + currenttime)
            val status = checkTimeStatus(position.marketOpeningTime!!, position.marketClosingTime!!)
            Log.e("showOpen", "showOpen==>>status:" + status)
            when (status) {
                1 -> startActivity(
                    Intent(
                        this,
                        ActivityIndashboard::class.java
                    ).putExtra(Constants.marketID, position.id)
                        .putExtra(Constants.marketType, position.marketType)
                        .putExtra(Constants.showALl, "no")
                )

                2 -> startActivity(
                    Intent(
                        this,
                        ActivityIndashboard::class.java
                    ).putExtra(Constants.marketID, position.id)
                        .putExtra(Constants.marketType, position.marketType)
                        .putExtra(Constants.showALl, "yes")
                )

                3 -> successdialog() //requireActivity().toast("Today's time is over for this market.")
                else -> successdialog() //requireActivity().toast("Today's time is over for this market.")
            }

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
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkTimeStatus(startTime: String, endTime: String): Int {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = dateFormat.format(Date())

        try {
            val startDate = dateFormat.parse(startTime)
            val endDate = dateFormat.parse(endTime)
            val currentTimeDate = dateFormat.parse(currentTime)

            if (currentTimeDate.before(startDate)) {
                return 1
            } else if (currentTimeDate.after(endDate)) {
                return 3
            } else {
                return 2
            }

        } catch (e: Exception) {
            e.printStackTrace()
            // Handle parsing exceptions if any
        }
        // Default return value if an exception occurs
        return -1
    }
    private fun successdialog() {
        val btnconfirm: TextView
        val placed: TextView
        val succesfull: TextView
        val dialog = Dialog(this)
        val li =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.dialogue_succefull, null, false)
        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        v.background = resources.getDrawable(R.drawable.bg_radius)
        dialog.setContentView(v)

        btnconfirm = dialog.findViewById(R.id.playagainbtn)
        succesfull = dialog.findViewById(R.id.succesfull)
        succesfull.visibility = View.GONE
        placed = dialog.findViewById(R.id.placed)
        btnconfirm.text = "OK"
        placed.text = "Today's time is over for this market."
        btnconfirm.setOnClickListener {
            dialog.dismiss()
            //  finish()
        }
        dialog.show()

    }
    fun vibrate() {
        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            this.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vib.vibrate(500)
        }
    }

    fun getContactUs() {
        viewModel.getContactUs.observe(this, Observer {
            MyApplication.ProgressBar(this, it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.value.status) {
                        try {
                            phoneNumber=it.value.data.phoneNumber.toString()

                            Log.e("Phone","Phone===>>"+phoneNumber)
                        }catch (ex:Exception){

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
            }
        })


        dataBinding.apply {
            viewModel.getContactUs(

            )

        }

    }

    fun whatsAppBtn() {
        val url = "https://api.whatsapp.com/send?phone="+"+91"+ phoneNumber +"&text=" + URLEncoder.encode("", "UTF-8")
        Log.e("url","Whayupurl==>>>"+url)
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
    fun callBtn() {
        val intent = Intent(Intent.ACTION_DIAL);
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

}

