package com.yunuscagliyan.photo_list.viewmodel

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class PhotoListState(
    val collectionName: String? = null,
    val collectionPhotos: Flow<PagingData<PhotoModel>> = emptyFlow()
)
