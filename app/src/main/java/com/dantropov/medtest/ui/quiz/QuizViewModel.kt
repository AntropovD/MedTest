package com.dantropov.medtest.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dantropov.medtest.compose.MedSavedStateHandle
import com.dantropov.medtest.compose.base.BaseViewModel
import com.dantropov.medtest.data.MedQuizRepository
import com.dantropov.medtest.database.model.MedQuiz
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class QuizUiState {
    data class Ready(val medQuiz: MedQuiz) : QuizUiState()
    data class CorrectAnswer(val correctOrder: Int) : QuizUiState()
    data class WrongAnswer(val wrongOrder: Int, val correctOrder: Int) : QuizUiState()
    data class NavigateToNextQuestion(val quizLevelData: QuizLevelData) : QuizUiState()
    data class Finish(val quizLevelData: QuizLevelData) : QuizUiState()
    object Empty : QuizUiState()
}

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val medQuizRepository: MedQuizRepository,
    private val state: MedSavedStateHandle
) : BaseViewModel() {
    private val _uiState = MutableLiveData<QuizUiState>(QuizUiState.Empty)
    val uiState: LiveData<QuizUiState> = _uiState

    fun init() {
        val data = state.getQuizData() ?: return

        viewModelScope.launch {
            val question = medQuizRepository.getQuestion(data.currentQuestionId)
            _uiState.value = QuizUiState.Ready(question)
        }
    }

    fun itemChoose(chosenItem: Int) {
        when (val uiState = uiState.value) {
            is QuizUiState.Ready -> {
                val medQuiz = uiState.medQuiz
                val answer = medQuiz.answers.getOrNull(chosenItem) ?: return
                if (answer.correct) {
                    _uiState.value = QuizUiState.CorrectAnswer(chosenItem)
                } else {
                    _uiState.value =
                        QuizUiState.WrongAnswer(chosenItem, medQuiz.answers.indexOfFirst { it.correct })
                }
            }
            else -> {}
        }
    }

    fun nextQuiz(data: QuizLevelData) {
        when (uiState.value) {
            is QuizUiState.CorrectAnswer -> {
                val newLevelData = data.copy(
                    currentQuestionId = data.currentQuestionId + 1,
                    rightAnswersCount = data.rightAnswersCount + 1
                )
                handleNewLevelData(newLevelData)
            }
            is QuizUiState.WrongAnswer -> {
                val newLevelData = data.copy(
                    currentQuestionId = data.currentQuestionId + 1,
                    rightAnswersCount = data.rightAnswersCount
                )
                handleNewLevelData(newLevelData)
            }
            else -> {}
        }
    }

    private fun handleNewLevelData(newLevelData: QuizLevelData) {
        if (newLevelData.currentQuestionId > newLevelData.endQuestionId) {
            _uiState.value = QuizUiState.Finish(newLevelData)
        } else {
            _uiState.value = QuizUiState.NavigateToNextQuestion(newLevelData)
        }
    }
}

//@HiltViewModel
//class QuizViewModel @Inject constructor(
//    private val medQuizRepository: MedQuizRepository
//) : ViewModel() {
//    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Empty)
//    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()
//
//    fun init(data: QuizLevelData) {
//        viewModelScope.launch {
//            val question = medQuizRepository.getQuestion(data.currentQuestionId)
//            _uiState.value = QuizUiState.Ready(question)
//        }
//    }
//
//    fun itemChoose(chosenItem: Int) {
//        when (val uiState = uiState.value) {
//            is QuizUiState.Ready -> {
//                val medQuiz = uiState.medQuiz
//                val answer = medQuiz.answers.getOrNull(chosenItem) ?: return
//                if (answer.correct) {
//                    _uiState.value = QuizUiState.CorrectAnswer(chosenItem)
//                } else {
//                    _uiState.value =
//                        QuizUiState.WrongAnswer(chosenItem, medQuiz.answers.indexOfFirst { it.correct })
//                }
//            }
//            else -> {}
//        }
//    }
//
//    fun nextQuiz(data: QuizLevelData) {
//        when (uiState.value) {
//            is QuizUiState.CorrectAnswer -> {
//                val newLevelData = data.copy(
//                    currentQuestionId = data.currentQuestionId + 1,
//                    rightAnswersCount = data.rightAnswersCount + 1
//                )
//                handleNewLevelData(newLevelData)
//            }
//            is QuizUiState.WrongAnswer -> {
//                val newLevelData = data.copy(
//                    currentQuestionId = data.currentQuestionId + 1,
//                    rightAnswersCount = data.rightAnswersCount
//                )
//                handleNewLevelData(newLevelData)
//            }
//            else -> {}
//        }
//    }
//
//    private fun handleNewLevelData(newLevelData: QuizLevelData) {
//        if (newLevelData.currentQuestionId > newLevelData.endQuestionId) {
//            _uiState.value = QuizUiState.Finish(newLevelData)
//        } else {
//            _uiState.value = QuizUiState.NavigateToNextQuestion(newLevelData)
//        }
//    }
//
//    fun finishQuiz() {
//
//
//    }
//}