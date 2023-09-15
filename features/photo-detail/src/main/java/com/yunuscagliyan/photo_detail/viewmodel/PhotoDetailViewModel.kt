package com.yunuscagliyan.photo_detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.domain.DownloadImage
import com.yunuscagliyan.core.util.DownloadState
import com.yunuscagliyan.core_ui.components.button.LoadingButtonType
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.photo_detail.ui.PhotoDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val downloadImage: DownloadImage
) : CoreViewModel<PhotoDetailState, PhotoDetailEvent>() {
    override fun getInitialState(): PhotoDetailState = PhotoDetailState()


    override fun onEvent(event: PhotoDetailEvent) {
        when (event) {
            is PhotoDetailEvent.InitPhotoModel -> {
                updateState {
                    copy(
                        photoModel = event.photoModel
                    )
                }
            }

            is PhotoDetailEvent.OnBackPress -> {
                popBack()
            }

            is PhotoDetailEvent.OnFavouriteClick -> {
                // TODO Insert / Delete row RoomDB
                updateState {
                    copy(
                        isFavourite = event.isFavourite
                    )
                }
            }

            is PhotoDetailEvent.OnSaveClick -> {
                state.value.photoModel?.links?.download?.let {
                    viewModelScope.launch(Dispatchers.IO) {
                        downloadImage.invoke(imageUrl = it).collectLatest { downloadState ->
                            updateState {
                                when (downloadState) {
                                    is DownloadState.Error -> {
                                        copy(
                                            saveButtonType = LoadingButtonType.ERROR
                                        )
                                    }

                                    is DownloadState.Finished -> {
                                        copy(
                                            saveButtonType = LoadingButtonType.SUCCESS
                                        )
                                    }

                                    is DownloadState.Loading -> {
                                        copy(
                                            saveButtonType = LoadingButtonType.LOADING
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is PhotoDetailEvent.OnSetClick -> {
                // TODO open Choose Screen Bottom Sheet
            }
        }
    }
}