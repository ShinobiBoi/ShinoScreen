package com.besha.shinobihub.features.favourite.data.remote

import com.besha.shinobihub.appcore.data.mappers.toDataState
import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.data.remote.ApiServices
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.favourite.domain.remote.FavouriteRemoteClient
import javax.inject.Inject

class FavouriteRemoteClientImp
    @Inject
    constructor(private val api: ApiServices) : FavouriteRemoteClient {
        override suspend fun getMovieFavourite(
            accountId: Int,
            sessionId: String,
        ): DataState<MediaResponse> {
            return api.getFavoriteMovies(accountId, sessionId).toDataState()
        }

        override suspend fun getTvFavourite(
            accountId: Int,
            sessionId: String,
        ): DataState<MediaResponse> {
            return api.getFavoriteTv(accountId, sessionId).toDataState()
        }
    }
