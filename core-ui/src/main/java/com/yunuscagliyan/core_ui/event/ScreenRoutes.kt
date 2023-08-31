package com.yunuscagliyan.core_ui.event

sealed class ScreenRoutes(val route:String){
    object MainScreen: ScreenRoutes("main_screen")
}
