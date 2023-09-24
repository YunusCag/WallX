package com.yunuscagliyan.core_ui.model.enums

import androidx.annotation.StringRes
import com.yunuscagliyan.core_ui.R

enum class SourceType(
    @StringRes val text: Int
) {
    RANDOM(
        text = R.string.settings_source_random
    ),
    FAVOURITE(
        text = R.string.settings_source_Favourite
    );

    companion object {
        fun fromIndex(index: Int) = values().getOrNull(index)
    }
}