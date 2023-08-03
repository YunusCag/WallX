package com.yunuscagliyan.core_ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


val lightColor = WallXAppColor(
    primary = Color(0xFF202020),
    primaryDark = Color(0xFF202020),
    secondary = Color(0xFF4A148C),
    accent = Color(0xFFE65100),
    background = Color(0xFFF5F5F5),
    black = Color(0xFF000000),
    white = Color(0xFFFFFFFF),
    textPrimary = Color(0xFFFFFFFF),
    secondaryGray = Color(0xFF8294A8),
    card = Color(0xFF8294A8),
    bottomBar = Color(0xFF673AB7),
    dividerColor = Color(0xFF9e9e9e),
    isDark = false
)

val darkColor = WallXAppColor(
    primary = Color(0xFF202020),
    primaryDark = Color(0xFF202020),
    secondary = Color(0xFF4A148C),
    accent = Color(0xFFE65100),
    background = Color(0xFF000000),
    black = Color(0xFF000000),
    white = Color(0xFFFFFFFF),
    textPrimary = Color(0xFFFFFFFF),
    secondaryGray = Color(0xFF8294A8),
    card = Color(0xFF8294A8),
    bottomBar = Color(0xFF673AB7),
    dividerColor = Color(0xFF9e9e9e),
    isDark = false
)

@Stable
class WallXAppColor(
    white:Color,
    black:Color,
    primary:Color,
    primaryDark:Color,
    secondary:Color,
    accent:Color,
    background:Color,
    textPrimary:Color,
    secondaryGray:Color,
    card:Color,
    bottomBar:Color,
    dividerColor:Color,
    isDark:Boolean
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
        bottomBar =bottomBar,
        dividerColor = dividerColor,
        isDark = isDark
    )

    fun update(other:WallXAppColor) {
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
        isDark = other.isDark
    }
}