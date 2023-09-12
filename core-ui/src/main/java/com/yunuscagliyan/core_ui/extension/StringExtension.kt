package com.yunuscagliyan.core_ui.extension

import androidx.compose.ui.graphics.Color
import timber.log.Timber

fun String.toColor(): Color? {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: Exception) {
        Timber.e(e.localizedMessage)
        null
    }

}