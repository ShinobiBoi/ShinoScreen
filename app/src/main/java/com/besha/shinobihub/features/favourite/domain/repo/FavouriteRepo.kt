package com.besha.shinobihub.features.favourite.domain.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem

interface FavouriteRepo {
    suspend fun getMovieFavourite(
        accountId: Int,
        sessionId: String,
    ): DataState<List<MediaItem>>

    suspend fun getTvFavourite(
        accountId: Int,
        sessionId: String,
    ): DataState<List<MediaItem>>
}
