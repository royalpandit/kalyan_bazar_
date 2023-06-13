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
import com.app.kalyanbazar.databinding.AdapterWalletStatementBinding
import com.app.kalyanbazar.model.User

//adapter_wallet_statement
class AdapterWalletStatement(
    private val activity: Activity,
    var list: ArrayList<User>,
 ) : RecyclerView.Adapter<AdapterWalletStatement.ViewResource>() {
    interface onClicklistUser {
       // fun onItemClickUser(position: LabourListItem)
       // fun onItemClickUserFire(position: LabourListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewResource {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_wallet_statement, parent, false)
        return ViewResource(view)
    }

    override fun onBindViewHolder(holder: ViewResource, position: Int) {

        // holder.binding.labourName.text = list[position].name
        holder.binding.bonusName.text = list[position].name
        holder.binding.dateTime.text = list[position].address

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewResource internal constructor(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {


        var binding: AdapterWalletStatementBinding = DataBindingUtil.bind(itemView!!)!!

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}