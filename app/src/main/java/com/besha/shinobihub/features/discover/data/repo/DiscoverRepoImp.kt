package com.besha.shinobihub.features.discover.data.repo

import com.besha.shinobihub.appcore.data.mappers.validate
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.features.discover.domain.remote.DiscoverRemoteClient
import com.besha.shinobihub.features.discover.domain.repo.DiscoverRepo
import javax.inject.Inject

class DiscoverRepoImp
    @Inject
    constructor(val remote: DiscoverRemoteClient) : DiscoverRepo {
        override suspend fun getMovieDiscover(genreId: String): DataState<List<MediaItem>> {
            return remote.getMovieDiscover(genreId).validate()
        }

        override suspend fun getTvDiscover(genreId: String): DataState<List<MediaItem>> {
            return remote.getTvDiscover(genreId).validate()
        }
    }
