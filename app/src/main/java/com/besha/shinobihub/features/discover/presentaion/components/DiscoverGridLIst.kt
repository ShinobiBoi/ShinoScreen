package com.besha.shinobihub.features.discover.presentaion.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.MediaViewState

@Composable
fun DiscoverGridLIst(
    state: MediaViewState,
    modifier: Modifier = Modifier,
    onItemClick: (Int, MediaType) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.data.isNullOrEmpty() -> {
                Text(
                    text = "It's empty here!",
                    color = colorResource(R.color.gray),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.data) { poster ->

                        MediumPosterItem(
                            poster.resolvedTitle,
                            poster.resolvedPoster,
                            poster.media_type,
                            poster.id,
                            onItemClick,
                        )
                    }
                }
            }
        }
    }
}
