package com.dantropov.medtest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dantropov.medtest.database.model.MedQuiz
import kotlinx.coroutines.flow.Flow

@Dao
interface MedQuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(medQuizList: List<MedQuiz>)

    @Query("SELECT * FROM medQuiz WHERE id= :id")
    suspend fun getQuestion(id: Int): MedQuiz

    @Query("SELECT COUNT(*) FROM medquiz")
    suspend fun getQuizCount(): Int
}