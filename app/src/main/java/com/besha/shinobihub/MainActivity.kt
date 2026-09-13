package com.besha.shinobihub

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.auth.AuthScreen
import com.besha.shinobihub.features.home.presentaion.screen.HomeScreen
import com.besha.shinobihub.features.main.MainScreen
import com.besha.shinobihub.ui.theme.ComposeShinobiCimaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var rootController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mediaId = intent.getIntExtra("media_id", -1)
            val mediaTypeName = intent.getStringExtra("media_type") ?: "movie"
            val mediaType = MediaType(mediaTypeName)

            ComposeShinobiCimaTheme {
                rootController = rememberNavController()

                NavHost(
                    navController = rootController,
                    startDestination = ScreenResources.AuthScreenRoute,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    composable<ScreenResources.AuthScreenRoute> {
                        AuthScreen(rootController, mediaId, mediaType)
                    }
                    composable<ScreenResources.MainScreeRoute> {
                        val args = it.toRoute<ScreenResources.MainScreeRoute>()
                        MainScreen(rootController, args.mediaId, args.mediaType)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            val mediaId = it.getIntExtra("media_id", -1)
            val mediaTypeName = it.getStringExtra("media_type") ?: "movie"
            val mediaType = MediaType(mediaTypeName)

            // Get NavController from Compose (you need a way to access it)
            // One option is using a singleton or ViewModel to hold the controller
            rootController.navigate(ScreenResources.MainScreeRoute(mediaId, mediaType)) {
                popUpTo(ScreenResources.AuthScreenRoute) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    ComposeShinobiCimaTheme {
        val mockNavController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                CustomBottomNavigationBar(
                    ScreenResources.HomeScreenRoute,
                ) {}
            },
        ) { innerPadding ->
            // Mock Home Screen for preview
            val x = innerPadding
            HomeScreen(controller = mockNavController)
        }
    }
}
