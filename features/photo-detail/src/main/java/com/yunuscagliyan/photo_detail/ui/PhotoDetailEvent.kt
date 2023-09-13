package com.yunuscagliyan.photo_detail.ui

sealed class PhotoDetailEvent {

    object OnBackPress : PhotoDetailEvent()
    data class OnFavouriteClick(val isFavourite: Boolean) : PhotoDetailEvent()
}
