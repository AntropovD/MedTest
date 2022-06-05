package com.dantropov.medtest

import android.content.Intent
import com.dantropov.medtest.ui.main.MainActivity
import com.dantropov.medtest.ui.main.StartFragment
import com.dantropov.medtest.ui.training.TrainingFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun Start() = FragmentScreen("Start") {
        StartFragment.getNewInstance()
    }

    fun Training() = FragmentScreen("Training") {
        TrainingFragment.getNewInstance()
    }

    fun Exam() = FragmentScreen("Exam") {
        StartFragment.getNewInstance()
    }

    fun Main() = ActivityScreen {
        Intent(it, MainActivity::class.java)
    }
}