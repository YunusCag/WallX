package com.yunuscagliyan.home.favourite.ui

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel

sealed class FavouriteEvent {
    object InitPhotoList : FavouriteEvent()

    data class OnPhotoClick(val photoModel: PhotoModel) : FavouriteEvent()

    data class OnFavouriteClick(val index: Int, val photoModel: PhotoModel) : FavouriteEvent()
}
