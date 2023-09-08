package com.yunuscagliyan.core.data.remote.service

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.response.CollectionResponse
import com.yunuscagliyan.core.util.Constant.NetworkPathUtil.PHOTOS_PATH
import com.yunuscagliyan.core.util.Constant.NetworkPathUtil.SEARCH_COLLECTION_PATH
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.ORDER_BY
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.PAGE
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.PER_PAGE
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.QUERY
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {

    @GET(PHOTOS_PATH)
    suspend fun getPhotos(
        @Query(PAGE) page: Int,
        @Query(PER_PAGE) perPage: Int,
        @Query(ORDER_BY) orderBy: String
    ): List<PhotoModel>

    @GET(SEARCH_COLLECTION_PATH)
    suspend fun getSearchCollections(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int,
        @Query(PER_PAGE) perPage: Int,
        @Query(ORDER_BY) orderBy: String
    ): CollectionResponse
}