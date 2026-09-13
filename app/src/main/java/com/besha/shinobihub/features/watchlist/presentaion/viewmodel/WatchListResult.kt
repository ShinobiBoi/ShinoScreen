package com.besha.shinobihub.features.watchlist.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.Result

sealed class WatchListResult : Result<WatchListViewState> {
    data class MediaLoaded(val media: MediaViewState) : WatchListResult() {
        override fun reduce(
            defaultState: WatchListViewState,
            oldState: WatchListViewState,
        ): WatchListViewState {
            return oldState.copy(
                media = media,
            )
        }
    }

    data class ChangeMediaType(val mediaType: MediaType) : WatchListResult() {
        override fun reduce(
            defaultState: WatchListViewState,
            oldState: WatchListViewState,
        ): WatchListViewState {
            return oldState.copy(
                mediaType = mediaType,
            )
        }
    }
}
