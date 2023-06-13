package com.app.kalyanbazar.activity

import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kalyanbazar.R
import com.app.kalyanbazar.adapter.AdapterBidHistory
import com.app.kalyanbazar.databinding.ActivityBidHistoryBinding
import com.app.kalyanbazar.model.User
import com.app.kalyanbazar.utils.BaseActivity
import com.app.kalyanbazar.utils.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityBidHistory : BaseActivity<ActivityBidHistoryBinding>() {

    var strHistory: Int = 0
    override fun getLayoutResId(): Int = R.layout.activity_bid_history

    override fun setupViews() {
        strHistory = intent.getIntExtra(getString(R.string.history), 0)

        dataBinding.apply {
            if (strHistory==100){
                toolbar.tvTitle.text="Win History"
            }else if (strHistory==200){
                toolbar.tvTitle.text="Bid History"
            }else{
                toolbar.tvTitle.text="Win History"
            }
          //  toolbar.tvTitle.text="Bid History"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }

            fromDate.text=Helper.getCurrentDate()
            toDate.text=Helper.getCurrentDate()


            fromDate.setOnClickListener {
                Helper.DatePickerDialogBoxAll(
                    this@ActivityBidHistory, this@ActivityBidHistory,
                    dataBinding.fromDate
                )
            }
                toDate.setOnClickListener {
                    Helper.DatePickerDialogBoxAll(
                        this@ActivityBidHistory, this@ActivityBidHistory,
                        dataBinding.fromDate
                    )
            }


            submitbidhistory.setOnClickListener {

                setAdapter()
            }
           /* */
        }
    }


    override fun setupViewsOnResume() {
     }

    fun setAdapter(){
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this@ActivityBidHistory, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val users = ArrayList<User>()

        //adding some dummy data to the list
        users.add(User("Welcome Bnous", "2023-06-01 10:20:09","Milan Morning","10:20 AM","12:20 PM","220-47-223","Teen Bazar"))
        users.add(User("New Bonus", "2023-06-03 10:20:09","Rudrakh Morning","10:20 AM","12:20 PM","120-47-253","Char Bazar"))
        users.add(User("Joining Bonus", "2023-06-08 10:20:09","Kalyan Morning","10:20 AM","12:20 PM","820-47-223","Five Bazar"))
        users.add(User("Logout Bonus Also", "2023-06-12 10:20:09","Madhur Morning","10:20 AM","12:20 PM","720-47-423","Six Bazar"))

        //creating our adapter
        val adapter = AdapterBidHistory(this,users)

        //now adding the adapter to recyclerview
        dataBinding.recyclerView.adapter = adapter

    }
}