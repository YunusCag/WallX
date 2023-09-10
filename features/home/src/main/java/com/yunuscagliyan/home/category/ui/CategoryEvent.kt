package com.yunuscagliyan.home.category.ui

import com.yunuscagliyan.core.data.remote.model.collection.CollectionModel

sealed class CategoryEvent {
    data class OnCollectionClick(val collectionModel: CollectionModel) : CategoryEvent()
}
