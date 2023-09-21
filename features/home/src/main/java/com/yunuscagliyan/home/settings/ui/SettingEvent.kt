package com.yunuscagliyan.home.settings.ui

import com.yunuscagliyan.core_ui.model.ThemeSelection

sealed class SettingEvent {

    data class AutoChangeWallpaper(val auto: Boolean) : SettingEvent()
    data class ThemeBottomSheet(val open: Boolean) : SettingEvent()

    data class OnThemeClicked(val themeSelection: ThemeSelection) : SettingEvent()
}