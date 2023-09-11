package com.yunuscagliyan.core_ui.event

import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.COLLECTION_ID
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.COLLECTION_NAME

sealed class ScreenRoutes(val route: String) {
    object MainScreen : ScreenRoutes("main_screen")
    object HomeScreen : ScreenRoutes("home_screen")
    object CategoryScreen : ScreenRoutes("category_screen")
    object FavouriteScreen : ScreenRoutes("favourite_screen")
    object SettingsScreen : ScreenRoutes("settings_screen")
    object PhotoListScreen :
        ScreenRoutes("photo_list_screen/{$COLLECTION_ID}/{$COLLECTION_NAME}") {
        fun navigate(collectionId: String?, collectionName: String?) =
            "photo_list_screen/$collectionId/$collectionName"
    }
}
