package com.besha.shinobihub.features.discover.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.Action

sealed class DiscoverAction : Action {
    data class GetDiscoverMovie(val genreId: String) : DiscoverAction()

    data class GetDiscoverTv(val genreId: String) : DiscoverAction()

    data class ChangeMediaType(val type: MediaType) : DiscoverAction()

    object GetGenreList : DiscoverAction()

    data class ToggleGenre(val genreId: Int) : DiscoverAction()

    object ClearFilters : DiscoverAction()
}
