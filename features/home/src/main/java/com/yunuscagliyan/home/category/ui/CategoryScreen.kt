package com.yunuscagliyan.home.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.home.category.viewModel.CategoryState
import com.yunuscagliyan.home.category.viewModel.CategoryViewModel


object CategoryScreen : CoreScreen<CategoryState, CategoryEvent>() {
    override val route: String
        get() = ScreenRoutes.CategoryScreen.route

    @Composable
    override fun viewModel(): CategoryViewModel = hiltViewModel()

    @Composable
    override fun Content(
        state: CategoryState,
        onEvent: (CategoryEvent) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
        ) {

        }
    }
}