package com.yunuscagliyan.core.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.remote.service.UnsplashService
import com.yunuscagliyan.core.data.remote.source.CollectionSearchSource
import com.yunuscagliyan.core.data.remote.source.PhotoSource
import com.yunuscagliyan.core.util.Constant.PaginationUtil.PER_PAGE
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val unsplashService: UnsplashService
) {
    fun getPhotos(orderBy: PhotoOrderBy) = Pager(
        config = PagingConfig(
            pageSize = PER_PAGE
        ),
        pagingSourceFactory = {
            PhotoSource(
                unsplashService = unsplashService,
                orderBy = orderBy
            )
        }
    ).flow

    fun getSearchCollections(
        query: String,
        orderBy: PhotoOrderBy
    ) = Pager(
        config = PagingConfig(
            pageSize = PER_PAGE
        ),
        pagingSourceFactory = {
            CollectionSearchSource(
                unsplashService = unsplashService,
                query = query,
                orderBy = orderBy
            )
        }
    ).flow
}