package com.besha.shinobihub.features.detail.presentaion.components.youtubeplayer

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.besha.shinobihub.features.detail.data.model.video.VideoItem
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayer(video: VideoItem) {
    if (video.site == "YouTube" && video.type == "Trailer" && !video.id.isNullOrEmpty()) {
        Text(
            modifier = Modifier.padding(top = 32.dp, start = 18.dp, end = 18.dp),
            text = video.name!!,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        AndroidView(
            modifier =
                Modifier
                    .padding(top = 8.dp, start = 18.dp, end = 18.dp)
                    .fillMaxWidth()
                    .height(220.dp),
            factory = { ctx ->
                YouTubePlayerView(ctx).apply {
                    // For Lifecycle-aware behavior:
                    (ctx as? ComponentActivity)?.lifecycle?.addObserver(this)

                    addYouTubePlayerListener(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                // loadVideo expects (videoId, startSeconds)
                                youTubePlayer.cueVideo(video.key, 0f)
                            }
                        },
                    )
                }
            },
        )
    }
}
