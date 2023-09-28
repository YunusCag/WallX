package com.yunuscagliyan.core.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.remote.model.collection.CollectionModel
import com.yunuscagliyan.core.data.remote.service.PixabayService
import com.yunuscagliyan.core.util.Constant.PaginationUtil.PER_PAGE
import java.lang.Exception

class CollectionSearchSource(
    private val pixabayService: PixabayService,
    private val query: String,
    private val orderBy: PhotoOrderBy
) : PagingSource<Int, CollectionModel>() {
    override fun getRefreshKey(state: PagingState<Int, CollectionModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionModel> {
        return try {
            val page = params.key ?: 1
            val response = pixabayService.getSearchCollections(
                query = query,
                page = page,
                perPage = PER_PAGE,
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