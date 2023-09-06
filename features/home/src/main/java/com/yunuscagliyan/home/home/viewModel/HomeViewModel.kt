package com.yunuscagliyan.home.home.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.remote.repository.PhotoRepository
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.home.ui.HomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : CoreViewModel<HomeState, HomeEvent>() {
    override fun getInitialState(): HomeState = HomeState()

    init {
        getPhotos()
    }

    fun getPhotos() {
        updateState {
            copy(
                popularPhotos = photoRepository.getPhotos(
                    orderBy = PhotoOrderBy.Popular
                ).cachedIn(viewModelScope)
            )
        }
    }
    override fun onEvent(event: HomeEvent) {

    }
}