package com.yunuscagliyan.core_ui.components.admob

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.yunuscagliyan.core_ui.BuildConfig

@Composable
fun BannerAd(
    modifier: Modifier = Modifier,
    adSize: AdSize = AdSize.BANNER
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth(),
            factory = {
                val request = AdRequest.Builder()
                    .build()
                AdView(it).apply {
                    setAdSize(adSize)
                    adUnitId = BuildConfig.ADMOB_BANNER_ID
                    loadAd(request)
                }
            }
        )
    }
}