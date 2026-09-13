package com.besha.shinobihub.features.discover.presentaion.viewmodel

import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.Result

sealed class DiscoverResult : Result<DiscoverViewState> {
    data class Loading(val state: Boolean) : DiscoverResult() {
        override fun reduce(
            defaultState: DiscoverViewState,
            oldState: DiscoverViewState,
        ): DiscoverViewState {
            return oldState.copy(
                isLoading = state,
            )
        }
    }

    data class GenreList(val state: CommonViewState<List<Genre>>) : DiscoverResult() {
        override fun reduce(
            defaultState: DiscoverViewState,
            oldState: DiscoverViewState,
        ): DiscoverViewState {
            return oldState.copy(
                genres = state,
            )
        }
    }

    data class Type(val state: MediaType) : DiscoverResult() {
        override fun reduce(
            defaultState: DiscoverViewState,
            oldState: DiscoverViewState,
        ): DiscoverViewState {
            return oldState.copy(
                mediaType = state,
            )
        }
    }

    data class MediaLoaded(val state: MediaViewState) : DiscoverResult() {
        override fun reduce(
            defaultState: DiscoverViewState,
            oldState: DiscoverViewState,
        ): DiscoverViewState {
            return oldState.copy(
                media = state,
            )
        }
    }
}
