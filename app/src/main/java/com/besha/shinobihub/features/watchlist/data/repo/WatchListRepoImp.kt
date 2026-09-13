package com.besha.shinobihub.features.watchlist.data.repo

import com.besha.shinobihub.appcore.data.mappers.validate
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.features.watchlist.domain.remote.WatchListRemoteClient
import com.besha.shinobihub.features.watchlist.domain.repo.WatchListRepo
import javax.inject.Inject

class WatchListRepoImp
    @Inject
    constructor(val remote: WatchListRemoteClient) : WatchListRepo {
        override suspend fun getMovieWatchlist(
            accountId: Int,
            sessionId: String,
        ): DataState<List<MediaItem>> {
            return remote.getMovieWatchlist(accountId, sessionId).validate()
        }

        override suspend fun getTvWatchlist(
            accountId: Int,
            sessionId: String,
        ): DataState<List<MediaItem>> {
            return remote.getTvWatchlist(accountId, sessionId).validate()
        }
    }
