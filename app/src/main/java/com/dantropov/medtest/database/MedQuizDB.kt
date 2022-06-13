package com.dantropov.medtest.database

import android.content.res.Resources
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dantropov.medtest.R
import com.dantropov.medtest.database.converter.AnswersConverter
import com.dantropov.medtest.database.dao.MedQuizDao
import com.dantropov.medtest.database.model.MedQuiz
import com.dantropov.medtest.di.ApplicationScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [MedQuiz::class], version = 1)
@TypeConverters(AnswersConverter::class)
abstract class MedQuizDB : RoomDatabase() {

    abstract fun medQuizDao(): MedQuizDao

    class Callback @Inject constructor(
        private val database: Provider<MedQuizDB>,
        @ApplicationScope private val applicationScope: CoroutineScope,
        private val resources: Resources
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val medQuizDao = database.get().medQuizDao()
            applicationScope.launch {
                prePopulateDatabase(medQuizDao)
            }
        }

        private suspend fun prePopulateDatabase(climbingRouteDao: MedQuizDao) {
            val jsonString = resources.openRawResource(R.raw.med_tests).bufferedReader().use { it.readText() }
            val typeToken = object : TypeToken<List<MedQuiz>>() {}.type
            val medQuizList = Gson().fromJson<List<MedQuiz>>(jsonString, typeToken)
            climbingRouteDao.insertAll(medQuizList)
        }
    }
}