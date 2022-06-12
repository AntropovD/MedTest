package com.dantropov.medtest.util.animation

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.widget.TextView
import com.dantropov.medtest.R

object TextViewAnimation {
    fun animateCorrectAnswer(textView: TextView) {
        val backgroundColorAnimator = ObjectAnimator.ofObject(
            textView,
            "backgroundColor",
            ArgbEvaluator(),
            Color.WHITE,
            textView.resources.getColor(R.color.colorCorrect, null)
        )
        backgroundColorAnimator.duration = 300
        backgroundColorAnimator.repeatCount = 1
        backgroundColorAnimator.start()
    }
}