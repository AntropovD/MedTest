package com.dantropov.medtest.ui.training.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dantropov.medtest.databinding.ItemTrainingBinding

class TrainingAdapter() : RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {

    private var dataList: MutableList<TrainingLevelData> = mutableListOf()

    class ViewHolder(private val binding: ItemTrainingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TrainingLevelData) {
            binding.title.text = formatTrainingSequence(data)
        }

        private fun formatTrainingSequence(data: TrainingLevelData): String {
            return "${data.start} - ${data.end}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitData(trainingLevels: List<TrainingLevelData>) {
        dataList = trainingLevels.toMutableList()
    }
}