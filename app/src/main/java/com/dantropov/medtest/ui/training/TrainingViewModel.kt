package com.dantropov.medtest.ui.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dantropov.medtest.data.MedQuizRepository
import com.dantropov.medtest.ui.training.adapter.TrainingLevelData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TrainingUiState {
    data class Ready(val trainingLevels: List<TrainingLevelData>) : TrainingUiState()
    object Empty : TrainingUiState()
}

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val medQuizRepository: MedQuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TrainingUiState>(TrainingUiState.Empty)
    val uiState: StateFlow<TrainingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val trainingLevels = medQuizRepository.getTrainingLevels()
            _uiState.value = TrainingUiState.Ready(trainingLevels)
        }
    }

    companion object {
        const val TRAINING_SIZE = 10
    }
}