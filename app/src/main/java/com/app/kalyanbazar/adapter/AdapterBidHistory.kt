package com.app.kalyanbazar.adapter

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.AdapterBidHistoryBinding
import com.app.kalyanbazar.databinding.AdapterWalletStatementBinding
import com.app.kalyanbazar.model.User

class AdapterBidHistory (
    private val activity: Activity,
    var list: ArrayList<User>,
) : RecyclerView.Adapter<AdapterBidHistory.ViewResource>() {
    interface onClicklistUser {
        // fun onItemClickUser(position: LabourListItem)
        // fun onItemClickUserFire(position: LabourListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewResource {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_bid_history, parent, false)
        return ViewResource(view)
    }

    override fun onBindViewHolder(holder: ViewResource, position: Int) {

        // holder.binding.labourName.text = list[position].name
        holder.binding.gameName.text = list[position].gameName
        holder.binding.gameSession.text = list[position].closeMarket
        holder.binding.gameDate.text = list[position].openMarket
        holder.binding.bidPoints.text = "+14"
        holder.binding.gameNumberOpen.text = list[position].closeMarket

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewResource internal constructor(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {


        var binding: AdapterBidHistoryBinding = DataBindingUtil.bind(itemView!!)!!

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}