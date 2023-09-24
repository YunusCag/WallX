package com.yunuscagliyan.core_ui.viewmodel

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.model.enums.PeriodicTimeType

@Stable
data class SharedState(
    val themeSelection: ThemeSelection = ThemeSelection.SYSTEM,
    val periodicTimeType: PeriodicTimeType = PeriodicTimeType.MINUTES_15
)
