package com.yunuscagliyan.photo_detail.viewmodel

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core_ui.components.button.LoadingButtonType

@Stable
data class PhotoDetailState(
    val imageUrl: String? = null,
    val blurHash: String? = null,
    val isFavourite: Boolean = false,
    val downloadUrl: String? = null,
    val isSaveLoading: Boolean = false,
    val isSaveFinished: Boolean = false,
    val saveButtonType: LoadingButtonType = LoadingButtonType.INIT,
    val setButtonType: LoadingButtonType = LoadingButtonType.INIT,
)
