package com.besha.shinobihub.features.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.login.presentaion.screen.LoginScreen
import com.besha.shinobihub.features.splash.presentation.screen.SplashScreen

@Composable
fun AuthScreen(
    rootController: NavController,
    mediaId: Int,
    mediaType: MediaType?,
) {
    val childController = rememberNavController()
    NavHost(
        navController = childController,
        startDestination = ScreenResources.SplashScreenRoute,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable<ScreenResources.SplashScreenRoute> {
            SplashScreen(rootController, childController, mediaId, mediaType)
        }
        composable<ScreenResources.LoginScreeRoute> {
            LoginScreen(rootController)
        }
    }
}
