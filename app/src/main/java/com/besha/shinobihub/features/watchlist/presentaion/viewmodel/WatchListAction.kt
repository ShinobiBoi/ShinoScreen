package com.besha.shinobihub.features.watchlist.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.Action

sealed class WatchListAction : Action {
    object GetMovieWatchList : WatchListAction()

    object GetTvWatchList : WatchListAction()

    data class ChangeMediaType(val mediaType: MediaType) : WatchListAction()
}
