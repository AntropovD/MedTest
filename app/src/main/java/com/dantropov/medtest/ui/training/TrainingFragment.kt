package com.dantropov.medtest.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dantropov.medtest.navigation.Screen
import com.dantropov.medtest.navigation.navigate
import com.dantropov.medtest.theme.MedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrainingFragment : Fragment() {

    //    private val bindingHolder = ViewBindingHolder<FragmentTrainingBinding>()
//    private val binding get() = bindingHolder.binding
//    private val adapter = TrainingAdapter()
//    private val viewModel: TrainingViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View = bindingHolder.createView(viewLifecycleOwner) {
//        FragmentTrainingBinding.inflate(inflater, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.plantList.adapter = adapter
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.uiState.collect { uiState ->
//                    when (uiState) {
//                        is TrainingUiState.Ready -> {
//                            adapter.submitData(uiState.trainingLevels)
//                        }
//                        else -> {}
//                    }
//                }
//            }
//        }
//    }
    private val viewModel: TrainingViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.Start)
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                MedTheme {
                    val state = viewModel.uiState.observeAsState().value ?: return@MedTheme
                    AnimatedContent(
                        targetState = state,
                        transitionSpec = {
                            fadeIn() with fadeOut()
                        }
                    ) { targetState ->
                        // It's important to use targetState and not state, as its critical to ensure
                        // a successful lookup of all the incoming and outgoing content during
                        // content transform.
                        when (targetState) {
                            is TrainingUiState.Ready -> ScaffoldWithTopBarReady(targetState)
                            is TrainingUiState.Empty -> ScaffoldWithTopBarLoading()
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val ANIMATION_FADE_OUT_DURATION = 200
    }
}