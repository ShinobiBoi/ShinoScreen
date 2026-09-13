package com.besha.shinobihub.features.discover.presentaion.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.discover.presentaion.components.DiscoverGridLIst
import com.besha.shinobihub.features.discover.presentaion.components.GenreList
import com.besha.shinobihub.features.discover.presentaion.viewmodel.DiscoverAction
import com.besha.shinobihub.features.discover.presentaion.viewmodel.DiscoverViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    navController: NavController,
    genreId: Int,
) {
    val discoverViewModel = hiltViewModel<DiscoverViewModel>()
    val state by discoverViewModel.viewStates.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var didToggle by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Initial load
    LaunchedEffect(Unit) {
        discoverViewModel.executeAction(DiscoverAction.GetGenreList)
        discoverViewModel.executeAction(DiscoverAction.GetDiscoverMovie(""))
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(),
    ) {
        // Bottom sheet for filtering
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = colorResource(R.color.white),
                dragHandle = {},
            ) {
                DiscoverFilterSheet(
                    currentMediaType = state.mediaType,
                    onMediaTypeChange = { newType ->
                        discoverViewModel.executeAction(DiscoverAction.ChangeMediaType(newType))
                        val selectedIds =
                            discoverViewModel.viewStates.value.genres.data
                                ?.filter { it.selected }
                                ?.joinToString(",") { it.id.toString() }
                                ?: ""
                        when (newType) {
                            MediaType.Movies ->
                                discoverViewModel.executeAction(
                                    DiscoverAction.GetDiscoverMovie(selectedIds),
                                )
                            MediaType.Tv ->
                                discoverViewModel.executeAction(
                                    DiscoverAction.GetDiscoverTv(selectedIds),
                                )
                            else -> {}
                        }
                    },
                    onCancel = { showBottomSheet = false },
                    toggleGenre = { genre ->
                        discoverViewModel.executeAction(DiscoverAction.ToggleGenre(genre.id!!))

                        val selectedIds =
                            discoverViewModel.viewStates.value.genres.data
                                ?.filter { it.selected }
                                ?.joinToString(",") { it.id.toString() }
                                ?: ""

                        when (discoverViewModel.viewStates.value.mediaType) {
                            MediaType.Movies ->
                                discoverViewModel.executeAction(
                                    DiscoverAction.GetDiscoverMovie(selectedIds),
                                )
                            MediaType.Tv ->
                                discoverViewModel.executeAction(
                                    DiscoverAction.GetDiscoverTv(selectedIds),
                                )
                            else -> {}
                        }
                    },
                    genres = state.genres.data?.sortedByDescending { it.selected } ?: emptyList(),
                    onClearFilters = {
                        discoverViewModel.executeAction(DiscoverAction.ClearFilters)
                    },
                )
            }
        }

        // When media type changes, reload using selected genres
        LaunchedEffect(
            key1 = state.mediaType,
            key2 = state.genres,
        ) {
            if (!state.genres.data.isNullOrEmpty() && !didToggle) {
                discoverViewModel.executeAction(DiscoverAction.ToggleGenre(genreId))
                didToggle = true
            }

            val selectedIds =
                state.genres.data
                    ?.filter { it.selected }
                    ?.joinToString(",") { it.id.toString() }
                    ?: ""

            when (state.mediaType) {
                MediaType.Movies ->
                    discoverViewModel.executeAction(
                        DiscoverAction.GetDiscoverMovie(selectedIds),
                    )
                MediaType.Tv ->
                    discoverViewModel.executeAction(
                        DiscoverAction.GetDiscoverTv(selectedIds),
                    )
                else -> {}
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Popular Genres",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 45.dp, start = 14.dp),
            )

            Text(
                text = "View All",
                fontSize = 16.sp,
                color = colorResource(R.color.gray),
                modifier =
                    Modifier
                        .padding(top = 47.dp, end = 17.dp)
                        .clickable { showBottomSheet = true },
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        state.genres.data?.let {
            GenreList(it) { genreId ->
                discoverViewModel.executeAction(DiscoverAction.ToggleGenre(genreId))
            }
        }

        // Display results
        DiscoverGridLIst(
            state = state.media,
            modifier = Modifier.padding(top = 40.dp, start = 17.dp),
        ) { id, type ->
            navController.navigate(ScreenResources.DetailScreenRoute(id, type))
        }
    }
}

@Composable
fun DiscoverFilterSheet(
    currentMediaType: MediaType,
    onMediaTypeChange: (MediaType) -> Unit,
    onCancel: () -> Unit,
    onClearFilters: () -> Unit,
    toggleGenre: (genre: Genre) -> Unit,
    genres: List<Genre>,
) {
    var expanded by remember { mutableStateOf(false) }
    val SelectedColor = colorResource(R.color.light_blue)
    val visibleGenres = if (expanded) genres else genres.take((genres.size / 2).coerceAtLeast(1))

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(16.dp)
                .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Header Row — Close + Title + Clear Filters
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Black,
                    modifier =
                        Modifier
                            .size(28.dp)
                            .clickable { onCancel() },
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Discover Filters",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }

        // Genre grid
        FlowRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            visibleGenres.forEach { item ->
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = cardElevation(defaultElevation = 4.dp),
                    colors =
                        cardColors(
                            containerColor =
                                if (item.selected) {
                                    SelectedColor
                                } else {
                                    colorResource(R.color.light_gray)
                                },
                        ),
                    onClick = { toggleGenre(item) },
                ) {
                    Text(
                        text = item.name ?: "",
                        color =
                            if (item.selected) {
                                colorResource(R.color.white)
                            } else {
                                colorResource(R.color.black)
                            },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        fontSize = 15.sp,
                    )
                }
            }
        }

        if (genres.size > visibleGenres.size) {
            TextButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(if (expanded) "Show Less" else "Show More")
            }
        }

        // Media type radio column
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Media Type",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp),
            )

            val options = listOf(MediaType.Movies, MediaType.Tv)
            options.forEach { type ->
                val selected = type == currentMediaType
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { onMediaTypeChange(type) }
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                ) {
                    RadioButton(
                        selected = selected,
                        onClick = { onMediaTypeChange(type) },
                        colors =
                            RadioButtonDefaults.colors(
                                selectedColor = SelectedColor,
                                unselectedColor = colorResource(R.color.gray),
                            ),
                    )
                    Text(
                        text = if (type == MediaType.Movies) "Movies" else "TV Series",
                        color = if (selected) colorResource(R.color.black) else colorResource(R.color.gray),
                        fontSize = 16.sp,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(R.color.light_blue)),
            onClick = {
                onClearFilters()
                onCancel()
            },
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "Clear Filters",
                color = colorResource(R.color.white),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
            )
        }

        // Bottom close button
        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Close")
        }
    }
}
