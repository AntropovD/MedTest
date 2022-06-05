package com.dantropov.medtest.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dantropov.medtest.MedTestApp
import com.dantropov.medtest.R
import com.dantropov.medtest.Screens.Start
import com.dantropov.medtest.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.dantropov.medtest.ui.common.BackButtonListener
import com.dantropov.medtest.ui.common.ChainHolder
import com.github.terrakok.cicerone.*
import moxy.MvpAppCompatActivity
import java.lang.ref.WeakReference
import java.util.ArrayList
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), ChainHolder {

    override val chain = ArrayList<WeakReference<Fragment>>()

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private lateinit var binding: ActivityMainBinding

    private val navigator: Navigator = object : AppNavigator(this, R.id.main_container) {
        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MedTestApp.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Forward(Start())))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            super.onBackPressed()
        }
    }
}