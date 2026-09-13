package com.besha.shinobihub.features.home.domain.remote

import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.home.data.model.account.AccountResponse

interface HomeRemoteClient {
    suspend fun getPopularMovies(page: Int): DataState<MediaResponse>

    suspend fun getTopRatedMovies(page: Int): DataState<MediaResponse>

    suspend fun getUpComingMovies(page: Int): DataState<MediaResponse>

    suspend fun getOnTheAirTv(page: Int): DataState<MediaResponse>

    suspend fun getPopularTv(page: Int): DataState<MediaResponse>

    suspend fun getTopRatedTv(page: Int): DataState<MediaResponse>

    suspend fun getAccount(sessionId: String): DataState<AccountResponse>
}
