package com.yunuscagliyan.photo_detail.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.panBy
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.zoomBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.devtamuno.composeblurhash.ExperimentalComposeBlurHash
import com.devtamuno.composeblurhash.ext.rememberBlurHashPainter
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.util.Constant.DurationUtil.TRANSITION_DURATION
import com.yunuscagliyan.core.util.Constant.NavigationArgumentKey.PHOTO_KEY
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.anim.AnimationBox
import com.yunuscagliyan.core_ui.components.button.FavouriteButton
import com.yunuscagliyan.core_ui.components.button.FilledLoadingButton
import com.yunuscagliyan.core_ui.components.dialog.ErrorDialog
import com.yunuscagliyan.core_ui.components.image.WallImage
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.components.sheet.BaseModalSheet
import com.yunuscagliyan.core_ui.components.sheet.SingleSelectionBottomSheet
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.extension.asString
import com.yunuscagliyan.core_ui.model.SelectionModel
import com.yunuscagliyan.core_ui.screen.CoreScreen
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.photo_detail.viewmodel.PhotoDetailState
import com.yunuscagliyan.photo_detail.viewmodel.PhotoDetailViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object PhotoDetailScreen : CoreScreen<PhotoDetailState, PhotoDetailEvent>() {
    override val route: String
        get() = ScreenRoutes.PhotoDetailScreen.route


    @Composable
    override fun viewModel(): PhotoDetailViewModel = hiltViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(
        state: PhotoDetailState,
        onEvent: (PhotoDetailEvent) -> Unit
    ) {
        LaunchedEffect(key1 = Unit) {
            val navArgument = getNavArgument(PHOTO_KEY)
            if (navArgument is PhotoModel) {
                onEvent(PhotoDetailEvent.InitPhotoModel(navArgument))
            }
        }

        val view = LocalView.current
        val activity = LocalContext.current as Activity
        val window = activity.window
        val coroutine = rememberCoroutineScope()

        val detailBarColor = WallXAppTheme.colors.black.copy(
            alpha = 0.3f
        )
        val barColor = WallXAppTheme.colors.bottomBar
        val primaryDark = WallXAppTheme.colors.primaryDark
        val isDark = WallXAppTheme.colors.isDark

        val onBackPressed: () -> Unit = remember {
            {
                window.statusBarColor = primaryDark.toArgb()
                window.navigationBarColor = barColor.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                    !isDark
                onEvent(PhotoDetailEvent.OnBackPress)
            }
        }

        val onFavouriteClick: (Boolean) -> Unit = remember {
            {
                onEvent(PhotoDetailEvent.OnFavouriteClick(isFavourite = it))
            }
        }

        val onSaveClick: () -> Unit = remember {
            {
                onEvent(PhotoDetailEvent.OnSaveClick)
            }
        }

        val onSetClick: () -> Unit = remember {
            {
                onEvent(PhotoDetailEvent.OnSetClick)
            }
        }

        LaunchedEffect(key1 = Unit) {
            coroutine.launch {
                delay(TRANSITION_DURATION.toLong())
                window.statusBarColor = detailBarColor.toArgb()
                window.navigationBarColor = detailBarColor.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                    false
            }
        }

        var scale by remember { mutableFloatStateOf(1f) }
        var offset by remember { mutableStateOf(Offset(0f, 0f)) }

        val zoomState = rememberTransformableState { zoomChange, offsetChange, _ ->
            scale *= zoomChange
            offset = if (scale > 1f) offsetChange else Offset(0f, 0f)
        }

        BackHandler {
            onBackPressed()
        }

        if (state.showWallpaperSelectionSheet) {
            SingleSelectionBottomSheet(
                onDismissRequest = {
                    onEvent(PhotoDetailEvent.BottomSheet(isOpen = false))
                },
                selectedIndex = state.sheetSelectionIndex,
                title = stringResource(id = R.string.photo_detail_sheet_screen_selection_title),
                selections = listOf(
                    SelectionModel(
                        title = stringResource(id = R.string.photo_detail_sheet_screen_selection_home_screen)
                    ),
                    SelectionModel(
                        title = stringResource(id = R.string.photo_detail_sheet_screen_selection_lock)
                    ),
                    SelectionModel(
                        title = stringResource(id = R.string.photo_detail_sheet_screen_selection_both)
                    )
                ),
            ) { index, _ ->
                onEvent(PhotoDetailEvent.OnScreenSelection(index = index))
            }
        }

        if(state.showErrorDialog){
            ErrorDialog(
                description = state.errorMessage?.asString(context = LocalContext.current)
                    ?: stringResource(
                        id = R.string.common_error
                    ),
                onDismissRequest = {
                    onEvent(PhotoDetailEvent.OnOkayClickErrorDialog)
                },
                onOkayClicked = {
                    onEvent(PhotoDetailEvent.OnOkayClickErrorDialog)
                }
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        coroutine.launch {
                            zoomState.zoomBy(zoom)
                            zoomState.panBy(pan)
                        }
                    }
                },
            color = Color.Transparent
        ) {
            state.photoModel?.let { photo ->
                PhotoImage(
                    photo = photo,
                    scale = scale,
                    offset = offset
                )
            }
            MainUIFrame(
                backgroundColor = Color.Transparent,
                topBar = {
                    TopBar(
                        isFavourite = state.isFavourite,
                        onBackPress = onBackPressed,
                        onFavouriteClick = onFavouriteClick
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    InfoCard(
                        state = state,
                        onSaveClick = onSaveClick,
                        onSetClick = onSetClick
                    )

                }
            }
        }

    }

    @Composable
    private fun InfoCard(
        state: PhotoDetailState,
        onSaveClick: () -> Unit,
        onSetClick: () -> Unit
    ) {
        AnimationBox {
            Box(
                modifier = Modifier
                    .background(
                        WallXAppTheme.colors.black.copy(
                            alpha = 0.05f
                        )
                    )
                    .padding(
                        vertical = WallXAppTheme.dimension.paddingMedium2,
                        horizontal = WallXAppTheme.dimension.paddingSmall2
                    )

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
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
                                url = state.photoModel?.user?.profileImage?.medium,
                                contentScale = ContentScale.Fit
                            )
                        }

                        Spacer(modifier = Modifier.width(WallXAppTheme.dimension.paddingSmall2))
                        Text(
                            "${state.photoModel?.user?.firstName ?: EMPTY_STRING} ${state.photoModel?.user?.lastName ?: EMPTY_STRING}",
                            style = WallXAppTheme.typography.normal1,
                            color = WallXAppTheme.colors.white,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingMedium1))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(intrinsicSize = IntrinsicSize.Min)
                    ) {
                        FilledLoadingButton(
                            modifier = Modifier.weight(1f),
                            initialText = stringResource(id = R.string.photo_detail_save_button_initial),
                            successText = stringResource(id = R.string.photo_detail_save_button_success),
                            errorText = stringResource(id = R.string.photo_detail_save_button_failed),
                            type = state.saveButtonType,
                            iconId = R.drawable.ic_save,
                            backgroundColor = WallXAppTheme.colors.accent,
                            onClick = onSaveClick
                        )
                        Spacer(modifier = Modifier.width(WallXAppTheme.dimension.paddingSmall3))
                        FilledLoadingButton(
                            modifier = Modifier.weight(1f),
                            initialText = stringResource(id = R.string.photo_detail_set_button_initial),
                            successText = stringResource(id = R.string.photo_detail_set_button_success),
                            errorText = stringResource(id = R.string.photo_detail_set_button_failed),
                            type = state.setButtonType,
                            iconId = R.drawable.ic_wallpaper,
                            backgroundColor = WallXAppTheme.colors.secondary,
                            onClick = onSetClick
                        )
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalComposeBlurHash::class)
    private fun PhotoImage(
        photo: PhotoModel,
        scale: Float,
        offset: Offset,
    ) {

        val configuration = LocalConfiguration.current
        val density = LocalDensity.current.density

        val screenWidthDp = configuration.screenWidthDp
        val screenHeightDp = configuration.screenHeightDp
        val screenWidthPx = (screenWidthDp * density).toInt()
        val screenHeightPx = (screenHeightDp * density).toInt()


        val placeHolder = rememberBlurHashPainter(
            blurString = photo.blurHash ?: EMPTY_STRING,
            width = Int.MAX_VALUE,
            height = Int.MAX_VALUE,
        )

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.urls?.raw)
                .size(Size(screenWidthPx, screenHeightPx))
                .crossfade(true)
                .build()
        )


        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                if (photo.blurHash != null) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        painter = placeHolder,
                        contentDescription = EMPTY_STRING,
                        contentScale = ContentScale.FillHeight
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(WallXAppTheme.colors.background)
                    ) {
                        CircularProgressIndicator(
                            color = WallXAppTheme.colors.background,
                            trackColor = WallXAppTheme.colors.secondary,
                            strokeWidth = WallXAppTheme.dimension.borderWidth
                        )
                    }
                }
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = maxOf(1f, minOf(3f, scale)),
                            scaleY = maxOf(1f, minOf(3f, scale)),
                            translationX = offset.x,
                            translationY = offset.y
                        ),
                    painter = painter,
                    contentDescription = EMPTY_STRING,
                    contentScale = ContentScale.FillHeight
                )

            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(WallXAppTheme.colors.background)
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(
        onBackPress: () -> Unit,
        isFavourite: Boolean,
        onFavouriteClick: (Boolean) -> Unit
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = WallXAppTheme.colors.black.copy(
                    alpha = 0.3f
                ),

                ),
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

            },
            actions = {
                FavouriteButton(
                    isFavourite = isFavourite,
                    onClick = onFavouriteClick
                )
            }
        )
    }
}