package com.besha.shinobihub.features.detail.presentaion.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.components.PosterList
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.detail.data.model.mark.MarkRequest
import com.besha.shinobihub.features.detail.domain.constants.DetailTab
import com.besha.shinobihub.features.detail.presentaion.components.creditlist.CreditsList
import com.besha.shinobihub.features.detail.presentaion.components.expandabletext.ExpandableText
import com.besha.shinobihub.features.detail.presentaion.components.header.PeopleHeader
import com.besha.shinobihub.features.detail.presentaion.components.reviewslist.ReviewsList
import com.besha.shinobihub.features.detail.presentaion.components.seasonlist.SeasonsList
import com.besha.shinobihub.features.detail.presentaion.components.youtubeplayer.YouTubePlayer
import com.besha.shinobihub.features.detail.presentaion.viewmodel.DetailActions
import com.besha.shinobihub.features.detail.presentaion.viewmodel.DetailViewModel
import com.besha.shinobihub.ui.theme.poppinsFamily

@SuppressLint("DefaultLocale")
@Composable
fun MediaDetailScreen(
    mediaId: Int,
    mediaType: MediaType,
    navController: NavController,
) {
    val viewModel = hiltViewModel<DetailViewModel>()

    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    // Define proportions relative to screen size
    val backdropHeightFraction = 0.275f
    val backdropHeight = screenHeight * backdropHeightFraction
    val posterHeight = 200.dp

    val state by viewModel.viewStates.collectAsState()
    val mediaItem = state.detailMediaItem.data

    LaunchedEffect(Unit) {
        viewModel.executeAction(DetailActions.GetSessionId)

        when (mediaType) {
            MediaType.Movies -> {
                viewModel.executeAction(DetailActions.GetMovieAccountState(mediaId))
                viewModel.executeAction(DetailActions.GetDetailMovie(mediaId))
                viewModel.executeAction(DetailActions.GetMovieVideo(mediaId))
                viewModel.executeAction(DetailActions.GetMovieCredits(mediaId))
                viewModel.executeAction(DetailActions.GetMovieSimilar(mediaId))
                viewModel.executeAction(DetailActions.GetMovieReviews(mediaId))
            }

            MediaType.Tv -> {
                viewModel.executeAction(DetailActions.GetTvAccountState(mediaId))
                viewModel.executeAction(DetailActions.GetDetailTv(mediaId))
                viewModel.executeAction(DetailActions.GetTvVideo(mediaId))
                viewModel.executeAction(DetailActions.GetTvCredits(mediaId))
                viewModel.executeAction(DetailActions.GetTvSimilar(mediaId))
                viewModel.executeAction(DetailActions.GetTvReviews(mediaId))
            }

            MediaType.People -> {
                viewModel.executeAction(DetailActions.GetDetailPerson(mediaId))
                viewModel.executeAction(DetailActions.GetPeopleCredits(mediaId))
            }

            else -> {}
        }
    }

    LaunchedEffect(state.toggleCode) {
        when (state.toggleCode) {
            1 -> Toast.makeText(context, "Item added successfully", Toast.LENGTH_SHORT).show()
            13 -> Toast.makeText(context, "Item removed successfully", Toast.LENGTH_SHORT).show()
        }
        viewModel.executeAction(DetailActions.ResetToggleCode)
    }

    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
        ) {
            // Backdrop image
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original${mediaItem?.backdrop_path ?: ""}",
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(backdropHeight),
                contentScale = ContentScale.FillBounds,
            )

            // Foreground content (poster + title)
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        // Offset makes poster overlap halfway over the backdrop
                        .padding(top = if (mediaType != MediaType.People) (backdropHeight - (posterHeight * 0.35f)) else 0.dp),
            ) {
                if (mediaType == MediaType.People) {
                    mediaItem?.let {
                        PeopleHeader(posterHeight, mediaItem, state.sessionId)
                    }
                } else {
                    mediaItem?.let {
                        // 🔽 This is the inlined MediaHeader content
                        Row(modifier = Modifier.padding(horizontal = 18.dp)) {
                            // Poster
                            Card(
                                modifier =
                                    Modifier
                                        .width(140.dp)
                                        .height(posterHeight),
                                shape = RoundedCornerShape(10.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                border = BorderStroke(2.dp, colorResource(R.color.white)),
                            ) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/original${mediaItem.resolvedPoster ?: ""}",
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.padding(top = (posterHeight * 0.35f))) {
                                Text(
                                    modifier = Modifier.padding(top = 8.dp),
                                    text = mediaItem.resolvedTilte ?: "",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.black),
                                )

                                Text(
                                    text =
                                        (
                                            mediaItem.resolvedDate?.split("-")?.getOrNull(0)
                                                ?: ""
                                        ) +
                                            " ‧ " + (mediaItem.genres?.getOrNull(0)?.name ?: "") +
                                            " ‧ " + (
                                                if (mediaType == MediaType.Movies) {
                                                    "${mediaItem.runtime} min"
                                                } else if (mediaType == MediaType.Tv) {
                                                    "${mediaItem.number_of_seasons} Seasons"
                                                } else {
                                                    ""
                                                }
                                            ),
                                    color = colorResource(R.color.gray),
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(R.drawable.tmdb),
                                        contentDescription = null,
                                        modifier =
                                            Modifier
                                                .size(45.dp, 16.dp)
                                                .background(Color(0xFF0d253f))
                                                .padding(horizontal = 3.dp),
                                    )

                                    Text(
                                        modifier = Modifier.padding(start = 8.dp),
                                        text =
                                            buildAnnotatedString {
                                                withStyle(style = SpanStyle(color = colorResource(R.color.black))) {
                                                    append(
                                                        String.format(
                                                            "%.1f",
                                                            mediaItem.vote_average,
                                                        ),
                                                    )
                                                }
                                                withStyle(style = SpanStyle(color = colorResource(R.color.gray))) {
                                                    append("/10.0")
                                                }
                                            },
                                    )
                                }

                                if (state.sessionId != null) {
                                    Row(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(top = 8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    ) {
                                        // Watchlist Button
                                        Card(
                                            shape = RoundedCornerShape(10.dp),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                            colors =
                                                CardDefaults.cardColors(
                                                    containerColor =
                                                        colorResource(
                                                            R.color.light_gray,
                                                        ),
                                                ),
                                            modifier =
                                                Modifier
                                                    .weight(1f) // evenly share space
                                                    .height(48.dp),
                                        ) {
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center,
                                            ) {
                                                if (state.isWatchlist.isLoading) {
                                                    CircularProgressIndicator()
                                                } else {
                                                    Row(
                                                        modifier =
                                                            Modifier.clickable {
                                                                viewModel.executeAction(
                                                                    DetailActions.ToggleWatchList(
                                                                        MarkRequest(
                                                                            media_type = mediaType.value,
                                                                            media_id = mediaId,
                                                                            watchlist = state.isWatchlist.data?.not(),
                                                                        ),
                                                                    ),
                                                                )
                                                            },
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.Center,
                                                    ) {
                                                        Image(
                                                            painter =
                                                                painterResource(
                                                                    if (state.isWatchlist.data == true) R.drawable.ic_saved else R.drawable.ic_save,
                                                                ),
                                                            contentDescription = "save button",
                                                            modifier = Modifier.size(20.dp),
                                                        )
                                                        Text(
                                                            text = if (state.isWatchlist.data == true) "Saved" else "Save",
                                                            color = colorResource(R.color.gray),
                                                            modifier = Modifier.padding(start = 6.dp),
                                                            fontSize = 15.sp,
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        // Favorite Button
                                        Card(
                                            shape = RoundedCornerShape(10.dp),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                            colors =
                                                CardDefaults.cardColors(
                                                    containerColor =
                                                        colorResource(
                                                            R.color.light_gray,
                                                        ),
                                                ),
                                            modifier =
                                                Modifier
                                                    .weight(1f) // evenly share space
                                                    .height(48.dp),
                                        ) {
                                            if (state.isFavorite.isLoading) {
                                                CircularProgressIndicator()
                                            } else {
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.Center,
                                                ) {
                                                    Row(
                                                        modifier =
                                                            Modifier.clickable {
                                                                viewModel.executeAction(
                                                                    DetailActions.ToggleFavorite(
                                                                        MarkRequest(
                                                                            media_type = mediaType.value,
                                                                            media_id = mediaId,
                                                                            favorite = state.isFavorite.data?.not(),
                                                                        ),
                                                                    ),
                                                                )
                                                            },
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.Center,
                                                    ) {
                                                        Image(
                                                            painter =
                                                                painterResource(
                                                                    if (state.isFavorite.data == true) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24,
                                                                ),
                                                            contentDescription = "like button",
                                                            modifier = Modifier.size(20.dp),
                                                        )
                                                        Text(
                                                            text = if (state.isFavorite.data == true) "Liked" else "Like",
                                                            color = colorResource(R.color.gray),
                                                            modifier = Modifier.padding(start = 6.dp),
                                                            fontSize = 15.sp,
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // 🔼 End of inlined MediaHeader
                    }
                }

                FlowRow(
                    modifier = Modifier.padding(top = 30.dp, start = 18.dp, end = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    mediaItem?.genres?.forEach { item ->

                        Card(
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_gray)),
                        ) {
                            Text(
                                text = item.name ?: "",
                                color = colorResource(R.color.black),
                                modifier =
                                    Modifier.padding(
                                        horizontal = 20.dp,
                                        vertical = 8.dp,
                                    ),
                                fontSize = 15.sp,
                            )
                        }
                    }
                }

                Card(
                    modifier =
                        Modifier
                            .padding(top = 50.dp)
                            .fillMaxHeight(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.off_white)),
                    border = BorderStroke(2.dp, colorResource(R.color.gray)),
                ) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                    ) {
                        val selectedTab = state.selectedTab.data

                        Row(
                            modifier =
                                Modifier
                                    .padding(top = 28.dp, start = 18.dp, end = 18.dp)
                                    .fillMaxWidth(0.8f) // doesn’t take full width, but centered
                                    .align(Alignment.CenterHorizontally)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(colorResource(R.color.light_gray))
                                    .padding(3.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            if (mediaType != MediaType.People) {
                                DetailTab.entries.filter { tab ->
                                    // Show "Seasons" only for TV media type
                                    mediaType == MediaType.Tv || tab != DetailTab.SEASONS
                                }.forEach { tab ->

                                    TabItem(
                                        label = tab.label,
                                        isSelected = selectedTab == tab,
                                        onClick = {
                                            viewModel.executeAction(
                                                DetailActions.SwitchTab(
                                                    tab,
                                                ),
                                            )
                                        },
                                        modifier =
                                            Modifier
                                                .weight(1f),
                                    )
                                }
                            }
                        }

                        when (selectedTab) {
                            DetailTab.INFO -> {
                                state.credits.data?.let {
                                    CreditsList(it) { id, mediaType ->
                                        navController.navigate(
                                            ScreenResources.DetailScreenRoute(
                                                id,
                                                mediaType,
                                            ),
                                        )
                                    }
                                }

                                Text(
                                    modifier =
                                        Modifier.padding(
                                            top = 32.dp,
                                            start = 18.dp,
                                            end = 18.dp,
                                        ),
                                    text = "About",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                val about =
                                    if (mediaType == MediaType.People) mediaItem?.biography else mediaItem?.overview
                                ExpandableText(
                                    text = about ?: "",
                                    fontFamily = poppinsFamily,
                                    modifier =
                                        Modifier.padding(
                                            top = 16.dp,
                                            start = 18.dp,
                                            end = 18.dp,
                                        ),
                                )

                                state.videoList.data?.forEach {
                                    YouTubePlayer(video = it)
                                }

                                state.similar.data?.let {
                                    Text(
                                        modifier =
                                            Modifier.padding(
                                                top = 32.dp,
                                                start = 18.dp,
                                                end = 18.dp,
                                            ),
                                        text = "Similar",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    PosterList(it) { id, mediaType ->
                                        navController.navigate(
                                            ScreenResources.DetailScreenRoute(
                                                id,
                                                mediaType,
                                            ),
                                        )
                                    }
                                }
                                state.peopleCredits.data?.let {
                                    Text(
                                        modifier =
                                            Modifier.padding(
                                                top = 32.dp,
                                                start = 18.dp,
                                                end = 18.dp,
                                            ),
                                        text = "Known for",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    PosterList(it) { id, mediaType ->
                                        navController.navigate(
                                            ScreenResources.DetailScreenRoute(
                                                id,
                                                mediaType,
                                            ),
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            DetailTab.REVIEWS -> {
                                Text(
                                    modifier = Modifier.padding(top = 32.dp, start = 18.dp),
                                    text = "Reviews",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                ReviewsList(state.review.data)

                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            DetailTab.SEASONS -> {
                                val seasons = state.detailMediaItem.data?.seasons ?: emptyList()

                                if (seasons.isNotEmpty()) {
                                    Text(
                                        modifier = Modifier.padding(top = 32.dp, start = 18.dp),
                                        text = "Seasons",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                    )

                                    SeasonsList(seasons)
                                } else {
                                    Box(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 40.dp),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "No seasons available.",
                                            color = colorResource(R.color.gray),
                                            fontSize = 14.sp,
                                            fontFamily = poppinsFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) colorResource(R.color.white) else colorResource(R.color.light_gray)
    val textColor = if (isSelected) colorResource(R.color.black) else colorResource(R.color.gray)

    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundColor)
                .clickable { onClick() }
                .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
