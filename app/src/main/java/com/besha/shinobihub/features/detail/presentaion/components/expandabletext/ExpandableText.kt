package com.besha.shinobihub.features.detail.presentaion.components.expandabletext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.besha.shinobihub.R

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = FontFamily.Default,
    minimizedMaxLines: Int = 3, // 👈 use lines instead of characters
    expandText: String = "Show more",
    collapseText: String = "Show less",
    linkColor: Color = colorResource(R.color.dark_blue),
) {
    var expanded by remember { mutableStateOf(false) }
    var isTextOverflowing by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            fontFamily = fontFamily,
            onTextLayout = { textLayoutResult ->
                // Detect if text doesn't fit in given maxLines
                isTextOverflowing = textLayoutResult.hasVisualOverflow
            },
        )

        if (isTextOverflowing || expanded) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (expanded) collapseText else expandText,
                color = linkColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier =
                    Modifier
                        .clickable { expanded = !expanded }
                        .padding(vertical = 2.dp),
            )
        }
    }
}
