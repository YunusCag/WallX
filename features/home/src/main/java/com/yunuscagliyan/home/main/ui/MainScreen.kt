package com.yunuscagliyan.home.main.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yunuscagliyan.core.util.Constant
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.home.category.ui.CategoryScreen
import com.yunuscagliyan.home.favourite.ui.FavouriteScreen
import com.yunuscagliyan.home.home.ui.HomeScreen
import com.yunuscagliyan.home.main.viewModel.MainState
import com.yunuscagliyan.home.main.viewModel.MainViewModel
import com.yunuscagliyan.home.settings.ui.SettingScreen

object MainScreen : CoreScreen<MainState, MainEvent>() {
    override val route: String
        get() = ScreenRoutes.MainScreen.route

    @Composable
    override fun viewModel(): MainViewModel = hiltViewModel<MainViewModel>()

    @Composable
    override fun Content(
        state: MainState,
        onEvent: (MainEvent) -> Unit
    ) {
        val navHostController = rememberNavController()
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        MainUIFrame(
            topBar = {
                TopBar()
            },
            bottomBar = {
                BottomBar(
                    state = state,
                    currentRoute = currentRoute,
                    navHostController = navHostController
                )
            }
        ) {
            this.navController?.let { navController ->
                SetupNavGraph(
                    mainHostController = navHostController,
                    rootNavController = navController
                )
            }

        }
    }

    @Composable
    private fun BottomBar(
        state: MainState,
        currentRoute: String?,
        navHostController: NavHostController
    ) {
        NavigationBar(
            containerColor = WallXAppTheme.colors.bottomBar,
            contentColor = Color.Transparent,
            tonalElevation = 0.dp,
        ) {
            state.bottomBarItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = item.navRoute == currentRoute,
                    onClick = {
                        navHostController.navigate(item.navRoute) {
                            navHostController.graph.startDestinationRoute?.let { screenRoute ->
                                popUpTo(screenRoute) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.name),
                            modifier = Modifier
                                .size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            stringResource(id = item.name),
                            style = WallXAppTheme.typography.small1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = WallXAppTheme.colors.textPrimary
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedTextColor = WallXAppTheme.colors.textPrimary,
                        selectedIconColor = WallXAppTheme.colors.white,
                        indicatorColor = WallXAppTheme.colors.secondary,
                        unselectedIconColor = WallXAppTheme.colors.textPrimary
                    )
                )
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar() {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = WallXAppTheme.typography.title1,
                    color = WallXAppTheme.colors.white
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = WallXAppTheme.colors.primary
            )
        )
    }

    @Composable
    fun SetupNavGraph(
        modifier: Modifier = Modifier,
        mainHostController: NavHostController,
        rootNavController: NavHostController,
    ) {
        NavHost(
            modifier = modifier
                .fillMaxSize(),
            navController = mainHostController,
            startDestination = ScreenRoutes.HomeScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        Constant.DurationUtil.TRANSITION_DURATION,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        Constant.DurationUtil.TRANSITION_DURATION,
                        easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        Constant.DurationUtil.TRANSITION_DURATION,
                        easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        Constant.DurationUtil.TRANSITION_DURATION,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            HomeScreen.composable(
                builder = this,
                navHostController = rootNavController
            )
            CategoryScreen.composable(
                builder = this,
                navHostController = rootNavController
            )
            FavouriteScreen.composable(
                builder = this,
                navHostController = rootNavController
            )
            SettingScreen.composable(
                builder = this,
                navHostController = rootNavController
            )
        }
    }
}