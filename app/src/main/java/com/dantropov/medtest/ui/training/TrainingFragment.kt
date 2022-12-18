package com.dantropov.medtest.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
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
                        when (targetState) {
                            is TrainingUiState.Ready -> ScaffoldWithTopBarReady(targetState) {
                                viewModel.trainingLevelClick(it)
                            }
                            is TrainingUiState.Empty -> ScaffoldWithTopBarLoading()
                        }
                    }
                }
            }
        }
    }
}