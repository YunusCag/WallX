package com.yunuscagliyan.core_ui.extension

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.yunuscagliyan.core.util.Constant.WorkManagerUtil.AUTO_WALLPAPER_MANAGER_NAME
import com.yunuscagliyan.core_ui.manager.AutoWallpaperManager
import com.yunuscagliyan.core_ui.model.enums.PeriodicTimeType
import timber.log.Timber
import java.util.concurrent.TimeUnit

fun Context.getDeviceWidthAndHeight(): Pair<Int, Int> {
    val metrics = this.resources.displayMetrics
    val screenWidth = metrics.widthPixels
    val screenHeight = metrics.heightPixels

    return Pair(screenWidth, screenHeight)
}

fun Context.cancelAllWorkManager() {
    val workManager = WorkManager.getInstance(applicationContext)
    workManager.cancelAllWorkByTag(AUTO_WALLPAPER_MANAGER_NAME)
    workManager.cancelUniqueWork(AUTO_WALLPAPER_MANAGER_NAME)
    Timber.e("AutoWallpaper Cancel all WorkManager")
}

fun Context.startWorkManager(
    periodicTimeType: PeriodicTimeType
) {
    val workRequest = PeriodicWorkRequestBuilder<AutoWallpaperManager>(
        repeatInterval = periodicTimeType.repeatInterval.toLong(),
        repeatIntervalTimeUnit = periodicTimeType.timeUnit,
        flexTimeInterval = periodicTimeType.repeatInterval.toLong(),
        flexTimeIntervalUnit = periodicTimeType.timeUnit,
    )
        .addTag(AUTO_WALLPAPER_MANAGER_NAME)
        .setInitialDelay(
            duration = periodicTimeType.repeatInterval.toLong(),
            timeUnit = periodicTimeType.timeUnit
        )
        .setBackoffCriteria(
            backoffPolicy = BackoffPolicy.LINEAR,
            backoffDelay = 15,
            timeUnit = TimeUnit.MINUTES
        ).setConstraints(
            Constraints(
                requiredNetworkType = NetworkType.CONNECTED,
            )
        )
        .build()
    val workManager = WorkManager.getInstance(applicationContext)
    workManager.enqueueUniquePeriodicWork(
        AUTO_WALLPAPER_MANAGER_NAME,
        ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
        workRequest
    )
}