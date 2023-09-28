package com.yunuscagliyan.search.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.grid.PhotoStaggeredGrid
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.search.viewModel.SearchState
import com.yunuscagliyan.search.viewModel.SearchViewModel

object SearchScreen : CoreScreen<SearchState, SearchEvent>() {
    override val route: String
        get() = ScreenRoutes.SearchScreen.route

    @Composable
    override fun viewModel(): SearchViewModel = hiltViewModel()

    @Composable
    override fun Content(
        state: SearchState,
        onEvent: (SearchEvent) -> Unit
    ) {
        val photos = state.photos.collectAsLazyPagingItems()

        MainUIFrame(
            topBar = {
                SearchTopBar(
                    query = state.search,
                    onBackPress = {
                        onEvent(SearchEvent.OnBackPress)
                    },
                    onSearch = {
                        onEvent(SearchEvent.OnSearch)
                    },
                    onQueryChange = {
                        onEvent(SearchEvent.OnQuerySearch(it))
                    }
                )
            }
        ) {
            PhotoStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize(),
                photosLazyItems = photos,
                onClick = {
                    onEvent(SearchEvent.OnPhotoClick(it))
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SearchTopBar(
        query: String,
        onQueryChange: (String) -> Unit,
        onSearch: () -> Unit,
        onBackPress: () -> Unit
    ) {

        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.common_back),
                        tint = WallXAppTheme.colors.white
                    )
                }
            },
            title = {
                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    textStyle = WallXAppTheme.typography.normal1,
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = WallXAppTheme.colors.white
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    onQueryChange(EMPTY_STRING)
                                }
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = null,
                                    tint = WallXAppTheme.colors.white
                                )
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            "Search",
                            style = WallXAppTheme.typography.normal1,
                            color = WallXAppTheme.colors.secondaryGray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = WallXAppTheme.colors.white,
                        focusedTextColor = WallXAppTheme.colors.white,
                        unfocusedTextColor = WallXAppTheme.colors.white,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearch()
                        }
                    ),
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = WallXAppTheme.colors.primary
            )
        )

    }
}