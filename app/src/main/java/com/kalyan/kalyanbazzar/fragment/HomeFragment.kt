package com.kalyan.kalyanbazzar.fragment

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalyan.kalyanbazzar.BuildConfig
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.activity.*
import com.kalyan.kalyanbazzar.adapter.AdapterHome
import com.kalyan.kalyanbazzar.adapter.AdapterImageSlider
import com.kalyan.kalyanbazzar.data.network.Resource
import com.kalyan.kalyanbazzar.databinding.FragmentHomeBinding
import com.kalyan.kalyanbazzar.model.User
import com.kalyan.kalyanbazzar.model.response.ResponseDashBoardListItem
import com.kalyan.kalyanbazzar.model.response.ResponseImageSlider
import com.kalyan.kalyanbazzar.utils.*
import com.kalyan.kalyanbazzar.utils.MyApplication.Companion.toast
import com.kalyan.kalyanbazzar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), AdapterHome.onClicklistBazar {
    private val viewModel by viewModels<HomeViewModel>()
    var arr: ArrayList<ResponseDashBoardListItem> = ArrayList()
    var arrImageSlider: ArrayList<ResponseImageSlider> = ArrayList()
    val users = ArrayList<User>()
    private var currentPage = 0
    private var NUM_PAGES = 0
    var isBetting = false
    var isUserStatus = false
    var inBetween: Boolean = true
    var currenttime: String = ""
    var popUpMessage: String = ""
    var maintenece: String = ""
    var phoneNumber: String = ""




    //fragment_home
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun setupViews() {
        dataBinding.apply {
            // contTop.ivBack.visibility = View.GONE
            getImageSlider()
            getContactUs()
            getUserList()
            getInformation()
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
                val addfund = Intent(requireActivity(), ActivityAddPoint::class.java)
                startActivity(addfund)
            }

            withdrawItem.setOnClickListener {
                val withdrawPoints = Intent(requireActivity(), ActivityWithdrawFund::class.java)
                startActivity(withdrawPoints)
            }

            starlineItem.setOnClickListener {
                val intent = Intent(requireActivity(), ActivityStarline::class.java)
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

    override fun setupViewsOnResume() {
    }

    //

//getImageSlider
    private fun getDashboardList(isBetting: Boolean) {
        viewModel.RequestDashBoardList.observe(this, Observer {
            MyApplication.ProgressBar(requireActivity(), it is Resource.Loading)
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
                    activity = requireActivity(),
                    retry = { getDashboardList(this.isBetting) })

                is Resource.Loading -> {}
            }
        })
        viewModel.RequestDashBoardList(
            marketType = "normal"
        )
    }
     private fun setHomeUserAdapter(isBetting: Boolean) {
        val adapter = AdapterHome(requireActivity(), arr, this, isBetting)
        dataBinding.recRecyclerView.setHasFixedSize(true)
        dataBinding.recRecyclerView.adapter = adapter
        dataBinding.recRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

    }

    private fun getImageSlider() {
        viewModel.getImageSlider.observe(this, Observer {
            MyApplication.ProgressBar(requireActivity(), it is Resource.Loading)
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
                    activity = requireActivity(),
                    retry = { getImageSlider() })

                is Resource.Loading -> {}
            }
        })
        viewModel.getImageSlider(
        )
    }

    private fun setImageAdapter() {
        val adapter = AdapterImageSlider(requireActivity(), arrImageSlider)
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
                requireActivity().toast("You are Not Verified")
                return
            }

            currenttime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            Log.e("Currenttime", "time--->>>" + currenttime)
            val status = checkTimeStatus(position.marketOpeningTime!!, position.marketClosingTime!!)
            Log.e("showOpen", "showOpen==>>status:" + status)
            when (status) {
                1 -> startActivity(
                    Intent(
                        requireActivity(),
                        ActivityIndashboard::class.java
                    ).putExtra(Constants.marketID, position.id)
                        .putExtra(Constants.marketType, position.marketType)
                        .putExtra(Constants.showALl, "no")
                )

                2 -> startActivity(
                    Intent(
                        requireActivity(),
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

    fun vibrate() {
        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                requireActivity().getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            requireActivity().getSystemService(VIBRATOR_SERVICE) as Vibrator
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
            MyApplication.ProgressBar(requireActivity(), it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {


                        isBetting = it.value.data.betting!!
                        isUserStatus = it.value.data.userStatus!!
                        if (isBetting.equals(false)) {
                            dataBinding.apply {
                                aaa.visibility=View.GONE
                                withdrawItem.visibility = View.GONE
                                addpointsItem.visibility = View.GONE
                                starlineItem.visibility = View.GONE
                                //   dsds
                            }
                        } else {
                            dataBinding.apply {
                                aaa.visibility=View.VISIBLE
                                withdrawItem.visibility = View.VISIBLE
                                addpointsItem.visibility = View.VISIBLE
                                starlineItem.visibility = View.VISIBLE
                            }
                        }

                        if (isUserStatus.equals(false)) {
                            MyApplication.tinyDB.clear()
                            val contactUs = Intent(requireActivity(), ActivityLogin::class.java)
                            startActivity(contactUs)
                            requireActivity().finish()
                        }
                     //   getDashboardList(isBetting)

                    }
                }

                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = requireActivity(),
                    retry = {
                       // getUserList()
                    })

                is Resource.Loading -> {}
            }
        })
        viewModel.getUserList(
            userID = MyApplication.tinyDB.getInt(Constants.SharedPref.OWNER_ID, -1)
        )

    }

    fun openPopup(popUpMessage: String) {
        val builder = AlertDialog.Builder(requireActivity())
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
        val dialog = Dialog(requireActivity())
        val li =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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

    private fun successdialognew(maintenece: String) {
        val btnconfirm: TextView
        val placed: TextView
        val succesfull: TextView
        val photo: ImageView
        val dialog = Dialog(requireActivity())
        val li =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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

    fun getInformation() {
        viewModel.getInformation.observe(requireActivity(), Observer {
            MyApplication.ProgressBar(requireActivity(), it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        try {
                            popUpMessage = it.value.data[0].information!!.popUpMessage!!.message.toString()
                           /* if (it.value.data[0].information!!.appMaintanence!!.status == true) {
                                maintenece =
                                    it.value.data[0].information!!.appMaintanence!!.message.toString()
                                successdialognew(maintenece)
                            } else {
                            }*/

                            dataBinding.apply {
                                tvhelp.text=popUpMessage
                                tvhelp.isSelected=true
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
                    activity = requireActivity(), retry = {
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
        viewModel.getContactUs.observe(requireActivity(), Observer {
            MyApplication.ProgressBar(requireActivity(), it is Resource.Loading)
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
                    activity = requireActivity(), retry = {

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
