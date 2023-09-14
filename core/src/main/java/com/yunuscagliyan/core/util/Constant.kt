package com.yunuscagliyan.core.util

class Constant {


    object StringParameter {
        const val EMPTY_STRING = ""
    }

    object NetworkPathUtil {
        const val PHOTOS_PATH = "photos"
        const val SEARCH_COLLECTION_PATH = "search/collections"
        const val COLLECTION_PHOTOS = "collections/{collection_id}/photos"
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
        const val TIME_OUT_SECONDS = 60L
        const val MAX_AGE_DAY = 10
    }

    object NavigationArgumentKey {
        const val COLLECTION_ID = "collection_id"
        const val COLLECTION_NAME = "collection_name"
        const val PHOTO_URL_KEY = "photo_url_key"
        const val PHOTO_HASH_KEY = "photo_hash_key"
        const val PHOTO_DOWNLOAD_URL_KEY = "photo_download_url_key"
    }

    object DurationUtil {
        const val DEFAULT_ANIMATION_BUTTON_DURATION = 100
        const val DEFAULT_ANIMATION_DURATION = 300L
        const val TRANSITION_DURATION = 500
        const val SHIMMER_DURATION = 1000
    }

    object FileUtil {
        const val PUBLIC_FOLDER_NAME = "WallX"
    }
}