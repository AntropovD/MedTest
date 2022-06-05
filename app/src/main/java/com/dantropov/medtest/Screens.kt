package com.dantropov.medtest

import android.content.Intent
import com.dantropov.medtest.ui.main.MainActivity
import com.dantropov.medtest.ui.main.SampleFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun Sample(number: Int) = FragmentScreen("Sample($number)") {
        SampleFragment.getNewInstance(number)
    }

    fun Main() = ActivityScreen {
        Intent(it, MainActivity::class.java)
    }
}