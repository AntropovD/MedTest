package com.dantropov.medtest.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dantropov.medtest.navigation.Screen
import com.dantropov.medtest.navigation.navigate
import com.dantropov.medtest.theme.MedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {

    private val viewModel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.init()
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.Start, navigateToEvent.getBundle())
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                MedTheme {
                    StartScreen(
                        onEvent = { event ->
                            when (event) {
                                StartEvent.NavigateToTraining -> viewModel.navigateToTraining()
                                StartEvent.NavigateToExam -> viewModel.navigateToExam()
                                StartEvent.NavigateToStats -> viewModel.navigateToStats()
                            }
                        }
                    )
                }
            }
        }
    }
}