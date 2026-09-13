package com.besha.shinobihub.features.home.presentaion.compoents

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType
import kotlinx.coroutines.delay

@Composable
fun TrendingMovieBannerPager(
    mediaItems: List<MediaItem>,
    pagerState: PagerState,
    onItemClick: (Int, MediaType) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // Auto-slide logic
    LaunchedEffect(Unit) {
        while (true) {
            val currentPage = pagerState.currentPage
            delay(5000)
            if (mediaItems.isNotEmpty() && currentPage == pagerState.currentPage) {
                val nextPage = (pagerState.currentPage + 1) % mediaItems.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    // ---- The pager with overlay text ----
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
    ) { page ->
        val mediaItem = mediaItems[page]

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.3f)
                    .padding(horizontal = 13.dp, vertical = 24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(
                        onClick = { onItemClick(mediaItem.id, mediaItem.media_type) },
                    ),
        ) {
            // Movie image
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original${mediaItem.backdrop_path}",
                contentDescription = mediaItem.resolvedTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
            )

            // Gradient overlay (for better text visibility)
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                            ),
                        ),
            )

            // Text overlay (title + tag)
            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
            ) {
                Text(
                    modifier =
                        Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(colorResource(R.color.light_blue))
                            .padding(4.dp),
                    text = "Trending Now",
                    color = colorResource(R.color.white),
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = mediaItem.resolvedTitle ?: "",
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.basicMarquee(),
                )

                val currentPage = pagerState.currentPage
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                ) {
                    repeat(mediaItems.size) { index ->
                        val isSelected = currentPage == index
                        Box(
                            modifier =
                                Modifier
                                    .padding(4.dp)
                                    .size(if (isSelected) 10.dp else 8.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(if (isSelected) colorResource(R.color.white) else colorResource(R.color.gray)),
                        )
                    }
                }
            }
        }
    }
}
