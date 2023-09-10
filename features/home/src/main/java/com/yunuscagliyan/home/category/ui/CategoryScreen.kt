package com.yunuscagliyan.home.category.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.yunuscagliyan.core_ui.components.list.CollectionListView
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.home.category.viewModel.CategoryState
import com.yunuscagliyan.home.category.viewModel.CategoryViewModel


object CategoryScreen : CoreScreen<CategoryState, CategoryEvent>() {
    override val route: String
        get() = ScreenRoutes.CategoryScreen.route

    @Composable
    override fun viewModel(): CategoryViewModel = hiltViewModel()

    @Composable
    override fun Content(
        state: CategoryState,
        onEvent: (CategoryEvent) -> Unit
    ) {
        val collections = state.colletions.collectAsLazyPagingItems()
        CollectionListView(
            modifier = Modifier
                .fillMaxSize(),
            collections = collections,
            onClick = {
                onEvent(CategoryEvent.OnCollectionClick(it))
            }
        )
    }
}