package com.kalyan.kalyanbazzar.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.databinding.AdapterWalletStatementBinding
import com.kalyan.kalyanbazzar.model.response.ResponseGetUserFund
import com.kalyan.kalyanbazzar.utils.Helper

//adapter_wallet_statement
class AdapterWalletStatement(
    private val activity: Activity,
    var list: ArrayList<ResponseGetUserFund>,
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
        if (list[position].transactionType.equals("ADD_FUND")) {
            holder.binding.bonusName.text = "Point Add Via UPI"
        } else if (list[position].transactionType.equals("BONUS")) {
            holder.binding.bonusName.text = "Welcome Bonus Points"
        } else {
            holder.binding.bonusName.text = "Points Add Via Admin"
        }

        holder.binding.coins.text = list[position].amount.toString()
        holder.binding.dateTime.text =
            Helper.dateFormateConverter(list[position].createdAt.toString())

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