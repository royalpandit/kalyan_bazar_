package com.kalyan.kalyanbazzar.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.databinding.AdapterTransferHistoryBinding
import com.kalyan.kalyanbazzar.model.response.ResponseWithdrawalList

//adapter_transfer_history
class AdapterTransferHistory (
    private val activity: Activity,
    var list: ArrayList<ResponseWithdrawalList>,
) : RecyclerView.Adapter<AdapterTransferHistory.ViewResource>() {
    interface onClicklistUser {
        // fun onItemClickUser(position: LabourListItem)
        // fun onItemClickUserFire(position: LabourListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewResource {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_transfer_history, parent, false)
        return ViewResource(view)
    }

    override fun onBindViewHolder(holder: ViewResource, position: Int) {

      holder.binding.bonusName.text = "Transfer to "+list[position].userIdFirstName

        if (list[position].isRejected.equals("PENDING")){
            holder.binding.bonusName.setTextColor(Color.parseColor("#f29339"))
            holder.binding.tranStatus.text="Request in Progress"
        }else if(list[position].isRejected.equals("APPROVE")){
            holder.binding.bonusName.setTextColor(Color.parseColor("#25D366"))
            holder.binding.tranStatus.text="Request is Approved"
        }else if(list[position].isRejected.equals("REJECT")){
            holder.binding.bonusName.setTextColor(Color.parseColor("#F44336"))
            holder.binding.tranStatus.text="Request is Rejected"
        }else{
            holder.binding.bonusName.setTextColor(Color.parseColor("#f29339"))
            holder.binding.tranStatus.text="Request in Progress"
        }
        holder.binding.coins.text = list[position].amount.toString()
        holder.binding.dateTime.text = list[position].createdAt


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewResource internal constructor(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {


        var binding: AdapterTransferHistoryBinding= DataBindingUtil.bind(itemView!!)!!

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}