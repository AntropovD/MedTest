package com.dantropov.medtest.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dantropov.medtest.data.MedQuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val medQuizRepository: MedQuizRepository
) : ViewModel() {
    fun init() {
        // Need to prepopulate db
        viewModelScope.launch {
            medQuizRepository.getQuestionsCount()
        }
    }
}
