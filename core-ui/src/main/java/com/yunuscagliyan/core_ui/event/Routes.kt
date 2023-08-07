package com.yunuscagliyan.core_ui.event

import androidx.navigation.NavOptions

sealed class Routes{
    data class NavigateToRoute(
        val pageRoute: String,
        val options: RouteNavigationOptions? = null
    ):Routes()

    data class PopBackRoute(
        val route: String,
        val inclusive: Boolean = false
    ): Routes()

    object PopBack: Routes()
}


data class RouteNavigationOptions(
    val popUpTo: String,
    val inclusive: Boolean = false
) {
    fun getNavOptions() = NavOptions.Builder().setPopUpTo(popUpTo,inclusive).build()
}