package com.yunuscagliyan.home.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.home.home.viewModel.HomeState
import com.yunuscagliyan.home.home.viewModel.HomeViewModel


object HomeScreen : CoreScreen<HomeState, HomeEvent>() {
    override val route: String
        get() = ScreenRoutes.HomeScreen.route

    @Composable
    override fun viewModel(): HomeViewModel = hiltViewModel()

    @Composable
    override fun Content(
        state: HomeState,
        onEvent: (HomeEvent) -> Unit
    ) {
        val popularPhotos = state.popularPhotos.collectAsLazyPagingItems()
        LazyColumn {
            items(popularPhotos.itemCount, key = popularPhotos.itemKey()) { index ->
                val photo = popularPhotos[index]
                photo?.urls?.thumb?.let {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = it,
                        contentDescription = it,
                        contentScale = ContentScale.FillWidth
                    )
                }

            }
        }
    }

}