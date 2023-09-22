package com.yunuscagliyan.core.util

class Constant {


    object StringParameter {
        const val EMPTY_STRING = ""
        const val DEFAULT_SEARCH_VALUE = "Wallpapers"
    }

    object NetworkPathUtil {
        const val PHOTOS_PATH = "photos"
        const val SEARCH_COLLECTION_PATH = "search/collections"
        const val SEARCH_PHOTO_PATH = "search/photos"
        const val COLLECTION_PHOTOS = "collections/{collection_id}/photos"
        const val RANDOM_PHOTO = "photos/random"
    }

    object NetworkQueryParamKey {
        const val QUERY = "query"
        const val PAGE = "page"
        const val PER_PAGE = "per_page"
        const val ORDER_BY = "order_by"
        const val CLIENT_ID = "client_id"
        const val COLLECTION_ID = "collection_id"
        const val ORIENTATION = "orientation"
    }

    object PaginationUtil {
        const val PER_PAGE = 20
    }

    object NetworkCacheUtil {
        const val CACHE_HEADER_KEY = "Cache-Control"
        const val CACHE_FILE_NAME = "offlineCache"
        const val MAX_SIZE = 10L * 1024L * 1024L
        const val TIME_OUT_SECONDS = 120L
        const val MAX_AGE_DAY = 10
    }

    object DBUtil {
        const val PHOTO_ENTITY_NAME = "photo_entity"
        const val DB_VERSION = 1
        const val DB_NAME = "photo_db"
    }

    object PreferencesUtil {
        const val PREFERENCE_NAME = "wall_preferences"
    }

    object NavigationArgumentKey {
        const val COLLECTION_ID = "collection_id"
        const val COLLECTION_NAME = "collection_name"
        const val PHOTO_KEY = "photo_key"
    }

    object DurationUtil {
        const val DEFAULT_ANIMATION_BUTTON_DURATION = 100
        const val DEFAULT_ANIMATION_DURATION = 300L
        const val TRANSITION_DURATION = 500
        const val SHIMMER_DURATION = 1000
        const val SPLASH_DURATION = 2000
        const val DEFAULT_DELAY = 200L
    }

    object FileUtil {
        const val PUBLIC_FOLDER_NAME = "WallX"
    }

    object WorkManagerUtil {
        const val AUTO_WALLPAPER_MANAGER_NAME = "auto_wallpaper_manager"
    }
}