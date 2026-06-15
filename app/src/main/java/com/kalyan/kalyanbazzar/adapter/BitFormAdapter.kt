package com.kalyan.kalyanbazzar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.model.response.BitformModel
import java.util.ArrayList

class BitFormAdapter (
    val context: Context,
    val result: ArrayList<BitformModel>
) :
    RecyclerView.Adapter<BitFormAdapter.ViewHolder>() {
    private var onItemClickListener: BitFormAdapter.ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitFormAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bitformlist, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.rllist.visibility = View.VISIBLE
          //  holder.tvdigits.text = "Open " + result.get(position).panaDate
            holder.tvdigits.text = result.get(position).pana
            holder.tvcloseddigits.visibility = View.GONE
          //  holder.tvcloseddigits.text = "Close " + result.get(position).digitsclose
            holder.tvpoints.text = result.get(position).points

            holder.img1.setOnClickListener(View.OnClickListener {
                onItemClickListener?.onItemClick(holder.img1, result.get(position).points)
                result.removeAt(position)
                notifyDataSetChanged()

            })

    }

    override fun getItemCount(): Int {
        return result.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var rl: RelativeLayout
        var tvdigits: TextView
        var img1: ImageView
        var tvpoints: TextView
        var rllist: CardView
        var tvcloseddigits: TextView


        lateinit var mainlayout: LinearLayout

        init {

            tvcloseddigits = itemView.findViewById(R.id.tvclosedigits)
            rl = itemView.findViewById(R.id.rl1)
            tvdigits = itemView.findViewById(R.id.tvdigits)
            tvpoints = itemView.findViewById(R.id.tvpoints)
            rllist = itemView.findViewById(R.id.rllist)
            img1 = itemView.findViewById(R.id.closebid)

        }
    }

    fun setItemClickListener(clickListener: ItemClickListener) {
        onItemClickListener = clickListener
    }


    interface ItemClickListener {
        fun onItemClick(view: View, category_id1: String)
    }


}