package com.yunuscagliyan.search.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.search.viewModel.SearchState
import com.yunuscagliyan.search.viewModel.SearchViewModel

object SearchScreen:CoreScreen<SearchState,SearchEvent>() {
    override val route: String
        get() = ScreenRoutes.SearchScreen.route

    @Composable
    override fun viewModel(): SearchViewModel = hiltViewModel()

    @Composable
    override fun Content(state: SearchState, onEvent: (SearchEvent) -> Unit) {
        MainUIFrame {

        }
    }
}