package com.besha.shinobihub.appcore.data.model.movie

import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType

data class MediaItemDto(
    val id: Int,
    val media_type: String? = null,
    val adult: Boolean? = null,
    val popularity: Double? = null,
    val overview: String? = null,
    val genre_ids: List<Int>? = null,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    // Movie-specific
    val title: String? = null,
    val original_title: String? = null,
    val release_date: String? = null,
    // TV-specific
    val name: String? = null,
    val original_name: String? = null,
    val first_air_date: String? = null,
    val origin_country: List<String>? = null,
    // Person-specific
    val known_for_department: String? = null,
    val profile_path: String? = null,
    val gender: Int? = null,
    val known_for: List<MediaItemDto>? = null, // 👈 recursive list
)

fun MediaItemDto.toDomain(): MediaItem {
    val resolvedType =
        when {
            // If TMDB gives us a media_type, just use it
            media_type != null ->
                when (media_type) {
                    "movie" -> MediaType.Movies
                    "tv" -> MediaType.Tv
                    "person" -> MediaType.People
                    else -> {
                        MediaType.All
                    }
                }

            // Otherwise, infer type based on available fields (your if logic)
            !known_for_department.isNullOrEmpty() || !profile_path.isNullOrEmpty() -> MediaType.People
            !title.isNullOrEmpty() -> MediaType.Movies
            !name.isNullOrEmpty() -> MediaType.Tv
            else -> {
                MediaType.All
            }
        }

    val resolvedTitle =
        when {
            !title.isNullOrEmpty() -> title
            !name.isNullOrEmpty() -> name
            else -> null
        }

    val resolvedPoster =
        when {
            !poster_path.isNullOrEmpty() -> poster_path
            !profile_path.isNullOrEmpty() -> profile_path
            else -> null
        }

    val resolvedDate =
        when {
            !release_date.isNullOrEmpty() -> release_date
            !first_air_date.isNullOrEmpty() -> first_air_date
            else -> null
        }

    return MediaItem(
        id = id,
        media_type = resolvedType,
        resolvedTitle = resolvedTitle ?: "",
        resolvedPoster = resolvedPoster ?: "",
        resolvedDate = resolvedDate ?: "",
        adult = adult,
        popularity = popularity,
        overview = overview,
        genre_ids = genre_ids,
        backdrop_path = backdrop_path,
        vote_average = vote_average,
        vote_count = vote_count,
        original_title = original_title,
        original_name = original_name,
        origin_country = origin_country,
        known_for_department = known_for_department,
        gender = gender,
        known_for = known_for?.map { it }, // or `.map { it.toDomain() }` if you want to map recursively
    )
}
