package com.dantropov.medtest.ui.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dantropov.medtest.compose.MedSavedStateHandle
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
    private val state: MedSavedStateHandle
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> = _navigateTo

    private val _uiState = MutableLiveData<TrainingUiState>(TrainingUiState.Empty)
    val uiState: LiveData<TrainingUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            val trainingLevels = medQuizRepository.getTrainingLevels()
            delay(300)
            _uiState.value = TrainingUiState.Ready(trainingLevels)
        }
    }

    fun trainingLevelClick(trainingLevelData: TrainingLevelData) {
        state.saveQuizData(QuizLevelData.createFromTrainingLevelData(trainingLevelData))
        _navigateTo.value = Event(Screen.Quiz)
    }

    companion object {
        const val TRAINING_SIZE = 10
    }
}