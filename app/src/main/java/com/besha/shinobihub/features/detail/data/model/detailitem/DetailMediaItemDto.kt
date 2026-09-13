package com.besha.shinobihub.features.detail.data.model.detailitem

import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.data.model.movie.MediaItemDto
import com.besha.shinobihub.appcore.data.model.movie.toDomain
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.features.detail.domain.model.DetailMediaItem

data class DetailMediaItemDto(
    val id: Int,
    val media_type: String? = null,
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
    val birthday: String? = null,
    val biography: String? = null,
    val known_for: List<MediaItemDto>? = null, // 👈 recursive list
)

fun DetailMediaItemDto.toDomain(): DetailMediaItem {
    val resolvedType =
        when {
            // If TMDB gives us a media_type, just use it
            media_type != null ->
                when (media_type) {
                    "movie" -> MediaType.Movies
                    "tv" -> MediaType.Tv
                    "person" -> MediaType.People
                    else -> MediaType.All
                }

            // Otherwise, infer type based on available fields (your if logic)
            !known_for_department.isNullOrEmpty() || !profile_path.isNullOrEmpty() -> MediaType.People
            !title.isNullOrEmpty() -> MediaType.Movies
            !name.isNullOrEmpty() -> MediaType.Tv
            else -> MediaType.All
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

    return DetailMediaItem(
        id = id,
        media_type = resolvedType,
        resolvedTilte = resolvedTitle ?: "",
        resolvedPoster = resolvedPoster ?: "",
        resolvedDate = resolvedDate ?: "",
        original_language = original_language,
        runtime = runtime,
        number_of_seasons = number_of_seasons,
        adult = adult,
        popularity = popularity,
        overview = overview,
        genres = genres,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        vote_average = vote_average,
        vote_count = vote_count,
        title = title,
        original_title = original_title,
        release_date = release_date,
        name = name,
        original_name = original_name,
        first_air_date = first_air_date,
        seasons = seasons,
        origin_country = origin_country,
        known_for_department = known_for_department,
        profile_path = profile_path,
        gender = gender,
        birthday = birthday,
        biography = biography,
        known_for = known_for?.map { it.toDomain() }, // or `.map { it.toDomain() }` if you want to map recursively
    )
}
