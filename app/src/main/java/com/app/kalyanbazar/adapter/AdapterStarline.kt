package com.app.kalyanbazar.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.AdapterStarlineBinding
import com.app.kalyanbazar.model.response.ResponseStarline

class AdapterStarline (
     private val activity: Activity,
     var list: ArrayList<ResponseStarline>,
     private val onClick: onClicklistBazar,
) : RecyclerView.Adapter<AdapterStarline.ViewResource>() {
    interface onClicklistBazar {
        fun onItemClickBazar(position: ResponseStarline, rlhead: RelativeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewResource {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_starline, parent, false)
        return ViewResource(view)
    }

    override fun onBindViewHolder(holder: ViewResource, position: Int) {


        Log.e("MarketPoint==>",""+list[position].marketName)

        holder.binding.eventNumber.text = list[position].openPanaResult
        holder.binding.openingTime.text = list[position].marketName


         if (list[position].marketStatus!!.equals(true)){
             if (list[position].openingStatus!!.equals(true)) {
                 val rotate = AnimationUtils.loadAnimation(activity, R.anim.round)
                 holder.binding.eventStatus.startAnimation(rotate)
                 holder.binding.eventStatus.setImageResource(R.drawable.play)
                 holder.itemView.setOnClickListener {
                     onClick.onItemClickBazar(list[position],holder.binding.rlhead)

                 }
             }
              }else{
             holder.binding.eventStatus.setImageResource(R.drawable.close)
                }
        val animation = AnimationUtils.loadAnimation(activity, R.anim.dd)
        holder.binding.eventNumber.setAnimation(animation)




    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewResource internal constructor(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {


        var binding: AdapterStarlineBinding = DataBindingUtil.bind(itemView!!)!!

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}