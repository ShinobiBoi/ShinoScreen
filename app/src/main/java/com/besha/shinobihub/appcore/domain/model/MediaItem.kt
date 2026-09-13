package com.besha.shinobihub.appcore.domain.model

import com.besha.shinobihub.appcore.data.model.movie.MediaItemDto

data class MediaItem(
    val id: Int,
    val media_type: MediaType, // "movie", "tv", or "person"
    val resolvedTitle: String? = null,
    val resolvedPoster: String? = null,
    val resolvedDate: String? = null,
    val adult: Boolean? = null,
    val popularity: Double? = null,
    val overview: String? = null,
    val genre_ids: List<Int>? = null,
    val backdrop_path: String? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    // Movie-specific
    val original_title: String? = null,
    // TV-specific
    val original_name: String? = null,
    val origin_country: List<String>? = null,
    // Person-specific
    val known_for_department: String? = null,
    val gender: Int? = null,
    val known_for: List<MediaItemDto>? = null, // 👈 recursive list
)
