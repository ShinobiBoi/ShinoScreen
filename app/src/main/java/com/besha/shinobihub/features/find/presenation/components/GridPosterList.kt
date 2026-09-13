package com.besha.shinobihub.features.find.presenation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.besha.shinobihub.appcore.components.SmallPosterItem
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType

@Composable
fun GridPosterList(
    modifier: Modifier = Modifier,
    posters: List<MediaItem>,
    onItemClick: (Int, MediaType) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(posters) { poster ->

            SmallPosterItem(
                poster.resolvedTitle,
                poster.resolvedPoster,
                poster.media_type,
                poster.id,
                onItemClick,
            )
        }
    }
}
