package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.compose.menu.RestaurantMenu
import com.sarang.torang.compose.menu.RestaurantMenuScreen
import com.sarang.torang.compose.type.RestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetail.RestaurantOverViewScreen
import com.sarang.torang.compose.type.LocalRestaurantOverViewImageLoader
import com.sarang.torang.compose.type.LocalRestaurantOverviewRestaurantInfo
import com.sarang.torang.compose.type.RestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewImageLoader
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewRestaurantInfo

@OptIn(ExperimentalMaterial3Api::class)
val customRestaurantOverviewInRestaurantDetailContainer : RestaurantOverviewInRestaurantDetailContainer = {
    CompositionLocalProvider(
        LocalRestaurantOverViewImageLoader provides restaurantOverViewImageLoader,
        LocalRestaurantOverviewRestaurantInfo provides restaurantOverViewRestaurantInfo,
    ) {
        RestaurantOverViewScreen(restaurantId = 234)
    }
}

val customRestaurantMenuInRestaurantDetailContainer : RestaurantMenuInRestaurantDetailContainer = {
    RestaurantMenuScreen(restaurantId = 234)
}