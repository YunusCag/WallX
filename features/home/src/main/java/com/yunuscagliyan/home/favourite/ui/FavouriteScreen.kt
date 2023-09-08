package com.yunuscagliyan.home.favourite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.home.favourite.viewModel.FavouriteState
import com.yunuscagliyan.home.favourite.viewModel.FavouriteViewModel

object FavouriteScreen : CoreScreen<FavouriteState, FavouriteEvent>() {
    override val route: String
        get() = ScreenRoutes.FavouriteScreen.route

    @Composable
    override fun viewModel(): FavouriteViewModel = hiltViewModel()

    @Composable
    override fun Content(
        state: FavouriteState,
        onEvent: (FavouriteEvent) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
        ) {

        }
    }
}