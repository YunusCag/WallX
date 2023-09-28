package com.yunuscagliyan.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.local.dao.PhotoDao
import com.yunuscagliyan.core.data.local.entity.PhotoEntity
import com.yunuscagliyan.core.data.mapper.toPhotoModel
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.service.PixabayService
import com.yunuscagliyan.core.data.remote.source.CollectionPhotoSource
import com.yunuscagliyan.core.data.remote.source.CollectionSearchSource
import com.yunuscagliyan.core.data.remote.source.PhotoSearchSource
import com.yunuscagliyan.core.data.remote.source.PhotoSource
import com.yunuscagliyan.core.util.Constant.PaginationUtil.PER_PAGE
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core.util.UIText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val pixabayService: PixabayService,
    private val photoDao: PhotoDao
) {
    fun getPhotos(orderBy: PhotoOrderBy) = Pager(
        config = PagingConfig(
            pageSize = PER_PAGE
        ),
        pagingSourceFactory = {
            PhotoSource(
                pixabayService = pixabayService,
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
                pixabayService = pixabayService,
                query = query,
                orderBy = orderBy
            )
        }
    ).flow
    fun getSearchPhotos(
        query: String?=null,
        orderBy: PhotoOrderBy
    ) = Pager(
        config = PagingConfig(
            pageSize = PER_PAGE
        ),
        pagingSourceFactory = {
            PhotoSearchSource(
                pixabayService = pixabayService,
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
                pixabayService = pixabayService,
                collectionId = collectionId,
                orderBy = orderBy
            )
        }
    ).flow

    fun getLocalPhotos(): Flow<Resource<List<PhotoEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val entities = photoDao.getPhotos()
            emit(Resource.Success(entities))
        } catch (e: Exception) {
            emit(Resource.Error(UIText.DynamicString(e.localizedMessage)))
        }
    }


    fun getLocalPhotoById(photoId: Long):Flow<Resource<PhotoModel?>> = flow {
        try {
            emit(Resource.Loading())
            val entity = photoDao.getPhotoById(photoId = photoId)
            emit(Resource.Success(entity?.toPhotoModel()))
        } catch (e: Exception) {
            emit(Resource.Error(UIText.DynamicString(e.localizedMessage)))
        }
    }
    suspend fun insertPhoto(photoEntity: PhotoEntity) {
        try {
            photoDao.insertPhoto(photoEntity = photoEntity)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }

    suspend fun deletePhoto(photoId: Long) {
        try {
            photoDao.deletePhoto(photoId = photoId)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }
}