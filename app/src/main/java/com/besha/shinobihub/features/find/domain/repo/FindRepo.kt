package com.besha.shinobihub.features.find.domain.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem

interface FindRepo {
    suspend fun searchMulti(
        query: String,
        page: Int,
    ): DataState<List<MediaItem>>

    suspend fun searchMovie(
        query: String,
        page: Int,
    ): DataState<List<MediaItem>>

    suspend fun searchTv(
        query: String,
        page: Int,
    ): DataState<List<MediaItem>>

    suspend fun searchPeople(
        query: String,
        page: Int,
    ): DataState<List<MediaItem>>
}
