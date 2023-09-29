package com.yunuscagliyan.core_ui.domain

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import com.yunuscagliyan.core.util.DownloadState
import com.yunuscagliyan.core_ui.extension.getDeviceWidthAndHeight
import com.yunuscagliyan.core_ui.model.enums.WallpaperScreenType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
            val screenSize = context.getDeviceWidthAndHeight()
            val width = screenSize.first
            val height = screenSize.second

            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.suggestDesiredDimensions(width, height)
            when (wallpaperScreenType) {
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
            emit(DownloadState.Finished)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
            emit(DownloadState.Error(e))
        }
    }
}