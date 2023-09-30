package com.yunuscagliyan.home.favourite.viewModel

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.data.local.entity.PhotoEntity
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel

@Stable
data class FavouriteState(
    val photoList: List<PhotoEntity> = emptyList()
)
