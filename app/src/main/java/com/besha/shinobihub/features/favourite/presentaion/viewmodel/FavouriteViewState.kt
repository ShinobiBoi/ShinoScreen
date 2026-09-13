package com.besha.shinobihub.features.favourite.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.ViewState

data class FavouriteViewState(
    val media: MediaViewState = CommonViewState(),
    val mediaType: MediaType = MediaType.Movies,
) : ViewState
