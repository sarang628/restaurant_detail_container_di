package com.sarang.torang.di.restaurant_detail_container_di

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.RestaurantInfoViewModel
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.internal.components.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.compose.restaurantdetail.RestaurantOverViewScreen
import com.sarang.torang.compose.restaurantdetail.feed.LocalRestaurantFeed
import com.sarang.torang.compose.type.LocalPullToRefresh
import com.sarang.torang.compose.type.LocalRestaurantOverViewImageLoader
import com.sarang.torang.compose.type.LocalRestaurantOverviewRestaurantInfo
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.restaurant_overview_di.CustomRestaurantOverviewPullToRefreshType
import com.sarang.torang.di.restaurant_overview_di.customRestaurantFeedType
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewImageLoader
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewRestaurantInfo

private val tag = "__customRestaurantOverviewInRestaurantDetailContainer"
@OptIn(ExperimentalMaterial3Api::class)
fun customRestaurantOverviewInRestaurantDetailContainer(
    rootNavController: RootNavController,
    onComment      : (Int) -> Unit    = { Log.w(tag, "onComment callback is not set") },
    onShare        : (Int) -> Unit    = { Log.w(tag, "onShare callback is not set") },
    onMenu         : (Int) -> Unit    = { Log.w(tag, "onMenu callback is not set") },
    onErrorMessage : (String) -> Unit = { }
) : RestaurantOverviewInRestaurantDetailContainer = { restaurantId ->
    val restaurantInfoViewModel : RestaurantInfoViewModel = hiltViewModel()
    CompositionLocalProvider(
        LocalRestaurantOverViewImageLoader provides restaurantOverViewImageLoader,
        LocalRestaurantOverviewRestaurantInfo provides restaurantOverViewRestaurantInfo(rootNavController, restaurantInfoViewModel),
        LocalFeedImageLoader provides { CustomFeedImageLoader().invoke(it) },
        LocalExpandableTextType provides CustomExpandableTextType,
        LocalRestaurantFeed provides customRestaurantFeedType(onComment = onComment,
                                                              onShare = onShare,
                                                              onMenu = onMenu,
                                                              rootNavController = rootNavController),
        LocalPullToRefresh provides CustomRestaurantOverviewPullToRefreshType
    ) {
        RestaurantOverViewScreen(restaurantId = restaurantId,
            onProfile = {rootNavController.profile(it)},
            onContents = { Log.d("__customRestaurantOverviewInRestaurantDetailContainer", "onContents: ${it}"); rootNavController.review(it)},
            onLocation = { Log.d("__customRestaurantOverviewInRestaurantDetailContainer", "onLocation") },
            onRefresh = { restaurantInfoViewModel.refresh(restaurantId = restaurantId) },
            isRefreshRestaurantInfo = restaurantInfoViewModel.isRefresh,
            onErrorMessage = onErrorMessage
        )
    }
}