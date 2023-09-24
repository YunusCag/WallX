package com.yunuscagliyan.core.domain

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.service.UnsplashService
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core.util.UIText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetRandomPhoto @Inject constructor(
    private val unsplashService: UnsplashService
) {
    operator fun invoke(): Flow<Resource<PhotoModel>> = flow {
        try {
            val photo = unsplashService.getRandomPhoto()
            emit(Resource.Success(photo))
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
            emit(Resource.Error(UIText.DynamicString(e.localizedMessage)))
        }
    }
}