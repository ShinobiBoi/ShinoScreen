package com.besha.shinobihub.features.detail.presentaion.components.reviewslist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.besha.shinobihub.R
import com.besha.shinobihub.features.detail.data.model.review.Review
import com.besha.shinobihub.features.detail.presentaion.components.expandabletext.ExpandableText

@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.off_white)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, colorResource(R.color.light_gray)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(50))
                            .background(colorResource(R.color.dark_blue)),
                    contentAlignment = Alignment.Center,
                ) {
                    val initials = review.author?.take(1)?.uppercase() ?: "?"
                    Text(
                        text = initials,
                        color = colorResource(R.color.white),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }

                Column {
                    Text(
                        text = review.author ?: "Unknown",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = colorResource(R.color.black),
                    )
                    Text(
                        text = review.created_at?.substringBefore("T") ?: "",
                        fontSize = 12.sp,
                        color = colorResource(R.color.gray),
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            ExpandableText(
                text = review.content ?: "",
                minimizedMaxLines = 5,
            )
        }
    }
}
