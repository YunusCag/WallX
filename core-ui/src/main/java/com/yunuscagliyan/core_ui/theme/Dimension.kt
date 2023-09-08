package com.yunuscagliyan.core_ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



val compactDimensions = WallXDimen(
    paddingSmall1 = 4.dp,
    paddingSmall2 = 8.dp,
    paddingSmall3 = 12.dp,

    paddingMedium1 = 16.dp,
    paddingMedium2 = 24.dp,

    iconSizeSmall = 24.dp,
    iconSizeLarge = 64.dp,

    borderWidth = 1.dp,

    tabHeight = 30.dp,
    screenType = ScreenType.COMPACT
)
val mediumDimensions = WallXDimen(
    paddingSmall1 = 8.dp,
    paddingSmall2 = 12.dp,
    paddingSmall3 = 16.dp,

    paddingMedium1 = 24.dp,
    paddingMedium2 = 32.dp,

    borderWidth = 2.dp,
    iconSizeSmall = 32.dp,
    iconSizeLarge = 72.dp,

    tabHeight = 40.dp,

    screenType = ScreenType.MEDIUM
)
val LargeDimensions = WallXDimen(
    paddingSmall1 = 12.dp,
    paddingSmall2 = 16.dp,
    paddingSmall3 = 32.dp,

    paddingMedium1 = 32.dp,
    paddingMedium2 = 40.dp,

    borderWidth = 3.dp,
    iconSizeSmall = 40.dp,
    iconSizeLarge = 80.dp,

    tabHeight = 50.dp,
    screenType = ScreenType.LARGE
)

data class WallXDimen(
    val paddingSmall1:Dp = 0.dp,
    val paddingSmall2:Dp = 0.dp,
    val paddingSmall3:Dp = 0.dp,

    val paddingMedium1:Dp = 0.dp,
    val paddingMedium2:Dp = 0.dp,
    val paddingMedium3:Dp = 0.dp,

    val iconSizeSmall:Dp = 0.dp,
    val iconSizeMedium:Dp = 0.dp,
    val iconSizeLarge:Dp = 0.dp,
    val large1:Dp = 0.dp,
    val large2:Dp = 0.dp,

    val borderWidth: Dp = 0.dp,

    val tabHeight: Dp = 0.dp,
    val screenType: ScreenType = ScreenType.LARGE
)

enum class ScreenType {
    COMPACT,
    MEDIUM,
    LARGE
}