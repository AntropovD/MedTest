package com.dantropov.medtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dantropov.medtest.database.converter.AnswersConverter
import com.dantropov.medtest.database.dao.MedQuizDao
import com.dantropov.medtest.database.model.MedQuiz
import com.dantropov.medtest.database.worker.MedicineDatabaseWorker
import com.dantropov.medtest.database.worker.MedicineDatabaseWorker.Companion.KEY_FILENAME

@Database(entities = [MedQuiz::class], version = 1)
@TypeConverters(AnswersConverter::class)
abstract class MedQuizDB : RoomDatabase() {

    abstract fun medQuizDao(): MedQuizDao

    companion object {
        private const val DATABASE_NAME = "medicine.db"
        private const val PLANT_DATA_FILENAME = "med_tests.json"

        @Volatile
        private var instance: MedQuizDB? = null

        fun getInstance(context: Context): MedQuizDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MedQuizDB {
            return Room.databaseBuilder(context, MedQuizDB::class.java, DATABASE_NAME)
                .createFromAsset("medicine.db")
                .build()
        }

//        private fun buildDatabase(context: Context): MedQuizDB {
//            return Room.databaseBuilder(context, MedQuizDB::class.java, DATABASE_NAME)
//                .addCallback(
//                    object : RoomDatabase.Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<MedicineDatabaseWorker>()
//                                .setInputData(workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME))
//                                .build()
//                            WorkManager.getInstance(context).enqueue(request)
//                        }
//                    }
//                )
//                .build()
//        }
    }
}