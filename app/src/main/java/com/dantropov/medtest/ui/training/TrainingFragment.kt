package com.dantropov.medtest.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dantropov.medtest.databinding.FragmentTrainingBinding
import com.dantropov.medtest.ui.base.ViewBindingHolder
import com.dantropov.medtest.ui.training.adapter.TrainingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrainingFragment : Fragment() {

    private val bindingHolder = ViewBindingHolder<FragmentTrainingBinding>()
    private val binding get() = bindingHolder.binding
    private var initJob: Job? = null
    private val adapter = TrainingAdapter()

    val viewModel: TrainingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindingHolder.createView(viewLifecycleOwner) {
        FragmentTrainingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.plantList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    adapter.submitData(uiState.trainingLevels)
                }
            }
        }
    }
}