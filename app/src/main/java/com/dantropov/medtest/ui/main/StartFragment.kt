package com.dantropov.medtest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dantropov.medtest.databinding.FragmentStartBinding
import com.dantropov.medtest.mvp.main.StartPresenter
import com.dantropov.medtest.mvp.main.StartView
import com.dantropov.medtest.ui.common.ViewBindingHolder
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : MvpAppCompatFragment(), StartView {

    private val bindingHolder = ViewBindingHolder<FragmentStartBinding>()
    private val binding get() = bindingHolder.binding

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: StartPresenter

    @ProvidePresenter
    fun createPresenter() = StartPresenter(router)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindingHolder.createView(viewLifecycleOwner) {
        FragmentStartBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvTraining.setOnClickListener { presenter.navigateToTraining() }
        binding.cvExam.setOnClickListener { presenter.navigateToExam() }
    }

    companion object {
        fun getNewInstance(): StartFragment = StartFragment()
    }
}