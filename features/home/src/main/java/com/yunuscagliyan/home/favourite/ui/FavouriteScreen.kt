package com.yunuscagliyan.home.favourite.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.card.FavouritePhotoCard
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.home.favourite.viewModel.FavouriteState
import com.yunuscagliyan.home.favourite.viewModel.FavouriteViewModel

object FavouriteScreen : CoreScreen<FavouriteState, FavouriteEvent>() {
    override val route: String
        get() = ScreenRoutes.FavouriteScreen.route

    @Composable
    override fun viewModel(): FavouriteViewModel = hiltViewModel()

    @Composable
    override fun Content(
        state: FavouriteState,
        onEvent: (FavouriteEvent) -> Unit
    ) {
        LaunchedEffect(key1 = Unit) {
            onEvent(FavouriteEvent.InitPhotoList)
        }
        if (state.photoList.isNotEmpty()) {
            PhotoGrid(
                photoList = state.photoList,
                onClick = {
                    onEvent(FavouriteEvent.OnPhotoClick(it))
                },
                onFavouriteClick = { index, photoModel ->
                    onEvent(
                        FavouriteEvent.OnFavouriteClick(
                            index = index,
                            photoModel = photoModel
                        )
                    )
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_circle_heart),
                    contentDescription = stringResource(id = R.string.common_no_favourites),
                    modifier = Modifier
                        .size(
                            WallXAppTheme.dimension.large1
                        ),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(WallXAppTheme.colors.secondary)
                )
                Spacer(
                    modifier = Modifier
                        .height(WallXAppTheme.dimension.paddingSmall1)
                )
                Text(
                    text = stringResource(id = R.string.common_no_favourites),
                    style = WallXAppTheme.typography.normal1,
                    color = WallXAppTheme.colors.textPrimary
                )
            }
        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun PhotoGrid(
        photoList: List<PhotoModel>,
        onClick: (PhotoModel) -> Unit,
        onFavouriteClick: (Int, PhotoModel) -> Unit,
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(WallXAppTheme.dimension.paddingMedium1),
            horizontalArrangement = Arrangement.spacedBy(WallXAppTheme.dimension.paddingMedium1),
            verticalArrangement = Arrangement.spacedBy(WallXAppTheme.dimension.paddingMedium1)
        ) {
            items(photoList.size) { index ->
                val photoModel = photoList[index]
                FavouritePhotoCard(
                    modifier = Modifier
                        .noRippleClickable {
                            onClick(photoModel)
                        }
                        .aspectRatio(WallXAppTheme.dimension.favouritePhotoAspectRatio)
                        .animateItemPlacement(),
                    imageUrl = photoModel.urls?.small,
                    hexColor = photoModel.color,
                    onFavouriteClick = {
                        onFavouriteClick(index, photoModel)
                    }
                )
            }
        }
    }
}