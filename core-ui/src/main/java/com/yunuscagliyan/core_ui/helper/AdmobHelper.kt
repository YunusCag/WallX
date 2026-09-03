package com.yunuscagliyan.core_ui.helper

import android.app.Activity
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.yunuscagliyan.core.util.InterstitialFrequency

object AdmobHelper {
    private var interstitialAd: InterstitialAd? = null
    private var isRequestInFlight: Boolean = false
    private val frequency = InterstitialFrequency()

    fun loadInterstitialAd(ad: InterstitialAd) {
        this.interstitialAd = ad
        this.isRequestInFlight = false
    }

    fun onInterstitialLoadFailed() {
        clearInterstitialAd()
        this.isRequestInFlight = false
    }

    /**
     * The due check now runs on every event until an ad actually shows, so without
     * this guard each one would fire another request while the first is still in
     * flight.
     */
    fun shouldRequestInterstitial(): Boolean = interstitialAd == null && !isRequestInFlight

    fun markInterstitialRequested() {
        this.isRequestInFlight = true
    }

    fun clearInterstitialAd() {
        this.interstitialAd?.fullScreenContentCallback = null
        this.interstitialAd = null
    }

    fun isInterstitialAdLoaded(): Boolean = this.interstitialAd != null

    fun isEventLoaded(): Boolean = frequency.isDue()

    fun setInterstitialFullScreenCallback(callback: FullScreenContentCallback) {
        this.interstitialAd?.fullScreenContentCallback = callback
    }

    fun showInterstitial(activity: Activity) {
        frequency.onShown()
        this.interstitialAd?.show(activity)
    }

    fun increaseEventCounter() {
        frequency.recordEvent()
    }
}
