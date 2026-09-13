package com.besha.shinobihub.appcore.domain.repo

import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem

interface SharedRepo {
    suspend fun getTrendingAll(page: Int): DataState<List<MediaItem>>

    suspend fun getTrendingMovies(page: Int): DataState<List<MediaItem>>

    suspend fun getTrendingTv(page: Int): DataState<List<MediaItem>>

    suspend fun getTrendingPeople(page: Int): DataState<List<MediaItem>>

    suspend fun getGenreList(): DataState<List<Genre>>
}
