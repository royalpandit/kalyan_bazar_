package com.kalyan.kalyanbazzar.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.databinding.AdapterIndashboardBinding
import com.kalyan.kalyanbazzar.model.response.ResponseInDashBoard
import com.bumptech.glide.Glide

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