package com.yunuscagliyan.core_ui.components.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.android.gms.ads.AdSize
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core_ui.components.admob.BannerAd
import com.yunuscagliyan.core_ui.components.card.PhotoImageCard
import com.yunuscagliyan.core_ui.components.empty.NetworkEmptyView
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.extension.shimmerEffect
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun PhotoStaggeredGrid(
    modifier: Modifier = Modifier,
    photosLazyItems: LazyPagingItems<PhotoModel>,
    onClick: (PhotoModel) -> Unit
) {

    when (photosLazyItems.loadState.refresh) {
        is LoadState.Error -> {
            NetworkErrorView(
                modifier = modifier
                    .fillMaxSize(),
                onRefreshClick = {
                    photosLazyItems.refresh()
                }
            )
        }

        is LoadState.Loading -> {
            LazyVerticalStaggeredGrid(
                modifier = modifier
                    .fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(WallXAppTheme.dimension.paddingMedium1),
                horizontalArrangement = Arrangement.spacedBy(WallXAppTheme.dimension.paddingMedium1),
                verticalItemSpacing = WallXAppTheme.dimension.paddingMedium1
            ) {
                items(50) {
                    Box(
                        modifier = Modifier
                            .clip(WallXAppTheme.shapes.large)
                            .fillMaxWidth()
                            .heightIn(min = 150.dp)
                            .shimmerEffect()
                    )
                }
            }
        }

        is LoadState.NotLoading -> {
            if (photosLazyItems.itemCount > 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    LazyVerticalStaggeredGrid(
                        modifier = modifier
                            .fillMaxSize(),
                        columns = StaggeredGridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            top = WallXAppTheme.dimension.paddingMedium1,
                            start = WallXAppTheme.dimension.paddingMedium1,
                            end = WallXAppTheme.dimension.paddingMedium1,
                            bottom = WallXAppTheme.dimension.defaultBannerHeight + WallXAppTheme.dimension.paddingMedium1
                        ),
                        horizontalArrangement = Arrangement.spacedBy(WallXAppTheme.dimension.paddingMedium1),
                        verticalItemSpacing = WallXAppTheme.dimension.paddingMedium1
                    ) {
                        items(photosLazyItems.itemCount) { index ->
                            val photoModel = photosLazyItems[index]
                            PhotoImageCard(
                                modifier = Modifier
                                    .noRippleClickable {
                                        if (photoModel != null) {
                                            onClick(photoModel)
                                        }
                                    },
                                imageUrl = photoModel?.webformatURL,
                            )
                        }
                    }
                    BannerAd()
                }
            } else {
                NetworkEmptyView(
                    modifier = modifier
                        .fillMaxSize()
                )
            }

        }
    }


}