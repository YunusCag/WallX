package com.yunuscagliyan.core_ui.viewmodel

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core_ui.model.ThemeSelection

@Stable
data class SharedState(
    val themeSelection: ThemeSelection = ThemeSelection.SYSTEM
)
