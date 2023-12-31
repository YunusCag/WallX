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
    iconSizeMedium = 32.dp,
    iconSizeLarge = 64.dp,

    borderWidth = 1.dp,
    circularLoadingWidth = 2.dp,

    tabHeight = 40.dp,
    collectionItemHeight = 150.dp,
    favouritePhotoAspectRatio = 0.6f,

    collectionProfileImage = 40.dp,
    favouriteButtonSize = 36.dp,

    large1 = 72.dp,

    screenType = ScreenType.COMPACT
)
val mediumDimensions = WallXDimen(
    paddingSmall1 = 8.dp,
    paddingSmall2 = 12.dp,
    paddingSmall3 = 16.dp,

    paddingMedium1 = 24.dp,
    paddingMedium2 = 32.dp,

    borderWidth = 2.dp,
    circularLoadingWidth = 3.dp,

    iconSizeSmall = 32.dp,
    iconSizeMedium = 36.dp,
    iconSizeLarge = 72.dp,

    tabHeight = 50.dp,
    collectionItemHeight = 200.dp,
    favouritePhotoAspectRatio = 1f,

    collectionProfileImage = 50.dp,
    favouriteButtonSize = 48.dp,

    large1 = 96.dp,

    screenType = ScreenType.MEDIUM
)
val LargeDimensions = WallXDimen(
    paddingSmall1 = 12.dp,
    paddingSmall2 = 16.dp,
    paddingSmall3 = 32.dp,

    paddingMedium1 = 32.dp,
    paddingMedium2 = 40.dp,

    borderWidth = 3.dp,
    circularLoadingWidth = 4.dp,

    iconSizeSmall = 36.dp,
    iconSizeMedium = 40.dp,
    iconSizeLarge = 80.dp,

    tabHeight = 60.dp,
    collectionItemHeight = 250.dp,
    favouritePhotoAspectRatio = 1.6f,

    collectionProfileImage = 60.dp,
    favouriteButtonSize = 64.dp,

    large1 = 128.dp,

    screenType = ScreenType.LARGE
)

data class WallXDimen(
    val paddingSmall1: Dp = 0.dp,
    val paddingSmall2: Dp = 0.dp,
    val paddingSmall3: Dp = 0.dp,

    val paddingMedium1: Dp = 0.dp,
    val paddingMedium2: Dp = 0.dp,
    val paddingMedium3: Dp = 0.dp,

    val iconSizeSmall: Dp = 0.dp,
    val iconSizeMedium: Dp = 0.dp,
    val iconSizeLarge: Dp = 0.dp,
    val large1: Dp = 0.dp,
    val large2: Dp = 0.dp,

    val borderWidth: Dp = 0.dp,
    val circularLoadingWidth: Dp = 0.dp,

    val tabHeight: Dp = 0.dp,
    val collectionItemHeight: Dp = 0.dp,
    val favouritePhotoAspectRatio: Float = 1f,

    val collectionProfileImage: Dp = 0.dp,
    val favouriteButtonSize: Dp = 0.dp,

    val defaultBannerHeight: Dp = 50.dp,

    val screenType: ScreenType = ScreenType.LARGE
)

enum class ScreenType {
    COMPACT,
    MEDIUM,
    LARGE
}