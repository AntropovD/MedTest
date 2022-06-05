package com.dantropov.medtest.dagger

import com.dantropov.medtest.dagger.module.NavigationModule
import com.dantropov.medtest.ui.main.MainActivity
import com.dantropov.medtest.ui.main.StartFragment
import com.dantropov.medtest.ui.training.TrainingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: StartFragment)

    fun inject(fragment: TrainingFragment)
}