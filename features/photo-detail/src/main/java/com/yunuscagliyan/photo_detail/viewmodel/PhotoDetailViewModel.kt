package com.yunuscagliyan.photo_detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.domain.DownloadImage
import com.yunuscagliyan.core.util.Constant
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_DOWNLOAD_URL_KEY
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_HASH_KEY
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_URL_KEY
import com.yunuscagliyan.core.util.DownloadState
import com.yunuscagliyan.core_ui.components.button.LoadingButtonType
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.photo_detail.ui.PhotoDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val downloadImage: DownloadImage
) : CoreViewModel<PhotoDetailState, PhotoDetailEvent>() {
    override fun getInitialState(): PhotoDetailState = PhotoDetailState()

    init {
        initState()
    }

    private fun initState() {
        val url = savedStateHandle.get<String>(PHOTO_URL_KEY)
        val blurHash = savedStateHandle.get<String>(PHOTO_HASH_KEY)
        val downloadUrl = savedStateHandle.get<String>(PHOTO_DOWNLOAD_URL_KEY)
        updateState {
            copy(
                imageUrl = url,
                blurHash = blurHash,
                downloadUrl = downloadUrl
            )
        }


    }

    override fun onEvent(event: PhotoDetailEvent) {
        when (event) {
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
                state.value.downloadUrl?.let {
                    viewModelScope.launch(Dispatchers.IO) {
                        downloadImage.invoke(imageUrl = it).collectLatest { downloadState ->
                            updateState {
                                when (downloadState) {
                                    is DownloadState.Error -> {
                                        copy(
                                            isSaveLoading = false,
                                            isSaveFinished = false,
                                            saveButtonType = LoadingButtonType.ERROR
                                        )
                                    }

                                    is DownloadState.Finished -> {
                                        copy(
                                            isSaveFinished = true,
                                            isSaveLoading = false,
                                            saveButtonType = LoadingButtonType.SUCCESS
                                        )
                                    }

                                    is DownloadState.Loading -> {
                                        copy(
                                            isSaveFinished = false,
                                            isSaveLoading = true,
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