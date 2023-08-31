package com.yunuscagliyan.home.main.viewModel

import androidx.compose.runtime.Stable

@Stable
data class MainState(
    val toolbarTitle: String = ""
)


data class MainNavigationItem(
    val name: String,
)
