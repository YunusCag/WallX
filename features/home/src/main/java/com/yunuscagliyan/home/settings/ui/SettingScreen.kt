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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.anim.AnimatedTransition
import com.yunuscagliyan.core_ui.components.card.SettingCard
import com.yunuscagliyan.core_ui.components.sheet.SingleSelectionBottomSheet
import com.yunuscagliyan.core_ui.components.tile.SettingItemSwitchTile
import com.yunuscagliyan.core_ui.components.tile.SettingItemTile
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.extension.cancelAllWorkManager
import com.yunuscagliyan.core_ui.extension.navigateFeedback
import com.yunuscagliyan.core_ui.extension.navigateRateApp
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.extension.shareApp
import com.yunuscagliyan.core_ui.extension.startWorkManager
import com.yunuscagliyan.core_ui.model.SelectionModel
import com.yunuscagliyan.core_ui.model.SettingItemAction
import com.yunuscagliyan.core_ui.model.SettingItemModel
import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.model.enums.PeriodicTimeType
import com.yunuscagliyan.core_ui.model.enums.WallpaperScreenType
import com.yunuscagliyan.core_ui.model.enums.SourceType
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.home.settings.viewModel.SettingState
import com.yunuscagliyan.home.settings.viewModel.SettingViewModel

object SettingScreen : CoreScreen<SettingState, SettingEvent>() {
    override val route: String
        get() = ScreenRoutes.SettingsScreen.route

    @Composable
    override fun viewModel(): SettingViewModel = hiltViewModel()

