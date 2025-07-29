package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry
import com.sarang.torang.RestaurantNavScreen
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.RestaurantGalleryScreen
import com.sarang.torang.compose.menu.RestaurantMenuScreen
import com.sarang.torang.compose.type.RestaurantOverviewInRestaurantDetailContainer
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
import com.sarang.torang.compose.type.RestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_gallery_di.restaurantGalleryImageLoader
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewImageLoader
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewRestaurantInfo

@OptIn(ExperimentalMaterial3Api::class)
val customRestaurantOverviewInRestaurantDetailContainer : RestaurantOverviewInRestaurantDetailContainer = {
    CompositionLocalProvider(
        LocalRestaurantOverViewImageLoader provides restaurantOverViewImageLoader,
        LocalRestaurantOverviewRestaurantInfo provides restaurantOverViewRestaurantInfo,
    ) {
        RestaurantOverViewScreen(restaurantId = it)
    }
}

val customRestaurantMenuInRestaurantDetailContainer : RestaurantMenuInRestaurantDetailContainer = {
    RestaurantMenuScreen(restaurantId = it)
}

val customRestaurantReviewInRestaurantDetailContainer : RestaurantReviewInRestaurantDetailContainer = {

}

val customRestaurantGalleryInRestaurantDetailContainer : RestaurantGalleryInRestaurantDetailContainer = {
    CompositionLocalProvider(LocalRestaurantGalleryImageLoader provides restaurantGalleryImageLoader) {
    RestaurantGalleryScreen(restaurantId = it)
    }
}

fun provideRestaurantDetailContainer(rootNavController: RootNavController): @Composable (NavBackStackEntry)->Unit = {
    val restaurantId = it.arguments?.getString("restaurantId")
    CompositionLocalProvider(
        LocalRestaurantOverviewInRestaurantDetailContainer provides customRestaurantOverviewInRestaurantDetailContainer,
        LocalRestaurantMenuInRestaurantDetailContainer provides customRestaurantMenuInRestaurantDetailContainer,
        LocalRestaurantReviewInRestaurantDetailContainer provides customRestaurantReviewInRestaurantDetailContainer,
        LocalRestaurantGalleryInRestaurantDetailContainer provides customRestaurantGalleryInRestaurantDetailContainer,
    ) {
        RestaurantNavScreen(restaurantId = restaurantId?.toInt() ?: 0, onBack = { rootNavController.popBackStack() })
    }
}