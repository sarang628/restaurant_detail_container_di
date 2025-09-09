package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry
import com.sarang.torang.RestaurantNavScreen
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.menu.LocalRestaurantMenuImageLoader
import com.sarang.torang.compose.type.LocalRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_menu_di.customRestaurantMenuImageLoader

fun provideRestaurantDetailContainer(rootNavController: RootNavController): @Composable (NavBackStackEntry)->Unit = {
    val restaurantId = it.arguments?.getString("restaurantId")
    CompositionLocalProvider(
        LocalRestaurantOverviewInRestaurantDetailContainer provides customRestaurantOverviewInRestaurantDetailContainer(rootNavController),
        LocalRestaurantMenuInRestaurantDetailContainer provides customRestaurantMenuInRestaurantDetailContainer,
        LocalRestaurantReviewInRestaurantDetailContainer provides customRestaurantReviewInRestaurantDetailContainer(rootNavController),
        LocalRestaurantGalleryInRestaurantDetailContainer provides customRestaurantGalleryInRestaurantDetailContainer,
        LocalRestaurantMenuImageLoader provides customRestaurantMenuImageLoader
    ) {
        RestaurantNavScreen(restaurantId = restaurantId?.toInt() ?: 0, onBack = { rootNavController.popBackStack() })
    }
}