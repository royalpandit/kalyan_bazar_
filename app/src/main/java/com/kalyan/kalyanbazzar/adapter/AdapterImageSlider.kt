package com.kalyan.kalyanbazzar.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.kalyan.kalyanbazzar.R
 import com.kalyan.kalyanbazzar.model.response.ResponseImageSlider
 import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

//
class AdapterImageSlider (
    val activity: Activity,
    val list: ArrayList<ResponseImageSlider>
    ) : PagerAdapter() {
    // on below line we are creating a method
    // as get count to return the size of the list.

    override fun getCount(): Int {
        return list.size
    }


    // on below line we are returning the object
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    // on below line we are initializing
    // our item and inflating our layout file
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // on below line we are initializing
        // our layout inflater.
        val mLayoutInflater =
            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // on below line we are inflating our custom
        // layout file which we have created.
        val itemView: View = mLayoutInflater.inflate(R.layout.adapter_image_slider, container, false)

        // on below line we are initializing
        // our image view with the id.
        val imageView: ImageView = itemView.findViewById<View>(R.id.iv_images) as ImageView

        // on below line we are setting
        // image resource for image view.
        Glide.with(activity).load(list[position].imageUrl).into(imageView);

      //  imageView.setImageResource(list[position].imageUrl)

        // on the below line we are adding this
        // item view to the container.
        Objects.requireNonNull(container).addView(itemView)

        // on below line we are simply
        // returning our item view.
        return itemView
    }

    // on below line we are creating a destroy item method.
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // on below line we are removing view
        container.removeView(`object` as RelativeLayout)
    }
}