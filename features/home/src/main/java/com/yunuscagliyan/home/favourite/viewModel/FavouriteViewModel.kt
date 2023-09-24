package com.yunuscagliyan.home.favourite.viewModel

import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.repository.PhotoRepository
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_KEY
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core.util.UIText
import com.yunuscagliyan.core_ui.event.Event
import com.yunuscagliyan.core_ui.event.NavArgument
import com.yunuscagliyan.core_ui.event.Routes
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.home.favourite.ui.FavouriteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: PhotoRepository
) : CoreViewModel<FavouriteState, FavouriteEvent>() {
    override fun getInitialState(): FavouriteState = FavouriteState()


    override fun onEvent(event: FavouriteEvent) {
        when (event) {
            is FavouriteEvent.InitPhotoList -> {
                getLocalPhotos()
            }

            is FavouriteEvent.OnPhotoClick -> {
                sendEvent(
                    Event.Navigation(
                        Routes.NavigateToRoute(
                            pageRoute = ScreenRoutes.PhotoDetailScreen.route,
                            navArgument = NavArgument(
                                key = PHOTO_KEY,
                                data = event.photoModel
                            )
                        )
                    )
                )
            }

            is FavouriteEvent.OnFavouriteClick -> {
                updateState {
                    val filteredList = photoList.filter { it.id != event.photoModel.id }
                    copy(
                        photoList = filteredList
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    event.photoModel.id?.let { id ->
                        repository.deletePhoto(photoId = id)
                    }
                    sendEvent(
                        Event.ShowSnackBar(
                            message = UIText.StringResource(
                                resId = R.string.favourite_remove_message
                            ),
                            actionLabel = UIText.StringResource(
                                resId = R.string.favourite_revoke
                            ),
                            onClick = {
                                onRevokeClicked(event.index, event.photoModel)
                            }
                        )
                    )
                }
            }
        }
    }

    private fun onRevokeClicked(index: Int, photoModel: PhotoModel) {
        viewModelScope.launch {
            repository.insertPhoto(
                photoModel = photoModel
            )
            updateState {
                val tempList = photoList.toMutableList()
                tempList.add(index, photoModel)
                copy(
                    photoList = tempList
                )
            }
        }
    }

    private fun getLocalPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLocalPhotos().collectLatest { resource ->
                if (resource is Resource.Success) {
                    updateState {
                        copy(
                            photoList = resource.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}