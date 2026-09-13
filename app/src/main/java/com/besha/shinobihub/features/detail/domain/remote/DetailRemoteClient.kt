package com.besha.shinobihub.features.detail.domain.remote

import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse
import com.besha.shinobihub.features.detail.data.model.detailitem.DetailMediaItemDto
import com.besha.shinobihub.features.detail.data.model.mark.MarkRequest
import com.besha.shinobihub.features.detail.data.model.mark.MarkResponse
import com.besha.shinobihub.features.detail.data.model.review.ReviewResponse
import com.besha.shinobihub.features.detail.data.model.status.AccountStatesResponse
import com.besha.shinobihub.features.detail.data.model.video.VideoResponse

interface DetailRemoteClient {
    // Existing methods ...
    suspend fun getDetailMovie(movieId: Int): DataState<DetailMediaItemDto>

    suspend fun getDetailTv(seriesId: Int): DataState<DetailMediaItemDto>

    suspend fun getDetailPerson(personId: Int): DataState<DetailMediaItemDto>

    suspend fun getMovieVideo(movieId: Int): DataState<VideoResponse>

    suspend fun getTvVideo(seriesId: Int): DataState<VideoResponse>

    suspend fun getMovieCredits(movieId: Int): DataState<CreditsResponse>

    suspend fun getTvCredits(seriesId: Int): DataState<CreditsResponse>

    suspend fun getPeopleCredits(personId: Int): DataState<MediaResponse>

    suspend fun getMovieSimilar(movieId: Int): DataState<MediaResponse>

    suspend fun getTvSimilar(seriesId: Int): DataState<MediaResponse>

    suspend fun getMovieReview(movieId: Int): DataState<ReviewResponse>

    suspend fun getTvReview(seriesId: Int): DataState<ReviewResponse>

    // 🔹 New methods
    suspend fun getMovieAccountState(
        movieId: Int,
        sessionId: String,
    ): DataState<AccountStatesResponse>

    suspend fun getTvAccountState(
        tvId: Int,
        sessionId: String,
    ): DataState<AccountStatesResponse>

    suspend fun toggleFavorite(
        accountId: Int,
        body: MarkRequest,
        sessionId: String,
    ): DataState<MarkResponse>

    suspend fun toggleWatchlist(
        accountId: Int,
        body: MarkRequest,
        sessionId: String,
    ): DataState<MarkResponse>
}
