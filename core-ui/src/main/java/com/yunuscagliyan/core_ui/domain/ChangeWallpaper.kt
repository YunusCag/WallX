package com.yunuscagliyan.core_ui.domain

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import com.yunuscagliyan.core.util.DownloadState
import com.yunuscagliyan.core.util.WallpaperCrop
import com.yunuscagliyan.core_ui.extension.getDeviceWidthAndHeight
import com.yunuscagliyan.core_ui.model.enums.WallpaperScreenType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * The single place a wallpaper is applied - the auto-change worker goes through here
 * too, so both paths share the same cropping and the same guards.
 */
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
            // Both can be false on managed profiles and some OEM builds, where
            // setBitmap would otherwise fail without telling the user why.
            if (!wallpaperManager.isWallpaperSupported) {
                throw IOException("This device does not support setting wallpapers")
            }
            if (!wallpaperManager.isSetWallpaperAllowed) {
                throw IOException("Setting wallpapers is not allowed on this device")
            }

            val (screenWidth, screenHeight) = context.getDeviceWidthAndHeight()
            val cropHint = WallpaperCrop.centerCropHint(
                sourceWidth = bitmap.width,
                sourceHeight = bitmap.height,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
            )?.let { Rect(it.left, it.top, it.right, it.bottom) }

            val flags = when (wallpaperScreenType) {
                WallpaperScreenType.HOME -> WallpaperManager.FLAG_SYSTEM
                WallpaperScreenType.LOCK -> WallpaperManager.FLAG_LOCK
                WallpaperScreenType.HOME_AND_LOCK ->
                    WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
            }

            wallpaperManager.setBitmap(bitmap, cropHint, true, flags)

            emit(DownloadState.Finished)
        } catch (e: Exception) {
            Timber.e(e, "Could not set the wallpaper")
            emit(DownloadState.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}
