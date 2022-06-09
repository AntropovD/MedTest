package com.dantropov.medtest.ui.training

import com.dantropov.medtest.ui.training.adapter.TrainingLevelData

data class TrainingUiState(
    val isLoading: Boolean = false,
    val trainingLevels: List<TrainingLevelData> = listOf()
)