package com.yunuscagliyan.home.home.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.repository.PhotoRepository
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_KEY
import com.yunuscagliyan.core_ui.event.Event
import com.yunuscagliyan.core_ui.event.NavArgument
import com.yunuscagliyan.core_ui.event.Routes
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.home.ui.HomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : CoreViewModel<HomeState, HomeEvent>() {
    override fun getInitialState(): HomeState = HomeState()

    init {
        getPhotos()
    }

    private fun getPhotos() {
        updateState {
            copy(
                newPhotos = photoRepository.getPhotos(
                    orderBy = PhotoOrderBy.Latest
                ).cachedIn(viewModelScope),
                popularPhotos = photoRepository.getPhotos(
                    orderBy = PhotoOrderBy.Popular
                ).cachedIn(viewModelScope)
            )
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnPhotoClick -> {
                sendEvent(
                    Event.Navigation(
                        Routes.NavigateToRoute(
                            ScreenRoutes.PhotoDetailScreen.route,
                            navArgument = NavArgument(
                                key = PHOTO_KEY,
                                data = event.photoModel
                            )
                        )
                    )
                )
            }
        }
    }
}