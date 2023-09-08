package com.yunuscagliyan.home.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.home.settings.viewModel.SettingState
import com.yunuscagliyan.home.settings.viewModel.SettingViewModel

object SettingScreen : CoreScreen<SettingState, SettingEvent>() {
    override val route: String
        get() = ScreenRoutes.SettingsScreen.route

    @Composable
    override fun viewModel(): SettingViewModel = hiltViewModel()

    @Composable
    override fun Content(state: SettingState, onEvent: (SettingEvent) -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow)
        ) {

        }
    }
}