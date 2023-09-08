package com.yunuscagliyan.core_ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.data.remote.model.collection.CollectionModel
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.image.WallImage
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import timber.log.Timber

@Composable
fun CollectionCard(
    collectionModel: CollectionModel?
) {
    Box(
        modifier = Modifier
            .clip(WallXAppTheme.shapes.large)
            .fillMaxWidth()
            .height(WallXAppTheme.dimension.collectionItemHeight)
    ) {
        WallImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(WallXAppTheme.dimension.collectionItemHeight),
            url = collectionModel?.coverPhoto?.urls?.small,
            hexColor = collectionModel?.coverPhoto?.color,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
                .padding(
                    WallXAppTheme.dimension.paddingSmall2
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    collectionModel?.title ?: EMPTY_STRING,
                    style = WallXAppTheme.typography.normal2,
                    color = WallXAppTheme.colors.white,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    "${collectionModel?.totalPhotos ?: 0} Photos",
                    style = WallXAppTheme.typography.normal3,
                    color = WallXAppTheme.colors.white,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .size(WallXAppTheme.dimension.collectionProfileImage),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    WallImage(
                        url = collectionModel?.user?.profileImage?.medium,
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.width(WallXAppTheme.dimension.paddingSmall2))
                Text(
                    "${collectionModel?.user?.firstName ?: EMPTY_STRING} ${collectionModel?.user?.lastName ?: EMPTY_STRING}",
                    style = WallXAppTheme.typography.normal2,
                    color = WallXAppTheme.colors.white,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}


