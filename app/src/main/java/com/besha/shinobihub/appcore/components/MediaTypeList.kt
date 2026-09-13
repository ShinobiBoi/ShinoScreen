package com.besha.shinobihub.appcore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.domain.model.MediaType

@Composable
fun MediaTypeList(
    mediaType: MediaType,
    cardClick: (type: MediaType) -> Unit,
) {
    LazyRow(
        modifier =
            Modifier
                .fillMaxWidth().padding(horizontal = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(MediaType.entries) {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            if (it == mediaType) {
                                colorResource(R.color.black)
                            } else {
                                colorResource(
                                    R.color.light_gray,
                                )
                            },
                    ),
                onClick = { cardClick(it) },
            ) {
                Text(
                    text = if (it == MediaType.Tv)"Tv series" else it.name,
                    color = if (it == mediaType) colorResource(R.color.white) else colorResource(R.color.gray),
                    fontSize = 18.sp,
                    modifier =
                        Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 5.dp,
                            bottom = 5.dp,
                        ),
                )
            }
        }
    }
}
