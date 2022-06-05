package com.dantropov.medtest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dantropov.medtest.MedTestApp
import com.dantropov.medtest.databinding.FragmentSampleBinding
import com.dantropov.medtest.mvp.main.SamplePresenter
import com.dantropov.medtest.ui.common.BackButtonListener
import com.dantropov.medtest.ui.common.BaseFragment
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.mvp.main.SampleView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SampleFragment : BaseFragment(), SampleView, BackButtonListener {

    private var _binding: FragmentSampleBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: SamplePresenter

    @ProvidePresenter
    fun createSamplePresenter() = SamplePresenter(router)

    override val name: String
        get() = requireArguments().getInt(EXTRA_NUMBER).toString()
    override val creationTime: Long
        get() = requireArguments().getLong(EXTRA_TIME, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        MedTestApp.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setTitle(title: String) {
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    companion object {
        private const val EXTRA_NUMBER = "extra_number"
        private const val EXTRA_TIME = "extra_time"

        fun getNewInstance(number: Int): SampleFragment {
            return SampleFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_NUMBER, number)
                    putLong(EXTRA_TIME, System.currentTimeMillis())
                }
            }
        }
    }
}