package com.yunuscagliyan.wallx

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.yunuscagliyan.core.util.Constant.DurationUtil.SPLASH_DURATION
import com.yunuscagliyan.core.util.MobileAdsConsentManager
import com.yunuscagliyan.core_ui.extension.loadInterstitial
import com.yunuscagliyan.core_ui.extension.scheduleReminderNotification
import com.yunuscagliyan.core_ui.helper.AdmobHelper
import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.core_ui.viewmodel.SharedViewModel
import com.yunuscagliyan.core_ui.helper.NotificationHelper
import com.yunuscagliyan.wallx.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var keepScreen: Boolean = true

    private var isMobileAdsInitializedCalled = AtomicBoolean(false)
    private lateinit var mobileAdsConsentManager: MobileAdsConsentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition {
            keepScreen
        }
        getConsent()
        // Independent of ads: hanging this off initializeMobileAdsSdk() meant a user
        // who declined consent, or whose consent request failed, never got the
        // reminder scheduled at all.
        checkNotificationPermission()
        lifecycleScope.launch {
            delay(SPLASH_DURATION.toLong())
            keepScreen = false
        }
        setContent {
            val sharedViewModel: SharedViewModel = hiltViewModel()
            val sharedState by sharedViewModel.sharedState
            val darkTheme = when (sharedState.themeSelection) {
                ThemeSelection.SYSTEM -> isSystemInDarkTheme()
                ThemeSelection.DARK -> true
                ThemeSelection.LIGHT -> false
            }


            WallXAppTheme(
                darkTheme = darkTheme
            ) {
                val navHostController = rememberNavController()
                SetupNavGraph(
                    navHostController = navHostController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                scheduleReminderNotification()
            }
        }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                scheduleReminderNotification()
            } else {
                requestNotificationPermission.launch(permission)
            }
        } else {
            scheduleReminderNotification()
        }
    }



    private fun getConsent() {
        mobileAdsConsentManager = MobileAdsConsentManager.getInstance(applicationContext)
        mobileAdsConsentManager.gatherConsent(this) { error ->
            if (error != null) {
                Timber.e("${error.errorCode}: ${error.message}")
            }
            if (mobileAdsConsentManager.canRequestAds) {
                initializeMobileAdsSdk()
            }
        }
        if (mobileAdsConsentManager.canRequestAds) {
            initializeMobileAdsSdk()
        }
    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializedCalled.getAndSet(true)) return
        MobileAds.initialize(this)
        this.loadInterstitial()
    }

    override fun onDestroy() {
        super.onDestroy()
        AdmobHelper.clearInterstitialAd()
    }
}