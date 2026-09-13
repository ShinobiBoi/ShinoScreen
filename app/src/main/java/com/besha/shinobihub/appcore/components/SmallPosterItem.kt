package com.besha.shinobihub.appcore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.domain.model.MediaType

@Composable
fun SmallPosterItem(
    title: String?,
    posterPath: String?,
    mediaType: MediaType,
    mediaId: Int,
    onItemClick: (Int, MediaType) -> Unit,
) {
    Column(
        modifier =
            Modifier.clickable(
                onClick = {
                    if (!posterPath.isNullOrEmpty()) {
                        onItemClick(mediaId, mediaType)
                    }
                },
            ),
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            if (posterPath.isNullOrEmpty()) {
                Image(
                    painter = painterResource(R.drawable.no_image_ic),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(width = 115.dp, height = 156.dp)
                            .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )
            } else {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500$posterPath",
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(width = 115.dp, height = 156.dp)
                            .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.no_image_ic),
                    error = painterResource(R.drawable.no_image_ic),
                )
            }
        }
        Text(
            modifier =
                Modifier
                    .width(115.dp)
                    .background(Color.Transparent)
                    .padding(top = 10.dp)
                    .basicMarquee(),
            text = title ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.gray),
        )
    }
}
