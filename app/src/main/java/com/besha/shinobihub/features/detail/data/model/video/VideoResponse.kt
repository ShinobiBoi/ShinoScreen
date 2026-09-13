package com.besha.shinobihub.features.detail.data.model.video

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val id: Int? = 0,
    @SerializedName("results")
    val videoItems: List<VideoItem>? = listOf(),
)
