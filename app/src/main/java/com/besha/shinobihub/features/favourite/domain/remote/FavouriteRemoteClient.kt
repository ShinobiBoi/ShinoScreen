package com.besha.shinobihub.features.favourite.domain.remote

import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.domain.DataState

interface FavouriteRemoteClient {
    suspend fun getMovieFavourite(
        accountId: Int,
        sessionId: String,
    ): DataState<MediaResponse>

    suspend fun getTvFavourite(
        accountId: Int,
        sessionId: String,
    ): DataState<MediaResponse>
}
