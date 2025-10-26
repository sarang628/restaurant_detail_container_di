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
import com.sarang.torang.compose.type.RestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.restaurant_overview_di.CustomRestaurantFeedType
import com.sarang.torang.di.restaurant_overview_di.CustomRestaurantOverviewPullToRefreshType
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewImageLoader
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewRestaurantInfo

@OptIn(ExperimentalMaterial3Api::class)
fun customRestaurantOverviewInRestaurantDetailContainer(rootNavController: RootNavController) : RestaurantOverviewInRestaurantDetailContainer = {
    val restaurantInfoViewModel : RestaurantInfoViewModel = hiltViewModel()
    CompositionLocalProvider(
        LocalRestaurantOverViewImageLoader provides restaurantOverViewImageLoader,
        LocalRestaurantOverviewRestaurantInfo provides restaurantOverViewRestaurantInfo(rootNavController, restaurantInfoViewModel),
        LocalFeedImageLoader provides { CustomFeedImageLoader().invoke(it) },
        LocalExpandableTextType provides CustomExpandableTextType,
        LocalRestaurantFeed provides CustomRestaurantFeedType,
        LocalPullToRefresh provides CustomRestaurantOverviewPullToRefreshType
    ) {
        RestaurantOverViewScreen(restaurantId = it,
            onProfile = {rootNavController.profile(it)},
            onContents = { Log.d("__customRestaurantOverviewInRestaurantDetailContainer", "onContents: ${it}"); rootNavController.review(it)},
            onLocation = { Log.d("__customRestaurantOverviewInRestaurantDetailContainer", "onLocation") },
            onRefresh = { restaurantInfoViewModel.refresh(it) },
            isRefreshRestaurantInfo = restaurantInfoViewModel.isRefresh
        )
    }
}