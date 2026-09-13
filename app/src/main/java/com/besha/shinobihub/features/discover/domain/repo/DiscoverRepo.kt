package com.besha.shinobihub.features.discover.domain.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem

interface DiscoverRepo {
    suspend fun getMovieDiscover(genreId: String): DataState<List<MediaItem>>

    suspend fun getTvDiscover(genreId: String): DataState<List<MediaItem>>
}
