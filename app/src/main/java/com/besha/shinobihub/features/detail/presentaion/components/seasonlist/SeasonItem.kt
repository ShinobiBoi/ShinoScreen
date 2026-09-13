package com.besha.shinobihub.features.detail.presentaion.components.seasonlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.besha.shinobihub.R
import com.besha.shinobihub.features.detail.presentaion.components.expandabletext.ExpandableText

@Composable
fun SeasonItem(season: com.besha.shinobihub.features.detail.data.model.detailitem.Season) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.off_white)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
        ) {
            // Poster image
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${season.poster_path}",
                contentDescription = season.name,
                modifier =
                    Modifier
                        .size(width = 100.dp, height = 140.dp)
                        .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Season info
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
            ) {
                Text(
                    text = season.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(R.color.black),
                )

                Text(
                    text = "Episodes: ${season.episode_count}",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    modifier = Modifier.padding(top = 4.dp),
                )

                if (season.overview.isNotEmpty()) {
                    ExpandableText(
                        modifier = Modifier.padding(top = 6.dp),
                        text = season.overview,
                        minimizedMaxLines = 4,
                    )
                }
            }
        }
    }
}
