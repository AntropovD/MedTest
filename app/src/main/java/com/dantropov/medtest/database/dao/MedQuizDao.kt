package com.dantropov.medtest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dantropov.medtest.database.model.MedQuiz

@Dao
interface MedQuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(medQuizList: List<MedQuiz>)
}