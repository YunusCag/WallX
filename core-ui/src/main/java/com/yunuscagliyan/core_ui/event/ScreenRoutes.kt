package com.yunuscagliyan.core_ui.event

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.COLLECTION_ID
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.COLLECTION_NAME
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_HEX_COLOR_KEY
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_URL_KEY

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

    object PhotoDetailScreen :
        ScreenRoutes("photo_detail_screen?$PHOTO_URL_KEY={$PHOTO_URL_KEY}&$PHOTO_HEX_COLOR_KEY={$PHOTO_HEX_COLOR_KEY}") {
        fun navigate(photoModel: PhotoModel) =
            "photo_detail_screen?$PHOTO_URL_KEY=${photoModel.urls?.small}&$PHOTO_HEX_COLOR_KEY=${photoModel.color}"
    }
}
