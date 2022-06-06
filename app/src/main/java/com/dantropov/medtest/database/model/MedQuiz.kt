package com.dantropov.medtest.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["type"])])
data class MedQuiz(
    @PrimaryKey val id: Long,
    val type: String,
    val question: String,
    val answers: MutableList<Answer>
)