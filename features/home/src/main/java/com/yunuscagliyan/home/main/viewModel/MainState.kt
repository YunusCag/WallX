package com.yunuscagliyan.home.main.viewModel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.event.ScreenRoutes

@Stable
data class MainState(
    val toolbarTitle: String = "",
    val bottomBarItems: List<MainNavigationItem> = listOf(
        MainNavigationItem(
            name = R.string.bottom_bar_home_title,
            icon = R.drawable.ic_home,
            navRoute = ScreenRoutes.HomeScreen.route
        ),
        MainNavigationItem(
            name = R.string.bottom_bar_category_title,
            icon = R.drawable.ic_category,
            navRoute = ScreenRoutes.CategoryScreen.route
        ),
        MainNavigationItem(
            name = R.string.bottom_bar_favourites_title,
            icon = R.drawable.ic_heart,
            navRoute = ScreenRoutes.FavouriteScreen.route
        ),
        MainNavigationItem(
            name = R.string.bottom_bar_settings_title,
            icon = R.drawable.ic_settings,
            navRoute = ScreenRoutes.SettingsScreen.route
        ),
    )
)


data class MainNavigationItem(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val navRoute: String
)
