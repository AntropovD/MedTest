package com.dantropov.medtest.mvp.main

import com.dantropov.medtest.Screens.Exam
import com.dantropov.medtest.Screens.Training
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class StartPresenter @Inject constructor(private val router: Router) : MvpPresenter<StartView>() {

    fun navigateToTraining() {
        router.navigateTo(Training())
    }

    fun navigateToExam() {
        router.navigateTo(Exam())
    }
}