package com.dantropov.medtest.ui.start

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.dantropov.medtest.R
import com.dantropov.medtest.compose.base.BaseViewModel
import com.dantropov.medtest.data.MedQuizRepository
import com.dantropov.medtest.navigation.Screen
import com.dantropov.medtest.ui.quiz.QuizLevelData
import com.dantropov.medtest.util.Constants
import com.dantropov.medtest.util.Constants.QUIZ_LEVEL_DATA
import com.dantropov.medtest.util.Constants.QUIZ_NAME_ID
import com.dantropov.medtest.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val medQuizRepository: MedQuizRepository
) : BaseViewModel() {

    var questionsCount = -1

    fun init() {
        // Need to prepopulate db
        viewModelScope.launch {
            questionsCount = medQuizRepository.getQuestionsCount()
        }
    }

    fun navigateToTraining() {
        _navigateTo.value = Event(Screen.Training)
    }

    fun navigateToExam() {
        _navigateTo.value = Event(
            Screen.Quiz,
            bundleOf(
                QUIZ_NAME_ID to R.string.exam,
                QUIZ_LEVEL_DATA to QuizLevelData.createExamTrainingLevelData(questionsCount)
            )
        )
    }

    fun navigateToStats() {
        //
    }
}
