package com.besha.shinobihub.features.detail.data.remote

import com.besha.shinobihub.appcore.data.mappers.toDataState
import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.appcore.data.remote.ApiServices
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse
import com.besha.shinobihub.features.detail.data.model.detailitem.DetailMediaItemDto
import com.besha.shinobihub.features.detail.data.model.mark.MarkRequest
import com.besha.shinobihub.features.detail.data.model.mark.MarkResponse
import com.besha.shinobihub.features.detail.data.model.review.ReviewResponse
import com.besha.shinobihub.features.detail.data.model.status.AccountStatesResponse
import com.besha.shinobihub.features.detail.data.model.video.VideoResponse
import com.besha.shinobihub.features.detail.domain.remote.DetailRemoteClient
import retrofit2.HttpException
import javax.inject.Inject

class DetailRemoteClientImp
    @Inject
    constructor(val api: ApiServices) : DetailRemoteClient {
        override suspend fun getDetailMovie(movieId: Int): DataState<DetailMediaItemDto> {
            return api.getDetailMovie(movieId).toDataState()
        }

        override suspend fun getDetailTv(seriesId: Int): DataState<DetailMediaItemDto> {
            return api.getDetailTv(seriesId).toDataState()
        }

        override suspend fun getDetailPerson(personId: Int): DataState<DetailMediaItemDto> {
            return api.getDetailPerson(personId).toDataState()
        }

        override suspend fun getMovieVideo(movieId: Int): DataState<VideoResponse> {
            return api.getMovieVideo(movieId).toDataState()
        }

        override suspend fun getTvVideo(seriesId: Int): DataState<VideoResponse> {
            return api.getTvVideo(seriesId).toDataState()
        }

        override suspend fun getMovieCredits(movieId: Int): DataState<CreditsResponse> {
            return api.getMovieCredits(movieId).toDataState()
        }

        override suspend fun getTvCredits(seriesId: Int): DataState<CreditsResponse> {
            return api.getTvCredits(seriesId).toDataState()
        }

        override suspend fun getPeopleCredits(personId: Int): DataState<MediaResponse> {
            return api.getPersonCredits(personId).toDataState()
        }

        override suspend fun getMovieSimilar(movieId: Int): DataState<MediaResponse> {
            return api.getMovieSimilar(movieId).toDataState()
        }

        override suspend fun getTvSimilar(seriesId: Int): DataState<MediaResponse> {
            return api.getTvSimilar(seriesId).toDataState()
        }

        override suspend fun getMovieReview(movieId: Int): DataState<ReviewResponse> {
            return api.getMovieReviews(movieId).toDataState()
        }

        override suspend fun getTvReview(seriesId: Int): DataState<ReviewResponse> {
            return api.getTvReviews(seriesId).toDataState()
        }

        override suspend fun getMovieAccountState(
            movieId: Int,
            sessionId: String,
        ): DataState<AccountStatesResponse> {
            return try {
                val result = api.getMovieAccountStates(movieId, sessionId)
                if (result.isSuccessful) {
                    result.body()?.let {
                        DataState.Success(it)
                    } ?: DataState.Empty
                } else {
                    DataState.Error(HttpException(result))
                }
            } catch (t: Throwable) {
                DataState.Error(t)
            }
        }

        override suspend fun getTvAccountState(
            tvId: Int,
            sessionId: String,
        ): DataState<AccountStatesResponse> {
            return try {
                val result = api.getTvAccountStates(tvId, sessionId)
                if (result.isSuccessful) {
                    result.body()?.let {
                        DataState.Success(it)
                    } ?: DataState.Empty
                } else {
                    DataState.Error(HttpException(result))
                }
            } catch (t: Throwable) {
                DataState.Error(t)
            }
        }

        override suspend fun toggleFavorite(
            accountId: Int,
            body: MarkRequest,
            sessionId: String,
        ): DataState<MarkResponse> {
            return try {
                val result = api.toggleFavorite(accountId, sessionId, body)
                if (result.isSuccessful) {
                    result.body()?.let {
                        DataState.Success(it)
                    } ?: DataState.Empty
                } else {
                    DataState.Error(HttpException(result))
                }
            } catch (t: Throwable) {
                DataState.Error(t)
            }
        }

        override suspend fun toggleWatchlist(
            accountId: Int,
            body: MarkRequest,
            sessionId: String,
        ): DataState<MarkResponse> {
            return try {
                val result = api.toggleWatchlist(accountId, sessionId, body)
                if (result.isSuccessful) {
                    result.body()?.let {
                        DataState.Success(it)
                    } ?: DataState.Empty
                } else {
                    DataState.Error(HttpException(result))
                }
            } catch (t: Throwable) {
                DataState.Error(t)
            }
        }
    }
