package com.dantropov.medtest.ui.base

import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

class ViewBindingHolder<T : ViewBinding> {

    private var _binding: T? = null

    val binding: T
        get() = _binding!!

    fun createView(lifecycleOwner: LifecycleOwner, inflater: () -> T): View {
        val newBinding = inflater.invoke()
        _binding = newBinding
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                _binding = null
            }
        })
        return newBinding.root
    }
}