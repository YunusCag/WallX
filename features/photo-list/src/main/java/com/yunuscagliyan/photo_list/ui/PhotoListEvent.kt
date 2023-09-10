package com.yunuscagliyan.photo_list.ui

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel

sealed class PhotoListEvent {
    object OnBackPress : PhotoListEvent()
    data class OnPhotoClick(val photoModel: PhotoModel) : PhotoListEvent()
}
