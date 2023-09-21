package com.yunuscagliyan.home.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.card.SettingCard
import com.yunuscagliyan.core_ui.components.sheet.SingleSelectionBottomSheet
import com.yunuscagliyan.core_ui.components.tile.SettingItemTile
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.model.SelectionModel
import com.yunuscagliyan.core_ui.model.SettingItemAction
import com.yunuscagliyan.core_ui.model.SettingItemModel
import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.home.settings.viewModel.SettingState
import com.yunuscagliyan.home.settings.viewModel.SettingViewModel

object SettingScreen : CoreScreen<SettingState, SettingEvent>() {
    override val route: String
        get() = ScreenRoutes.SettingsScreen.route

    @Composable
    override fun viewModel(): SettingViewModel = hiltViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(state: SettingState, onEvent: (SettingEvent) -> Unit) {
        if (state.showThemeBottomSheet) {
            SingleSelectionBottomSheet(
                title = stringResource(id = R.string.settings_theme),
                selections = listOf(
                    SelectionModel(
                        title = stringResource(id = R.string.settings_theme_system),
                    ),
                    SelectionModel(
                        title = stringResource(id = R.string.settings_theme_dark),
                    ),
                    SelectionModel(
                        title = stringResource(id = R.string.settings_theme_light),
                    )
                ),
                onDismissRequest = {
                    onEvent(SettingEvent.ThemeBottomSheet(false))
                },
                selectedIndex = state.selectedTheme.index,
                onSelect = { index, _ ->
                    onEvent(
                        SettingEvent.OnThemeClicked(
                            themeSelection = ThemeSelection.fromIndex(index)
                                ?: ThemeSelection.SYSTEM
                        )
                    )
                }
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingMedium1))
            Section(
                title = stringResource(id = R.string.settings_general),
                settingItems = state.generalItems,
                onClick = { action ->
                    when (action) {
                        SettingItemAction.THEME -> {
                            onEvent(SettingEvent.ThemeBottomSheet(true))
                        }

                        else -> Unit
                    }
                }
            )
            Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingMedium1))
            Section(
                title = stringResource(id = R.string.settings_about),
                settingItems = state.aboutItems,
                onClick = { action ->
                    when (action) {
                        SettingItemAction.RATE -> {

                        }

                        SettingItemAction.FEEDBACK -> {

                        }

                        SettingItemAction.SHARE_APP -> {

                        }

                        else -> Unit
                    }
                }
            )

        }
    }

    @Composable
    private fun Section(
        title: String,
        settingItems: List<SettingItemModel>,
        onClick: (SettingItemAction) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = title,
                style = WallXAppTheme.typography.title2,
                color = WallXAppTheme.colors.textPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = WallXAppTheme.dimension.paddingSmall3,
                    )
            )
            Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingSmall2))
            SettingCard(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                settingItems.forEachIndexed() { index, item ->
                    SettingItemTile(
                        modifier = Modifier
                            .noRippleClickable {
                                onClick(item.action)
                            },
                        title = stringResource(id = item.title),
                        icon = item.icon
                    )
                    if (settingItems.size - 1 != index) {
                        Divider(
                            color = WallXAppTheme.colors.dividerColor,
                            thickness = WallXAppTheme.dimension.borderWidth
                        )
                    }
                }
            }
        }
    }
}