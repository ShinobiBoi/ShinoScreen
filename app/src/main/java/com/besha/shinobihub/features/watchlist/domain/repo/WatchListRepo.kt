package com.besha.shinobihub.features.watchlist.domain.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem

interface WatchListRepo {
    suspend fun getMovieWatchlist(
        accountId: Int,
        sessionId: String,
    ): DataState<List<MediaItem>>

    suspend fun getTvWatchlist(
        accountId: Int,
        sessionId: String,
    ): DataState<List<MediaItem>>
}
