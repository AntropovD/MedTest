package com.dantropov.medtest

import android.app.Application
import com.github.terrakok.cicerone.Cicerone

class MedTestApp : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: MedTestApp
            private set
    }
}