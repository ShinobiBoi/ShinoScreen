package com.besha.shinobihub.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.core.app.NotificationCompat
import coil.ImageLoader
import coil.request.ImageRequest
import com.besha.shinobihub.MainActivity
import com.besha.shinobihub.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrendingFCMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.data["title"] ?: "Trending Now"
        val content = message.data["content"] ?: "Check out today's trending pick!"
        val poster = message.data["poster"] ?: ""
        val backdrop = message.data["backdrop"] ?: ""
        val id = message.data["media_id"] ?: ""
        val mediatype = message.data["mediatype"] ?: ""

        CoroutineScope(Dispatchers.IO).launch {
            sendNotification(title, content, poster, backdrop, id.toInt(), mediatype)
        }
    }

    override fun onNewToken(token: String) {
        // send token to your backend if needed
    }

    private suspend fun sendNotification(
        title: String,
        content: String,
        poster_path: String,
        backdrop_path: String,
        mediaId: Int,
        mediaType: String,
    ) {
        val channelId = "trending_channel"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent =
            Intent(applicationContext, MainActivity::class.java).apply {
                putExtra("media_id", mediaId) // optional extra to know it came from notification
                putExtra("media_type", mediaType)

                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        val pendingIntent =
            PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
            )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    channelId,
                    "Trending Movies",
                    NotificationManager.IMPORTANCE_DEFAULT,
                )
            notificationManager.createNotificationChannel(channel)
        }

        // Load image from URL into bitmap
        val posterUrl =
            "https://image.tmdb.org/t/p/w500${poster_path }" // whatever your model calls it
        val backdropUrl =
            "https://image.tmdb.org/t/p/w500${backdrop_path }" // whatever your model calls it

        val bitmapPoster = loadBitmap(posterUrl)
        val bitmapBackdrop = loadBitmap(backdropUrl)

        val builder =
            NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.shinobihub)
                .setContentTitle(title)
                .setContentText("Check out: $content")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        if (bitmapPoster != null) {
            builder.setLargeIcon(bitmapPoster)
        }

        if (bitmapBackdrop != null) {
            builder.setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmapBackdrop)
                    .bigLargeIcon(null as Bitmap?),
            )
        }

        notificationManager.notify(1, builder.build())
    }

    // Load image into Bitmap using Coil
    private suspend fun loadBitmap(url: String): Bitmap? {
        return try {
            val loader = ImageLoader(applicationContext)
            val request =
                ImageRequest.Builder(applicationContext)
                    .data(url)
                    .allowHardware(false) // hardware bitmaps can't be put in notifications
                    .build()
            val result = loader.execute(request)
            (result.drawable as BitmapDrawable).bitmap
        } catch (e: Exception) {
            null
        }
    }
}
