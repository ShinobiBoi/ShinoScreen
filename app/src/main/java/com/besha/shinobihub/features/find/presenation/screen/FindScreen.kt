package com.besha.shinobihub.features.find.presenation.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.components.MediaTypeList
import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.find.presenation.components.FindMediaGridList
import com.besha.shinobihub.features.find.presenation.viewmodel.FindAction
import com.besha.shinobihub.features.find.presenation.viewmodel.FindViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindScreen(navController: NavController) {
    Column {
        val findViewModel = hiltViewModel<FindViewModel>()
        val state by findViewModel.viewStates.collectAsState()
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState =
            rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
            )

        LaunchedEffect(Unit) {
            findViewModel.executeAction(FindAction.GetGenreList)
        }

        OutlinedTextField(
            value = state.query.data ?: "",
            onValueChange = {
                findViewModel.executeAction(FindAction.ChangeQuery(it))
            }, // now valid
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Search movies...") },
            singleLine = true,
            shape = RoundedCornerShape(30.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                IconButton(onClick = { showBottomSheet = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Filter",
                        tint = colorResource(R.color.gray),
                    )
                }
            },
        )

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = colorResource(R.color.white),
                dragHandle = {},
            ) {
                FilterSheetContent(
                    onApply = {
                        // apply filters
                        showBottomSheet = false
                    },
                    onCancel = { showBottomSheet = false },
                    currentMediaType = state.mediaType,
                    onClearFilters = { findViewModel.executeAction(FindAction.ClearFilters) },
                    onMediaTypeChange = { findViewModel.executeAction(FindAction.ChangeMediaType(it)) },
                    toggleGenre = { findViewModel.executeAction(FindAction.ToggleGenre(it)) },
                    genres = state.genres.data?.sortedByDescending { it.selected } ?: emptyList(),
                )
            }
        }

        MediaTypeList(state.mediaType) {
            findViewModel.executeAction(FindAction.ChangeMediaType(it))
        }

        LaunchedEffect(
            key1 = state.mediaType,
            key2 = state.query.data,
            key3 = state.genres.data,
        ) {
            var filterList = emptyList<Genre>()
            state.genres.data?.let {
                filterList = it.filter { it.selected }
            }

            if (state.query.data.isNullOrEmpty()) {
                when (state.mediaType) {
                    MediaType.All -> {
                        findViewModel.executeAction(FindAction.GetTrendingAll(filterList))
                    }

                    MediaType.Movies -> {
                        findViewModel.executeAction(FindAction.GetTrendingMovies(filterList))
                    }

                    MediaType.Tv -> {
                        findViewModel.executeAction(FindAction.GetTrendingTv(filterList))
                    }

                    MediaType.People -> {
                        findViewModel.executeAction(FindAction.GetTrendingPeople(filterList))
                    }

                    else -> {}
                }
            } else {
                when (state.mediaType) {
                    MediaType.All -> {
                        findViewModel.executeAction(
                            FindAction.SearchMulti(
                                state.query.data ?: "",
                                filterList,
                            ),
                        )
                    }

                    MediaType.Movies -> {
                        findViewModel.executeAction(
                            FindAction.SearchMovie(
                                state.query.data ?: "",
                                filterList,
                            ),
                        )
                    }

                    MediaType.Tv -> {
                        findViewModel.executeAction(
                            FindAction.SearchTv(
                                state.query.data ?: "",
                                filterList,
                            ),
                        )
                    }

                    MediaType.People -> {
                        findViewModel.executeAction(
                            FindAction.SearchPeople(
                                state.query.data ?: "",
                                filterList,
                            ),
                        )
                    }

                    else -> {}
                }
            }
        }

        FindMediaGridList(
            modifier = Modifier.padding(top = 30.dp, start = 13.dp, end = 13.dp),
            state = state.media,
        ) { id, type ->
            navController.navigate(ScreenResources.DetailScreenRoute(id, type))
        }
    }
}

@Composable
fun FilterSheetContent(
    onApply: () -> Unit,
    onCancel: () -> Unit,
    onClearFilters: () -> Unit,
    currentMediaType: MediaType,
    onMediaTypeChange: (MediaType) -> Unit,
    toggleGenre: (genre: Genre) -> Unit,
    genres: List<Genre>,
) {
    var expanded by remember { mutableStateOf(false) }
    val SelectedColor = colorResource(R.color.light_blue)
    val visibleGenres = if (expanded) genres else genres.take((genres.size / 2).coerceAtLeast(1))
    val scrollState = rememberScrollState()

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(16.dp)
                .verticalScroll(scrollState)
                .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Header Row — Close + Title
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
                    text = "Search Filters",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }

        // Genre grid (identical styling to DiscoverFilterSheet)
        FlowRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            visibleGenres.forEach { item ->
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors =
                        CardDefaults.cardColors(
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

        // Show more / less button
        if (genres.size > visibleGenres.size) {
            TextButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(if (expanded) "Show Less" else "Show More")
            }
        }

        // Media type radio selection (NEW)
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

            MediaType.entries.forEach { type ->
                val selected = type == currentMediaType

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { onMediaTypeChange(type) }
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                ) {
                    androidx.compose.material3.RadioButton(
                        selected = selected,
                        onClick = { onMediaTypeChange(type) },
                        colors =
                            androidx.compose.material3.RadioButtonDefaults.colors(
                                selectedColor = SelectedColor,
                                unselectedColor = colorResource(R.color.gray),
                            ),
                    )
                    Text(
                        text =
                            when (type) {
                                MediaType.All -> "All"
                                MediaType.Movies -> "Movies"
                                MediaType.Tv -> "TV Series"
                                MediaType.People -> "People"
                            },
                        color = if (selected) colorResource(R.color.black) else colorResource(R.color.gray),
                        fontSize = 16.sp,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Apply Filters button — styled like Discover’s "Clear Filters"
        TextButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(R.color.light_blue)),
            onClick = onApply,
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "Apply Filters",
                color = colorResource(R.color.white),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
            )
        }

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

        // Close button
        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Close")
        }
    }
}
