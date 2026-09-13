package com.besha.shinobihub.features.discover.domain.remote

import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.domain.DataState

interface DiscoverRemoteClient {
    suspend fun getMovieDiscover(genreId: String): DataState<MediaResponse>

    suspend fun getTvDiscover(genreId: String): DataState<MediaResponse>
}
