package com.app.kalyanbazar.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.AdapterHomeBinding
import com.app.kalyanbazar.model.response.ResponseDashBoardListItem
import com.romainpiel.shimmer.Shimmer
import com.romainpiel.shimmer.ShimmerTextView

//
class AdapterHome(
    private val activity: Activity,
    var list: ArrayList<ResponseDashBoardListItem>,
    private val onClick: onClicklistBazar,
) : RecyclerView.Adapter<AdapterHome.ViewResource>() {
    interface onClicklistBazar {
      fun onItemClickBazar(position: ResponseDashBoardListItem, rlhead: RelativeLayout)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewResource {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_home, parent, false)
        return ViewResource(view)
    }

    override fun onBindViewHolder(holder: ViewResource, position: Int) {


        Log.e("MarketPoint==>",""+list[position].marketName)

        holder.binding.eventNumber.text = list[position].marketCode
        holder.binding.openingTime.text = list[position].marketOpeningTime
        holder.binding.closingTime.text = list[position].marketClosingTime
        holder.binding.eventType.text = list[position].marketName
        val shimmer = Shimmer()

        shimmer.start<ShimmerTextView>( holder.binding.eventType)
        if (list[position].marketStatus!!.equals(true)){
            val rotate = AnimationUtils.loadAnimation(activity, R.anim.round)
            holder.binding.eventStatus.startAnimation(rotate)
            holder.binding.eventStatus.setImageResource(R.drawable.play)
            holder.binding.marketOpen.visibility=View.VISIBLE
            holder.binding.marketOpen.setText("Market is Running")
            holder.binding.marketOpen.setBackgroundColor(ContextCompat.getColor(activity, R.color.white))
            holder.binding.marketOpen.setTextColor(ContextCompat.getColor(activity, R.color.greendark))
        }else{
            holder.binding.marketOpen.visibility=View.VISIBLE
            holder.binding.eventStatus.setImageResource(R.drawable.close)
            holder.binding.marketOpen.setText("Market Closed")
            holder.binding.marketOpen.setBackgroundColor(ContextCompat.getColor(activity, R.color.white))
            holder.binding.marketOpen.setTextColor(ContextCompat.getColor(activity, R.color.red_200))
        }
        val animation = AnimationUtils.loadAnimation(activity, R.anim.dd)
        holder.binding.eventNumber.setAnimation(animation)


        holder.itemView.setOnClickListener {
            onClick.onItemClickBazar(list[position],holder.binding.rlhead)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewResource internal constructor(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {


        var binding: AdapterHomeBinding = DataBindingUtil.bind(itemView!!)!!

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}