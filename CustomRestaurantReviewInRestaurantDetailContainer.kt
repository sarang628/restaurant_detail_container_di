package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.compose.feed.LocalPullToRefreshLayoutType
import com.sarang.torang.compose.feed.component.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.component.LocalFeedCompose
import com.sarang.torang.compose.feed.internal.components.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.compose.type.RestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.CustomPullToRefreshType
import com.sarang.torang.viewmodels.FeedDialogsViewModel
import com.sryang.library.pullrefresh.rememberPullToRefreshState

fun customRestaurantReviewInRestaurantDetailContainer(rootNavController: RootNavController) : RestaurantReviewInRestaurantDetailContainer = {
    val state = rememberPullToRefreshState()
    val dialogsViewModel: FeedDialogsViewModel = hiltViewModel()
    CompositionLocalProvider(LocalFeedCompose provides CustomFeedCompose,
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType,
        LocalPullToRefreshLayoutType provides CustomPullToRefreshType,
        LocalFeedImageLoader provides CustomFeedImageLoader,
        LocalExpandableTextType provides CustomExpandableTextType) {
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
}