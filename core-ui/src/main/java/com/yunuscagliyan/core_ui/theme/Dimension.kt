package com.yunuscagliyan.core_ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



val compactDimensions = WallXDimen(
    screenType = ScreenType.COMPACT
)
val mediumDimensions = WallXDimen(
    screenType = ScreenType.MEDIUM
)
val LargeDimensions = WallXDimen(
    screenType = ScreenType.LARGE
)

data class WallXDimen(
    val small1:Dp = 0.dp,
    val small2:Dp = 0.dp,
    val small3:Dp = 0.dp,
    val medium1:Dp = 0.dp,
    val medium2:Dp = 0.dp,
    val medium3:Dp = 0.dp,
    val large1:Dp = 0.dp,
    val large2:Dp = 0.dp,
    val screenType: ScreenType = ScreenType.LARGE
)

enum class ScreenType {
    COMPACT,
    MEDIUM,
    LARGE
}