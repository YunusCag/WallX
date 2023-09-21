package com.yunuscagliyan.core_ui.model.enums

import androidx.annotation.StringRes
import com.yunuscagliyan.core_ui.R

enum class PeriodicTimeType(
    @StringRes val text: Int
) {
    MINUTES_15(
        text = R.string.settings_period_1
    ),
    MINUTES_30(
        text = R.string.settings_period_2
    ),
    HOUR_1(
        text = R.string.settings_period_3
    ),
    HOUR_3(
        text = R.string.settings_period_4
    ),
    HOUR_6(
        text = R.string.settings_period_5
    ),
    HOUR_12(
        text = R.string.settings_period_6
    ),
    HOUR_24(
        text = R.string.settings_period_7
    ),
    HOUR_48(
        text = R.string.settings_period_8
    );

    companion object {
        fun fromIndex(index: Int) = values().getOrNull(index)
    }
}