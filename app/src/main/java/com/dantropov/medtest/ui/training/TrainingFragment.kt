package com.dantropov.medtest.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dantropov.medtest.MedTestApp
import com.dantropov.medtest.databinding.FragmentStartBinding
import com.dantropov.medtest.databinding.FragmentTrainingBinding
import com.dantropov.medtest.mvp.training.TrainingPresenter
import com.dantropov.medtest.mvp.training.TrainingView
import com.dantropov.medtest.ui.common.BackButtonListener
import com.dantropov.medtest.ui.common.ViewBindingHolder
import com.github.terrakok.cicerone.Router
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class TrainingFragment : MvpAppCompatFragment(), TrainingView, BackButtonListener {

    private val bindingHolder = ViewBindingHolder<FragmentTrainingBinding>()
    private val binding get() = bindingHolder.binding

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: TrainingPresenter

    @ProvidePresenter
    fun createPresenter() = TrainingPresenter(router)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindingHolder.createView(viewLifecycleOwner) {
        FragmentTrainingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun setTitle(text: String) {
    }

    override fun openBrowser(url: String) {
    }

    companion object {
        fun getNewInstance(): TrainingFragment = TrainingFragment()
    }
}