package com.yunuscagliyan.core_ui.manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yunuscagliyan.core_ui.helper.NotificationHelper

/**
 * Shows the "come back and look at wallpapers" reminder.
 *
 * Scheduled as unique work by [com.yunuscagliyan.core_ui.extension.scheduleReminderNotification],
 * which replaces any pending reminder every time the app is opened.
 */
class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        NotificationHelper(applicationContext).show()
        return Result.success()
    }
}
