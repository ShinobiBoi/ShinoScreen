package com.besha.shinobihub.features.detail.presentaion.components.creditlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.besha.shinobihub.appcore.components.SmallPosterItem
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse

@Composable
fun CreditsList(
    creditsResponse: CreditsResponse,
    onItemClick: (Int, MediaType) -> Unit,
) {
    creditsResponse.cast?.let {
        Text(
            modifier = Modifier.padding(top = 32.dp, start = 18.dp),
            text = "Cast",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        LazyRow(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(
                creditsResponse.cast.filter {
                    !it?.profile_path.isNullOrEmpty()
                },
            ) { cast ->

                SmallPosterItem(
                    cast?.name,
                    cast?.profile_path,
                    MediaType.People,
                    cast?.id!!,
                ) { id, type ->
                    onItemClick(id, type)
                }
            }
        }
    }

    if (!creditsResponse.crew.isNullOrEmpty()) {
        Text(
            modifier = Modifier.padding(top = 32.dp, start = 18.dp),
            text = "Crew",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        LazyRow(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(creditsResponse.crew) { crew ->
                SmallPosterItem(
                    crew?.name,
                    crew?.profile_path,
                    MediaType.People,
                    crew?.id ?: 0,
                ) { id, type ->
                    onItemClick(id, type)
                }
            }
        }
    }
}
