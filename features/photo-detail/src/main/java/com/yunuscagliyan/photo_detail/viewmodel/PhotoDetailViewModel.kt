package com.yunuscagliyan.photo_detail.viewmodel

import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core_ui.model.enums.WallpaperScreenType
import com.yunuscagliyan.core.data.repository.PhotoRepository
import com.yunuscagliyan.core_ui.domain.ChangeWallpaper
import com.yunuscagliyan.core.domain.DownloadImageAndSave
import com.yunuscagliyan.core_ui.domain.DownloadImageAsBitmap
import com.yunuscagliyan.core.util.DownloadState
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core.util.UIText
import com.yunuscagliyan.core_ui.components.button.LoadingButtonType
import com.yunuscagliyan.core_ui.event.Event
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.photo_detail.ui.PhotoDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val downloadImageAndSave: DownloadImageAndSave,
    private val downloadImageAsBitmap: DownloadImageAsBitmap,
    private val changeWallpaper: ChangeWallpaper,
    private val repository: PhotoRepository
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
                event.photoModel.id?.let {
                    checkPhotoLiked(
                        photoId = it
                    )
                }
            }

            is PhotoDetailEvent.OnBackPress -> {
                popBack()
            }

            is PhotoDetailEvent.OnFavouriteClick -> {
                updateState {
                    copy(
                        isFavourite = event.isFavourite
                    )
                }
                state.value.photoModel?.let { photoModel ->
                    viewModelScope.launch(Dispatchers.IO) {
                        if (event.isFavourite) {
                            repository.insertPhoto(photoModel = photoModel)
                        } else {
                            photoModel.id?.let { id ->
                                repository.deletePhoto(photoId = id)
                            }
                        }
                    }
                }
            }

            is PhotoDetailEvent.OnSaveClick -> {
                updateState {
                    copy(
                        shouldShowRewarded = false
                    )
                }
                downloadAndSaveImage()
            }

            is PhotoDetailEvent.OnSetClick -> {
                if (state.value.setBitmap != null) {
                    updateState {
                        copy(
                            showWallpaperSelectionSheet = true
                        )
                    }
                } else {
                    downloadBitmap()
                }
            }

            is PhotoDetailEvent.OnDismissErrorDialog -> {
                updateState {
                    copy(
                        showErrorDialog = false
                    )
                }
            }

            is PhotoDetailEvent.OnOkayClickErrorDialog -> {
                updateState {
                    copy(
                        showErrorDialog = false,
                        saveButtonType = if (saveButtonType == LoadingButtonType.ERROR) LoadingButtonType.INIT else saveButtonType,
                        setButtonType = if (setButtonType == LoadingButtonType.ERROR) LoadingButtonType.INIT else setButtonType,
                    )
                }
            }

            is PhotoDetailEvent.BottomSheet -> {
                updateState {
                    copy(
                        showWallpaperSelectionSheet = event.isOpen
                    )
                }
            }

            is PhotoDetailEvent.ShowAdBottomSheet -> {
                updateState {
                    copy(
                        showWatchAdSheet = event.open
                    )
                }
            }

            is PhotoDetailEvent.ShowRewardAdError -> {
                sendEvent(
                    Event.Toast(
                        message = UIText.StringResource(
                            resId = R.string.photo_detail_reward_ad_error
                        )
                    )
                )
            }

            is PhotoDetailEvent.OnScreenSelection -> {
                setWallpaper(
                    index = event.index
                )

                updateState {
                    copy(
                        sheetSelectionIndex = event.index
                    )
                }
            }
        }
    }

    private fun checkPhotoLiked(photoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLocalPhotoById(
                photoId = photoId
            ).collectLatest { resource ->
                if (resource is Resource.Success) {
                    updateState {
                        copy(
                            isFavourite = resource.data != null
                        )
                    }
                }
            }
        }
    }

    private fun setWallpaper(index: Int) {
        state.value.setBitmap?.let { bitmap ->
            val type = WallpaperScreenType.fromIndex(index)
            viewModelScope.launch(Dispatchers.IO) {
                changeWallpaper.invoke(
                    bitmap = bitmap,
                    wallpaperScreenType = type ?: WallpaperScreenType.HOME_AND_LOCK
                ).collectLatest { downloadState ->
                    when (downloadState) {
                        is DownloadState.Error -> {
                            updateState {
                                copy(
                                    setButtonType = LoadingButtonType.INIT,
                                    showWallpaperSelectionSheet = false,
                                    showErrorDialog = true,
                                )
                            }
                        }

                        is DownloadState.Loading -> {
                            updateState {
                                copy(
                                    setButtonType = LoadingButtonType.LOADING,
                                    showWallpaperSelectionSheet = false
                                )
                            }
                        }

                        is DownloadState.Finished -> {
                            updateState {
                                copy(
                                    setButtonType = LoadingButtonType.INIT,
                                    showWallpaperSelectionSheet = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun downloadAndSaveImage() {
        state.value.photoModel?.links?.download?.let {
            viewModelScope.launch(Dispatchers.IO) {
                downloadImageAndSave.invoke(
                    imageUrl = it,
                    triggerUrl = state.value.photoModel?.links?.downloadLocation
                ).collectLatest { downloadState ->
                    updateState {
                        when (downloadState) {
                            is DownloadState.Error -> {
                                copy(
                                    saveButtonType = LoadingButtonType.ERROR,
                                    showErrorDialog = true
                                )
                            }

                            is DownloadState.Finished -> {
                                sendEvent(
                                    Event.ShowSnackBar(
                                        message = UIText.StringResource(
                                            resId = R.string.photo_detail_save_success_message
                                        )
                                    )
                                )
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

    private fun downloadBitmap() {
        state.value.photoModel?.links?.download?.let {
            viewModelScope.launch(Dispatchers.IO) {
                downloadImageAsBitmap.invoke(
                    imageUrl = it,
                    triggerUrl = state.value.photoModel?.links?.downloadLocation
                ).collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            updateState {
                                copy(
                                    setButtonType = LoadingButtonType.LOADING
                                )
                            }
                        }

                        is Resource.Error -> {
                            updateState {
                                copy(
                                    setButtonType = LoadingButtonType.ERROR,
                                    showErrorDialog = true
                                )
                            }
                        }

                        is Resource.Success -> {
                            updateState {
                                copy(
                                    showWallpaperSelectionSheet = true,
                                    sheetSelectionIndex = -1,
                                    setButtonType = LoadingButtonType.INIT,
                                    setBitmap = resource.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}