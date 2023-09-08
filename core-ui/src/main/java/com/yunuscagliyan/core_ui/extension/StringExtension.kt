package com.yunuscagliyan.core_ui.extension

import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}