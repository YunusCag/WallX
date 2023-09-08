package com.yunuscagliyan.core_ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object WallXAppTheme {
    val colors: WallXAppColor
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val shapes: WallXShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current

    val typography: WallXTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimension: WallXDimen
        @Composable
        @ReadOnlyComposable
        get() = LocalDimension.current

}

private val LocalAppColors = compositionLocalOf<WallXAppColor> {
    DarkColor
}

private val LocalAppShapes = compositionLocalOf<WallXShapes> {
    LargeShapes
}

private val LocalTypography = compositionLocalOf<WallXTypography> {
    LargeTypography
}
private val LocalDimension = compositionLocalOf<WallXDimen> {
    LargeDimensions
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun WallXAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val activity = LocalContext.current as Activity
    val colors = if (darkTheme) {
        DarkColor
    } else {
        LightColor
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = activity.window
            window.statusBarColor = colors.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val rememberedColors = remember { colors.copy() }.apply { update(colors) }

    val window = calculateWindowSizeClass(activity = activity)

    var shapes = remember{LargeShapes}
    var typography = remember{LargeTypography}
    var dimension = remember{LargeDimensions}

    when (window.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            shapes = compactShapes
            typography = compactTypography
            dimension = compactDimensions
        }

        WindowWidthSizeClass.Medium -> {
            shapes = mediumShapes
            typography = MediumTypography
            dimension = mediumDimensions
        }

        WindowWidthSizeClass.Expanded -> {
            shapes = LargeShapes
            typography = LargeTypography
            dimension = LargeDimensions
        }
    }

    CompositionLocalProvider(
        LocalAppColors provides rememberedColors,
        LocalAppShapes provides shapes,
        LocalTypography provides typography,
        LocalDimension provides dimension,
        content = content
    )
}