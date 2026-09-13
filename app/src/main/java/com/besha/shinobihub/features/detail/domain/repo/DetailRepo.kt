package com.besha.shinobihub.features.detail.domain.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse
import com.besha.shinobihub.features.detail.data.model.mark.MarkRequest
import com.besha.shinobihub.features.detail.data.model.mark.MarkResponse
import com.besha.shinobihub.features.detail.data.model.review.Review
import com.besha.shinobihub.features.detail.data.model.status.AccountStatesResponse
import com.besha.shinobihub.features.detail.data.model.video.VideoItem
import com.besha.shinobihub.features.detail.domain.model.DetailMediaItem

interface DetailRepo {
    suspend fun getDetailMovie(movieId: Int): DataState<DetailMediaItem>

    suspend fun getDetailTv(seriesId: Int): DataState<DetailMediaItem>

    suspend fun getDetailPerson(personId: Int): DataState<DetailMediaItem>

    suspend fun getMovieVideo(movieId: Int): DataState<List<VideoItem>>

    suspend fun getTvVideo(seriesId: Int): DataState<List<VideoItem>>

    suspend fun getMovieCredits(movieId: Int): DataState<CreditsResponse>

    suspend fun getTvCredits(seriesId: Int): DataState<CreditsResponse>

    suspend fun getPeopleCredits(personId: Int): DataState<List<MediaItem>>

    suspend fun getMovieSimilar(movieId: Int): DataState<List<MediaItem>>

    suspend fun getTvSimilar(seriesId: Int): DataState<List<MediaItem>>

    suspend fun getMovieReview(movieId: Int): DataState<List<Review>>

    suspend fun getTvReview(seriesId: Int): DataState<List<Review>>

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
