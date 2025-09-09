package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.compose.menu.LocalRestaurantMenuPullToRefresh
import com.sarang.torang.compose.menu.RestaurantMenuScreen
import com.sarang.torang.compose.type.RestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_menu_di.CustomRestaurantMenuPullToRefresh

val customRestaurantMenuInRestaurantDetailContainer : RestaurantMenuInRestaurantDetailContainer = {
    CompositionLocalProvider(LocalRestaurantMenuPullToRefresh provides CustomRestaurantMenuPullToRefresh){
        RestaurantMenuScreen(restaurantId = it)
    }
}