package com.sarang.torang.di.restaurant_detail_container_di

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.dialogsbox.compose.DialogsBoxViewModel
import com.sarang.torang.compose.restaurantdetailcontainer.RestaurantNavScreen
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.menu.LocalRestaurantMenuImageLoader
import com.sarang.torang.compose.restaurantdetailcontainer.type.LocalRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.LocalRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.LocalRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.LocalRestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.di.dialogsbox_di.ProvideDialogsBox
import com.sarang.torang.di.restaurant_menu_di.customRestaurantMenuImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun provideRestaurantDetailContainer(rootNavController: RootNavController = RootNavController(),
                                     onErrorMessage : (String) -> Unit = { },
                                    ): @Composable (Int)->Unit = { restaurantId ->
    val dialogsViewModel    : DialogsBoxViewModel   = hiltViewModel()
    val snackBarHostState   : SnackbarHostState     by remember { mutableStateOf(SnackbarHostState()) }
    val isLogin             : Boolean               by dialogsViewModel.isLogin.collectAsStateWithLifecycle()
    val coroutineScope      : CoroutineScope        = rememberCoroutineScope()
    CompositionLocalProvider(
        LocalRestaurantOverviewInRestaurantDetailContainer provides
                customRestaurantOverviewInRestaurantDetailContainer(rootNavController = rootNavController,
                                                                    onMenu = dialogsViewModel::onMenu,
                                                                    onShare = { if(isLogin)
                                                                                    dialogsViewModel.onShare(it)
                                                                                else
                                                                                    rootNavController.emailLogin() },
                                                                    onComment = dialogsViewModel::onComment,
                                                                    onErrorMessage = { coroutineScope.launch {
                                                                        snackBarHostState.showSnackbar(it)
                                                                    }}),
        LocalRestaurantMenuInRestaurantDetailContainer provides customRestaurantMenuInRestaurantDetailContainer,
        LocalRestaurantReviewInRestaurantDetailContainer provides customRestaurantReviewInRestaurantDetailContainer(rootNavController),
        LocalRestaurantGalleryInRestaurantDetailContainer provides customRestaurantGalleryInRestaurantDetailContainer,
        LocalRestaurantMenuImageLoader provides customRestaurantMenuImageLoader
    ) {
        ProvideDialogsBox(dialogsViewModel = dialogsViewModel) {
            RestaurantNavScreen(restaurantId = restaurantId,
                                onBack = { rootNavController.popBackStack() },
                                snackBarHostState = snackBarHostState)
        }
    }
}