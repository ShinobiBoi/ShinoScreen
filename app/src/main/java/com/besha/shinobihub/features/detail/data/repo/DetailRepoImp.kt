package com.besha.shinobihub.features.detail.data.repo

import com.besha.shinobihub.appcore.data.mappers.validate
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse
import com.besha.shinobihub.features.detail.data.model.detailitem.DetailMediaItemDto
import com.besha.shinobihub.features.detail.data.model.detailitem.toDomain
import com.besha.shinobihub.features.detail.data.model.mark.MarkRequest
import com.besha.shinobihub.features.detail.data.model.mark.MarkResponse
import com.besha.shinobihub.features.detail.data.model.review.Review
import com.besha.shinobihub.features.detail.data.model.status.AccountStatesResponse
import com.besha.shinobihub.features.detail.data.model.video.VideoItem
import com.besha.shinobihub.features.detail.domain.model.DetailMediaItem
import com.besha.shinobihub.features.detail.domain.remote.DetailRemoteClient
import com.besha.shinobihub.features.detail.domain.repo.DetailRepo
import javax.inject.Inject

class DetailRepoImp
    @Inject
    constructor(val remote: DetailRemoteClient) : DetailRepo {
        override suspend fun getDetailMovie(movieId: Int): DataState<DetailMediaItem> {
            return resultValidate(remote.getDetailMovie(movieId))
        }

        override suspend fun getDetailTv(seriesId: Int): DataState<DetailMediaItem> {
            return resultValidate(remote.getDetailTv(seriesId))
        }

        override suspend fun getDetailPerson(personId: Int): DataState<DetailMediaItem> {
            return resultValidate(remote.getDetailPerson(personId))
        }

        override suspend fun getMovieVideo(movieId: Int): DataState<List<VideoItem>> {
            val result = remote.getMovieVideo(movieId)
            return when (result) {
                is DataState.Success -> {
                    if (result.data.videoItems.isNullOrEmpty()) {
                        DataState.Empty
                    } else {
                        DataState.Success(
                            result.data.videoItems.filter {
                                it.name?.contains("Official Trailer") ?: false
                            },
                        )
                    }
                }
                is DataState.Error -> {
                    DataState.Error(result.throwable)
                }
                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }

        override suspend fun getTvVideo(seriesId: Int): DataState<List<VideoItem>> {
            return when (val result = remote.getTvVideo(seriesId)) {
                is DataState.Success -> {
                    if (result.data.videoItems.isNullOrEmpty()) {
                        DataState.Empty
                    } else {
                        DataState.Success(
                            result.data.videoItems.filter {
                                it.type == "Trailer"
                            },
                        )
                    }
                }
                is DataState.Error -> {
                    DataState.Error(result.throwable)
                }
                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }

        override suspend fun getMovieCredits(movieId: Int): DataState<CreditsResponse> {
            return when (val result = remote.getMovieCredits(movieId)) {
                is DataState.Success -> {
                    DataState.Success(result.data)
                }
                is DataState.Error -> {
                    DataState.Error(result.throwable)
                }
                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }

        override suspend fun getTvCredits(seriesId: Int): DataState<CreditsResponse> {
            return when (val result = remote.getTvCredits(seriesId)) {
                is DataState.Success -> {
                    DataState.Success(result.data)
                }
                is DataState.Error -> {
                    DataState.Error(result.throwable)
                }
                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }

        override suspend fun getPeopleCredits(personId: Int): DataState<List<MediaItem>> {
            return remote.getPeopleCredits(personId).validate()
        }

        override suspend fun getMovieSimilar(movieId: Int): DataState<List<MediaItem>> {
            return remote.getMovieSimilar(movieId).validate()
        }

        override suspend fun getTvSimilar(seriesId: Int): DataState<List<MediaItem>> {
            return remote.getTvSimilar(seriesId).validate()
        }

        override suspend fun getMovieReview(movieId: Int): DataState<List<Review>> {
            return when (val result = remote.getMovieReview(movieId)) {
                is DataState.Success -> {
                    if (result.data.reviews.isNullOrEmpty()) {
                        DataState.Empty
                    } else {
                        DataState.Success(result.data.reviews)
                    }
                }

                is DataState.Error -> {
                    DataState.Error(result.throwable)
                }

                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }

        override suspend fun getTvReview(seriesId: Int): DataState<List<Review>> {
            return when (val result = remote.getTvReview(seriesId)) {
                is DataState.Success -> {
                    if (result.data.reviews.isNullOrEmpty()) {
                        DataState.Empty
                    } else {
                        DataState.Success(result.data.reviews)
                    }
                }

                is DataState.Error -> {
                    DataState.Error(result.throwable)
                }

                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }

        private fun resultValidate(result: DataState<DetailMediaItemDto>): DataState<DetailMediaItem> {
            return when (result) {
                is DataState.Success -> {
                    DataState.Success(result.data.toDomain())
                }
                is DataState.Error -> {
                    DataState.Error(result.throwable)
                }
                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }

        override suspend fun getMovieAccountState(
            movieId: Int,
            sessionId: String,
        ): DataState<AccountStatesResponse> {
            return when (val result = remote.getMovieAccountState(movieId, sessionId)) {
                is DataState.Success -> DataState.Success(result.data)
                is DataState.Error -> DataState.Error(result.throwable)
                else -> DataState.Error(UnknownError())
            }
        }

        override suspend fun getTvAccountState(
            tvId: Int,
            sessionId: String,
        ): DataState<AccountStatesResponse> {
            return when (val result = remote.getTvAccountState(tvId, sessionId)) {
                is DataState.Success -> DataState.Success(result.data)
                is DataState.Error -> DataState.Error(result.throwable)
                else -> DataState.Error(UnknownError())
            }
        }

        override suspend fun toggleFavorite(
            accountId: Int,
            body: MarkRequest,
            sessionId: String,
        ): DataState<MarkResponse> {
            return when (val result = remote.toggleFavorite(accountId, body, sessionId)) {
                is DataState.Success -> DataState.Success(result.data)
                is DataState.Error -> DataState.Error(result.throwable)
                else -> DataState.Error(UnknownError())
            }
        }

        override suspend fun toggleWatchlist(
            accountId: Int,
            body: MarkRequest,
            sessionId: String,
        ): DataState<MarkResponse> {
            return when (val result = remote.toggleWatchlist(accountId, body, sessionId)) {
                is DataState.Success -> DataState.Success(result.data)
                is DataState.Error -> DataState.Error(result.throwable)
                else -> DataState.Error(UnknownError())
            }
        }
    }
