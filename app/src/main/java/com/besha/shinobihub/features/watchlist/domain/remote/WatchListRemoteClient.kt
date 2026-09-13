package com.besha.shinobihub.features.watchlist.domain.remote

import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.domain.DataState

interface WatchListRemoteClient {
    suspend fun getMovieWatchlist(
        accountId: Int,
        sessionId: String,
    ): DataState<MediaResponse>

    suspend fun getTvWatchlist(
        accountId: Int,
        sessionId: String,
    ): DataState<MediaResponse>
}
