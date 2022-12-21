package com.dantropov.medtest.util

import android.os.Bundle

data class Event<out T>(private val content: T, private val bundle: Bundle? = null) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun getBundle(): Bundle? = bundle

    fun peekContent(): T = content
}
