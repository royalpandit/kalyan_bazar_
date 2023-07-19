package com.app.kalyanbazar.adapter

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.app.kalyanbazar.databinding.AdapterIndashboardBinding
import com.app.kalyanbazar.model.response.ResponseDashBoardListItem
import com.app.kalyanbazar.model.response.ResponseInDashBoard
import com.bumptech.glide.Glide
import com.romainpiel.shimmer.Shimmer
import com.romainpiel.shimmer.ShimmerTextView

class AdapterInDashboard (
    private val activity: Activity,
    var list: ArrayList<ResponseInDashBoard>,
    private val onClick: onClicklistBazar,
) : RecyclerView.Adapter<AdapterInDashboard.ViewResource>() {
    interface onClicklistBazar {
        fun onItemClickBazar(position: ResponseInDashBoard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewResource {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_indashboard, parent, false)
        return ViewResource(view)
    }

    override fun onBindViewHolder(holder: ViewResource, position: Int) {


        holder.binding.textView.text = list[position].name
        Glide.with(activity).load(list[position].imageUrl).into(holder.binding.salesimg);


        holder.itemView.setOnClickListener {
            onClick.onItemClickBazar(list[position])

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewResource internal constructor(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {


        var binding: AdapterIndashboardBinding = DataBindingUtil.bind(itemView!!)!!

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}