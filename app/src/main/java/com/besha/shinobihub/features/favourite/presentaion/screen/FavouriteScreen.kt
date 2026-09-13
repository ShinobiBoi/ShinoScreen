package com.besha.shinobihub.features.favourite.presentaion.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.besha.shinobihub.features.favourite.presentaion.viewmodel.*
import com.besha.shinobihub.features.find.presenation.components.FindMediaGridList

@Composable
fun FavouriteScreen(navController: NavController) {
    val viewModel = hiltViewModel<FavouriteViewModel>()
    val state by viewModel.viewStates.collectAsState()

    // Whenever media type changes, trigger appropriate fetch
    LaunchedEffect(state.mediaType) {
        when (state.mediaType) {
            MediaType.Movies -> viewModel.executeAction(FavouriteAction.GetMovieFavourite)
            MediaType.Tv -> viewModel.executeAction(FavouriteAction.GetTvFavourite)
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
            text = "My Favourites",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )

        val mediaTypeList = listOf(MediaType.Movies, MediaType.Tv)

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            mediaTypeList.forEach { type ->
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                if (type == state.mediaType) {
                                    colorResource(R.color.black)
                                } else {
                                    colorResource(R.color.light_gray)
                                },
                        ),
                    onClick = { viewModel.executeAction(FavouriteAction.ChangeMediaType(type)) },
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = if (type == MediaType.Tv) "Tv series" else type.name,
                        color =
                            if (type == state.mediaType) {
                                colorResource(R.color.white)
                            } else {
                                colorResource(R.color.gray)
                            },
                        fontSize = 18.sp,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp, horizontal = 20.dp),
                    )
                }
            }
        }

        FindMediaGridList(
            modifier = Modifier.padding(top = 20.dp, start = 13.dp, end = 13.dp),
            state = state.media,
        ) { id, type ->
            navController.navigate(ScreenResources.DetailScreenRoute(id, type))
        }
    }
}
