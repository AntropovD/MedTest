package com.dantropov.medtest.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dantropov.medtest.MedTestApp
import com.dantropov.medtest.databinding.FragmentTrainingBinding
import com.dantropov.medtest.mvp.training.TrainingPresenter
import com.dantropov.medtest.mvp.training.TrainingView
import com.dantropov.medtest.ui.common.BackButtonListener
import com.github.terrakok.cicerone.Router
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class TrainingFragment : MvpAppCompatFragment(), TrainingView, BackButtonListener {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: TrainingPresenter

    @ProvidePresenter
    fun createPresenter2() = TrainingPresenter(router)

    override fun onCreate(savedInstanceState: Bundle?) {
        MedTestApp.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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