package com.besha.shinobihub.features.find.domain.remote

import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.domain.DataState

interface FindRemoteClient {
    suspend fun searchMulti(
        query: String,
        page: Int,
    ): DataState<MediaResponse>

    suspend fun searchMovie(
        query: String,
        page: Int,
    ): DataState<MediaResponse>

    suspend fun searchTv(
        query: String,
        page: Int,
    ): DataState<MediaResponse>

    suspend fun searchPeople(
        query: String,
        page: Int,
    ): DataState<MediaResponse>
}
