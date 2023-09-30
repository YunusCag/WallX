package com.yunuscagliyan.wallx

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.yunuscagliyan.core.BuildConfig
import com.yunuscagliyan.core.data.local.dao.PhotoDao
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.data.remote.service.PixabayService
import com.yunuscagliyan.core_ui.manager.AutoWallpaperManager
import com.yunuscagliyan.core_ui.helper.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject
import com.yunuscagliyan.core_ui.R

@HiltAndroidApp
class WallXApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: WallXWorkerFactory

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationHelper.CHANNEL_ID,
                getString(R.string.notification_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = getString(R.string.notification_name)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
    }
}

class WallXWorkerFactory @Inject constructor(
    private val photoDao: PhotoDao,
    private val pixabayService: PixabayService,
    private val preferences: Preferences,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = AutoWallpaperManager(
        context = appContext,
        params = workerParameters,
        photoDao = photoDao,
        pixabayService = pixabayService,
        preferences = preferences
    )

}