package com.yunuscagliyan.core_ui.event

sealed class ScreenRoutes(val route:String){
    object MainScreen: ScreenRoutes("main_screen")
    object HomeScreen: ScreenRoutes("home_screen")
    object CategoryScreen: ScreenRoutes("category_screen")
    object FavouriteScreen: ScreenRoutes("favourite_screen")
    object SettingsScreen: ScreenRoutes("settings_screen")
}
