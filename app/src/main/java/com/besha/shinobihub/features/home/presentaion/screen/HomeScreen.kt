package com.besha.shinobihub.features.home.presentaion.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.components.MediaTypeList
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.home.presentaion.compoents.HomeGenreList
import com.besha.shinobihub.features.home.presentaion.compoents.HomeMoviesList
import com.besha.shinobihub.features.home.presentaion.compoents.TrendingMovieBannerPager
import com.besha.shinobihub.features.home.presentaion.viewmodel.HomeAction
import com.besha.shinobihub.features.home.presentaion.viewmodel.HomeViewModel

@Composable
fun HomeScreen(controller: NavController) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(pageCount = { 8 })
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
    ) {
        val viewModel = hiltViewModel<HomeViewModel>()

        val state by viewModel.viewStates.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.executeAction(HomeAction.GetTrendingAll)
            viewModel.executeAction(HomeAction.GetAccount)
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = "Hi,  ${state.account.data?.username ?: "Guest"} \uD83D\uDC4B\uD83C\uDFFC",
                    fontSize = 30.sp,
                    color = colorResource(R.color.black),
                )
                Text(
                    text = "Experience the Eternity",
                    fontSize = 18.sp,
                    color = colorResource(R.color.gray),
                )
            }

            val profilePic = state.account.data?.avatar?.tmdb?.avatar_path

            if (profilePic != null) {
                AsyncImage(
                    modifier =
                        Modifier
                            .clip(CircleShape)
                            .size(54.dp),
                    model = "https://image.tmdb.org/t/p/original$profilePic",
                    contentDescription = "profile pic",
                    contentScale = ContentScale.Crop,
                )
            } else {
                (
                    Box(
                        modifier =
                            Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(50))
                                .background(colorResource(R.color.dark_blue)),
                        contentAlignment = Alignment.Center,
                    ) {
                        val initials = state.account.data?.username?.take(1)?.uppercase() ?: "?"
                        Text(
                            text = initials,
                            color = colorResource(R.color.white),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }
                )
            }
        }

        MediaTypeList(state.mediaType.data ?: MediaType.All) {
            viewModel.executeAction(HomeAction.ChangeMediaType(it))
        }

        state.trendingAll.data?.let {
            TrendingMovieBannerPager(it.subList(0, 8), pagerState) { id, mediaTpe ->
                controller.navigate(ScreenResources.DetailScreenRoute(id, mediaTpe))
            }
        }

        Text(
            text = "Find now",
            color = colorResource(R.color.black),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 13.dp, top = 10.dp),
        )

        HomeGenreList {
            controller.navigate(ScreenResources.DiscoverScreenRoute(it))
        }

        when (state.mediaType.data) {
            MediaType.All -> {
                if (state.trendingMovies.data.isNullOrEmpty()) viewModel.executeAction(HomeAction.GetTrendingMovies)
                if (state.trendingTv.data.isNullOrEmpty())viewModel.executeAction(HomeAction.GetTrendingTv)
                if (state.trendingPeople.data.isNullOrEmpty())viewModel.executeAction(HomeAction.GetTrendingPeople)

                HomeMoviesList(state.trendingMovies, title = "Trending movies") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }

                HomeMoviesList(state.trendingTv, title = "Trending tv series") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }

                HomeMoviesList(state.trendingPeople, title = "Trending people") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }
            }
            MediaType.Movies -> {
                if (state.popularMovies.data.isNullOrEmpty()) viewModel.executeAction(HomeAction.GetPopularMovies)
                if (state.topRatedMovies.data.isNullOrEmpty())viewModel.executeAction(HomeAction.GetTopRatedMovies)
                if (state.upComingMovies.data.isNullOrEmpty()) viewModel.executeAction(HomeAction.GetUpComingMovies)

                HomeMoviesList(state.trendingMovies, title = "Trending movies") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }

                HomeMoviesList(state.popularMovies, title = "Popular movies") { id, type ->

                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }

                HomeMoviesList(state.topRatedMovies, title = "Top rated movies") { id, type ->

                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }

                HomeMoviesList(state.upComingMovies, title = "Upcoming movies") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }
            }
            MediaType.Tv -> {
                if (state.onTheAirTv.data.isNullOrEmpty())viewModel.executeAction(HomeAction.GetOnTheAirTv)
                if (state.topRatedTv.data.isNullOrEmpty()) viewModel.executeAction(HomeAction.GetTopRatedTv)
                if (state.popularTv.data.isNullOrEmpty()) viewModel.executeAction(HomeAction.GetPopularTv)

                HomeMoviesList(state.onTheAirTv, title = "On the air tv series") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }
                HomeMoviesList(state.trendingTv, title = "Trending tv series") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }
                HomeMoviesList(state.topRatedTv, title = "Top rated tv series") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }
                HomeMoviesList(state.popularTv, title = "Popular tv series") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }
            }
            MediaType.People -> {
                HomeMoviesList(state.trendingPeople, title = "Trending people") { id, type ->
                    controller.navigate(ScreenResources.DetailScreenRoute(id, type))
                }
            }
            else -> {}
        }
    }
}
