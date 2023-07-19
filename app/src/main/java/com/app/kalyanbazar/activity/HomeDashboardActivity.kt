package com.app.kalyanbazar.activity

 import android.content.Intent
 import android.net.Uri
 import android.util.Log
 import android.view.MenuItem
 import android.view.View
 import android.widget.TextView
 import android.widget.Toast
 import androidx.activity.viewModels
 import androidx.appcompat.app.ActionBarDrawerToggle
 import androidx.core.view.GravityCompat
 import androidx.fragment.app.Fragment
 import androidx.fragment.app.FragmentTransaction
 import androidx.lifecycle.Observer
 import com.app.kalyanbazar.BuildConfig
 import com.app.kalyanbazar.R
 import com.app.kalyanbazar.data.network.Resource
 import com.app.kalyanbazar.databinding.ActivityHomeDashboardBinding
 import com.app.kalyanbazar.fragment.HomeFragment
 import com.app.kalyanbazar.utils.BaseActivity
 import com.app.kalyanbazar.utils.Constants
 import com.app.kalyanbazar.utils.MyApplication
 import com.app.kalyanbazar.utils.handleApiError
 import com.app.kalyanbazar.viewModel.HomeViewModel
 import com.google.android.material.navigation.NavigationView
 import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeDashboardActivity : BaseActivity<ActivityHomeDashboardBinding>(){
    private val viewModel by viewModels<HomeViewModel>()
    var fragment: Fragment? = null
    var refrelID: String = ""
    lateinit var toggle: ActionBarDrawerToggle

    override fun getLayoutResId(): Int = R.layout.activity_home_dashboard

    override fun setupViews() {
        dataBinding.apply {

            setSupportActionBar(dataBinding.toolbar)
            val toggle = ActionBarDrawerToggle(
                this@HomeDashboardActivity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            dataBinding.drawerLayout.addDrawerListener(toggle)
           // toggle.syncState()
             dataBinding.navigationView.setItemIconTintList(null)


            confToolbar()
            profile()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            fragment = HomeFragment()
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment!!)
            ft.commit()


/*
            swipeRefLyt.setOnRefreshListener {

                fragment = HomeFragment()
                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                ft.replace(R.id.content_frame, fragment!!)
                ft.commit()
            }*/
        }

     }

    override fun setupViewsOnResume() {

     }
    private fun confToolbar() {
       // var fragment: Fragment? = null
        profile()
        dataBinding.toolbar.setNavigationOnClickListener(View.OnClickListener { v: View? ->
            dataBinding.drawerLayout.openDrawer(
                GravityCompat.START
            )
        })
        dataBinding.navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home ->{
                    fragment = HomeFragment()
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
                    Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()

                      val addfund = Intent(this@HomeDashboardActivity, ActivityAddPoint::class.java)
                     startActivity(addfund)
                }
                R.id.withdrawPoints -> {
                   val withdrawPoints = Intent(this@HomeDashboardActivity, ActivityWithdrawFund::class.java)
                    startActivity(withdrawPoints)
                }
                R.id.walletStatement -> {
                 val intent = Intent(this@HomeDashboardActivity, ActivityWalletStatement::class.java)
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
                R.id.game_values -> {
                   val gameRates = Intent(this@HomeDashboardActivity, ActivityGameRate::class.java)
                    gameRates.putExtra(getString(R.string.history), 1)
                    startActivity(gameRates)
                }
                R.id.how_to_learn -> {
                  /*  val howToPlay = Intent(this@HomeDashboardActivity, easyTwo::class.java)
                    howToPlay.putExtra(getString(R.string.f1bmain_activity), 2)
                    startActivity(howToPlay)*/
                }
                R.id.contactUs -> {
                   val contactUs = Intent(this@HomeDashboardActivity, ActivitySupport::class.java)
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
                        Intent.EXTRA_TEXT,""" Hi! I'm inviting you to use Paytm. it's simple and secure way to make satta  """+""""""+
                        """
                        or click my link below to get an exclsive welcome reward when you make your first UPI Payment via Add fund
                        Switch to india's highest rated  and most trusted Kalyan Bazar
                        
                        https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                        """.trimIndent()+""+"""use my refrel Code"""+"-"+refrelID
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
                 val contactUs = Intent(this@HomeDashboardActivity, ActivityChangePassword::class.java)
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
   /* override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            }
            R.id.seeFullProfile -> {
                Toast.makeText(this, "seeFullProfile", Toast.LENGTH_SHORT).show()
            }
            R.id.changePassword -> {
                Toast.makeText(this, "changePassword", Toast.LENGTH_SHORT).show()
            }
            R.id.walletStatement -> {
                Toast.makeText(this, "walletStatement", Toast.LENGTH_SHORT).show()
            }
            R.id.winHistory -> {
                Toast.makeText(this, "winHistory", Toast.LENGTH_SHORT).show()
            }
            R.id.game_values -> {
                Toast.makeText(this, "game_values", Toast.LENGTH_SHORT).show()
            }
            R.id.bidHistory -> {
                Toast.makeText(this, "bidHistory", Toast.LENGTH_SHORT).show()
            }
            R.id.how_to_learn -> {
                Toast.makeText(this, "how_to_learn", Toast.LENGTH_SHORT).show()
            }
            R.id.addFunds -> {
                Toast.makeText(this, "addFunds", Toast.LENGTH_SHORT).show()
            }
            R.id.withdrawPoints -> {
                Toast.makeText(this, "withdrawPoints", Toast.LENGTH_SHORT).show()
            }
            R.id.transferPoints -> {
                Toast.makeText(this, "transferPoints", Toast.LENGTH_SHORT).show()
            }
            R.id.contactUs -> {
                Toast.makeText(this, "contactUs", Toast.LENGTH_SHORT).show()
            }
            R.id.shareWithFriends -> {
                Toast.makeText(this, "shareWithFriends", Toast.LENGTH_SHORT).show()
            }
            R.id.rateApp -> {
                Toast.makeText(this, "rateApp", Toast.LENGTH_SHORT).show()
            }
            R.id.logout -> {
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
            }

        }
        dataBinding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }*/


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
                        val tvUserName =   dataBinding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvusername)
                        val tvemail =   dataBinding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvemail)
                        tvUserName.text=it.value.data.firstName+""+it.value.data.lastName
                        tvemail.text=it.value.data.email
                        refrelID=it.value.data.referralId.toString()
                        MyApplication.tinyDB.putInt(Constants.SharedPref.OWNER_ID, it.value.data.id!!)
                        MyApplication.tinyDB.putString(Constants.SharedPref.MOBILE, it.value.data.phoneNumber.toString()!!)
                        MyApplication.tinyDB.putString(Constants.SharedPref.EMAIL, it.value.data.email.toString()!!)
                        MyApplication.tinyDB.putString(Constants.SharedPref.USER_NAME, it.value.data.firstName.toString()!!+" "+it.value.data.lastName.toString()!!)

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
 }

