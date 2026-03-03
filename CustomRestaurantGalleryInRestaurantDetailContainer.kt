package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.compose.RestaurantGalleryScreen
import com.sarang.torang.compose.type.LocalRestaurantGalleryImageLoader
import com.sarang.torang.compose.restaurantdetailcontainer.type.Gallery
import com.sarang.torang.di.restaurant_gallery_di.restaurantGalleryImageLoader

val customRestaurantGalleryInRestaurantDetailContainer : Gallery = {
    CompositionLocalProvider(LocalRestaurantGalleryImageLoader provides restaurantGalleryImageLoader) {
        RestaurantGalleryScreen(restaurantId = it)
    }
}