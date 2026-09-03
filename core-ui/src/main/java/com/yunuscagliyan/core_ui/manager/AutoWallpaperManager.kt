package com.yunuscagliyan.core_ui.manager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yunuscagliyan.core.data.local.dao.PhotoDao
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.data.mapper.toPhotoModel
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.service.PixabayService
import com.yunuscagliyan.core.util.PhotoQuality
import com.yunuscagliyan.core.util.BitmapSampling
import com.yunuscagliyan.core.util.DownloadState
import com.yunuscagliyan.core_ui.extension.getDeviceWidthAndHeight
import com.yunuscagliyan.core_ui.domain.ChangeWallpaper
import com.yunuscagliyan.core_ui.model.enums.WallpaperScreenType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Built by hand in WallXWorkerFactory, so it takes its dependencies as plain
 * constructor parameters. It is not a @HiltWorker: that annotation was never
 * processed (androidx.hilt:hilt-compiler is not on the classpath) and marking the
 * injected dependencies @Assisted was invalid anyway - only Context and
 * WorkerParameters may be assisted.
 */
class AutoWallpaperManager(
    private val context: Context,
    params: WorkerParameters,
    private val photoDao: PhotoDao,
    private val pixabayService: PixabayService,
    private val preferences: Preferences,
    private val changeWallpaper: ChangeWallpaper,
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
        PhotoQuality.bestImageUrl(photoModel)?.let { url ->
            val bitmap = downloadImage(imageUrl = url)
            bitmap?.let {
                // Same use case the detail screen calls, so cropping and the
                // wallpaper-allowed guards cannot drift between the two paths.
                changeWallpaper(bitmap = it, wallpaperScreenType = screenType).collect { state ->
                    if (state is DownloadState.Error) {
                        Log.e("AutoWallpaper", "Could not set wallpaper", state.error)
                    }
                }
            }
        }
    }

    private suspend fun downloadImage(imageUrl: String): Bitmap? {
        return try {
            val response = pixabayService.downloadImage(imageUrl = imageUrl)
            val bytes = response.byteStream().use { it.readBytes() }

            val (screenWidth, screenHeight) = context.getDeviceWidthAndHeight()
            val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)

            // Decoding at full size is wasted memory for a 1280px file and an OOM
            // once full API access starts returning multi-thousand pixel originals.
            val options = BitmapFactory.Options().apply {
                inSampleSize = BitmapSampling.calculateInSampleSize(
                    sourceWidth = bounds.outWidth,
                    sourceHeight = bounds.outHeight,
                    requestedWidth = screenWidth,
                    requestedHeight = screenHeight,
                )
            }
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
        } catch (e: Exception) {
            Log.e("AutoWallpaper", "downloadImage failed", e)
            null
        }
    }

}