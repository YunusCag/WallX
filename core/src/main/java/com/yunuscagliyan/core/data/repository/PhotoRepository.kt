package com.yunuscagliyan.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.local.dao.PhotoDao
import com.yunuscagliyan.core.data.mapper.toPhotoEntity
import com.yunuscagliyan.core.data.mapper.toPhotoModel
import com.yunuscagliyan.core.data.mapper.toPhotoModelList
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.service.UnsplashService
import com.yunuscagliyan.core.data.remote.source.CollectionPhotoSource
import com.yunuscagliyan.core.data.remote.source.CollectionSearchSource
import com.yunuscagliyan.core.data.remote.source.PhotoSource
import com.yunuscagliyan.core.util.Constant.PaginationUtil.PER_PAGE
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core.util.UIText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val unsplashService: UnsplashService,
    private val photoDao: PhotoDao
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

    fun getCollectionPhotos(
        collectionId: String,
        orderBy: PhotoOrderBy
    ) = Pager(
        config = PagingConfig(
            pageSize = PER_PAGE
        ),
        pagingSourceFactory = {
            CollectionPhotoSource(
                unsplashService = unsplashService,
                collectionId = collectionId,
                orderBy = orderBy
            )
        }
    ).flow

    fun getLocalPhotos(): Flow<Resource<List<PhotoModel>>> = flow {
        try {
            emit(Resource.Loading())
            val entities = photoDao.getPhotos()
            emit(Resource.Success(entities.toPhotoModelList()))
        } catch (e: Exception) {
            emit(Resource.Error(UIText.DynamicString(e.localizedMessage)))
        }
    }


    fun getLocalPhotoById(photoId: String):Flow<Resource<PhotoModel?>> = flow {
        try {
            emit(Resource.Loading())
            val entity = photoDao.getPhotoById(photoId = photoId)
            emit(Resource.Success(entity?.toPhotoModel()))
        } catch (e: Exception) {
            emit(Resource.Error(UIText.DynamicString(e.localizedMessage)))
        }
    }
    suspend fun insertPhoto(photoModel: PhotoModel) {
        try {
            photoDao.insertPhoto(photoEntity = photoModel.toPhotoEntity())
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }

    suspend fun deletePhoto(photoId: String) {
        try {
            photoDao.deletePhoto(photoId = photoId)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }
}