package com.yunuscagliyan.home.settings.viewModel

import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.model.enums.PeriodicTimeType
import com.yunuscagliyan.core_ui.model.enums.WallpaperScreenType
import com.yunuscagliyan.core_ui.model.enums.SourceType
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.settings.ui.SettingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val preferences: Preferences
) : CoreViewModel<SettingState, SettingEvent>() {
    override fun getInitialState(): SettingState = SettingState()

    init {
        initState()
    }

    private fun initState() {
        updateState {
            copy(
                selectedTheme = ThemeSelection.fromIndex(preferences.themeIndex)
                    ?: ThemeSelection.SYSTEM,
                autoChangeWallpaper = preferences.autoChange,
                selectedPeriodicTimeType = PeriodicTimeType.fromIndex(preferences.periodIndex)
                    ?: PeriodicTimeType.MINUTES_15,
                selectedSourceType = SourceType.fromIndex(preferences.sourceIndex)
                    ?: SourceType.RANDOM,
                selectedScreenType = WallpaperScreenType.fromIndex(preferences.screenIndex)
                    ?: WallpaperScreenType.HOME_AND_LOCK
            )
        }
    }

    override fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.AutoChangeWallpaper -> {
                updateState {
                    copy(
                        autoChangeWallpaper = event.auto
                    )
                }
                preferences.autoChange = event.auto
            }

            is SettingEvent.PeriodicTimeBottomSheet -> {
                updateState {
                    copy(
                        showPeriodicBottomSheet = event.open
                    )
                }
            }

            is SettingEvent.SourceTimeBottomSheet -> {
                updateState {
                    copy(
                        showSourceBottomSheet = event.open
                    )
                }
            }

            is SettingEvent.ScreenBottomSheet -> {
                updateState {
                    copy(
                        showScreenBottomSheet = event.open
                    )
                }
            }

            is SettingEvent.OnClickPeriodicType -> {
                updateState {
                    copy(
                        selectedPeriodicTimeType = event.type
                    )
                }
                preferences.periodIndex = event.type.ordinal
            }

            is SettingEvent.OnClickSourceType -> {
                updateState {
                    copy(
                        selectedSourceType = event.type
                    )
                }
                preferences.sourceIndex = event.type.ordinal
            }

            is SettingEvent.OnClickScreenType -> {
                updateState {
                    copy(
                        selectedScreenType = event.type
                    )
                }
                preferences.screenIndex = event.type.ordinal
            }

            is SettingEvent.OnThemeClicked -> {
                updateState {
                    copy(
                        selectedTheme = event.themeSelection
                    )
                }
                preferences.themeIndex = event.themeSelection.index

            }

            is SettingEvent.ThemeBottomSheet -> {
                updateState {
                    copy(
                        showThemeBottomSheet = event.open
                    )
                }
            }
        }
    }

}