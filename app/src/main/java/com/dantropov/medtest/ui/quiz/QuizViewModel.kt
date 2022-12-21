package com.dantropov.medtest.ui.quiz

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dantropov.medtest.R
import com.dantropov.medtest.compose.base.BaseViewModel
import com.dantropov.medtest.data.MedQuizRepository
import com.dantropov.medtest.database.model.Answer
import com.dantropov.medtest.database.model.MedQuiz
import com.dantropov.medtest.navigation.Screen
import com.dantropov.medtest.util.Constants.QUIZ_LEVEL_DATA
import com.dantropov.medtest.util.Constants.QUIZ_NAME_ID
import com.dantropov.medtest.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class QuizUiState {
    data class Ready(val medQuiz: MedQuiz, val state: QuestionState, val levelNameId: Int, val quizLevelData: QuizLevelData) : QuizUiState()
    data class Finish(val levelNameId: Int, val quizLevelData: QuizLevelData) : QuizUiState()
    object Loading : QuizUiState()
    object Error : QuizUiState()
}

sealed class QuestionState {
    object NotAnswered : QuestionState()
    data class CorrectAnswer(val correctAnswerId: Int) : QuestionState()
    data class WrongAnswer(val correctAnswerId: Int, val wrongAnswerId: Int) : QuestionState()
}

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val medQuizRepository: MedQuizRepository
) : BaseViewModel() {

    private var scope: Job? = null
    private val _uiState = MutableLiveData<QuizUiState>(QuizUiState.Loading)
    val uiState: LiveData<QuizUiState> = _uiState

    private var quizLevelData: QuizLevelData? = null
    private var question: MedQuiz? = null
    private var levelNameId: Int = R.string.practice

    fun init(arguments: Bundle?) {
        val data = arguments?.getParcelable<QuizLevelData>(QUIZ_LEVEL_DATA)
        levelNameId = arguments?.getInt(QUIZ_NAME_ID) ?: R.string.practice

        if (data == null) {
            _uiState.value = QuizUiState.Error
            return
        }
        quizLevelData = data

        _uiState.value = QuizUiState.Loading
        viewModelScope.launch {
            question = medQuizRepository.getQuestion(data.currentQuestionId)
            question?.let { _uiState.value = QuizUiState.Ready(it, QuestionState.NotAnswered, levelNameId, quizLevelData!!) }
                ?: run { _uiState.value = QuizUiState.Error }
        }
    }

    fun answerClick(answer: Answer, order: Int, state: QuestionState) {
        scope?.cancel()
        if (state != QuestionState.NotAnswered) {
            onClick(state)
            return
        }
        var state =
            question?.let { question ->
                if (answer.correct) QuestionState.CorrectAnswer(order)
                else {
                    val correctOrder = question.answers.indexOfFirst { it.correct }
                    QuestionState.WrongAnswer(correctOrder, order)
                }
            }
        state?.let { state ->
            question?.let { medQuiz ->
                _uiState.value = QuizUiState.Ready(medQuiz, state, levelNameId, quizLevelData!!)
                scope = viewModelScope.launch {
                    delay(5000)
                    onClick(state)
                }
            }
        }
    }

    fun onClick(state: QuestionState) {
        scope?.cancel()
        when (state) {
            is QuestionState.CorrectAnswer -> {
                quizLevelData?.let { data ->
                    val newLevelData = data.copy(
                        currentQuestionId = data.currentQuestionId + 1,
                        rightAnswersCount = data.rightAnswersCount + 1
                    )
                    handleNewLevelData(newLevelData)
                }
            }
            is QuestionState.WrongAnswer -> {
                quizLevelData?.let { data ->
                    val newLevelData = data.copy(
                        currentQuestionId = data.currentQuestionId + 1,
                        rightAnswersCount = data.rightAnswersCount
                    )
                    handleNewLevelData(newLevelData)
                }
            }
            QuestionState.NotAnswered -> {}
        }
    }

    private fun handleNewLevelData(newLevelData: QuizLevelData) {
        if (newLevelData.currentQuestionId > newLevelData.endQuestionId) {
            _uiState.value = QuizUiState.Finish(levelNameId, newLevelData)
        } else {
            init(
                bundleOf(
                    QUIZ_NAME_ID to levelNameId,
                    QUIZ_LEVEL_DATA to newLevelData
                )
            )
        }
    }

    fun finishClick() {
        _navigateTo.value = Event(Screen.Start)
    }
}

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
////
//    private fun handleNewLevelData(newLevelData: QuizLevelData) {
//        if (newLevelData.currentQuestionId > newLevelData.endQuestionId) {
//            _uiState.value = QuizUiState.Finish(newLevelData)
//        } else {
//            _uiState.value = QuizUiState.NavigateToNextQuestion(newLevelData)
//        }
//    }


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