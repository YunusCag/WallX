package com.yunuscagliyan.core_ui.model.enums

import androidx.annotation.StringRes
import com.yunuscagliyan.core_ui.R
import java.util.concurrent.TimeUnit


enum class PeriodicTimeType(
    @StringRes val text: Int,
    val repeatInterval:Int,
    val timeUnit:TimeUnit
) {
    MINUTES_15(
        text = R.string.settings_period_1,
        repeatInterval = 15,
        timeUnit = TimeUnit.MINUTES
    ),
    MINUTES_30(
        text = R.string.settings_period_2,
        repeatInterval = 30,
        timeUnit = TimeUnit.MINUTES
    ),
    HOUR_1(
        text = R.string.settings_period_3,
        repeatInterval = 1,
        timeUnit = TimeUnit.HOURS
    ),
    HOUR_3(
        text = R.string.settings_period_4,
        repeatInterval = 3,
        timeUnit = TimeUnit.HOURS
    ),
    HOUR_6(
        text = R.string.settings_period_5,
        repeatInterval = 6,
        timeUnit = TimeUnit.HOURS
    ),
    HOUR_12(
        text = R.string.settings_period_6,
        repeatInterval = 12,
        timeUnit = TimeUnit.HOURS
    ),
    HOUR_24(
        text = R.string.settings_period_7,
        repeatInterval = 24,
        timeUnit = TimeUnit.HOURS
    ),
    HOUR_48(
        text = R.string.settings_period_8,
        repeatInterval = 48,
        timeUnit = TimeUnit.HOURS
    );

    companion object {
        fun fromIndex(index: Int) = values().getOrNull(index)
    }
}