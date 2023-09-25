package com.yunuscagliyan.core_ui.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.android.gms.ads.AdSize
import com.yunuscagliyan.core.data.remote.model.collection.CollectionModel
import com.yunuscagliyan.core_ui.components.admob.BannerAd
import com.yunuscagliyan.core_ui.components.card.CollectionCard
import com.yunuscagliyan.core_ui.components.empty.NetworkEmptyView
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.extension.shimmerEffect
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun CollectionListView(
    modifier: Modifier = Modifier,
    collections: LazyPagingItems<CollectionModel>,
    onClick: (CollectionModel) -> Unit
) {
    when (collections.loadState.refresh) {
        is LoadState.Error -> {
            NetworkErrorView(
                modifier = modifier
                    .fillMaxSize(),
                onRefreshClick = {
                    collections.refresh()
                }
            )
        }

        is LoadState.Loading -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(WallXAppTheme.dimension.paddingMedium1),
                verticalArrangement = Arrangement.spacedBy(WallXAppTheme.dimension.paddingMedium1)
            ) {
                items(20) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(WallXAppTheme.dimension.collectionItemHeight)
                            .clip(WallXAppTheme.shapes.large)
                            .shimmerEffect()
                    )
                }
            }
        }

        is LoadState.NotLoading -> {
            if (collections.itemCount > 0) {
                LazyColumn(
                    modifier = modifier,
                    contentPadding = PaddingValues(WallXAppTheme.dimension.paddingMedium1),
                    verticalArrangement = Arrangement.spacedBy(WallXAppTheme.dimension.paddingMedium1)
                ) {
                    items(collections.itemCount) { index ->
                        val collection = collections[index]
                        Column {
                            CollectionCard(
                                modifier = Modifier
                                    .noRippleClickable {
                                        if (collection != null) {
                                            onClick(collection)
                                        }
                                    },
                                collectionModel = collection
                            )
                            if ((index == 1 || index % 5 == 0) && (index != 0)) {
                                BannerAd(
                                    modifier = Modifier
                                        .padding(
                                            top = WallXAppTheme.dimension.paddingMedium1,
                                        ),
                                    adSize = AdSize.LARGE_BANNER
                                )
                            }
                        }
                    }
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