package com.yunuscagliyan.home.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.grid.PhotoStaggeredGrid
import com.yunuscagliyan.core_ui.components.tab.WallTapRow
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.home.home.viewModel.HomeState
import com.yunuscagliyan.home.home.viewModel.HomeViewModel
import timber.log.Timber


object HomeScreen : CoreScreen<HomeState, HomeEvent>() {
    override val route: String
        get() = ScreenRoutes.HomeScreen.route

    @Composable
    override fun viewModel(): HomeViewModel = hiltViewModel()

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content(
        state: HomeState,
        onEvent: (HomeEvent) -> Unit
    ) {
        val newPhotos = state.newPhotos.collectAsLazyPagingItems()
        val popularPhotos = state.popularPhotos.collectAsLazyPagingItems()

        val onPhotoClick: (PhotoModel) -> Unit = remember {
            {
                Timber.e("Photo Clicked.")
                onEvent(HomeEvent.OnPhotoClick(it))
            }
        }

        WallTapRow(
            modifier = Modifier
                .fillMaxSize(),
            titles = listOf(
                stringResource(id = R.string.home_tab_latest),
                stringResource(id = R.string.home_tab_popular)
            )
        ) { page ->
            when (page) {
                0 -> {
                    PhotoStaggeredGrid(
                        modifier = Modifier
                            .fillMaxSize(),
                        photosLazyItems = newPhotos,
                        onClick = onPhotoClick
                    )
                }

                1 -> {
                    PhotoStaggeredGrid(
                        modifier = Modifier
                            .fillMaxSize(),
                        photosLazyItems = popularPhotos,
                        onClick = onPhotoClick
                    )
                }
            }
        }
    }

}