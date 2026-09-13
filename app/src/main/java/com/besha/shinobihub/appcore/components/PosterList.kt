package com.besha.shinobihub.appcore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType

@Composable
fun PosterList(
    posters: List<MediaItem>,
    onItemClick: (Int, MediaType) -> Unit,
) {
    LazyRow(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
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
