package com.dantropov.medtest.ui.training

import androidx.lifecycle.ViewModel
import com.dantropov.medtest.data.MedQuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val medQuizRepository: MedQuizRepository
) : ViewModel() {

    fun navigateToTraining() {
    }

    fun navigateToExam() {
    }
}