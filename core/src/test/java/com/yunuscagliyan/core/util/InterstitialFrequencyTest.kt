package com.yunuscagliyan.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InterstitialFrequencyTest {

    private fun frequency(threshold: Int = 8) = InterstitialFrequency(threshold)

    @Test
    fun `nothing is due before the threshold`() {
        val frequency = frequency()

        repeat(7) {
            frequency.recordEvent()
            assertFalse("due after ${frequency.currentCount()} events", frequency.isDue())
        }
    }

    @Test
    fun `an ad is due on the threshold event`() {
        val frequency = frequency()

        repeat(8) { frequency.recordEvent() }

        assertTrue(frequency.isDue())
    }

    @Test
    fun `showing an ad starts the interval over`() {
        val frequency = frequency()
        repeat(8) { frequency.recordEvent() }

        frequency.onShown()

        assertFalse(frequency.isDue())
        assertEquals(0, frequency.currentCount())
    }

    @Test
    fun `a missed slot retries on the very next event`() {
        val frequency = frequency()
        repeat(8) { frequency.recordEvent() }
        assertTrue(frequency.isDue())

        // No ad was loaded, so nothing is shown and the counter is left alone.
        frequency.recordEvent()

        assertTrue("a missed slot must not wait another full interval", frequency.isDue())
    }

    @Test
    fun `it stays due for as long as no ad shows`() {
        val frequency = frequency()
        repeat(20) { frequency.recordEvent() }

        repeat(5) { assertTrue(frequency.isDue()) }

        frequency.onShown()
        assertFalse(frequency.isDue())
    }

    @Test
    fun `a fresh instance is not due`() {
        assertFalse(frequency().isDue())
        assertEquals(0, frequency().currentCount())
    }

    @Test
    fun `the threshold is configurable`() {
        val frequency = frequency(threshold = 3)

        repeat(2) { frequency.recordEvent() }
        assertFalse(frequency.isDue())

        frequency.recordEvent()
        assertTrue(frequency.isDue())
    }
}
