package com.yunuscagliyan.core_ui.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.view.WindowManager
import android.net.Uri
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.yunuscagliyan.core.util.Constant.WorkManagerUtil.AUTO_WALLPAPER_MANAGER_NAME
import com.yunuscagliyan.core.util.Constant.WorkManagerUtil.REMINDER_WORKER_NAME
import com.yunuscagliyan.core.util.ReminderScheduler
import com.yunuscagliyan.core_ui.BuildConfig
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.helper.AdmobHelper
import com.yunuscagliyan.core_ui.manager.AutoWallpaperManager
import com.yunuscagliyan.core_ui.manager.ReminderWorker
import com.yunuscagliyan.core_ui.model.enums.PeriodicTimeType
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Full display size in pixels.
 *
 * resources.displayMetrics reports the app window, which is not the display in
 * multi-window, so the wallpaper crop would be computed against the wrong aspect
 * ratio. WindowMetrics gives the real bounds from API 30 on.
 */
fun Context.getDeviceWidthAndHeight(): Pair<Int, Int> {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        windowManager?.maximumWindowMetrics?.bounds?.let { bounds ->
            if (bounds.width() > 0 && bounds.height() > 0) {
                return Pair(bounds.width(), bounds.height())
            }
        }
    }
    val metrics = this.resources.displayMetrics
    return Pair(metrics.widthPixels, metrics.heightPixels)
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
        // UPDATE, not CANCEL_AND_REENQUEUE: Settings re-runs this on every visit and
        // cancelling would restart the full initial delay each time, so a user who
        // checks Settings often would never reach a wallpaper change.
        ExistingPeriodicWorkPolicy.UPDATE,
        workRequest
    )
}

/**
 * Schedules the reminder notification for [REMINDER_DELAY_DAY] days from now at
 * [REMINDER_HOUR_OF_DAY]:00 local time.
 *
 * Called every time the app is opened. The work is unique and enqueued with
 * [ExistingWorkPolicy.REPLACE], so an already pending reminder is cancelled and the
 * countdown starts over - a user who keeps coming back never gets the notification.
 */
fun Context.scheduleReminderNotification() {
    val now = System.currentTimeMillis()
    val delay = ReminderScheduler.initialDelayMillis(now)

    val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
        .addTag(REMINDER_WORKER_NAME)
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(applicationContext).enqueueUniqueWork(
        REMINDER_WORKER_NAME,
        ExistingWorkPolicy.REPLACE,
        workRequest
    )
    Timber.d("Reminder scheduled in ${delay}ms")
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Context.loadInterstitial() {
    // Without this the retry-on-every-event behaviour would queue a request per
    // navigation while the first one is still in flight.
    if (!AdmobHelper.shouldRequestInterstitial()) return
    AdmobHelper.markInterstitialRequested()

    val request = AdRequest.Builder()
        .build()
    InterstitialAd.load(
        this,
        BuildConfig.ADMOB_INTERSTITIAL_ID,
        request,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                Timber.e("Interstitial failed to load: ${adError.message}")
                AdmobHelper.onInterstitialLoadFailed()
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