package com.dantropov.medtest

import android.app.Application
import com.dantropov.medtest.dagger.AppComponent
import com.dantropov.medtest.dagger.DaggerAppComponent

class MedTestApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: MedTestApp
    }
}