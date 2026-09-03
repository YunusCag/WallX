package com.yunuscagliyan.core.util

import com.yunuscagliyan.core.util.Constant.WorkManagerUtil.REMINDER_DELAY_DAY
import com.yunuscagliyan.core.util.Constant.WorkManagerUtil.REMINDER_HOUR_OF_DAY
import java.util.Calendar
import java.util.TimeZone

/**
 * Works out when the reminder notification should fire.
 *
 * Kept free of Android types so the timing rule can be unit tested: the notification
 * is due [REMINDER_DELAY_DAY] days after the app was last opened, at
 * [REMINDER_HOUR_OF_DAY]:00 local time.
 */
object ReminderScheduler {

    fun nextTriggerAtMillis(
        nowMillis: Long,
        delayDays: Int = REMINDER_DELAY_DAY,
        hourOfDay: Int = REMINDER_HOUR_OF_DAY,
        timeZone: TimeZone = TimeZone.getDefault(),
    ): Long = Calendar.getInstance(timeZone).apply {
        timeInMillis = nowMillis
        add(Calendar.DAY_OF_YEAR, delayDays)
        set(Calendar.HOUR_OF_DAY, hourOfDay)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    /** Delay to hand to WorkManager, never negative. */
    fun initialDelayMillis(
        nowMillis: Long,
        delayDays: Int = REMINDER_DELAY_DAY,
        hourOfDay: Int = REMINDER_HOUR_OF_DAY,
        timeZone: TimeZone = TimeZone.getDefault(),
    ): Long = (nextTriggerAtMillis(nowMillis, delayDays, hourOfDay, timeZone) - nowMillis)
        .coerceAtLeast(0L)
}
