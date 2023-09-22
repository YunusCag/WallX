package com.yunuscagliyan.wallx

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.yunuscagliyan.core.util.Constant.DurationUtil.SPLASH_DURATION
import com.yunuscagliyan.core_ui.manager.AutoWallpaperManager
import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.core_ui.viewmodel.SharedViewModel
import com.yunuscagliyan.wallx.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var keepScreen: Boolean = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition {
            keepScreen
        }
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

            LaunchedEffect(key1 = Unit) {
                val workRequest = PeriodicWorkRequestBuilder<AutoWallpaperManager>(
                    repeatInterval = 15,
                    repeatIntervalTimeUnit = TimeUnit.MINUTES,
                    flexTimeInterval = 15,
                    flexTimeIntervalUnit = TimeUnit.MINUTES
                ).setBackoffCriteria(
                    backoffPolicy = BackoffPolicy.LINEAR,
                    duration = Duration.ofSeconds(15)
                ).setConstraints(
                    Constraints(
                        requiredNetworkType = NetworkType.CONNECTED
                    )
                ).build()
                val workManager = WorkManager.getInstance(applicationContext)
                workManager.enqueueUniquePeriodicWork(
                    "auto_change_work",
                    ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                    workRequest
                )
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
}