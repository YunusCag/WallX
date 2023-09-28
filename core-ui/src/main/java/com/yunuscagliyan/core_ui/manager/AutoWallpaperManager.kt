package com.yunuscagliyan.core_ui.manager

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yunuscagliyan.core.data.local.dao.PhotoDao
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.data.mapper.toPhotoModel
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.service.PixabayService
import com.yunuscagliyan.core_ui.extension.getDeviceWidthAndHeight
import com.yunuscagliyan.core_ui.model.enums.WallpaperScreenType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class AutoWallpaperManager @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    @Assisted private val photoDao: PhotoDao,
    @Assisted private val pixabayService: PixabayService,
    @Assisted private val preferences: Preferences,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val screenType =
                    WallpaperScreenType.fromIndex(preferences.screenIndex)
                        ?: WallpaperScreenType.HOME_AND_LOCK
                val photos = photoDao.getPhotos()
                if (photos.isNotEmpty()) {
                    val randomPhoto = photos.random()
                    Log.d("AutoWallpaper", "Favourite Photo:$randomPhoto")
                    downloadImageAndSetWallpaper(randomPhoto.toPhotoModel(), screenType)
                }
                Result.success()
            } catch (e: Exception) {
                Log.e("AutoWallpaper", "Error Message:${e.localizedMessage}")
                Result.retry()
            }
        }
    }

    private suspend fun downloadImageAndSetWallpaper(
        photoModel: PhotoModel,
        screenType: WallpaperScreenType
    ) {
        photoModel.largeImageURL?.let { url ->
            val bitmap = downloadImage(
                imageUrl = url,
            )
            bitmap?.let {
                setWallpaper(
                    bitmap = it,
                    screenType = screenType
                )
            }
        }
    }

    private suspend fun downloadImage(imageUrl: String): Bitmap? {
        return try {
            val response = pixabayService.downloadImage(imageUrl = imageUrl)
            val inputStream = response.byteStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val screenSize = this.context.getDeviceWidthAndHeight()
            val width = screenSize.first
            val height = screenSize.first
            Bitmap.createScaledBitmap(bitmap, width, height, true)
        } catch (e: Exception) {
            Log.e("AutoWallpaper", "downloadImage Error setWallpaper Message:${e.localizedMessage}")
            null
        }
    }

    @SuppressLint("MissingPermission")
    private fun setWallpaper(bitmap: Bitmap, screenType: WallpaperScreenType) {
        try {
            val wallpaperManager = WallpaperManager.getInstance(context)
            when (screenType) {
                WallpaperScreenType.HOME -> {
                    wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        true,
                        WallpaperManager.FLAG_SYSTEM
                    )
                }

                WallpaperScreenType.LOCK -> {
                    wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        true,
                        WallpaperManager.FLAG_LOCK
                    )
                }

                WallpaperScreenType.HOME_AND_LOCK -> {
                    wallpaperManager.setBitmap(
                        bitmap
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("AutoWallpaper", "Error setWallpaper Message:${e.localizedMessage}")
        }

    }
}