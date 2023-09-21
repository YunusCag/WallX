package com.yunuscagliyan.home.settings.ui

import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.model.enums.PeriodicTimeType
import com.yunuscagliyan.core_ui.model.enums.ScreenType
import com.yunuscagliyan.core_ui.model.enums.SourceType

sealed class SettingEvent {

    data class AutoChangeWallpaper(val auto: Boolean) : SettingEvent()
    data class ThemeBottomSheet(val open: Boolean) : SettingEvent()
    data class OnThemeClicked(val themeSelection: ThemeSelection) : SettingEvent()

    data class PeriodicTimeBottomSheet(val open: Boolean):SettingEvent()
    data class SourceTimeBottomSheet(val open: Boolean):SettingEvent()
    data class ScreenBottomSheet(val open: Boolean):SettingEvent()
    data class OnClickPeriodicType(val type: PeriodicTimeType) : SettingEvent()
    data class OnClickSourceType(val type: SourceType) : SettingEvent()
    data class OnClickScreenType(val type: ScreenType) : SettingEvent()
}