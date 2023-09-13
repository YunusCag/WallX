package com.yunuscagliyan.photo_detail.viewmodel

import androidx.compose.runtime.Stable

@Stable
data class PhotoDetailState(
    val imageUrl: String? = null,
    val blurHash: String? = null,
    val isFavourite: Boolean = false
)
