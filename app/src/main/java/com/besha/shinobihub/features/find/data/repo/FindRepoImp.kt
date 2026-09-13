package com.besha.shinobihub.features.find.data.repo

import com.besha.shinobihub.appcore.data.mappers.validate
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.features.find.domain.remote.FindRemoteClient
import com.besha.shinobihub.features.find.domain.repo.FindRepo
import javax.inject.Inject

class FindRepoImp
    @Inject
    constructor(val remote: FindRemoteClient) : FindRepo {
        override suspend fun searchMulti(
            query: String,
            page: Int,
        ): DataState<List<MediaItem>> {
            return remote.searchMulti(query, page).validate()
        }

        override suspend fun searchMovie(
            query: String,
            page: Int,
        ): DataState<List<MediaItem>> {
            return remote.searchMovie(query, page).validate()
        }

        override suspend fun searchTv(
            query: String,
            page: Int,
        ): DataState<List<MediaItem>> {
            return remote.searchTv(query, page).validate()
        }

        override suspend fun searchPeople(
            query: String,
            page: Int,
        ): DataState<List<MediaItem>> {
            return remote.searchPeople(query, page).validate()
        }
    }
