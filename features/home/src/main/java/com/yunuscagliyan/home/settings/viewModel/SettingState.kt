package com.yunuscagliyan.home.settings.viewModel

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core_ui.model.SettingItemModel
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.model.SettingItemAction
import com.yunuscagliyan.core_ui.model.ThemeSelection


@Stable
data class SettingState(
    val showThemeBottomSheet: Boolean = false,
    val selectedTheme: ThemeSelection = ThemeSelection.SYSTEM,
    val generalItems: List<SettingItemModel> = listOf(
        SettingItemModel(
            title = R.string.settings_theme,
            icon = R.drawable.ic_paint,
            action = SettingItemAction.THEME
        )
    ),
    val aboutItems: List<SettingItemModel> = listOf(
        SettingItemModel(
            title = R.string.settings_rate_us,
            icon = R.drawable.ic_star,
            action = SettingItemAction.RATE
        ),
        SettingItemModel(
            title = R.string.settings_send_feedback,
            icon = R.drawable.ic_email,
            action = SettingItemAction.FEEDBACK
        ),
        SettingItemModel(
            title = R.string.settings_share_app,
            icon = R.drawable.ic_comment,
            action = SettingItemAction.SHARE_APP
        ),
    )
)
