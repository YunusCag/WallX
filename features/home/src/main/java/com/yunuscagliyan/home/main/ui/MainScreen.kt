package com.yunuscagliyan.home.main.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.main.viewModel.MainNavigationItem
import com.yunuscagliyan.home.main.viewModel.MainState
import com.yunuscagliyan.home.main.viewModel.MainViewModel

object MainScreen : CoreScreen<MainState, MainEvent>() {
    override val route: String
        get() = ScreenRoutes.MainScreen.route

    @Composable
    override fun viewModel(): MainViewModel = hiltViewModel<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(
        state: MainState,
        onEvent: (MainEvent) -> Unit
    ) {
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf<MainNavigationItem>(
            MainNavigationItem(name = "Home"),
            MainNavigationItem(name = "Category"),
            MainNavigationItem(name = "Favourites"),
            MainNavigationItem(name = "Settings"),
        )
        MainUIFrame(
            topBar = {
                TopAppBar(title = {
                    Text(text = "WallX")
                })
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = index == selectedItem,
                            onClick = { selectedItem = index },
                            icon = {
                                Icon(
                                    when (index) {
                                        0 -> Icons.Filled.Home
                                        1 -> Icons.Rounded.Star
                                        2 -> Icons.Filled.Favorite
                                        3 -> Icons.Filled.Settings
                                        else -> Icons.Filled.Home
                                    },
                                    contentDescription = item.name
                                )
                            },
                            label = {
                                Text(item.name)
                            },
                            alwaysShowLabel = false
                        )
                    }
                }
            }
        ) {

        }
    }
}