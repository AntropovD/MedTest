package com.dantropov.medtest.mvp.training

import com.dantropov.medtest.Screens
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class TrainingPresenter @Inject constructor(private val router: Router) : MvpPresenter<TrainingView>() {

    fun navigateToTraining() {
        router.navigateTo(Screens.Training())
    }

    fun navigateToExam() {
        router.navigateTo(Screens.Exam())
    }
}