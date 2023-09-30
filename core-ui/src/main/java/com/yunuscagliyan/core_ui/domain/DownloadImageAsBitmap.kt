package com.yunuscagliyan.core_ui.domain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.yunuscagliyan.core.data.remote.service.PixabayService
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core.util.UIText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class DownloadImageAsBitmap @Inject constructor(
    private val pixabayService: PixabayService
) {
    operator fun invoke(imageUrl: String): Flow<Resource<Bitmap>> = flow {
        try {
            emit(Resource.Loading())
            val response = pixabayService.downloadImage(imageUrl = imageUrl)
            val inputStream = response.byteStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            emit(Resource.Success(bitmap))
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