package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.sarang.torang.FeedDialogsViewModel
import com.sarang.torang.RestaurantNavScreen
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.menu.LocalRestaurantMenuImageLoader
import com.sarang.torang.compose.type.LocalRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.di.dialogsbox_di.ProvideMainDialog
import com.sarang.torang.di.restaurant_menu_di.customRestaurantMenuImageLoader
import kotlinx.coroutines.launch

fun provideRestaurantDetailContainer(rootNavController: RootNavController = RootNavController(),
                                     onErrorMessage : (String) -> Unit = { },
                                     restaurantId : Int = 0
                                    ): @Composable ()->Unit = {
    //val restaurantId = it.arguments?.getString("restaurantId")
    val dialogsViewModel : FeedDialogsViewModel = hiltViewModel()
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val coroutineScope = rememberCoroutineScope()
    CompositionLocalProvider(
        LocalRestaurantOverviewInRestaurantDetailContainer provides
                customRestaurantOverviewInRestaurantDetailContainer(rootNavController = rootNavController,
                                                                    onMenu = dialogsViewModel::onMenu,
                                                                    onShare = dialogsViewModel::onShare,
                                                                    onComment = dialogsViewModel::onComment,
                                                                    onErrorMessage = { coroutineScope.launch {
                                                                        snackbarHostState.showSnackbar(it)
                                                                    }}),
        LocalRestaurantMenuInRestaurantDetailContainer provides customRestaurantMenuInRestaurantDetailContainer,
        LocalRestaurantReviewInRestaurantDetailContainer provides customRestaurantReviewInRestaurantDetailContainer(rootNavController),
        LocalRestaurantGalleryInRestaurantDetailContainer provides customRestaurantGalleryInRestaurantDetailContainer,
        LocalRestaurantMenuImageLoader provides customRestaurantMenuImageLoader
    ) {
        ProvideMainDialog(dialogsViewModel = dialogsViewModel) {
            RestaurantNavScreen(restaurantId = restaurantId,
                                onBack = { rootNavController.popBackStack() },
                                snackbarHostState = snackbarHostState)
        }
    }
}