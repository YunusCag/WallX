package com.yunuscagliyan.core_ui.components.tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WallTapRow(
    modifier: Modifier = Modifier,
    titles: List<String>,
    initialPage: Int = 0,
    tabHeight: Dp = WallXAppTheme.dimension.tabHeight,
    containerColor: Color = WallXAppTheme.colors.primary,
    selectedColor: Color = WallXAppTheme.colors.secondary,
    unSelectedColor: Color = WallXAppTheme.colors.white,
    tabTextStyle: TextStyle = WallXAppTheme.typography.normal1,
    pageContent: @Composable PagerScope.(page: Int) -> Unit
) {
    val pages = remember { titles }
    val pagerState = rememberPagerState(
        pageCount = { pages.size },
        initialPage = initialPage
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth(),
            containerColor = containerColor,
            divider = {
                Divider(
                    thickness = WallXAppTheme.dimension.borderWidth,
                    color = WallXAppTheme.colors.secondaryGray
                )
            }
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier
                        .height(tabHeight),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },

                    selectedContentColor = selectedColor,
                    unselectedContentColor = unSelectedColor,
                    content = {
                        Text(
                            text = title,
                            style = tabTextStyle
                        )
                    }
                )

            }
        }

        HorizontalPager(
            state = pagerState,
            pageContent = pageContent,
            pageSize = PageSize.Fill,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewWallTapRow() {
    WallTapRow(
        titles = listOf("New", "Popular"),
    ) {

    }
}