package com.yunuscagliyan.core.data.enums

enum class WallpaperScreenType(val value: Int) {
    Home(0),
    Lock(1),
    Both(2);

    companion object {
        fun fromIndex(index: Int): WallpaperScreenType? = values().getOrNull(index)
    }
}