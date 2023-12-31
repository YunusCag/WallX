package com.yunuscagliyan.core_ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


val LightColor = WallXAppColor(
    primary = Color(0xFF263238),
    primaryDark = Color(0xFF263238),
    secondary = Color(0xFFAB47BC),
    accent = Color(0xFF1B5E20),
    background = Color(0xFFFAFAFA),
    black = Color(0xFF000000),
    white = Color(0xFFFFFFFF),
    textPrimary = Color(0xFF000000),
    secondaryGray = Color(0xFF8294A8),
    card = Color(0xFFFFFFFF),
    bottomBar = Color(0xFFF5F5F5),
    dividerColor = Color(0xFF9e9e9e).copy(
        alpha = 0.3f
    ),
    error = Color(0xFFF44336),
    warning = Color(0xFFFFEB3B),
    success = Color(0xFF4CAF50),
    info = Color(0xFF03A9F4),
    isDark = false
)

val DarkColor = WallXAppColor(
    primary = Color(0xFF263238),
    primaryDark = Color(0xFF263238),
    secondary = Color(0xFFAB47BC),
    accent = Color(0xFF1B5E20),
    background = Color(0xFF37474F),
    black = Color(0xFF000000),
    white = Color(0xFFFFFFFF),
    textPrimary = Color(0xFFFFFFFF),
    secondaryGray = Color(0xFF8294A8),
    card = Color(0xFF546E7A),
    bottomBar = Color(0xFF263238),
    dividerColor = Color(0xFF9e9e9e).copy(
        alpha = 0.3f
    ),
    error = Color(0xFFF44336),
    warning = Color(0xFFFFEB3B),
    success = Color(0xFF4CAF50),
    info = Color(0xFF03A9F4),
    isDark = true
)

@Stable
class WallXAppColor(
    white: Color,
    black: Color,
    primary: Color,
    primaryDark: Color,
    secondary: Color,
    accent: Color,
    background: Color,
    textPrimary: Color,
    secondaryGray: Color,
    card: Color,
    bottomBar: Color,
    dividerColor: Color,
    error: Color,
    warning: Color,
    success: Color,
    info: Color,
    isDark: Boolean
) {
    var white by mutableStateOf(white)
        private set
    var black by mutableStateOf(black)
        private set
    var primary by mutableStateOf(primary)
        private set
    var primaryDark by mutableStateOf(primaryDark)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var accent by mutableStateOf(accent)
        private set
    var background by mutableStateOf(background)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var secondaryGray by mutableStateOf(secondaryGray)
        private set
    var card by mutableStateOf(card)
        private set
    var bottomBar by mutableStateOf(bottomBar)
        private set
    var dividerColor by mutableStateOf(dividerColor)
        private set
    var error by mutableStateOf(error)
        private set
    var warning by mutableStateOf(warning)
        private set
    var success by mutableStateOf(success)
        private set
    var info by mutableStateOf(info)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun copy(): WallXAppColor = WallXAppColor(
        white = white,
        black = black,
        primary = primary,
        primaryDark = primaryDark,
        secondary = secondary,
        accent = accent,
        background = background,
        textPrimary = textPrimary,
        secondaryGray = secondaryGray,
        card = card,
        bottomBar = bottomBar,
        dividerColor = dividerColor,
        error = error,
        warning = warning,
        success = success,
        info = info,
        isDark = isDark
    )

    fun update(other: WallXAppColor) {
        white = other.white
        black = other.black
        primary = other.primary
        primaryDark = other.primaryDark
        secondary = other.secondary
        accent = other.accent
        background = other.background
        textPrimary = other.textPrimary
        secondaryGray = other.secondaryGray
        card = other.card
        bottomBar = other.bottomBar
        dividerColor = other.dividerColor
        error = other.error
        warning = other.warning
        success = other.success
        info = other.info
        isDark = other.isDark
    }
}