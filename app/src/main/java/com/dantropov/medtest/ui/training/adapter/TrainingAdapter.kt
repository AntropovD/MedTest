package com.dantropov.medtest.ui.training.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dantropov.medtest.databinding.ItemTrainingBinding
import com.dantropov.medtest.ui.quiz.QuizLevelData
//import com.dantropov.medtest.ui.training.TrainingFragmentDirections

class TrainingAdapter : RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {

    private var dataList: MutableList<TrainingLevelData> = mutableListOf()

    class ViewHolder(private val binding: ItemTrainingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TrainingLevelData) {
            binding.title.text = formatTrainingSequence(data)
            binding.card.setOnClickListener {
                navigateToQuiz(it, data)
            }
        }

        private fun formatTrainingSequence(data: TrainingLevelData): String {
            return "${data.start} - ${data.end}"
        }

        private fun navigateToQuiz(view: View, data: TrainingLevelData) {
//            view.findNavController().navigate(
////                TrainingFragmentDirections.actionTrainingFragmentToQuizFragment(
////                    QuizLevelData.createFromTrainingLevelData(data)
////                )
//            )
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
        notifyDataSetChanged()
    }
}