package com.yunuscagliyan.core.data.local.preference

interface Preferences {
    var themeIndex: Int
    var autoChange: Boolean
    var periodIndex: Int
    var sourceIndex: Int
    var screenIndex: Int


    companion object {
        const val THEME_KEY = "theme_key"
        const val AUTO_CHANGE_KEY = "auto_change_key"
        const val PERIOD_INDEX_KEY = "period_index_key"
        const val SOURCE_INDEX_KEY = "source_index_key"
        const val SCREEN_INDEX_KEY = "screen_index_key"
    }
}