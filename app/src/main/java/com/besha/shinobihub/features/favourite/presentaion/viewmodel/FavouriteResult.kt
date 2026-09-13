package com.besha.shinobihub.features.favourite.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.Result

sealed class FavouriteResult() : Result<FavouriteViewState> {
    data class MediaLoaded(val media: MediaViewState) : FavouriteResult() {
        override fun reduce(
            defaultState: FavouriteViewState,
            oldState: FavouriteViewState,
        ): FavouriteViewState {
            return oldState.copy(
                media = media,
            )
        }
    }

    data class ChangeMediaType(val mediaType: MediaType) : FavouriteResult() {
        override fun reduce(
            defaultState: FavouriteViewState,
            oldState: FavouriteViewState,
        ): FavouriteViewState {
            return oldState.copy(
                mediaType = mediaType,
            )
        }
    }
}
