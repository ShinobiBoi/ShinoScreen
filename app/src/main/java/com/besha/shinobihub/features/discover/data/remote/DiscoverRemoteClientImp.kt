package com.besha.shinobihub.features.discover.data.remote

import com.besha.shinobihub.appcore.data.mappers.toDataState
import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.data.remote.ApiServices
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.discover.domain.remote.DiscoverRemoteClient
import javax.inject.Inject

class DiscoverRemoteClientImp
    @Inject
    constructor(val api: ApiServices) : DiscoverRemoteClient {
        override suspend fun getMovieDiscover(genreId: String): DataState<MediaResponse> {
            return api.getMovieDiscover(genreId).toDataState()
        }

        override suspend fun getTvDiscover(genreId: String): DataState<MediaResponse> {
            return api.getTvDiscover(genreId).toDataState()
        }
    }
