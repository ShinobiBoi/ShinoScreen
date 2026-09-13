package com.besha.shinobihub.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.besha.shinobihub.BottomNavViewModel
import com.besha.shinobihub.CustomBottomNavigationBar
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.about.AboutScreen
import com.besha.shinobihub.features.detail.presentaion.screen.MediaDetailScreen
import com.besha.shinobihub.features.discover.presentaion.screen.DiscoverScreen
import com.besha.shinobihub.features.favourite.presentaion.screen.FavouriteScreen
import com.besha.shinobihub.features.find.presenation.screen.FindScreen
import com.besha.shinobihub.features.home.presentaion.screen.HomeScreen
import com.besha.shinobihub.features.main.viewmodel.ConnectivityViewModel
import com.besha.shinobihub.features.nointernet.NoInternetScreen
import com.besha.shinobihub.features.profile.presenation.screen.ProfileScreen
import com.besha.shinobihub.features.watchlist.presentaion.screen.WatchListScreen


@Composable
fun MainScreen(rootController: NavController, mediaId: Int?, mediaType: MediaType?) {


    val connectivityViewModel = hiltViewModel<ConnectivityViewModel>()
    val isConnected by connectivityViewModel.isConnected.collectAsState()


    val navController = rememberNavController()
    val bottomNavViewModel = hiltViewModel<BottomNavViewModel>()
    val currentRoute by bottomNavViewModel.currentRoute.collectAsState()

    val lastNavigatedMediaId = rememberSaveable { mutableStateOf(-1) }


    if (isConnected) {
        LaunchedEffect(navController) {
            navController.currentBackStackEntryFlow.collect { backStackEntry ->
                val route = backStackEntry.destination.route
                route?.let {
                    ScreenResources.fromRoute(it)?.let { screen ->
                        bottomNavViewModel.onRouteSelected(screen)
                    }
                }
            }
        }


        LaunchedEffect(mediaId) {
            if (mediaId != null && mediaId != -1 && mediaType != null && lastNavigatedMediaId.value != mediaId) {
                navController.navigate(
                    ScreenResources.DetailScreenRoute(
                        id = mediaId,
                        mediaType = mediaType
                    )
                )
                lastNavigatedMediaId.value = mediaId
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {

        if (isConnected &&
            currentRoute !is ScreenResources.DetailScreenRoute &&
            currentRoute !is ScreenResources.DiscoverScreenRoute &&
            currentRoute !is ScreenResources.FavouritesScreenRoute &&
            currentRoute !is ScreenResources.WatchListScreenRoute &&
            currentRoute !is ScreenResources.AboutScreenRoute
        ) {
            CustomBottomNavigationBar(currentRoute) { selectedRoute ->
                if (selectedRoute != currentRoute) {
                    bottomNavViewModel.onRouteSelected(selectedRoute)
                    navController.navigate(selectedRoute) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        restoreState = true
                    }
                }
            }

        }


    }
    ) { innerPadding ->


        if (!isConnected) {
            NoInternetScreen(onRetry = { /* optional manual re-check trigger */ })
        } else {
            NavHost(
                navController = navController,
                startDestination = ScreenResources.HomeScreenRoute,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<ScreenResources.HomeScreenRoute> {

                    HomeScreen(controller = navController)

                }
                composable<ScreenResources.FindScreenRoute> {
                    FindScreen(navController)
                }

                composable<ScreenResources.DetailScreenRoute> {
                    val args = it.toRoute<ScreenResources.DetailScreenRoute>()
                    MediaDetailScreen(
                        args.id,
                        args.mediaType, navController
                    )
                }
                composable<ScreenResources.DiscoverScreenRoute> {
                    val args = it.toRoute<ScreenResources.DiscoverScreenRoute>()
                    DiscoverScreen(navController, args.genreId)
                }

                composable<ScreenResources.ProfileScreenRoute> {
                    ProfileScreen(rootController, navController)
                }
                composable<ScreenResources.WatchListScreenRoute> {
                    WatchListScreen(navController)
                }
                composable<ScreenResources.FavouritesScreenRoute> {
                    FavouriteScreen(navController)
                }
                composable<ScreenResources.AboutScreenRoute> {
                    AboutScreen(navController)
                }

            }
        }
    }

}