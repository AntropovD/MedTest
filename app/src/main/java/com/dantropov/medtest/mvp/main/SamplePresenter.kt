package com.dantropov.medtest.mvp.main

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.mvp.main.SampleView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SamplePresenter(private val router: Router) : MvpPresenter<SampleView>() {

}