package com.yunuscagliyan.home.favourite.ui

import com.yunuscagliyan.core.data.local.entity.PhotoEntity

sealed class FavouriteEvent {
    object InitPhotoList : FavouriteEvent()

    data class OnPhotoClick(val entity: PhotoEntity) : FavouriteEvent()

    data class OnFavouriteClick(val index: Int, val entity: PhotoEntity) : FavouriteEvent()
}
