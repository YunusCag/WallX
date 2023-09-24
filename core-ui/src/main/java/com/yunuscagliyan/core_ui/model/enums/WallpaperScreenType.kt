package com.yunuscagliyan.core_ui.model.enums

import androidx.annotation.StringRes
import com.yunuscagliyan.core_ui.R

enum class WallpaperScreenType(
    @StringRes val text: Int
) {
    HOME(
        text = R.string.settings_screen_home
    ),
    LOCK(
        text = R.string.settings_screen_lock
    ),
    HOME_AND_LOCK(
        text = R.string.settings_screen_home_and_lock
    );
    companion object {
        fun fromIndex(index: Int) = values().getOrNull(index)
    }
}