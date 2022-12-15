package com.dantropov.medtest.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dantropov.medtest.R
import java.security.InvalidParameterException

enum class Screen { Start, Training, Quiz, Exam }

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }
    when (to) {
        Screen.Start -> {
            findNavController().navigate(R.id.startFragment)
        }
        Screen.Training -> {
            findNavController().navigate(R.id.trainingFragment)
        }
        Screen.Quiz -> {
            findNavController().navigate(R.id.quizFragment)
        }
        Screen.Exam -> {
            // TODO
            findNavController().navigate(R.id.trainingFragment)
        }
    }
}
