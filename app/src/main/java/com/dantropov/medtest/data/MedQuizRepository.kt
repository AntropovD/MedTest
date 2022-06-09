package com.dantropov.medtest.data

import com.dantropov.medtest.database.dao.MedQuizDao
import com.dantropov.medtest.database.model.MedQuiz
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedQuizRepository @Inject constructor(private val medQuizDao: MedQuizDao) {

    suspend fun getQuestion(id: Int): MedQuiz {
        return medQuizDao.getQuestion(id)
    }

    suspend fun getQuestionsCount() : Int {
        return medQuizDao.getQuestionsCount()
    }
}