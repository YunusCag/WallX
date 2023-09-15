package com.yunuscagliyan.photo_detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.data.enums.WallpaperScreenType
import com.yunuscagliyan.core.domain.DownloadImageAndSave
import com.yunuscagliyan.core.domain.DownloadImageAsBitmap
import com.yunuscagliyan.core.domain.ChangeWallpaper
import com.yunuscagliyan.core.util.DownloadState
import com.yunuscagliyan.core.util.Resource
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
    private val downloadImageAndSave: DownloadImageAndSave,
    private val downloadImageAsBitmap: DownloadImageAsBitmap,
    private val changeWallpaper: ChangeWallpaper
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

            is PhotoDetailEvent.BottomSheet -> {
                updateState {
                    copy(
                        showWallpaperSelectionSheet = event.isOpen
                    )
                }
            }

            is PhotoDetailEvent.OnScreenSelection -> {
                setWallpaper(
                    index = event.index
                )

                updateState {
                    copy(
                        sheetSelectionIndex = event.index,
                        showWallpaperSelectionSheet = false
                    )
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
                    wallpaperScreenType = type ?: WallpaperScreenType.Both
                ).collectLatest { downloadState ->
                    when (downloadState) {
                        is DownloadState.Error -> {
                            updateState {
                                copy(
                                    setButtonType = LoadingButtonType.INIT,
                                    showWallpaperSelectionSheet = false
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
                downloadImageAndSave.invoke(imageUrl = it).collectLatest { downloadState ->
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

    private fun downloadBitmap() {
        state.value.photoModel?.links?.download?.let {
            viewModelScope.launch(Dispatchers.IO) {
                downloadImageAsBitmap.invoke(imageUrl = it).collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            updateState {
                                copy(
                                    setButtonType = LoadingButtonType.LOADING
                                )
                            }
                        }

                        is Resource.Error -> {
                            // TODO show Error Text
                            updateState {
                                copy(
                                    setButtonType = LoadingButtonType.INIT
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