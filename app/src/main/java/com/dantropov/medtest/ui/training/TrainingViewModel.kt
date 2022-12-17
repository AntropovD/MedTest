package com.dantropov.medtest.ui.training

import androidx.lifecycle.*
import com.dantropov.medtest.data.MedQuizRepository
import com.dantropov.medtest.navigation.Screen
import com.dantropov.medtest.ui.quiz.QuizLevelData
import com.dantropov.medtest.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TrainingUiState {
    data class Ready(val trainingLevels: List<TrainingLevelData>) : TrainingUiState()
    object Empty : TrainingUiState()
}

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val medQuizRepository: MedQuizRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> = _navigateTo

    private val _uiState = MutableLiveData<TrainingUiState>(TrainingUiState.Empty)
    val uiState: LiveData<TrainingUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            val trainingLevels = medQuizRepository.getTrainingLevels()
            delay(500)
            _uiState.value = TrainingUiState.Ready(trainingLevels)
        }
    }

    fun trainingLevelClick(trainingLevelData: TrainingLevelData) {
        state["quizLevelData"] = QuizLevelData.createFromTrainingLevelData(trainingLevelData)
        _navigateTo.value = Event(Screen.Quiz)
    }

    companion object {
        const val TRAINING_SIZE = 10
    }
}