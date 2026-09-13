package com.besha.shinobihub.features.detail.presentaion.components.seasonlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.besha.shinobihub.R

@Composable
fun SeasonsList(seasons: List<com.besha.shinobihub.features.detail.data.model.detailitem.Season>) {
    var expanded by remember { mutableStateOf(false) }
    val itemsToShow = if (expanded) seasons else seasons.take(5)

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsToShow.forEach { season ->
            SeasonItem(season)
        }

        // Only show the toggle if there are more than 3 seasons
        if (seasons.size > 5) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (expanded) "Show less" else "Show more",
                color = colorResource(R.color.dark_blue),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { expanded = !expanded }
                        .padding(vertical = 4.dp),
            )
        }
    }
}
