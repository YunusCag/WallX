package com.yunuscagliyan.core_ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {
    private var navArguments = mutableStateMapOf<String, Any?>()

    fun setArgument(key: String, data: Any?) {
        navArguments[key] = data
    }

    fun getArgument(key: String): Any? {
        return navArguments[key]
    }
}