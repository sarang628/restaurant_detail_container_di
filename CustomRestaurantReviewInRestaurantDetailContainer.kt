package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.compose.feed.internal.components.type.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.type.LocalFeedImageLoader
import com.sarang.torang.compose.feed.internal.components.type.LocalVideoPlayerType
import com.sarang.torang.compose.feed.type.LocalBottomDetectingLazyColumnType
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.compose.feed.type.LocalPullToRefreshLayoutType
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.basefeed_di.CustomVideoPlayerType
import com.sarang.torang.di.feed_di.CustomBottomDetectingLazyColumnType
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.customPullToRefreshforRestaurantReview

fun customRestaurantReviewInRestaurantDetailContainer(rootNavController: RootNavController) : RestaurantReviewInRestaurantDetailContainer = {
    CompositionLocalProvider(
        LocalVideoPlayerType provides CustomVideoPlayerType(),
        LocalFeedCompose provides CustomFeedCompose,
        LocalBottomDetectingLazyColumnType provides CustomBottomDetectingLazyColumnType,
        LocalPullToRefreshLayoutType provides customPullToRefreshforRestaurantReview,
        LocalFeedImageLoader provides { CustomFeedImageLoader().invoke(it) },
        LocalExpandableTextType provides CustomExpandableTextType) {
        FeedScreenByRestaurantId(restaurantId = it)
    }
}