package com.yunuscagliyan.core.data.remote.service

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.response.CollectionResponse
import com.yunuscagliyan.core.data.remote.response.PhotoResponse
import com.yunuscagliyan.core.util.Constant.NetworkPathUtil.COLLECTION_PHOTOS
import com.yunuscagliyan.core.util.Constant.NetworkPathUtil.PHOTOS_PATH
import com.yunuscagliyan.core.util.Constant.NetworkPathUtil.RANDOM_PHOTO
import com.yunuscagliyan.core.util.Constant.NetworkPathUtil.SEARCH_COLLECTION_PATH
import com.yunuscagliyan.core.util.Constant.NetworkPathUtil.SEARCH_PHOTO_PATH
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.COLLECTION_ID
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.ORDER_BY
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.ORIENTATION
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.PAGE
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.PER_PAGE
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.QUERY
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface PixabayService {

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

    @GET(SEARCH_PHOTO_PATH)
    suspend fun getSearchPhotos(
        @Query(QUERY) query: String? = null,
        @Query(PAGE) page: Int,
        @Query(PER_PAGE) perPage: Int,
        @Query(ORDER_BY) orderBy: String
    ): PhotoResponse

    @GET(COLLECTION_PHOTOS)
    suspend fun getCollectionPhotos(
        @Path(COLLECTION_ID) collectionId: String,
        @Query(PAGE) page: Int,
        @Query(PER_PAGE) perPage: Int,
        @Query(ORDER_BY) orderBy: String,
        @Query(ORIENTATION) orientation: String? = null,
    ): List<PhotoModel>

    @GET(RANDOM_PHOTO)
    suspend fun getRandomPhoto(): PhotoModel

    @GET
    @Streaming
    suspend fun downloadImage(@Url imageUrl: String): ResponseBody

    @GET
    suspend fun triggerDownload(@Url url: String): ResponseBody
}