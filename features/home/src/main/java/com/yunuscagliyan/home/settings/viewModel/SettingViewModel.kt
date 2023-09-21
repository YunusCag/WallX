package com.yunuscagliyan.home.settings.viewModel

import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core_ui.model.ThemeSelection
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
                    ?: ThemeSelection.SYSTEM
            )
        }
    }

    override fun onEvent(event: SettingEvent) {
        when (event) {
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