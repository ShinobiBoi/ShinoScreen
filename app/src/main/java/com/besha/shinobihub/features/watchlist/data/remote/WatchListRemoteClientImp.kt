package com.besha.shinobihub.features.watchlist.data.remote

import com.besha.shinobihub.appcore.data.mappers.toDataState
import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.data.remote.ApiServices
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.watchlist.domain.remote.WatchListRemoteClient
import javax.inject.Inject

class WatchListRemoteClientImp
    @Inject
    constructor(val api: ApiServices) : WatchListRemoteClient {
        override suspend fun getMovieWatchlist(
            accountId: Int,
            sessionId: String,
        ): DataState<MediaResponse> {
            return api.getWatchlistMovies(accountId, sessionId).toDataState()
        }

        override suspend fun getTvWatchlist(
            accountId: Int,
            sessionId: String,
        ): DataState<MediaResponse> {
            return api.getWatchlistTv(accountId, sessionId).toDataState()
        }
    }
