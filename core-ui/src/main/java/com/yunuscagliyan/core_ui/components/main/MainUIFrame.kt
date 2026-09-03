package com.yunuscagliyan.core_ui.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yunuscagliyan.core_ui.theme.WallXAppTheme


@Composable
fun MainUIFrame(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    backgroundColor: Color = WallXAppTheme.colors.background,
    // Painted behind the status bar. Window.statusBarColor is a no-op once the app
    // targets API 35+, so the app has to draw that strip itself.
    statusBarColor: Color = WallXAppTheme.colors.primaryDark,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding(),
            topBar = topBar,
            bottomBar = bottomBar,
            containerColor = backgroundColor,

            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        actionColor = WallXAppTheme.colors.secondary,
                        snackbarData = data
                    )
                }
            },
            content = {
                Box(
                    modifier = Modifier
                        .padding(it)
                ) {
                    content()
                }
            },
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(statusBarColor)
        )
    }
}
