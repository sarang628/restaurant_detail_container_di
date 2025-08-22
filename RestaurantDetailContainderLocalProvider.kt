package com.sarang.torang.di.restaurant_detail_container_di

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.sarang.torang.RestaurantNavScreen
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.RestaurantGalleryScreen
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.compose.menu.LocalRestaurantMenuImageLoader
import com.sarang.torang.compose.menu.RestaurantMenuImageLoader
import com.sarang.torang.compose.menu.RestaurantMenuScreen
import com.sarang.torang.compose.restaurantdetail.RestaurantOverViewScreen
import com.sarang.torang.compose.type.LocalRestaurantGalleryImageLoader
import com.sarang.torang.compose.type.LocalRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantOverViewImageLoader
import com.sarang.torang.compose.type.LocalRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantOverviewRestaurantInfo
import com.sarang.torang.compose.type.LocalRestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.compose.type.RestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.type.RestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.type.RestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.type.RestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.di.feed_di.provideBottomDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.main_di.provideFeed
import com.sarang.torang.di.restaurant_gallery_di.restaurantGalleryImageLoader
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewImageLoader
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewRestaurantInfo
import com.sarang.torang.di.video.provideVideoPlayer
import com.sarang.torang.viewmodels.FeedDialogsViewModel
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.RefreshIndicatorState
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@OptIn(ExperimentalMaterial3Api::class)
fun customRestaurantOverviewInRestaurantDetailContainer(rootNavController: RootNavController) : RestaurantOverviewInRestaurantDetailContainer = {
    CompositionLocalProvider(
        LocalRestaurantOverViewImageLoader provides restaurantOverViewImageLoader,
        LocalRestaurantOverviewRestaurantInfo provides restaurantOverViewRestaurantInfo,
    ) {
        RestaurantOverViewScreen(restaurantId = it, onProfile = {rootNavController.profile(it)}, onContents = { Log.d("__customRestaurantOverviewInRestaurantDetailContainer", "onContents: ${it}"); rootNavController.review(it)})
    }
}

val customRestaurantMenuInRestaurantDetailContainer : RestaurantMenuInRestaurantDetailContainer = {
    RestaurantMenuScreen(restaurantId = it)
}

fun customRestaurantReviewInRestaurantDetailContainer(rootNavController: RootNavController) : RestaurantReviewInRestaurantDetailContainer = {
    val state = rememberPullToRefreshState()
    val dialogsViewModel: FeedDialogsViewModel = hiltViewModel()
    FeedScreenByRestaurantId(restaurantId = it,
        /*shimmerBrush = { shimmerBrush(it) },
        feed = provideFeed(dialogsViewModel = dialogsViewModel, navController = rootNavController.navController, rootNavController = rootNavController, videoPlayer = provideVideoPlayer()),
        pullToRefreshLayout = { isRefreshing, onRefresh, contents ->
            if (isRefreshing) { state.updateState(RefreshIndicatorState.Refreshing) }
            else { state.updateState(RefreshIndicatorState.Default) }
            PullToRefreshLayout(pullRefreshLayoutState = state, refreshThreshold = 80, onRefresh = onRefresh) {
                contents.invoke() }
        },
        bottomDetectingLazyColumn = provideBottomDetectingLazyColumn()*/
        )
}

val customRestaurantGalleryInRestaurantDetailContainer : RestaurantGalleryInRestaurantDetailContainer = {
    CompositionLocalProvider(LocalRestaurantGalleryImageLoader provides restaurantGalleryImageLoader) {
    RestaurantGalleryScreen(restaurantId = it)
    }
}

val customLocalRestaurantMenuImageLoader : RestaurantMenuImageLoader = { modifier, url, width, height, scale ->
    // 여기서 실제 이미지 로딩 구현 예시
    provideTorangAsyncImage().invoke(modifier, url, width, height, scale)
}

fun provideRestaurantDetailContainer(rootNavController: RootNavController): @Composable (NavBackStackEntry)->Unit = {
    val restaurantId = it.arguments?.getString("restaurantId")
    CompositionLocalProvider(
        LocalRestaurantOverviewInRestaurantDetailContainer provides customRestaurantOverviewInRestaurantDetailContainer(rootNavController),
        LocalRestaurantMenuInRestaurantDetailContainer provides customRestaurantMenuInRestaurantDetailContainer,
        LocalRestaurantReviewInRestaurantDetailContainer provides customRestaurantReviewInRestaurantDetailContainer(rootNavController),
        LocalRestaurantGalleryInRestaurantDetailContainer provides customRestaurantGalleryInRestaurantDetailContainer,
        LocalRestaurantMenuImageLoader provides customLocalRestaurantMenuImageLoader
    ) {
        RestaurantNavScreen(restaurantId = restaurantId?.toInt() ?: 0, onBack = { rootNavController.popBackStack() })
    }
}