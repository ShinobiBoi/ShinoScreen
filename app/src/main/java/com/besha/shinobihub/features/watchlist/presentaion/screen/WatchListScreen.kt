package com.besha.shinobihub.features.watchlist.presentaion.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.find.presenation.components.FindMediaGridList
import com.besha.shinobihub.features.watchlist.presentaion.viewmodel.*

@Composable
fun WatchListScreen(navController: NavController) {
    val viewModel = hiltViewModel<WatchListViewModel>()
    val state by viewModel.viewStates.collectAsState()

    // Whenever media type changes, trigger appropriate fetch
    LaunchedEffect(state.mediaType) {
        when (state.mediaType) {
            MediaType.Movies -> viewModel.executeAction(WatchListAction.GetMovieWatchList)
            MediaType.Tv -> viewModel.executeAction(WatchListAction.GetTvWatchList)
            else -> {}
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
    ) {
        Text(
            text = "My Watchlist",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )

        // Toggle between Movies and TV like in FindScreen
        val mediaTypeList =
            listOf(
                MediaType.Movies,
                MediaType.Tv,
            )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 13.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            mediaTypeList.forEach {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                if (it == state.mediaType) {
                                    colorResource(R.color.black)
                                } else {
                                    colorResource(
                                        R.color.light_gray,
                                    )
                                },
                        ),
                    onClick = {
                        viewModel.executeAction(WatchListAction.ChangeMediaType(it))
                    },
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = if (it == MediaType.Tv)"Tv series" else it.name,
                        color =
                            if (it == state.mediaType) {
                                colorResource(R.color.white)
                            } else {
                                colorResource(
                                    R.color.gray,
                                )
                            },
                        fontSize = 18.sp,
                        modifier =
                            Modifier.fillMaxWidth().padding(
                                start = 20.dp,
                                end = 20.dp,
                                top = 5.dp,
                                bottom = 5.dp,
                            ),
                    )
                }
            }
        }

        // Grid List showing media (reuse your existing component)
        FindMediaGridList(
            modifier = Modifier.padding(top = 20.dp, start = 13.dp, end = 13.dp),
            state = state.media,
        ) { id, type ->
            navController.navigate(ScreenResources.DetailScreenRoute(id, type))
        }
    }
}
