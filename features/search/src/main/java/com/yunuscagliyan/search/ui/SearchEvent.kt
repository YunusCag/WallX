package com.yunuscagliyan.search.ui

import com.yunuscagliyan.core.data.remote.model.collection.CollectionModel
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel

sealed class SearchEvent {
    object OnBackPress : SearchEvent()
    object OnSearch : SearchEvent()
    data class OnQuerySearch(val text: String) : SearchEvent()

    data class OnCollectionClick(val collectionModel: CollectionModel) : SearchEvent()
    data class OnPhotoClick(val photoModel: PhotoModel) : SearchEvent()
}
