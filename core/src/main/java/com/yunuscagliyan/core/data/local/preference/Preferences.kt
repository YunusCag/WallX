package com.yunuscagliyan.core.data.local.preference

interface Preferences {
    var themeIndex: Int

    companion object {
        const val THEME_KEY = "THEME_KEY"
    }
}