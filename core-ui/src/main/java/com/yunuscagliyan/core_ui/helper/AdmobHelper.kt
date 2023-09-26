package com.yunuscagliyan.core_ui.helper

import android.app.Activity
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.yunuscagliyan.core.util.Constant.AdMobUtil.INTERSTITIAL_SHOWING_COUNTER

object AdmobHelper {
    private var interstitialAd: InterstitialAd? = null
    private var totalEventCounter: Int = 0

    fun loadInterstitialAd(ad: InterstitialAd) {
        this.interstitialAd = ad
    }

    fun clearInterstitialAd() {
        this.interstitialAd?.fullScreenContentCallback = null
        this.interstitialAd = null
    }

    fun isInterstitialAdLoaded(): Boolean {
        return this.interstitialAd != null
    }

    fun isEventLoaded(): Boolean {
        val counter = INTERSTITIAL_SHOWING_COUNTER
        return totalEventCounter != 0 && totalEventCounter % counter == 0
    }

    fun setInterstitialFullScreenCallback(callback: FullScreenContentCallback) {
        this.interstitialAd?.fullScreenContentCallback = callback
    }

    fun showInterstitial(activity: Activity) {
        resetEventCounter()
        this.interstitialAd?.show(activity)
    }

    fun increaseEventCounter() {
        this.totalEventCounter += 1
    }

    private fun resetEventCounter() {
        this.totalEventCounter = 0
    }
}