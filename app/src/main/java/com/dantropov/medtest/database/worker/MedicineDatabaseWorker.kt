package com.dantropov.medtest.database.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dantropov.medtest.database.MedQuizDB
import com.dantropov.medtest.database.model.MedQuiz
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicineDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    com.google.gson.stream.JsonReader(inputStream.reader()).use { jsonReader ->
                        val medQuizType = object : TypeToken<List<MedQuiz>>() {}.type
                        val medQuizList: List<MedQuiz> = Gson().fromJson(jsonReader, medQuizType)

                        val database = MedQuizDB.getInstance(applicationContext)
                        database.medQuizDao().insertAll(medQuizList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "MedicineDatabaseWorker"
        const val KEY_FILENAME = "PLANT_DATA_FILENAME"
    }
}