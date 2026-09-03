package com.yunuscagliyan.core.util

import com.yunuscagliyan.core.util.Constant.AdMobUtil.INTERSTITIAL_SHOWING_COUNTER

/**
 * Decides when an interstitial is due.
 *
 * The old rule was `counter % threshold == 0`, so reaching the threshold with no ad
 * loaded left the counter parked on it: the next event moved to threshold + 1 and the
 * check could not pass again for another full interval. Counting up and comparing
 * with >= means a missed slot simply retries on the following event.
 */
class InterstitialFrequency(
    private val threshold: Int = INTERSTITIAL_SHOWING_COUNTER,
) {
    private var eventCount: Int = 0

    fun recordEvent() {
        eventCount++
    }

    fun isDue(): Boolean = eventCount >= threshold

    fun onShown() {
        eventCount = 0
    }

    fun currentCount(): Int = eventCount
}
