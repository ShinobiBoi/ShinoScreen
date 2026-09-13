package com.besha.shinobihub.appcore.navigation

import com.besha.shinobihub.appcore.domain.model.MediaType
import kotlinx.serialization.Serializable

sealed class ScreenResources {
    @Serializable
    object AuthScreenRoute : ScreenResources()

    @Serializable
    object SplashScreenRoute : ScreenResources()

    @Serializable
    object LoginScreeRoute : ScreenResources()

    @Serializable
    data class MainScreeRoute(
        val mediaId: Int? = null,
        val mediaType: MediaType? = null,
    ) : ScreenResources()

    @Serializable
    object FindScreenRoute : ScreenResources()

    @Serializable
    object HomeScreenRoute : ScreenResources()

    @Serializable
    object ProfileScreenRoute : ScreenResources()

    @Serializable
    object WatchListScreenRoute : ScreenResources()

    @Serializable
    object FavouritesScreenRoute : ScreenResources()

    @Serializable
    object AboutScreenRoute : ScreenResources()

    @Serializable
    data class DiscoverScreenRoute(val genreId: Int) : ScreenResources()

    @Serializable
    data class DetailScreenRoute(
        val id: Int,
        val mediaType: MediaType,
    ) : ScreenResources()

    companion object {
        fun fromRoute(route: String): ScreenResources? {
            return when {
                route.contains(AuthScreenRoute::class.qualifiedName ?: "") -> AuthScreenRoute
                route.contains(SplashScreenRoute::class.qualifiedName ?: "") -> SplashScreenRoute
                route.contains(LoginScreeRoute::class.qualifiedName ?: "") -> LoginScreeRoute
                route.contains(MainScreeRoute::class.qualifiedName ?: "") -> MainScreeRoute()
                route.contains(HomeScreenRoute::class.qualifiedName ?: "") -> HomeScreenRoute
                route.contains(FindScreenRoute::class.qualifiedName ?: "") -> FindScreenRoute
                route.contains(ProfileScreenRoute::class.qualifiedName ?: "") -> ProfileScreenRoute
                route.contains(WatchListScreenRoute::class.qualifiedName ?: "") -> WatchListScreenRoute
                route.contains(FavouritesScreenRoute::class.qualifiedName ?: "") -> FavouritesScreenRoute
                route.contains(AboutScreenRoute::class.qualifiedName ?: "") -> AboutScreenRoute
                route.contains(DiscoverScreenRoute::class.qualifiedName ?: "") ->
                    DiscoverScreenRoute(1) // dummy id
                route.contains(DetailScreenRoute::class.qualifiedName ?: "") ->
                    DetailScreenRoute(0, MediaType.Movies) // dummy values
                else -> null
            }
        }
    }
}
