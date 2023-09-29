package com.yunuscagliyan.core_ui.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
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
import com.yunuscagliyan.core_ui.R
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


fun Context.navigateRateApp() {
    try {
        val uriString = "market://details?id=$packageName"
        Timber.e(uriString)
        val uri = Uri.parse(uriString)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Timber.e(e.localizedMessage)
        val uriString = "https://play.google.com/store/apps/details?id=$packageName"
        val uri = Uri.parse(uriString)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}

fun Context.navigateFeedback() {
    try {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("yunuscagliyan8@gmail.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "WallX - Feedback")
        startActivity(emailIntent)
    } catch (e: ActivityNotFoundException) {
        Timber.e(e.localizedMessage)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/email"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("yunuscagliyan8@gmail.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "WallX - Feedback")
        startActivity(Intent.createChooser(emailIntent, getString(R.string.common_feedback)))
    }
}

fun Context.shareApp() {
    try {
        val playStoreLink = "https://play.google.com/store/apps/details?id=$packageName"
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.common_share_app_text, playStoreLink))
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, getString(R.string.common_share_app_title)))
    } catch (e: Exception) {
        Timber.e(e.localizedMessage)
    }
}