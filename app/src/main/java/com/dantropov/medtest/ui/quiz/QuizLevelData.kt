package com.dantropov.medtest.ui.quiz

import android.os.Parcelable
import com.dantropov.medtest.ui.training.adapter.TrainingLevelData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizLevelData(
    val currentQuestionId: Int = 0,
    val startQuestionId: Int = 0,
    val endQuestionId: Int = 0,
    val rightAnswersCount: Int = 0
) : Parcelable {
    val questionsCount get() = endQuestionId - startQuestionId + 1
    val questionPosition get() = currentQuestionId - startQuestionId + 1

    companion object {
        fun createFromTrainingLevelData(data: TrainingLevelData): QuizLevelData {
            return QuizLevelData(
                data.start,
                data.start,
                data.end,
                0
            )
        }
    }
}