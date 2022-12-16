package com.dantropov.medtest.data

import com.dantropov.medtest.database.dao.MedQuizDao
import com.dantropov.medtest.database.model.MedQuiz
import com.dantropov.medtest.ui.training.TrainingViewModel
import com.dantropov.medtest.ui.training.TrainingLevelData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedQuizRepository @Inject constructor(private val medQuizDao: MedQuizDao) {

    suspend fun getQuestion(id: Int): MedQuiz {
        return medQuizDao.getQuestion(id)
    }

    suspend fun getTrainingLevels(): List<TrainingLevelData> {
        val count = getQuestionsCount()
        return (1..count).chunked(TrainingViewModel.TRAINING_SIZE)
            .map { TrainingLevelData(it.first(), it.last()) }
    }

    suspend fun getQuestionsCount(): Int {
        return medQuizDao.getQuestionsCount()
    }
}