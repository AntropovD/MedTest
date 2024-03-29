package com.dantropov.medtest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dantropov.medtest.database.model.MedQuiz

@Dao
interface MedQuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medQuizList: List<MedQuiz>)

    @Query("SELECT * FROM MedQuiz WHERE id= :id")
    suspend fun getQuestion(id: Int): MedQuiz

    @Query("SELECT COUNT(*) FROM MedQuiz")
    suspend fun getQuestionsCount(): Int
}