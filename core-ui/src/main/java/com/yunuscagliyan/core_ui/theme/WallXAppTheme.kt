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
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
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

private val LocalAppColors = staticCompositionLocalOf<WallXAppColor> {
    lightColor
}

private val LocalAppShapes = staticCompositionLocalOf<WallXShapes> {
    error("WallXAppShapes was not assigned.")
}

private val LocalTypography = staticCompositionLocalOf<WallXTypography> {
    error("WallXAppTypography was not assigned.")
}
private val LocalDimension = staticCompositionLocalOf<WallXDimen> {
    error("WallXAppDimen was not assigned.")
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun WallXAppThemeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    activity: Activity = LocalContext.current as Activity,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColor
    } else {
        lightColor
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val rememberedColors = remember { colors.copy() }.apply { update(colors) }

    val window = calculateWindowSizeClass(activity = activity)

    var shapes = largeShapes
    var typography = largeTypography
    var dimension = largeDimensions

    when (window.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            shapes = compactShapes
            typography = compactTypography
            dimension = compactDimensions
        }

        WindowWidthSizeClass.Medium -> {
            shapes = mediumShapes
            typography = mediumTypography
            dimension = mediumDimensions
        }

        WindowWidthSizeClass.Expanded -> {
            shapes = largeShapes
            typography = largeTypography
            dimension = largeDimensions
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