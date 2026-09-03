package com.yunuscagliyan.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.util.Calendar
import java.util.TimeZone
import org.junit.Test

class ReminderSchedulerTest {

    private val zone: TimeZone = TimeZone.getTimeZone("Europe/Istanbul")

    private fun at(
        year: Int, month: Int, day: Int,
        hour: Int, minute: Int = 0, second: Int = 0, millis: Int = 0,
    ): Long = Calendar.getInstance(zone).apply {
        clear()
        set(year, month - 1, day, hour, minute, second)
        set(Calendar.MILLISECOND, millis)
    }.timeInMillis

    private fun fieldsOf(millis: Long): List<Int> = Calendar.getInstance(zone).apply {
        timeInMillis = millis
    }.let {
        listOf(
            it.get(Calendar.YEAR),
            it.get(Calendar.MONTH) + 1,
            it.get(Calendar.DAY_OF_MONTH),
            it.get(Calendar.HOUR_OF_DAY),
            it.get(Calendar.MINUTE),
            it.get(Calendar.SECOND),
            it.get(Calendar.MILLISECOND),
        )
    }

    @Test
    fun `fires two days later at 19 00`() {
        val now = at(2026, 9, 3, hour = 10, minute = 15)

        val trigger = ReminderScheduler.nextTriggerAtMillis(now, timeZone = zone)

        assertEquals(listOf(2026, 9, 5, 19, 0, 0, 0), fieldsOf(trigger))
    }

    @Test
    fun `opening after 19 00 still waits two full days`() {
        val now = at(2026, 9, 3, hour = 23, minute = 45)

        val trigger = ReminderScheduler.nextTriggerAtMillis(now, timeZone = zone)

        assertEquals(listOf(2026, 9, 5, 19, 0, 0, 0), fieldsOf(trigger))
    }

    @Test
    fun `opening exactly at 19 00 moves to 19 00 two days later`() {
        val now = at(2026, 9, 3, hour = 19)

        val trigger = ReminderScheduler.nextTriggerAtMillis(now, timeZone = zone)

        assertEquals(listOf(2026, 9, 5, 19, 0, 0, 0), fieldsOf(trigger))
        assertEquals(2 * 24 * 60 * 60 * 1000L, trigger - now)
    }

    @Test
    fun `rolls over month and year boundaries`() {
        assertEquals(
            listOf(2026, 10, 1, 19, 0, 0, 0),
            fieldsOf(ReminderScheduler.nextTriggerAtMillis(at(2026, 9, 29, hour = 8), timeZone = zone)),
        )
        assertEquals(
            listOf(2027, 1, 1, 19, 0, 0, 0),
            fieldsOf(ReminderScheduler.nextTriggerAtMillis(at(2026, 12, 30, hour = 8), timeZone = zone)),
        )
    }

    @Test
    fun `handles leap day`() {
        assertEquals(
            listOf(2028, 3, 1, 19, 0, 0, 0),
            fieldsOf(ReminderScheduler.nextTriggerAtMillis(at(2028, 2, 28, hour = 8), timeZone = zone)),
        )
    }

    @Test
    fun `initial delay is always positive and matches the trigger`() {
        val now = at(2026, 9, 3, hour = 10, minute = 15)

        val delay = ReminderScheduler.initialDelayMillis(now, timeZone = zone)
        val trigger = ReminderScheduler.nextTriggerAtMillis(now, timeZone = zone)

        assertTrue(delay > 0)
        assertEquals(trigger - now, delay)
    }

    @Test
    fun `reopening the app pushes the trigger further out`() {
        val firstOpen = at(2026, 9, 3, hour = 10)
        val secondOpen = at(2026, 9, 4, hour = 9)

        val first = ReminderScheduler.nextTriggerAtMillis(firstOpen, timeZone = zone)
        val second = ReminderScheduler.nextTriggerAtMillis(secondOpen, timeZone = zone)

        assertEquals(listOf(2026, 9, 5, 19, 0, 0, 0), fieldsOf(first))
        assertEquals(listOf(2026, 9, 6, 19, 0, 0, 0), fieldsOf(second))
        assertTrue(second > first)
    }

    @Test
    fun `crossing a DST change still lands on 19 00 local time`() {
        // Europe/London moves off BST on 25 Oct 2026.
        val london = TimeZone.getTimeZone("Europe/London")
        val now = Calendar.getInstance(london).apply {
            clear()
            set(2026, Calendar.OCTOBER, 24, 12, 0, 0)
        }.timeInMillis

        val trigger = ReminderScheduler.nextTriggerAtMillis(now, timeZone = london)

        val cal = Calendar.getInstance(london).apply { timeInMillis = trigger }
        assertEquals(26, cal.get(Calendar.DAY_OF_MONTH))
        assertEquals(19, cal.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, cal.get(Calendar.MINUTE))
    }
}
