package com.yunuscagliyan.core_ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.yunuscagliyan.core_ui.event.Event
import com.yunuscagliyan.core_ui.event.Routes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.core_ui.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.collectLatest

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

            LaunchedEffect(key1 = Unit) {
                viewModel.uiEvent.collectLatest { event ->
                    when (event) {
                        is Event.Navigation -> {
                            handleNavigation(
                                navHostController = navHostController,
                                routes = event.state,
                                sharedViewModel = sharedViewModel
                            )
                        }
                    }
                }
            }

            Content(
                state = state,
                onEvent = viewModel::onEvent
            )
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