package com.app.kalyanbazar.adapter

import android.app.Activity
import android.content.Intent
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
import com.app.kalyanbazar.activity.ActivityChart
import com.app.kalyanbazar.activity.TrainingActivity
import com.app.kalyanbazar.databinding.AdapterHomeBinding
import com.app.kalyanbazar.model.response.ResponseDashBoardListItem
import com.app.kalyanbazar.utils.Helper
import com.romainpiel.shimmer.Shimmer
import com.romainpiel.shimmer.ShimmerTextView

//
class AdapterHome(
    private val activity: Activity,
    var list: ArrayList<ResponseDashBoardListItem>,
    private val onClick: onClicklistBazar,
    var isBetting: Boolean,
) : RecyclerView.Adapter<AdapterHome.ViewResource>() {
    interface onClicklistBazar {
        fun onItemClickBazar(position: ResponseDashBoardListItem, rlhead: RelativeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewResource {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_home, parent, false)
        return ViewResource(view)
    }

    override fun onBindViewHolder(holder: ViewResource, position: Int) {
        if (list[position].active!!.equals(false)) {
            holder.binding.cv.visibility = View.GONE
        } else {
            holder.binding.cv.visibility = View.VISIBLE

        }
        if (isBetting) {
            holder.binding.eventNumber.visibility = View.VISIBLE

             if (list[position].openingStatus!!.equals(true)) {
                val rotate = AnimationUtils.loadAnimation(activity, R.anim.round)
                holder.binding.eventStatus.startAnimation(rotate)
                holder.binding.eventStatus.setImageResource(R.drawable.ic_play_green)
                holder.binding.marketOpen.visibility = View.VISIBLE
                holder.binding.marketOpen.text = "Market is Running"
                holder.binding.marketOpen.setBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.white
                    )
                )
                holder.binding.marketOpen.setTextColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.greendark
                    )
                )
                /*holder.itemView.setOnClickListener {
                    onClick.onItemClickBazar(list[position], holder.binding.rlhead)
                }*/
            } else {
                holder.binding.marketOpen.visibility = View.VISIBLE
                holder.binding.eventStatus.setImageResource(R.drawable.close)
                holder.binding.marketOpen.text = "Market Closed"
                holder.binding.marketOpen.setBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.white
                    )
                )
                holder.binding.marketOpen.setTextColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.red_200
                    )
                )
            }
            val animation = AnimationUtils.loadAnimation(activity, R.anim.dd)
            holder.binding.eventNumber.animation = animation

        } else {
            holder.binding.eventNumber.visibility = View.GONE
            holder.binding.llchart.visibility = View.GONE

          //  holder.binding.eventStatus.visibility = View.GONE
           // holder.binding.lleventstaus.visibility = View.GONE

        }

        Log.e("MarketPoint==>", "" + list[position].marketName)
        if (list[position].openPanaResult.equals("") && list[position].closePanaResult.equals("")) {
            holder.binding.eventNumber.text = "XXX-XX-XXX"

        } else if (list[position].openPanaResult.equals(null)) {
            holder.binding.eventNumber.text = "XXX-X" + list[position].closePanaResult

        } else if (list[position].closePanaResult.equals(null)) {
            holder.binding.eventNumber.text = list[position].openPanaResult + "X-XXX"

        } else {
            holder.binding.eventNumber.text =
                list[position].openPanaResult + "" + list[position].closePanaResult
        }
        // holder.binding.eventNumber.text = list[position].marketCode
        holder.binding.openingTime.text =
            Helper.dateFormateampm(list[position].marketOpeningTime.toString())
        holder.binding.closingTime.text =
            Helper.dateFormateampm(list[position].marketClosingTime.toString())
        holder.binding.eventType.text = list[position].marketName
        val shimmer = Shimmer()

        shimmer.start<ShimmerTextView>(holder.binding.eventType)
        holder.binding.llchart.isEnabled = isBetting


        holder.binding.llchart.setOnClickListener {
            val chartTable = Intent(
                activity,
                ActivityChart::class.java
            ).putExtra(
                "market_name",
                list[position].marketName
            ).putExtra(
                "market_id",
                list[position].id.toString()
            )

            activity.startActivity(chartTable)
        }
        /*   holder.binding.llchart.setOnClickListener {

               val chartTable = Intent(activity, ActivityChart::class.java).putExtra(
                   "market_name",
                   list[position].marketName
               )
                   .putExtra("market_id", list[position].id.toString())
               activity.startActivity(chartTable)

           }*/

        holder.itemView.setOnClickListener {
            if (isBetting) {
                if (list[position].openingStatus!!.equals(true)) {
                    onClick.onItemClickBazar(
                        list[position],
                        holder.binding.rlhead
                    )
                }


            } else {

                val intent = Intent(
                    activity,
                    TrainingActivity::class.java
                )

                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewResource internal constructor(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {
        var binding: AdapterHomeBinding = DataBindingUtil.bind(itemView!!)!!

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}