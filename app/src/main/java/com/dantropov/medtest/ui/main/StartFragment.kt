package com.dantropov.medtest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dantropov.medtest.MedTestApp
import com.dantropov.medtest.databinding.FragmentStartBinding
import com.dantropov.medtest.mvp.main.StartPresenter
import com.dantropov.medtest.mvp.main.StartView
import com.github.terrakok.cicerone.Router
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class StartFragment : MvpAppCompatFragment(), StartView {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: StartPresenter

    @ProvidePresenter
    fun createPresenter() = StartPresenter(router)

    override fun onCreate(savedInstanceState: Bundle?) {
        MedTestApp.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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