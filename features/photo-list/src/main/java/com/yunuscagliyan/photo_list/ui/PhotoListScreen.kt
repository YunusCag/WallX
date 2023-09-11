package com.yunuscagliyan.photo_list.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.COLLECTION_ID
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.COLLECTION_NAME
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.grid.PhotoStaggeredGrid
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.photo_list.viewmodel.PhotoListState
import com.yunuscagliyan.photo_list.viewmodel.PhotoListViewModel

object PhotoListScreen : CoreScreen<PhotoListState, PhotoListEvent>() {
    override val route: String
        get() = ScreenRoutes.PhotoListScreen.route


    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(name = COLLECTION_ID) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(name = COLLECTION_NAME) {
            type = NavType.StringType
            nullable = true
        }
    )

    @Composable
    override fun viewModel(): PhotoListViewModel = hiltViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(
        state: PhotoListState,
        onEvent: (PhotoListEvent) -> Unit
    ) {
        val photos = state.collectionPhotos.collectAsLazyPagingItems()

        MainUIFrame(
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onEvent(PhotoListEvent.OnBackPress)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.common_back),
                                tint = WallXAppTheme.colors.white
                            )
                        }
                    },
                    title = {
                        Text(
                            text = state.collectionName ?: EMPTY_STRING,
                            style = WallXAppTheme.typography.title1,
                            color = WallXAppTheme.colors.white,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = WallXAppTheme.colors.primary
                    )
                )
            }
        ) {
            PhotoStaggeredGrid(
                photosLazyItems = photos,
                onClick = {
                    onEvent(PhotoListEvent.OnPhotoClick(it))
                }
            )
        }
    }

}