package com.yunuscagliyan.search.viewModel

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.yunuscagliyan.core.data.remote.model.collection.CollectionModel
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class SearchState(
    val search: String = EMPTY_STRING,
    val photos: Flow<PagingData<PhotoModel>> = emptyFlow(),
)
