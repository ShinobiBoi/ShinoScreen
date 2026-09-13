package com.besha.shinobihub.features.home.domain.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.features.home.data.model.account.AccountResponse

interface HomeRepo {
    suspend fun getPopularMovies(page: Int): DataState<List<MediaItem>>

    suspend fun getTopRatedMovies(page: Int): DataState<List<MediaItem>>

    suspend fun getUpComingMovies(page: Int): DataState<List<MediaItem>>

    suspend fun getOnTheAirTv(page: Int): DataState<List<MediaItem>>

    suspend fun getPopularTv(page: Int): DataState<List<MediaItem>>

    suspend fun getTopRatedTv(page: Int): DataState<List<MediaItem>>

    suspend fun getAccount(sessionId: String): DataState<AccountResponse>
}
