package com.yunuscagliyan.core.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.yunuscagliyan.core.data.remote.service.UnsplashService
import com.yunuscagliyan.core.extension.getDeviceWidthAndHeight
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core.util.UIText
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class DownloadImageAsBitmap @Inject constructor(
    private val unsplashService: UnsplashService,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(imageUrl: String, triggerUrl: String?): Flow<Resource<Bitmap>> = flow {
        try {
            emit(Resource.Loading())
            val response = unsplashService.downloadImage(imageUrl = imageUrl)
            triggerUrl?.let { unsplashService.triggerDownload(url = it) }
            val inputStream = response.byteStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val screenSize = context.getDeviceWidthAndHeight()
            val width = screenSize.first
            val height = screenSize.first
            val sizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            emit(Resource.Success(sizedBitmap))
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
            emit(
                Resource.Error(
                    message = UIText.DynamicString(e.localizedMessage ?: EMPTY_STRING)
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}