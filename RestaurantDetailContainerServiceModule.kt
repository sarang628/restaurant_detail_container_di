package com.sarang.torang.di.restaurant_detail_container_di

import com.sarang.torang.usecase.restaurantdetailcontainer.FindRestaurantNameByRestaurantIdUseCase
import com.sarang.torang.api.ApiRestaurant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RestaurantDetailContainerServiceModule {
    @Provides
    fun provideFindRestaurantNameByRestaurantId(apiRestaurant: ApiRestaurant): FindRestaurantNameByRestaurantIdUseCase {
        return object : FindRestaurantNameByRestaurantIdUseCase {
            override suspend fun invoke(restaurantId: Int): String {
                return apiRestaurant.getRestaurantById(restaurantId).restaurantName ?: ""
            }
        }
    }
}