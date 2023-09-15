package com.yunuscagliyan.core_ui.model

import androidx.annotation.DrawableRes

data class SelectionModel(
    val title: String,
    @DrawableRes val iconId: Int? = null
)