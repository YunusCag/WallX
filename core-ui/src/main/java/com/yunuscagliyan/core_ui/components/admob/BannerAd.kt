package com.yunuscagliyan.core_ui.components.admob

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.yunuscagliyan.core_ui.BuildConfig

@Composable
fun BannerAd(
    modifier: Modifier = Modifier,
    adSize: AdSize = AdSize.BANNER
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    // Holds the live view so the lifecycle observer can reach it. Deliberately not
    // snapshot state: nothing should recompose when the view is attached.
    val adViewHolder = remember { arrayOfNulls<AdView>(1) }

    // An AdView keeps auto-refreshing until it is paused, so a banner left behind by
    // navigation or a backgrounded app would go on billing impressions nobody sees.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> adViewHolder[0]?.pause()
                Lifecycle.Event.ON_RESUME -> adViewHolder[0]?.resume()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(adSize)
                    adUnitId = BuildConfig.ADMOB_BANNER_ID
                    loadAd(AdRequest.Builder().build())
                    adViewHolder[0] = this
                }
            },
            onRelease = { view ->
                adViewHolder[0] = null
                view.destroy()
            }
        )
    }
}
