package com.besha.shinobihub.features.detail.presentaion.viewmodel

import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.Result
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse
import com.besha.shinobihub.features.detail.data.model.review.Review
import com.besha.shinobihub.features.detail.data.model.video.VideoItem
import com.besha.shinobihub.features.detail.domain.constants.DetailTab
import com.besha.shinobihub.features.detail.domain.model.DetailMediaItem

sealed class DetailResults : Result<DetailViewState> {
    data class Loading(val state: Boolean) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(isLoading = state)
        }
    }

    data class SwitchTab(val state: CommonViewState<DetailTab>) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(selectedTab = state)
        }
    }

    data class DetailMediaLoaded(val state: CommonViewState<DetailMediaItem>) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(detailMediaItem = state)
        }
    }

    data class VideoList(val state: CommonViewState<List<VideoItem>>) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(videoList = state)
        }
    }

    data class CreditsLoad(val state: CommonViewState<CreditsResponse>) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(credits = state)
        }
    }

    data class PeopleCreditsLoad(val state: MediaViewState) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(peopleCredits = state)
        }
    }

    data class SimilarLoad(val state: MediaViewState) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(similar = state)
        }
    }

    data class ReviewsLoad(val state: CommonViewState<List<Review>>) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(review = state)
        }
    }

    data class SessionIdLoaded(val state: String?) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(sessionId = state)
        }
    }

    // Account state now directly updates booleans in the view state
    data class AccountStateLoaded(val favorite: Boolean, val watchlist: Boolean) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(
                isFavorite = CommonViewState(data = favorite),
                isWatchlist = CommonViewState(data = watchlist),
            )
        }
    }

    // Toggle now updates only the relevant flag and stores the code
    data class ToggleFavoriteResult(val isFavorite: CommonViewState<Boolean>, val code: Int) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(
                isFavorite = isFavorite,
                toggleCode = code,
            )
        }
    }

    data class ToggleWatchlistResult(val isWatchlist: CommonViewState<Boolean>, val code: Int) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(
                isWatchlist = isWatchlist,
                toggleCode = code,
            )
        }
    }

    data class ToggleCodeResult(val code: Int) : DetailResults() {
        override fun reduce(
            defaultState: DetailViewState,
            oldState: DetailViewState,
        ): DetailViewState {
            return oldState.copy(
                toggleCode = code,
            )
        }
    }
}
