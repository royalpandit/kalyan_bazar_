package com.app.kalyanbazar.activity

import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.ActivityHomeBinding
import com.app.kalyanbazar.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {


    override fun getLayoutResId(): Int = R.layout.activity_home

    override fun setupViews() {
        dataBinding.apply {

          //  setAdapter()
        }
     }

    override fun setupViewsOnResume() {
     }


 /*   fun setAdapter(){
        dataBinding.recRecyclerView.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val users = ArrayList<User>()

        //adding some dummy data to the list
        users.add(User("Welcome Bnous", "2023-06-01 10:20:09","Milan Morning","10:20 AM","12:20 PM","220-47-223","Teen Bazar"))
        users.add(User("New Bonus", "2023-06-03 10:20:09","Rudrakh Morning","10:20 AM","12:20 PM","120-47-253","Teen Bazar"))
        users.add(User("Joining Bonus", "2023-06-08 10:20:09","Kalyan Morning","10:20 AM","12:20 PM","820-47-223","Teen Bazar"))
        users.add(User("Logout Bonus Also", "2023-06-12 10:20:09","Madhur Morning","10:20 AM","12:20 PM","720-47-423","Teen Bazar"))

        //creating our adapter
        val adapter = AdapterHome(this, users, this)

        //now adding the adapter to recyclerview
        dataBinding.recRecyclerView.adapter = adapter


    }*/
}