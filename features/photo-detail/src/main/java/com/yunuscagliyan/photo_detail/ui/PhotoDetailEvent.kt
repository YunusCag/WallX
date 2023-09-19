package com.yunuscagliyan.photo_detail.ui

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel

sealed class PhotoDetailEvent {

    data class InitPhotoModel(
        val photoModel: PhotoModel
    ) : PhotoDetailEvent()

    object OnBackPress : PhotoDetailEvent()
    data class OnFavouriteClick(val isFavourite: Boolean) : PhotoDetailEvent()
    object OnSaveClick : PhotoDetailEvent()
    object OnSetClick : PhotoDetailEvent()

    object OnDismissErrorDialog:PhotoDetailEvent()
    object OnOkayClickErrorDialog:PhotoDetailEvent()
    data class BottomSheet(val isOpen: Boolean) : PhotoDetailEvent()
    data class OnScreenSelection(val index: Int) : PhotoDetailEvent()

}
