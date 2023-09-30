package com.yunuscagliyan.wallx

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.yunuscagliyan.core.util.Constant.DurationUtil.NOTIFICATION_PERIOD_INTERVAL
import com.yunuscagliyan.core.util.Constant.DurationUtil.SPLASH_DURATION
import com.yunuscagliyan.core_ui.extension.loadInterstitial
import com.yunuscagliyan.core_ui.helper.AdmobHelper
import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.core_ui.viewmodel.SharedViewModel
import com.yunuscagliyan.core_ui.helper.NotificationHelper
import com.yunuscagliyan.core_ui.receiver.NotificationReceiver
import com.yunuscagliyan.wallx.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var keepScreen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        MobileAds.initialize(this)
        this.loadInterstitial()
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition {
            keepScreen
        }
        lifecycleScope.launch {
            delay(SPLASH_DURATION.toLong())
            checkNotificationPermission()
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
                setNotificationReceiver()
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
                setNotificationReceiver()
            } else {
                requestNotificationPermission.launch(permission)
            }
        } else {
            setNotificationReceiver()
        }
    }

    private fun setNotificationReceiver() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
        )
        val intervalInMillis = NOTIFICATION_PERIOD_INTERVAL
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            intervalInMillis,
            pendingIntent
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        AdmobHelper.clearInterstitialAd()
    }
}