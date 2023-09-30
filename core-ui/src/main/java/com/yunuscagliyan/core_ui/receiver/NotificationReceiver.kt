package com.yunuscagliyan.core_ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yunuscagliyan.core_ui.helper.NotificationHelper

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val helper = NotificationHelper(context)
        helper.show()
    }
}