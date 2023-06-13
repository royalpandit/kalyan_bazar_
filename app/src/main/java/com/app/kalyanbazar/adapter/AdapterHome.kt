package com.app.kalyanbazar.adapter

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.AdapterHomeBinding
import com.app.kalyanbazar.databinding.AdapterWalletStatementBinding
import com.app.kalyanbazar.model.User

//
class AdapterHome (
    private val activity: Activity,
    var list: ArrayList<User>,
) : RecyclerView.Adapter<AdapterHome.ViewResource>() {
    interface onClicklistUser {
        // fun onItemClickUser(position: LabourListItem)
        // fun onItemClickUserFire(position: LabourListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewResource {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_home, parent, false)
        return ViewResource(view)
    }

    override fun onBindViewHolder(holder: ViewResource, position: Int) {

        // holder.binding.labourName.text = list[position].name

        Log.e("MarketPoint==>",""+list[position].marketPoint)

        holder.binding.eventNumber.text = list[position].marketPoint
        holder.binding.openingTime.text = list[position].openMarket
        holder.binding.closingTime.text = list[position].closeMarket
        holder.binding.eventType.text = list[position].bazar

    }

    override fun getItemCount(): Int {
        Log.e("ListSize==>",""+list.size)
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