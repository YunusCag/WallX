package com.yunuscagliyan.core.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.service.UnsplashService
import com.yunuscagliyan.core.util.Constant

class PhotoSearchSource(
    private val unsplashService: UnsplashService,
    private val query: String,
    private val orderBy: PhotoOrderBy
) : PagingSource<Int, PhotoModel>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoModel> {
        return try {
            val page = params.key ?: 1
            val response = unsplashService.getSearchPhotos(
                query = query,
                page = page,
                perPage = Constant.PaginationUtil.PER_PAGE,
                orderBy = orderBy.value
            )
            LoadResult.Page(
                data = response.results ?: emptyList(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results?.isEmpty() == true) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}