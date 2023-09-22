package com.yunuscagliyan.core_ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.model.enums.PeriodicTimeType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {
    private var navArguments = mutableStateMapOf<String, Any?>()

    val sharedState = mutableStateOf(SharedState())

    init {
        initState()
    }

    private fun initState() {
        val themeIndex = preferences.themeIndex
        val periodicIndex = preferences.periodIndex
        sharedState.value = sharedState.value.copy(
            themeSelection = ThemeSelection.fromIndex(themeIndex) ?: ThemeSelection.SYSTEM,
            periodicTimeType = PeriodicTimeType.fromIndex(periodicIndex)
                ?: PeriodicTimeType.MINUTES_15
        )
    }

    fun setArgument(key: String, data: Any?) {
        navArguments[key] = data
    }

    fun getArgument(key: String): Any? {
        return navArguments[key]
    }

    fun changeTheme(selection: ThemeSelection) {
        sharedState.value = sharedState.value.copy(
            themeSelection = selection
        )
    }
}