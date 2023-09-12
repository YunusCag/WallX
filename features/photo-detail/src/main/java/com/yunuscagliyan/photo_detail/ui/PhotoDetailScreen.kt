package com.yunuscagliyan.photo_detail.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_HEX_COLOR_KEY
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_URL_KEY
import com.yunuscagliyan.core_ui.components.image.WallImage
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.photo_detail.viewmodel.PhotoDetailState
import com.yunuscagliyan.photo_detail.viewmodel.PhotoDetailViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object PhotoDetailScreen : CoreScreen<PhotoDetailState, PhotoDetailEvent>() {
    override val route: String
        get() = ScreenRoutes.PhotoDetailScreen.route

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(name = PHOTO_URL_KEY) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(name = PHOTO_HEX_COLOR_KEY) {
            type = NavType.StringType
            nullable = true
        }
    )

    @Composable
    override fun viewModel(): PhotoDetailViewModel = hiltViewModel()

    @Composable
    override fun Content(
        state: PhotoDetailState,
        onEvent: (PhotoDetailEvent) -> Unit
    ) {
        val activity = LocalContext.current as Activity
        val primaryDark = WallXAppTheme.colors.primaryDark
        val coroutine = rememberCoroutineScope()
        SideEffect {
            coroutine.launch {
                delay(100L)
                val window = activity.window
                window.statusBarColor = Color.Transparent.toArgb()
            }

        }

        BackHandler {
            val window = activity.window
            window.statusBarColor = primaryDark.toArgb()
            navController?.popBackStack()
        }
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.Transparent
        ) {
            WallImage(
                modifier = Modifier
                    .fillMaxSize(),
                url = state.imageUrl,
                hexColor = state.hexColorString,
                contentScale = ContentScale.FillHeight
            )
        }

    }
}