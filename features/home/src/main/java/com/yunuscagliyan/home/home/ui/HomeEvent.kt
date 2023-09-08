package com.yunuscagliyan.home.home.ui

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel

sealed class HomeEvent {

    data class OnPhotoClick(val photoModel: PhotoModel) : HomeEvent()
}
