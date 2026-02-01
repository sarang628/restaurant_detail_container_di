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
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.menu.LocalRestaurantMenuImageLoader
import com.sarang.torang.compose.restaurantdetailcontainer.RestaurantNavScreenWithModules
import com.sarang.torang.di.dialogsbox_di.ProvideDialogsBox
import com.sarang.torang.di.restaurant_menu_di.customRestaurantMenuImageLoader
import com.sarang.torang.dialogsbox.compose.DialogsBoxViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun provideRestaurantDetailContainer(rootNavController: RootNavController = RootNavController(),
                                     onErrorMessage : (String) -> Unit = { },
                                    ): @Composable (Int)->Unit = { restaurantId ->
    val dialogsViewModel    : DialogsBoxViewModel   = hiltViewModel()
    val snackBarHostState   : SnackbarHostState     by remember { mutableStateOf(SnackbarHostState()) }
    val isLogin             : Boolean               by dialogsViewModel.isLogin.collectAsStateWithLifecycle()
    val coroutineScope      : CoroutineScope        = rememberCoroutineScope()
    val restaurantOverView = customRestaurantOverviewInRestaurantDetailContainer(rootNavController = rootNavController,
        onMenu = dialogsViewModel::onMenu,
        onShare = { if(isLogin) dialogsViewModel.onShare(it)
                    else rootNavController.emailLogin() },
        onComment = dialogsViewModel::onComment,
        onErrorMessage = { coroutineScope.launch {
            snackBarHostState.showSnackbar(it)
        }})
    val menu = customRestaurantMenuInRestaurantDetailContainer
    val review = customRestaurantReviewInRestaurantDetailContainer(rootNavController)
    val gallery = customRestaurantGalleryInRestaurantDetailContainer
    CompositionLocalProvider(
        LocalRestaurantMenuImageLoader provides customRestaurantMenuImageLoader
    ) {
        ProvideDialogsBox(dialogsViewModel = dialogsViewModel) {
            RestaurantNavScreenWithModules(restaurantId         = restaurantId,
                                           onBack               = { rootNavController.popBackStack() },
                                           snackBarHostState    = snackBarHostState,
                                           overView             = restaurantOverView,
                                           menu                 = menu,
                                           review               = review,
                                           gallery              = gallery)
        }
    }
}