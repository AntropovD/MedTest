package com.dantropov.medtest.compose

import androidx.lifecycle.SavedStateHandle
import com.dantropov.medtest.ui.quiz.QuizLevelData
import javax.inject.Inject

class MedSavedStateHandle @Inject constructor(private val savedStateHandle: SavedStateHandle) {

    fun saveQuizData(quizLevelData: QuizLevelData) {
        savedStateHandle[QUIZ_LEVEL_DATA] = quizLevelData
    }

    fun getQuizData(): QuizLevelData? {
        return savedStateHandle.get<QuizLevelData>(QUIZ_LEVEL_DATA)
    }

    companion object {
        private const val QUIZ_LEVEL_DATA = "quizLevelData"
    }
}