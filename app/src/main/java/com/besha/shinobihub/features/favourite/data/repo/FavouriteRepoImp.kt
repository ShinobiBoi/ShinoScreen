package com.besha.shinobihub.features.favourite.data.repo

import com.besha.shinobihub.appcore.data.mappers.validate
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.features.favourite.data.remote.FavouriteRemoteClientImp
import com.besha.shinobihub.features.favourite.domain.repo.FavouriteRepo
import javax.inject.Inject

class FavouriteRepoImp
    @Inject
    constructor(private val remote: FavouriteRemoteClientImp) : FavouriteRepo {
        override suspend fun getMovieFavourite(
            accountId: Int,
            sessionId: String,
        ): DataState<List<MediaItem>> {
            return remote.getMovieFavourite(accountId, sessionId).validate()
        }

        override suspend fun getTvFavourite(
            accountId: Int,
            sessionId: String,
        ): DataState<List<MediaItem>> {
            return remote.getTvFavourite(accountId, sessionId).validate()
        }
    }
