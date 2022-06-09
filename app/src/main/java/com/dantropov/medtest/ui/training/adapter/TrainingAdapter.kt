package com.dantropov.medtest.ui.training.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dantropov.medtest.databinding.ItemTrainingBinding

class TrainingAdapter(var datalist: List<TrainingData>) : RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemTrainingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TrainingData) {
            binding.title.text = formatTrainingSequence(data)
        }

        private fun formatTrainingSequence(data: TrainingData): String {
            return "${data.start} - ${data.end}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}