package com.yunuscagliyan.home.home.viewModel

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class HomeState(
    val popularPhotos: Flow<PagingData<PhotoModel>> = emptyFlow()
)
