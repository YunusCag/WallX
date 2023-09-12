package com.yunuscagliyan.photo_detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_HEX_COLOR_KEY
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_URL_KEY
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.photo_detail.ui.PhotoDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : CoreViewModel<PhotoDetailState, PhotoDetailEvent>() {
    override fun getInitialState(): PhotoDetailState = PhotoDetailState()

    init {
        initState()
    }

    private fun initState() {
        val url = savedStateHandle.get<String>(PHOTO_URL_KEY)
        val hexColorString = savedStateHandle.get<String>(PHOTO_HEX_COLOR_KEY)
        updateState {
            copy(
                imageUrl = url,
                hexColorString = hexColorString
            )
        }


    }

    override fun onEvent(event: PhotoDetailEvent) {

    }
}