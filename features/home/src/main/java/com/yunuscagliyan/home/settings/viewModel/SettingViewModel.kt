package com.yunuscagliyan.home.settings.viewModel

import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.settings.ui.SettingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

) : CoreViewModel<SettingState, SettingEvent>() {
    override fun getInitialState(): SettingState = SettingState()

    override fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.OnThemeClicked -> {
                updateState {
                    copy(
                        selectedTheme = event.themeSelection
                    )
                }
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