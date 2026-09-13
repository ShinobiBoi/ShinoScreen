package com.besha.shinobihub.appcore.domain.remote

import com.besha.shinobihub.appcore.data.model.genre.GenreResponse
import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.domain.DataState

interface SharedRemoteClient {
    suspend fun getTrendingAll(page: Int): DataState<MediaResponse>

    suspend fun getTrendingMovies(page: Int): DataState<MediaResponse>

    suspend fun getTrendingTv(page: Int): DataState<MediaResponse>

    suspend fun getTrendingPeople(page: Int): DataState<MediaResponse>

    suspend fun getGenreList(): DataState<GenreResponse>
}
