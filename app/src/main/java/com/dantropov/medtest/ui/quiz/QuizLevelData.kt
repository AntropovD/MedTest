package com.dantropov.medtest.ui.quiz

import android.os.Parcelable
import com.dantropov.medtest.ui.training.TrainingLevelData
import com.dantropov.medtest.ui.training.TrainingViewModel
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

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

        fun createExamTrainingLevelData(questionCount: Int): QuizLevelData {
            val lastQuestion = Random.nextInt(questionCount - TrainingViewModel.TRAINING_SIZE)
            return createFromTrainingLevelData(TrainingLevelData(lastQuestion, lastQuestion + 9))
        }
    }
}