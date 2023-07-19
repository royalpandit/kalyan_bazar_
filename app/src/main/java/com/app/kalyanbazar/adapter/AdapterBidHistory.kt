package com.app.kalyanbazar.adapter

import android.app.Activity
import android.graphics.Color
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
import com.app.kalyanbazar.model.response.ResponseGetBid

class AdapterBidHistory (
    private val activity: Activity,
    var list: ArrayList<ResponseGetBid> = ArrayList(),
    private val onClick:  onClicklistUser,
) : RecyclerView.Adapter<AdapterBidHistory.ViewResource>() {
    interface onClicklistUser {
        fun onItemClickUser(position: ResponseGetBid)
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
        holder.binding.gameName.text = list[position].marketName+"("+list[position].marketInsideName+")"
        if (list[position].session==true){
            holder.binding.gameSession.text="Session Open"
        }else{
            holder.binding.gameSession.text="Session Close"
        }
        if (list[position].isWon==true){
            holder.binding.bidPoints.setTextColor(Color.parseColor("#025B00"))
            holder.binding.bidPoints.text = "+"+list[position].points.toString()
         }else{
            holder.binding.bidPoints.setTextColor(Color.parseColor("#F44336"))
            holder.binding.bidPoints.text = "-"+list[position].points.toString()
         }
       // holder.binding.gameSession.text = list[position].closeMarket
        holder.binding.gameDate.text = list[position].panaDate
        holder.binding.bidPoints.text = list[position].points.toString()
        holder.binding.gameNumberOpen.text = list[position].pana
//SubmitedTransaction: TID1689589642265
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