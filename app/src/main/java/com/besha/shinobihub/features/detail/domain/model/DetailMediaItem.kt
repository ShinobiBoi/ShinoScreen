package com.besha.shinobihub.features.detail.domain.model

import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.features.detail.data.model.detailitem.Season

data class DetailMediaItem(
    val id: Int,
    val media_type: MediaType, // "movie", "tv", or "person"
    val resolvedTilte: String,
    val resolvedPoster: String,
    val resolvedDate: String,
    val adult: Boolean? = null,
    val popularity: Double? = null,
    val overview: String? = null,
    val genres: List<Genre>? = null,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    val original_language: String? = null,
    // Movie-specific
    val title: String? = null,
    val original_title: String? = null,
    val release_date: String? = null,
    val runtime: String? = null,
    // TV-specific
    val name: String? = null,
    val original_name: String? = null,
    val first_air_date: String? = null,
    val origin_country: List<String>? = null,
    val number_of_seasons: Int? = null,
    val seasons: List<Season>? = null,
    // Person-specific
    val known_for_department: String? = null,
    val profile_path: String? = null,
    val gender: Int? = null,
    val known_for: List<MediaItem>? = null, // 👈 recursive list
    val birthday: String? = null,
    val biography: String? = null,
)
