package com.yunuscagliyan.core_ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SettingItemModel(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val action: SettingItemAction
)


enum class SettingItemAction {
    THEME,
    RATE,
    FEEDBACK,
    SHARE_APP
}


enum class ThemeSelection(val index: Int) {
    SYSTEM(0),
    DARK(1),
    LIGHT(2);

    companion object {
        fun fromIndex(index: Int) = values().getOrNull(index)
    }
}