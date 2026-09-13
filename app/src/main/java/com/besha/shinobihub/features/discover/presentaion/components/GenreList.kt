package com.besha.shinobihub.features.discover.presentaion.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.data.model.genre.Genre

@Composable
fun GenreList(
    genreList: List<Genre>,
    cardClick: (genreId: Int) -> Unit,
) {
    val sortedList =
        remember(genreList) {
            genreList.sortedByDescending { it.selected }
        }

    LazyRow(
        modifier =
            Modifier
                .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(sortedList) {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            if (it.selected) {
                                colorResource(R.color.black)
                            } else {
                                colorResource(
                                    R.color.light_gray,
                                )
                            },
                    ),
                onClick = { cardClick(it.id!!) },
            ) {
                Text(
                    text = it.name ?: "",
                    color = if (it.selected) colorResource(R.color.white) else colorResource(R.color.gray),
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
