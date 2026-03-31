package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.RestaurantInfoViewModel
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.ImageRow
import com.sarang.torang.compose.RestaurantGalleryViewModel
import com.sarang.torang.compose.component.menu.LocalRestaurantMenuImageLoader
import com.sarang.torang.compose.component.type.LocalExpandableTextType
import com.sarang.torang.compose.component.type.LocalFeedImageLoader
import com.sarang.torang.compose.component.type.LocalVideoPlayerType
import com.sarang.torang.compose.feed.type.FeedTypeData
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.compose.galleryImages
import com.sarang.torang.compose.menu.RestaurantMenuViewModel
import com.sarang.torang.compose.menu.restaurantMenuList
import com.sarang.torang.compose.restaurantdetailcontainer.RestaurantDetailColumnScreenWithModules
import com.sarang.torang.compose.type.LocalRestaurantGalleryImageLoader
import com.sarang.torang.compose.type.RestaurantOverviewRestaurantInfo
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.basefeed_di.CustomVideoPlayerType
import com.sarang.torang.di.dialogsbox_di.ProvideDialogsBox
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.customPullToRefreshforRestaurantReview
import com.sarang.torang.di.feed_di.provideFeedGridPicture
import com.sarang.torang.di.restaurant_gallery_di.restaurantGalleryImageLoader
import com.sarang.torang.di.restaurant_menu_di.customRestaurantMenuImageLoader
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewRestaurantInfo
import com.sarang.torang.dialogsbox.compose.DialogsBoxViewModel
import com.sarang.torang.viewmodels.FeedScreenByRestaurantIdViewModel
import kotlinx.coroutines.CoroutineScope

private val tag = "__ProvideRestaurantDetailColumn"

fun ProvideRestaurantDetailColumn(rootNavController: RootNavController = RootNavController(),
                                  onErrorMessage : (String) -> Unit = { },
                                  ): @Composable (Int)->Unit = { restaurantId ->
    val dialogsViewModel: DialogsBoxViewModel = hiltViewModel()
    val restaurantInfoViewModel: RestaurantInfoViewModel = hiltViewModel()
    val menuViewModel: RestaurantMenuViewModel = hiltViewModel()
    val feedsViewModel: FeedScreenByRestaurantIdViewModel = hiltViewModel()
    val galleryViewModel: RestaurantGalleryViewModel = hiltViewModel()
    val snackBarHostState: SnackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val isLogin: Boolean by dialogsViewModel.isLogin.collectAsStateWithLifecycle()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val overView: RestaurantOverviewRestaurantInfo =
        restaurantOverViewRestaurantInfo(rootNavController, restaurantInfoViewModel)

    LaunchedEffect(restaurantId) {
        menuViewModel.loadMenu(restaurantId)
        feedsViewModel.getFeedByRestaurantId(restaurantId)
        galleryViewModel.loadImage(restaurantId)
    }

    val restaurantDetailColumn : @Composable () -> Unit = {
        RestaurantDetailColumnScreenWithModules(
            restaurantId = restaurantId,
            onBack = { rootNavController.popBackStack() },
            snackBarHostState = snackBarHostState,
            menuItemCount = menuViewModel.uiState.size,
            reviewItemCount = feedsViewModel.feedUiState.list.size,
            galleryItemCount = galleryViewModel.galleryImages().size,
            restaurantOverviewInfo = { overView.invoke(restaurantId) },
            menuListContent = {
                item { HeaderText("Menu") }
                restaurantMenuList(menuViewModel.uiState)
            },
            reviewListContent = {
                item { HeaderText("Review") }
                items(feedsViewModel.feedUiState.list) {
                    LocalFeedCompose.current.invoke(
                        FeedTypeData(
                            feed = it,
                            //onLike          = feedCallBack.onLike,
                            //onFavorite      = feedCallBack.onFavorite,
                            //onVideoClick    = { feedCallBack.onVideoClick.invoke(uiState.list[it].reviewId) },
                            //pageScrollable  = feedScreenConfig.pageScrollable,
                            //isLogin         = uiState.isLogin,
                            //imageHeight     = uiState.imageHeight(
                            //   density = LocalDensity.current,
                            //   screenWidthDp = LocalConfiguration.current.screenWidthDp,
                            //   screenHeightDp = LocalConfiguration.current.screenHeightDp
                            //),
                            //isPlaying = (playingIndex == it) && shouldPlay
                        )
                    )
                }
            },
            galleryContent = {
                item { HeaderText("Gallery") }
                items(galleryViewModel.galleryImages()) {
                    ImageRow(
                        galleryImages = it,
                        onImage = { })
                }
            },
        )
    }

    CompositionLocalProvider(
        LocalRestaurantMenuImageLoader provides customRestaurantMenuImageLoader,
        //for feed
        LocalVideoPlayerType provides CustomVideoPlayerType(),
        LocalFeedCompose provides provideFeedGridPicture(),
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType,
        LocalPullToRefreshLayoutType provides customPullToRefreshforRestaurantReview,
        LocalFeedImageLoader provides { CustomFeedImageLoader().invoke(it) },
        LocalExpandableTextType provides CustomExpandableTextType,
        // for gallery
        LocalRestaurantGalleryImageLoader provides restaurantGalleryImageLoader
    ) {
        ProvideDialogsBox(dialogsViewModel = dialogsViewModel) { restaurantDetailColumn.invoke() }
    }
}


@Composable fun HeaderText(text : String = ""){
    Text(modifier   = Modifier.padding(8.dp),
        text       = text,
        fontSize   = 20.sp,
        fontWeight = FontWeight.Bold)
}