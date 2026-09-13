package com.besha.shinobihub.features.find.presenation.viewmodel

import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.Result

sealed class FindResult : Result<FindViewState> {
    data class GenreList(val state: CommonViewState<List<Genre>>) : FindResult() {
        override fun reduce(
            defaultState: FindViewState,
            oldState: FindViewState,
        ): FindViewState {
            return oldState.copy(
                genres = state,
            )
        }
    }

    data class Type(val state: MediaType) : FindResult() {
        override fun reduce(
            defaultState: FindViewState,
            oldState: FindViewState,
        ): FindViewState {
            return oldState.copy(
                mediaType = state,
            )
        }
    }

    data class QueryChanged(val state: CommonViewState<String>) : FindResult() {
        override fun reduce(
            defaultState: FindViewState,
            oldState: FindViewState,
        ): FindViewState {
            return oldState.copy(
                query = state,
            )
        }
    }

    data class MediaLoaded(val state: MediaViewState) : FindResult() {
        override fun reduce(
            defaultState: FindViewState,
            oldState: FindViewState,
        ): FindViewState {
            return oldState.copy(
                media = state,
            )
        }
    }
}
