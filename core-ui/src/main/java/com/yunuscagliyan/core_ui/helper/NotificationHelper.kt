package com.yunuscagliyan.core_ui.helper

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.yunuscagliyan.core_ui.R

class NotificationHelper(
    private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun show() {
        val activityIntent = Intent(context,Class.forName("com.yunuscagliyan.wallx.MainActivity"))
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_REQUEST_CODE,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_wallpaper)
            .setContentTitle(context.getString(R.string.notification_periodic_title))
            .setContentText(context.getString(R.string.notification_periodic_description))
            .setContentIntent(activityPendingIntent)
            .build()
        if(notificationManager.areNotificationsEnabled()){
            notificationManager.notify(NOTIFICATION_REQUEST_CODE, notification)
        }
    }

    companion object {
        const val NOTIFICATION_REQUEST_CODE = 1
        const val CHANNEL_ID = "periodic_channel_id"
    }
}