package com.dantropov.medtest.compose.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dantropov.medtest.navigation.Screen
import com.dantropov.medtest.util.Event

open class BaseViewModel : ViewModel() {
    protected val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> = _navigateTo
}