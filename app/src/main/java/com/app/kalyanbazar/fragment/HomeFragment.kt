package com.app.kalyanbazar.fragment


import android.animation.ObjectAnimator
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.*
import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kalyanbazar.BuildConfig
import com.app.kalyanbazar.R
import com.app.kalyanbazar.activity.*
import com.app.kalyanbazar.adapter.AdapterHome
import com.app.kalyanbazar.adapter.AdapterImageSlider
import com.app.kalyanbazar.data.network.Resource
import com.app.kalyanbazar.databinding.FragmentHomeBinding
import com.app.kalyanbazar.model.User
import com.app.kalyanbazar.model.response.ResponseDashBoardListItem
import com.app.kalyanbazar.model.response.ResponseImageSlider
import com.app.kalyanbazar.utils.*
import com.app.kalyanbazar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), AdapterHome.onClicklistBazar {
    private val viewModel by viewModels<HomeViewModel>()
    var arr: ArrayList<ResponseDashBoardListItem> = ArrayList()
    var arrImageSlider: ArrayList<ResponseImageSlider> = ArrayList()
    val users = ArrayList<User>()
    private var currentPage = 0
    private var NUM_PAGES = 0

    //fragment_home
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun setupViews() {
        dataBinding.apply {
            // contTop.ivBack.visibility = View.GONE
            getImageSlider()
            getDashboardList()

            whatsappItem.setOnClickListener {

                val url = "https://api.whatsapp.com/send?phone=" + "+918107116566"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
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
    /* fun setAdapter(){

         users.add(User("Welcome Bnous", "2023-06-01 10:20:09","Milan Morning","10:20 AM","12:20 PM","220-47-223","Teen Bazar"))
         users.add(User("New Bonus", "2023-06-03 10:20:09","Rudrakh Morning","10:20 AM","12:20 PM","120-47-253","Teen Bazar"))
         users.add(User("Joining Bonus", "2023-06-08 10:20:09","Kalyan Morning","10:20 AM","12:20 PM","820-47-223","Teen Bazar"))
         users.add(User("Logout Bonus Also", "2023-06-12 10:20:09","Madhur Morning","10:20 AM","12:20 PM","720-47-423","Teen Bazar"))
         users.add(User("Refer Bonus Also", "2023-06-12 10:20:09","Sapna Morning","10:20 AM","12:20 PM","720-47-423","Teen Bazar"))
         users.add(User("Double Bonus Also", "2023-06-12 10:20:09","Double Morning","10:20 AM","12:20 PM","720-47-423","Teen Bazar"))
         users.add(User("Iphone Bonus Also", "2023-06-12 10:20:09","Iphone Morning","10:20 AM","12:20 PM","720-47-423","Teen Bazar"))
         users.add(User("Small Bonus Also", "2023-06-12 10:20:09","Small Morning","10:20 AM","12:20 PM","720-47-423","Teen Bazar"))
         users.add(User("Big Bonus Also", "2023-06-12 10:20:09","Big Morning","10:20 AM","12:20 PM","720-47-423","Teen Bazar"))
         users.add(User("Triple Bonus Also", "2023-06-12 10:20:09","Triple Morning","10:20 AM","12:20 PM","720-47-423","Teen Bazar"))


         val adapter = AdapterHome(requireActivity(), users, this)
         dataBinding.recRecyclerView.setHasFixedSize(true)
         dataBinding.recRecyclerView.adapter = adapter
         dataBinding.recRecyclerView.layoutManager = LinearLayoutManager(requireActivity())


     }*/
//getImageSlider
    private fun getDashboardList() {

        viewModel.RequestDashBoardList.observe(this, Observer {
            MyApplication.ProgressBar(requireActivity(), it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        //  dataBinding.rvHome.hideShimmer()
                        //       dataBinding.rvHome.hideShimmerAdapter()
                        arr = ArrayList()
                        arr = it.value.data

                        setHomeUserAdapter()
                    }
                }
                is Resource.Failure -> handleApiError(it,
                    dataBinding.root,
                    activity = requireActivity(),
                    retry = { getDashboardList() })
            }
        })
        viewModel.RequestDashBoardList(
            marketType = "normal"
        )
    }

    private fun setHomeUserAdapter() {

        val adapter = AdapterHome(requireActivity(), arr, this)
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
                    retry = { getDashboardList() })
            }
        })
        viewModel.getImageSlider(

        )
    }

    private fun setImageAdapter() {

        val adapter = AdapterImageSlider(requireActivity(), arrImageSlider)
        dataBinding.viewpager.setAdapter(adapter);
// Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            dataBinding.viewpager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

    }

    override fun onItemClickBazar(position: ResponseDashBoardListItem, rlhead: RelativeLayout) {
        if (position.marketStatus!!.equals(true)) {
            Log.e("not==>>", "not")
           // ActivityIndashboard
            startActivity(Intent(requireActivity(), ActivityIndashboard::class.java).putExtra(
                Constants.marketID, position.id).putExtra(
                Constants.marketType, position.marketType) )


        } else {
            Log.e("notvibrate==>>", "not")

            ObjectAnimator.ofFloat(rlhead, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f).setDuration(700).start()
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

}