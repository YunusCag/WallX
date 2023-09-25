package com.yunuscagliyan.wallx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.yunuscagliyan.core.util.Constant.DurationUtil.SPLASH_DURATION
import com.yunuscagliyan.core_ui.extension.loadInterstitial
import com.yunuscagliyan.core_ui.helper.AdmobHelper
import com.yunuscagliyan.core_ui.model.ThemeSelection
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.core_ui.viewmodel.SharedViewModel
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

    override fun onDestroy() {
        super.onDestroy()
        AdmobHelper.clearInterstitialAd()
    }
}