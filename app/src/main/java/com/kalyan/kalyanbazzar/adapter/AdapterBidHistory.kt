package com.kalyan.kalyanbazzar.adapter

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.databinding.AdapterBidHistoryBinding
import com.kalyan.kalyanbazzar.model.response.ResponseGetBid
import com.kalyan.kalyanbazzar.utils.Helper

class AdapterBidHistory(
    private val activity: Activity,
    var list: ArrayList<ResponseGetBid> = ArrayList(),
    private val onClick: onClicklistUser,
    private val markeTYpe: String,
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
Log.e("MArketAd","MarkeAdapet==>>>"+markeTYpe)
        if (markeTYpe.equals("STARLINE")){
            holder.binding.gameSession.text=""

        }else{
            if (list[position].session==true){
                holder.binding.gameSession.text="Session Open"
            }else{
                holder.binding.gameSession.text="Session Close"
            }
        }

        if (list[position].isWon==true){
            holder.binding.bidPoints.setTextColor(Color.parseColor("#025B00"))
            holder.binding.bidPoints.text = "+"+list[position].points.toString()
         }else{
            holder.binding.bidPoints.setTextColor(Color.parseColor("#F44336"))
            holder.binding.bidPoints.text = "-"+list[position].points.toString()
         }
       // holder.binding.gameSession.text = list[position].closeMarket

         //holder.binding.gameDate.text = Helper.dateFormateConverter(list[position].panaDate.toString())
         holder.binding.gameDate.text = Helper.dateFormateConverter(list[position].createdAt.toString())
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