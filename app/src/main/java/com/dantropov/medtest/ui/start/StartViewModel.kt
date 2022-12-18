package com.dantropov.medtest.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dantropov.medtest.compose.base.BaseViewModel
import com.dantropov.medtest.data.MedQuizRepository
import com.dantropov.medtest.navigation.Screen
import com.dantropov.medtest.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val medQuizRepository: MedQuizRepository
) : BaseViewModel() {

    fun init() {
        // Need to prepopulate db
        viewModelScope.launch {
            medQuizRepository.getQuestionsCount()
        }
    }

    fun navigateToTraining() {
        _navigateTo.value = Event(Screen.Training)
    }

    fun navigateToExam() {
        _navigateTo.value = Event(Screen.Exam)
    }

    fun navigateToStats() {
        //
    }
}
