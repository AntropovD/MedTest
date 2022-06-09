package com.dantropov.medtest.data

import com.dantropov.medtest.database.dao.MedQuizDao
import com.dantropov.medtest.database.model.MedQuiz
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedQuizRepository @Inject constructor(private val medQuizDao: MedQuizDao) {

    suspend fun getQuestion(id: Int): MedQuiz {
        return medQuizDao.getQuestion(id)
    }

    suspend fun getQuizCount() : Int {
        return medQuizDao.getQuizCount()
    }
}