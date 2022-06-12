package com.dantropov.medtest.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dantropov.medtest.databinding.FragmentStartBinding
import com.dantropov.medtest.util.view.ViewBindingHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {

    private val bindingHolder = ViewBindingHolder<FragmentStartBinding>()
    private val binding get() = bindingHolder.binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindingHolder.createView(viewLifecycleOwner) {
        FragmentStartBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvTraining.setOnClickListener { navigateToTraining() }
        binding.cvExam.setOnClickListener { navigateToExam() }
    }

    private fun navigateToTraining() {
        findNavController().navigate(StartFragmentDirections.actionStartFragmentToTrainingFragment())
    }

    private fun navigateToExam() {
    }
}