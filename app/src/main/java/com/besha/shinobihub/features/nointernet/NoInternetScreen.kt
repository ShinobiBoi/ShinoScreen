package com.besha.shinobihub.features.nointernet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.besha.shinobihub.R

@Composable
fun NoInternetScreen(onRetry: () -> Unit) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.WifiOff,
            contentDescription = "No internet",
            modifier = Modifier.size(96.dp),
            tint = colorResource(R.color.gray),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No Internet Connection",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please check your connection and try again.",
            fontSize = 15.sp,
            color = colorResource(R.color.gray),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.dark_blue)),
            modifier =
                Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp),
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, tint = colorResource(R.color.white))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Retry", color = colorResource(R.color.white))
        }
    }
}
