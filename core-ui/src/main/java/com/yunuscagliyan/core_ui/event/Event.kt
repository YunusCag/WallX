package com.yunuscagliyan.core_ui.event

import androidx.compose.material3.SnackbarDuration
import com.yunuscagliyan.core.util.UIText

sealed class Event {
    data class Navigation(val state: Routes) : Event()

    data class ShowSnackBar(
        val message: UIText,
        val actionLabel: UIText? = null,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val onClick: (() -> Unit)? = null
    ) : Event()

    data class Toast(
        val message: UIText
    ) : Event()
}
