package com.besha.shinobihub.features.home.presentaion.compoents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.besha.shinobihub.R
import com.besha.shinobihub.features.home.data.model.GenreIconListItem

@Composable
fun HomeGenreList(onCardClick: (Int) -> Unit) {
    val genreList =
        listOf(
            GenreIconListItem(35, "Comedy", R.drawable.ic_comedy),
            GenreIconListItem(80, "Crime", R.drawable.ic_crime),
            GenreIconListItem(28, "Action", R.drawable.ic_action),
            GenreIconListItem(53, "Thriller", R.drawable.ic_thriller),
            GenreIconListItem(27, "Horror", R.drawable.ic_horror),
            GenreIconListItem(14, "Fantasy", R.drawable.ic_fantasy),
            GenreIconListItem(10749, "Romance", R.drawable.ic_romance),
        )

    LazyRow(
        modifier =
            Modifier
                .fillMaxWidth().padding(top = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(genreList) { genre ->

            Card(
                modifier =
                    Modifier.size(90.dp).clickable(onClick = {
                        onCardClick(genre.id)
                    }),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.off_white)),
                // backgroundColor = Color(0xFFFAFAFA)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AsyncImage(
                        model = genre.icon,
                        contentDescription = genre.title,
                        modifier =
                            Modifier
                                .padding(top = 20.dp)
                                .size(31.dp),
                    )

                    Text(
                        text = genre.title,
                        modifier =
                            Modifier
                                .padding(top = 10.dp),
                    )
                }
            }
        }
    }
}
