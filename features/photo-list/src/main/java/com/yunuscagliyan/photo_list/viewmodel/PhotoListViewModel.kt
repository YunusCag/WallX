package com.yunuscagliyan.photo_list.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.remote.repository.PhotoRepository
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.COLLECTION_ID
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.COLLECTION_NAME
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.photo_list.ui.PhotoListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : CoreViewModel<PhotoListState, PhotoListEvent>() {
    override fun getInitialState(): PhotoListState = PhotoListState()

    init {
        initState()
    }

    private fun initState() {
        val collectionId = savedStateHandle.get<String>(COLLECTION_ID)
        val collectionName = savedStateHandle.get<String>(COLLECTION_NAME)

        updateState {
            copy(
                collectionName = collectionName,
                collectionPhotos = repository.getCollectionPhotos(
                    collectionId = collectionId ?: EMPTY_STRING,
                    orderBy = PhotoOrderBy.Popular
                ).cachedIn(viewModelScope)
            )
        }
    }

    override fun onEvent(event: PhotoListEvent) {
        when (event) {
            PhotoListEvent.OnBackPress -> {
                popBack()
            }

            is PhotoListEvent.OnPhotoClick -> {
                // TODO Navigate Photo Detail
            }
        }
    }
}