    @Composable
    override fun Content(state: SettingState, onEvent: (SettingEvent) -> Unit) {
        val context = LocalContext.current

        LaunchedEffect(
            key1 = state.autoChangeWallpaper,
            key2 = state.selectedPeriodicTimeType
        ) {
            if (state.autoChangeWallpaper) {
                context.startWorkManager(state.selectedPeriodicTimeType)
            } else {
                context.cancelAllWorkManager()
            }
        }

        if (state.showThemeBottomSheet) {
            ThemeSheet(
                selectedTheme = state.selectedTheme,
                onDismissRequest = {
                    onEvent(SettingEvent.ThemeBottomSheet(false))
                },
                onSelect = {
                    onEvent(
                        SettingEvent.OnThemeClicked(
                            themeSelection = it
                        )
                    )
                }
            )
        }
        if (state.showPeriodicBottomSheet) {
            PeriodicTimeTypeSheet(
                selectedType = state.selectedPeriodicTimeType,
                onSelect = {
                    onEvent(SettingEvent.OnClickPeriodicType(it))
                },
                onDismissRequest = {
                    onEvent(SettingEvent.PeriodicTimeBottomSheet(false))
                }
            )
        }

        if (state.showSourceBottomSheet) {
            SourceTypeSheet(
                selectedType = state.selectedSourceType,
                onSelect = {
                    onEvent(SettingEvent.OnClickSourceType(it))
                },
                onDismissRequest = {
                    onEvent(SettingEvent.SourceTimeBottomSheet(false))
                }
            )
        }

        if (state.showScreenBottomSheet) {
            ScreenTypeSheet(
                selectedType = state.selectedScreenType,
                onSelect = {
                    onEvent(SettingEvent.OnClickScreenType(it))
                },
                onDismissRequest = {
                    onEvent(SettingEvent.ScreenBottomSheet(false))
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
                },
                onBottom = {
                    SettingItemSwitchTile(
                        modifier = Modifier
                            .noRippleClickable {
                                val value = state.autoChangeWallpaper.not()
                                onEvent(SettingEvent.AutoChangeWallpaper(value))
                            },
                        title = stringResource(id = R.string.settings_auto_change_wallpaper),
                        icon = R.drawable.ic_wallpaper,
                        checked = state.autoChangeWallpaper,
                        onCheckedChange = {
                            onEvent(SettingEvent.AutoChangeWallpaper(it))
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingMedium1))
            AnimatedTransition(visible = state.autoChangeWallpaper) {
                Column {
                    Text(
                        text = stringResource(id = R.string.settings_auto_change_title),
                        style = WallXAppTheme.typography.title2,
                        color = WallXAppTheme.colors.textPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = WallXAppTheme.dimension.paddingSmall3,
                            )
                    )
                    Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingSmall2))
                    SettingCard {
                        SettingItemTile(
                            modifier = Modifier
                                .noRippleClickable {
                                    onEvent(SettingEvent.PeriodicTimeBottomSheet(true))
                                },
                            title = stringResource(id = R.string.settings_auto_periodic),
                            icon = R.drawable.ic_alarm_clock,
                            description = stringResource(id = state.selectedPeriodicTimeType.text)
                        )
                        Divider(
                            color = WallXAppTheme.colors.dividerColor,
                            thickness = WallXAppTheme.dimension.borderWidth
                        )
                        /*
                        SettingItemTile(
                            modifier = Modifier
                                .noRippleClickable {
                                    onEvent(SettingEvent.SourceTimeBottomSheet(true))
                                },
                            title = stringResource(id = R.string.settings_source),
                            icon = R.drawable.ic_source_type,
                            description = stringResource(id = state.selectedSourceType.text)
                        )
                        Divider(
                            color = WallXAppTheme.colors.dividerColor,
                            thickness = WallXAppTheme.dimension.borderWidth
                        )*/
                        SettingItemTile(
                            modifier = Modifier
                                .noRippleClickable {
                                    onEvent(SettingEvent.ScreenBottomSheet(true))
                                },
                            title = stringResource(id = R.string.settings_screen_type),
                            icon = R.drawable.ic_background,
                            description = stringResource(id = state.selectedScreenType.text)
                        )
                    }
                    Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingMedium1))
                }
            }
            Section(
                title = stringResource(id = R.string.settings_about),
                settingItems = state.aboutItems,
                onClick = { action ->
                    when (action) {
                        SettingItemAction.RATE -> {
                            context.navigateRateApp()
                        }

                        SettingItemAction.FEEDBACK -> {
                            context.navigateFeedback()
                        }

                        SettingItemAction.SHARE_APP -> {
                            context.shareApp()
                        }

                        else -> Unit
                    }
                }
            )

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ThemeSheet(
        onDismissRequest: () -> Unit,
        selectedTheme: ThemeSelection,
        onSelect: (ThemeSelection) -> Unit
    ) {
        SingleSelectionBottomSheet(
            title = stringResource(id = R.string.settings_theme),
            selections = listOf(
                SelectionModel(
                    title = stringResource(id = R.string.settings_theme_system),
                ),
                SelectionModel(
                    title = stringResource(id = R.string.settings_theme_light),
                ),
                SelectionModel(
                    title = stringResource(id = R.string.settings_theme_dark),
                )
            ),
            onDismissRequest = onDismissRequest,
            selectedIndex = selectedTheme.index,
            onSelect = { index, _ ->
                val selection = ThemeSelection.fromIndex(index)
                    ?: ThemeSelection.SYSTEM
                onSelect(selection)
                sharedViewModel?.changeTheme(selection)
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun PeriodicTimeTypeSheet(
        selectedType: PeriodicTimeType,
        onSelect: (PeriodicTimeType) -> Unit,
        onDismissRequest: () -> Unit
    ) {
        SingleSelectionBottomSheet(
            title = stringResource(id = R.string.settings_auto_periodic),
            selections = PeriodicTimeType.values().map {
                SelectionModel(
                    title = stringResource(id = it.text)
                )
            }.toList(),
            onDismissRequest = onDismissRequest,
            selectedIndex = selectedType.ordinal,
            onSelect = { index, _ ->
                onSelect(PeriodicTimeType.fromIndex(index) ?: PeriodicTimeType.MINUTES_15)
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SourceTypeSheet(
        selectedType: SourceType,
        onSelect: (SourceType) -> Unit,
        onDismissRequest: () -> Unit
    ) {
        SingleSelectionBottomSheet(
            title = stringResource(id = R.string.settings_source),
            selections = SourceType.values().map {
                SelectionModel(
                    title = stringResource(id = it.text)
                )
            }.toList(),
            onDismissRequest = onDismissRequest,
            selectedIndex = selectedType.ordinal,
            onSelect = { index, _ ->
                onSelect(SourceType.fromIndex(index) ?: SourceType.RANDOM)
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScreenTypeSheet(
        selectedType: WallpaperScreenType,
        onSelect: (WallpaperScreenType) -> Unit,
        onDismissRequest: () -> Unit
    ) {
        SingleSelectionBottomSheet(
            title = stringResource(id = R.string.settings_screen_type),
            selections = WallpaperScreenType.values().map {
                SelectionModel(
                    title = stringResource(id = it.text)
                )
            }.toList(),
            onDismissRequest = onDismissRequest,
            selectedIndex = selectedType.ordinal,
            onSelect = { index, _ ->
                onSelect(WallpaperScreenType.fromIndex(index) ?: WallpaperScreenType.HOME_AND_LOCK)
            }
        )
    }

    @Composable
    private fun Section(
        title: String,
        settingItems: List<SettingItemModel>,
        onBottom: (@Composable () -> Unit)? = null,
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
                    if (settingItems.size - 1 != index || onBottom != null) {
                        Divider(
                            color = WallXAppTheme.colors.dividerColor,
                            thickness = WallXAppTheme.dimension.borderWidth
                        )
                    }
                }
                onBottom?.invoke()
            }
        }
    }
}