package com.yunuscagliyan.home.category.viewModel

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.yunuscagliyan.core.data.remote.model.collection.CollectionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class CategoryState(
    val colletions: Flow<PagingData<CollectionModel>> = emptyFlow()
)
