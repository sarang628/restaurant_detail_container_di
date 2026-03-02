package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.menu.LocalRestaurantMenuImageLoader
import com.sarang.torang.compose.menu.LocalRestaurantMenuPullToRefresh
import com.sarang.torang.compose.menu.RestaurantMenuScreen
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_menu_di.CustomRestaurantMenuPullToRefresh
import com.sarang.torang.di.restaurant_menu_di.customRestaurantMenuImageLoader

val customRestaurantMenuInRestaurantDetailContainer : RestaurantMenuInRestaurantDetailContainer = {
    CompositionLocalProvider(LocalRestaurantMenuPullToRefresh provides CustomRestaurantMenuPullToRefresh,
        LocalRestaurantMenuImageLoader provides customRestaurantMenuImageLoader
    ){
        Box(Modifier.fillMaxWidth().height(100.dp)){
            RestaurantMenuScreen(restaurantId = it)
        }
    }
}