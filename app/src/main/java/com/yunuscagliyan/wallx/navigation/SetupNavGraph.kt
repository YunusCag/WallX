package com.yunuscagliyan.wallx.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yunuscagliyan.core.util.Constant.DurationUtil.TRANSITION_DURATION
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.viewmodel.SharedViewModel
import com.yunuscagliyan.home.main.ui.MainScreen
import com.yunuscagliyan.photo_detail.ui.PhotoDetailScreen
import com.yunuscagliyan.photo_list.ui.PhotoListScreen
import com.yunuscagliyan.search.ui.SearchScreen


@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navHostController,
        startDestination = ScreenRoutes.MainScreen.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(TRANSITION_DURATION, easing = LinearEasing)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(TRANSITION_DURATION, easing = LinearEasing)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(TRANSITION_DURATION, easing = LinearEasing)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(TRANSITION_DURATION, easing = LinearEasing)
            )
        }
    ) {
        MainScreen.composable(
            builder = this,
            navHostController = navHostController,
            sharedViewModel = sharedViewModel
        )
        PhotoListScreen.composable(
            builder = this,
            navHostController = navHostController,
            sharedViewModel = sharedViewModel
        )
        PhotoDetailScreen.composable(
            builder = this,
            navHostController = navHostController,
            sharedViewModel = sharedViewModel
        )
        SearchScreen.composable(
            builder = this,
            navHostController = navHostController,
            sharedViewModel = sharedViewModel
        )
    }
}