package com.dantropov.medtest.ui.quiz

import android.os.Parcelable
import com.dantropov.medtest.ui.training.TrainingLevelData
import com.dantropov.medtest.ui.training.TrainingViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizLevelData(
    val currentQuestionId: Int = 0,
    val startQuestionId: Int = 0,
    val endQuestionId: Int = 0,
    val rightAnswersCount: Int = 0,
    val questions: MutableList<Int> = mutableListOf()
) : Parcelable {
    val questionsCount get() = endQuestionId - startQuestionId + 1
    val questionPosition get() = currentQuestionId - startQuestionId + 1

    fun getCurrentQuestion(): Int {
        return questions[currentQuestionId]
    }

    companion object {
        fun createFromTrainingLevelData(data: TrainingLevelData): QuizLevelData {
            return QuizLevelData(
                data.start,
                data.start,
                data.end,
                0,
                (data.start..data.end).toMutableList()
            )
        }

        fun createExamTrainingLevelData(questionCount: Int): QuizLevelData {
            return QuizLevelData(
                0,
                0,
                TrainingViewModel.TRAINING_SIZE,
                0,
                (0 until questionCount).shuffled().take(TrainingViewModel.TRAINING_SIZE).toMutableList()
            )
        }
    }
}


fun main() {
    println(QuizLevelData.createExamTrainingLevelData(50))
}