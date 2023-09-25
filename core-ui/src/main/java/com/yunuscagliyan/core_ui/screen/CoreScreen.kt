package com.yunuscagliyan.core_ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.yunuscagliyan.core_ui.event.Event
import com.yunuscagliyan.core_ui.event.Routes
import com.yunuscagliyan.core_ui.extension.asString
import com.yunuscagliyan.core_ui.extension.showInterstitial
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.core_ui.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class CoreScreen<S, E> {
    var navController: NavHostController? = null

    abstract val route: String

    var sharedViewModel: SharedViewModel? = null

    open fun getArguments(): List<NamedNavArgument> = emptyList()
    open fun getDeepLinks(): List<NavDeepLink> = emptyList()

    @Composable
    abstract fun viewModel(): CoreViewModel<S, E>

    @Composable
    abstract fun Content(state: S, onEvent: (E) -> Unit)


    fun getNavArgument(key: String): Any? {
        return sharedViewModel?.getArgument(key)
    }

    fun composable(
        builder: NavGraphBuilder,
        navHostController: NavHostController,
        sharedViewModel: SharedViewModel
    ) {
        this.navController = navHostController
        this.sharedViewModel = sharedViewModel

        builder.composable(
            route = route,
            arguments = getArguments(),
            deepLinks = getDeepLinks()
        ) {
            val viewModel = viewModel()
            val state by viewModel.state.collectAsState()
            val context = LocalContext.current
            val coroutine = rememberCoroutineScope()
            val snackState = remember { SnackbarHostState() }

            LaunchedEffect(key1 = Unit) {
                viewModel.uiEvent.collectLatest { event ->
                    when (event) {
                        is Event.Navigation -> {
                            context.showInterstitial {
                                handleNavigation(
                                    navHostController = navHostController,
                                    routes = event.state,
                                    sharedViewModel = sharedViewModel
                                )
                            }
                        }

                        is Event.ShowSnackBar -> {
                            coroutine.launch {
                                val result = snackState.showSnackbar(
                                    message = event.message.asString(context),
                                    actionLabel = event.actionLabel?.asString(context),
                                    duration = event.duration
                                )
                                when (result) {
                                    SnackbarResult.Dismissed -> Unit
                                    SnackbarResult.ActionPerformed -> {
                                        event.onClick?.invoke()
                                    }
                                }
                            }
                        }

                        is Event.Toast -> {
                            Toast.makeText(
                                context,
                                event.message.asString(context),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

            Content(
                state = state,
                onEvent = viewModel::onEvent
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                SnackbarHost(
                    hostState = snackState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            bottom = WallXAppTheme.dimension.paddingMedium2
                        ),
                    snackbar = {
                        Snackbar(
                            snackbarData = it,
                            actionColor = WallXAppTheme.colors.secondary,
                            containerColor = WallXAppTheme.colors.card,
                            contentColor = WallXAppTheme.colors.textPrimary,
                            shape = WallXAppTheme.shapes.large,
                        )
                    }
                )
            }
        }
    }

    private fun handleNavigation(
        navHostController: NavHostController,
        routes: Routes,
        sharedViewModel: SharedViewModel
    ) {
        when (routes) {
            is Routes.NavigateToRoute -> {
                routes.navArgument?.let { navArgument ->
                    sharedViewModel.setArgument(navArgument.key, navArgument.data)
                }
                navHostController.navigate(
                    routes.pageRoute,
                    navOptions = routes.options?.getNavOptions()
                )
            }

            is Routes.PopBackRoute -> {
                navHostController.popBackStack(
                    route = routes.route,
                    inclusive = routes.inclusive
                )
            }

            is Routes.PopBack -> {
                navHostController.popBackStack()
            }
        }
    }
}