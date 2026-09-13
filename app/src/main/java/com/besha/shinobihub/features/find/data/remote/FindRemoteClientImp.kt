package com.besha.shinobihub.features.find.data.remote

import com.besha.shinobihub.appcore.data.mappers.toDataState
import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.data.remote.ApiServices
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.find.domain.remote.FindRemoteClient
import javax.inject.Inject

class FindRemoteClientImp
    @Inject
    constructor(val api: ApiServices) : FindRemoteClient {
        override suspend fun searchMulti(
            query: String,
            page: Int,
        ): DataState<MediaResponse> {
            return try {
                api.searchMulti(query, page).toDataState()
            } catch (t: Throwable) {
                DataState.Error(t)
            }
        }

        override suspend fun searchMovie(
            query: String,
            page: Int,
        ): DataState<MediaResponse> {
            return try {
                api.searchMovie(query, page).toDataState()
            } catch (t: Throwable) {
                DataState.Error(t)
            }
        }

        override suspend fun searchTv(
            query: String,
            page: Int,
        ): DataState<MediaResponse> {
            return try {
                api.searchTv(query, page).toDataState()
            } catch (t: Throwable) {
                DataState.Error(t)
            }
        }

        override suspend fun searchPeople(
            query: String,
            page: Int,
        ): DataState<MediaResponse> {
            return try {
                api.searchPeople(query, page).toDataState()
            } catch (t: Throwable) {
                DataState.Error(t)
            }
        }
    }
