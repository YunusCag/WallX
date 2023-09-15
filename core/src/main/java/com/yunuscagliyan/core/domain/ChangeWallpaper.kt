package com.yunuscagliyan.core.domain

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import com.yunuscagliyan.core.data.enums.WallpaperScreenType
import com.yunuscagliyan.core.util.DownloadState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class ChangeWallpaper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @SuppressLint("MissingPermission")
    operator fun invoke(
        bitmap: Bitmap,
        wallpaperScreenType: WallpaperScreenType
    ): Flow<DownloadState> = flow {
        try {
            emit(DownloadState.Loading)

            val wallpaperManager = WallpaperManager.getInstance(context)
            when (wallpaperScreenType) {
                WallpaperScreenType.Home -> {
                    wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        true,
                        WallpaperManager.FLAG_SYSTEM
                    )
                }

                WallpaperScreenType.Lock -> {
                    wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        true,
                        WallpaperManager.FLAG_LOCK
                    )
                }

                WallpaperScreenType.Both -> {
                    wallpaperManager.setBitmap(
                        bitmap
                    )
                }

                else -> {
                    wallpaperManager.setBitmap(
                        bitmap
                    )
                }
            }
            emit(DownloadState.Finished)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
            emit(DownloadState.Error(e))
        }
    }
}