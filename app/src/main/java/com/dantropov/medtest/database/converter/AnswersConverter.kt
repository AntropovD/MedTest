package com.dantropov.medtest.database.converter

import androidx.room.TypeConverter
import com.dantropov.medtest.database.model.Answer
import com.google.gson.Gson

class AnswersConverter {
    @TypeConverter
    fun listToJson(value: List<Answer>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Answer>::class.java).toList()
}