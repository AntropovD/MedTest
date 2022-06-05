package com.dantropov.medtest.mvp.training

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface TrainingView : MvpView {

    @AddToEndSingle
    fun setTitle(text: String)

    @OneExecution
    fun openBrowser(url: String)
}