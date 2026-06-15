// TrainingAdapter.kt
package com.kalyan.kalyanbazzar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalyan.kalyanbazzar.databinding.AdapterTrainingBinding
 import com.kalyan.kalyanbazzar.model.response.QuestionModel

class TrainingAdapter(
    private var list: ArrayList<QuestionModel>
) : RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: AdapterTrainingBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = AdapterTrainingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = list[position]

        holder.binding.tvQuestion.text =
            "${position + 1}. ${item.question}"

        holder.binding.rbYes.setOnClickListener {
            item.answer = "Yes"
        }

        holder.binding.rbNo.setOnClickListener {
            item.answer = "No"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}