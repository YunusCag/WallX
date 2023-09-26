package com.yunuscagliyan.core_ui.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.yunuscagliyan.core.util.Constant.WorkManagerUtil.AUTO_WALLPAPER_MANAGER_NAME
import com.yunuscagliyan.core_ui.BuildConfig
import com.yunuscagliyan.core_ui.helper.AdmobHelper
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

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Context.loadInterstitial() {
    val request = AdRequest.Builder()
        .build()
    InterstitialAd.load(
        this,
        BuildConfig.ADMOB_INTERSTITIAL_ID,
        request,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                AdmobHelper.clearInterstitialAd()

            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                AdmobHelper.loadInterstitialAd(interstitialAd)
            }
        }
    )
}

fun Context.showInterstitial(onAdDismissed: () -> Unit) {
    val context = this
    AdmobHelper.increaseEventCounter()
    if (AdmobHelper.isEventLoaded()) {
        if (AdmobHelper.isInterstitialAdLoaded()) {
            AdmobHelper.setInterstitialFullScreenCallback(object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)
                    AdmobHelper.clearInterstitialAd()
                    context.loadInterstitial()
                    onAdDismissed()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    AdmobHelper.clearInterstitialAd()
                    context.loadInterstitial()
                    onAdDismissed()
                }
            })
            this.findActivity()?.let { activity ->
                AdmobHelper.showInterstitial(activity)
            }
        } else {
            AdmobHelper.clearInterstitialAd()
            context.loadInterstitial()
            onAdDismissed()
        }
    } else {
        onAdDismissed()
    }
}