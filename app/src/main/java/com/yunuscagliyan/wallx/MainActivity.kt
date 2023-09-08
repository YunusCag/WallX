package com.yunuscagliyan.wallx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.wallx.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallXAppTheme {
                val navHostController = rememberNavController()
                SetupNavGraph(
                    navHostController = navHostController
                )
            }
        }
    }
}