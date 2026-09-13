package com.besha.shinobihub.features.favourite.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.Action

sealed class FavouriteAction() : Action {
    object GetMovieFavourite : FavouriteAction()

    object GetTvFavourite : FavouriteAction()

    data class ChangeMediaType(val mediaType: MediaType) : FavouriteAction()
}
