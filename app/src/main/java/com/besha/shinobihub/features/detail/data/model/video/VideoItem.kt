package com.besha.shinobihub.features.detail.data.model.video

data class VideoItem(
    val id: String? = "",
    val iso_3166_1: String? = "",
    val iso_639_1: String? = "",
    val key: String = "",
    val name: String? = "",
    val official: Boolean? = false,
    val published_at: String? = "",
    val site: String? = "",
    val size: Int? = 0,
    val type: String? = "",
)
